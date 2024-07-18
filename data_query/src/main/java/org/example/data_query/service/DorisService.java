package org.example.data_query.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DorisService {
    @Value("${doris.jdbc.url}")
    private String jdbcUrl;

    @Value("${doris.jdbc.username}")
    private String username;

    @Value("${doris.jdbc.password}")
    private String password;

    public List<Map<String, Object>> simpleQuery(String table) {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = String.format("SELECT * from %s", table);

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    row.put(columnName, value);
                }
                results.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public List<Map<String, Object>> query(String table, String field, String value, Boolean pattern) {
        //根据指定表和字段进行精确匹配查询或模糊匹配查询
        //boolean as indicator of whether fuzzy inqury should be applied

        List<Map<String, Object>> results = new ArrayList<>();

        String query, str;

        if (pattern) {
            // 精确
            query = String.format("SELECT * FROM %s WHERE %s = '%s'", table, field, value);
        } else {
            // 模糊 fuzzy
            str = String.format("%%%s%%", value); // 将值包装在百分号中以便模糊搜索
            query = String.format("SELECT * FROM %s WHERE %s LIKE '%s'", table, field, str);
        }

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object ans = resultSet.getObject(i);
                    row.put(columnName, ans);
                }
                results.add(row);
            }
            //}

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
    public List<Map<String, Object>> range_query(String table, String field, String start,String end) {
        //根据指定表和字段进行精确匹配查询或模糊匹配查询
        //boolean as indicator of whether fuzzy inqury should be applied

        List<Map<String, Object>> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)){

            DatabaseMetaData meta = connection.getMetaData();
            try(ResultSet rs = meta.getColumns(null, null, table, field)){

                if (rs.next()) {
                    String dataType = rs.getString("TYPE_NAME");
                    if(isRangeQueryableType(dataType)){
                        String query = String.format("select *from %s where %s between ? and ?" , table, field);
                        try(PreparedStatement stmt = connection.prepareStatement(query)){
                            stmt.setString(1, start);
                            stmt.setString(2, end);
                            try(ResultSet resultSet= stmt.executeQuery()){
                                ResultSetMetaData rsMetaData = resultSet.getMetaData();
                                int columnCount = rsMetaData.getColumnCount();
                                while (resultSet.next()) {
                                    // 假设表中有一个 id
                                    Map<String, Object> row = new HashMap<>();
                                    String columnName; Object ans;
                                    for(int i=1; i<= columnCount; ++i) {
                                        columnName = rsMetaData.getColumnName(i);
                                        ans = resultSet.getObject(i);
                                        row.put(columnName, ans);
                                    }
                                    results.add(row);
                                }
                            }
                        }

                    }else{
                        Map<String, Object> message = new HashMap<>();
                        message.put(String.format("该字段数据类型为%s, 不可进行范围查询", dataType), null);
                        results.add(message);
                    }
                }else{
                    Map<String, Object> message = new HashMap<>();
                    message.put("字段不存在于表中", null);
                    results.add(message);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static boolean isRangeQueryableType(String dataType) {
        return dataType.equalsIgnoreCase("DATE") ||
                dataType.equalsIgnoreCase("DATETIME") ||
                dataType.equalsIgnoreCase("TIMESTAMP") ||
                dataType.equalsIgnoreCase("FLOAT") ||
                dataType.equalsIgnoreCase("DOUBLE") ||
                dataType.equalsIgnoreCase("DECIMAL") ||
                dataType.equalsIgnoreCase("INT") ||
                dataType.equalsIgnoreCase("INTEGER") ||
                dataType.equalsIgnoreCase("BIGINT") ||
                dataType.equalsIgnoreCase("SMALLINT") ||
                dataType.equalsIgnoreCase("TINYINT");
    }

    public List<Map<String, Object>> aggregate(String tableName, String field, String aggregationType, String groupBy) throws JsonProcessingException {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql = buildAggregationQuery(tableName, field, aggregationType, groupBy);

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    row.put(columnName, value);
                }
                results.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    private String buildAggregationQuery(String tableName, String field, String aggregationType, String groupByField) {
        String baseQuery;
        switch (aggregationType.toLowerCase()) {
            case "count":
                baseQuery = String.format("SELECT %s, COUNT(*) AS result FROM %s GROUP BY %s", groupByField, tableName, groupByField);
                break;
            case "sum":
                baseQuery = String.format("SELECT %s, SUM(%s) AS result FROM %s GROUP BY %s", groupByField, field, tableName, groupByField);
                break;
            case "avg":
                baseQuery = String.format("SELECT %s, AVG(%s) AS result FROM %s GROUP BY %s", groupByField, field, tableName, groupByField);
                break;
            case "max":
                baseQuery = String.format("SELECT %s, MAX(%s) AS result FROM %s GROUP BY %s", groupByField, field, tableName, groupByField);
                break;
            case "min":
                baseQuery = String.format("SELECT %s, MIN(%s) AS result FROM %s GROUP BY %s", groupByField, field, tableName, groupByField);
                break;
            default:
                throw new IllegalArgumentException("Unsupported aggregation type: " + aggregationType);
        }
        return baseQuery;
    }

    public List<String> getAllTables(String databaseName) {
        List<String> tables = new ArrayList<>();
        String sql = String.format("SELECT TABLE_NAME FROM information_schema.tables WHERE table_schema = '%s'", databaseName);

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                tables.add(resultSet.getString("TABLE_NAME"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }
}
