package org.example.datacenter.service;

import org.example.datacenter.exception.BadRequestException;
import org.example.datacenter.mapper.DataBaseMapper;
import org.example.datacenter.model.CreateTableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataBaseService {
    @Autowired
    private DataBaseMapper dataBaseMapper;

    public void createTable(CreateTableRequest request) {
        // 生成创建表的SQL语句
        StringBuilder createSQL = new StringBuilder("CREATE TABLE ");
        createSQL.append(request.getName()).append(" (");

        String fieldsSQL = request.getFields().stream()
                .map(field -> field.getName() + " " + field.getType() + (field.isPrimaryKey() ? " PRIMARY KEY" : ""))
                .collect(Collectors.joining(", "));

        createSQL.append(fieldsSQL).append(")");

        dataBaseMapper.createTable(request.getName(), createSQL.toString());
        // 在table_permissions表中插入对应的记录
        dataBaseMapper.insertTablePermission(request.getName(), request.getPermission());

    }
    public void deleteTable(String tableName) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new BadRequestException("Table " + tableName + " does not exist");
        }
        // 删除表格
        dataBaseMapper.deleteTable(tableName);
        // 删除table_permissions表中的记录
        dataBaseMapper.deleteTablePermission(tableName);
    }
    public void addField(String tableName, String columnName, String columnType) {
        String fieldSQL = columnName + " " + columnType;
        dataBaseMapper.addField(tableName, fieldSQL);
    }
    public void removeField(String tableName, String columnName) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new BadRequestException("Table " + tableName + " does not exist");
        }
        if (dataBaseMapper.columnExists(tableName, columnName) == 0) {
            throw new BadRequestException("Column " + columnName + " does not exist in table " + tableName);
        }
        dataBaseMapper.removeField(tableName, columnName);
    }

    public List<String> getAllTables() {
        return dataBaseMapper.getAllTables();
    }

    public List<Map<String, Object>> getTableInfo(String tableName) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new BadRequestException("Table " + tableName + " does not exist");
        }
        return dataBaseMapper.getTableInfo(tableName);
    }
    public List<Map<String, Object>> getTableData(String tableName) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new BadRequestException("Table " + tableName + " does not exist");
        }
        return dataBaseMapper.getTableData(tableName);
    }
    public void updateTableField(String tableName, String columnName, String columnValue, String primaryKey, String primaryKeyValue) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new BadRequestException("Table " + tableName + " does not exist");
        }
        if (dataBaseMapper.columnExists(tableName, columnName) == 0) {
            throw new BadRequestException("Column " + columnName + " does not exist in table " + tableName);
        }
        dataBaseMapper.updateTableField(tableName, columnName, columnValue, primaryKey, primaryKeyValue);
    }

    public void updateTableData(String tableName, List<Map<String, Object>> dataToUpdate) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new BadRequestException("Table " + tableName + " does not exist");
        }

        for (Map<String, Object> row : dataToUpdate) {
            if (row.isEmpty()) {
                throw new BadRequestException("Empty data row provided");
            }

            // 假设每行的第一个键值对是主键
            Map.Entry<String, Object> primaryKeyEntry = row.entrySet().iterator().next();
            String primaryKey = primaryKeyEntry.getKey();
            String primaryKeyValue = primaryKeyEntry.getValue().toString();

            // 从更新数据中移除主键
            row.remove(primaryKey);

            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String columnName = entry.getKey();
                String columnValue = entry.getValue().toString();

                if (dataBaseMapper.columnExists(tableName, columnName) == 0) {
                    throw new BadRequestException("Column " + columnName + " does not exist in table " + tableName);
                }

                dataBaseMapper.updateTableField(tableName, columnName, columnValue, primaryKey, primaryKeyValue);
            }
        }
    }

}
