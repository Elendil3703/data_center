package org.example.datacenter.service;

import org.example.datacenter.exception.BadRequestException;
import org.example.datacenter.mapper.DataBaseMapper;
import org.example.datacenter.mapper.PermissionMapper;
import org.example.datacenter.model.Admin;
import org.example.datacenter.model.CreateTableRequest;
import org.example.datacenter.model.TablePermissionResponse;
import org.example.datacenter.model.TablePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DataBaseService {
    @Autowired
    private DataBaseMapper dataBaseMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    public void updatePermission(String name, String password, String readable, String writable) {
        try {
            // 检查用户是否存在于admin表和MySQL user表中
            if (permissionMapper.countUserInAdmin(name) == 0 || permissionMapper.countUserInMySQL(name) == 0) {
                throw new IllegalArgumentException("User does not exist in admin table or MySQL user table.");
            }

            // 删除admin表中的用户
            permissionMapper.deletePermission(name);

            // 删除MySQL用户
            permissionMapper.dropUser(name);

            // 从readable字符串中解析出ID列表
            List<Integer> readableList = Arrays.stream(readable.split(","))
                    .map(Integer::parseInt)
                    .toList();

            // 获取所有permission为0的ID列表
            List<Integer> idsWithPermissionZero = permissionMapper.getIdsWithPermissionZero();

            // 合并两个ID列表并去重
            List<Integer> combinedReadableList = Stream.concat(readableList.stream(), idsWithPermissionZero.stream())
                    .distinct()
                    .collect(Collectors.toList());

            readable = combinedReadableList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));

            // 从writable字符串中解析出ID列表
            List<Integer> writableList = Arrays.stream(writable.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            // 重新插入用户权限到admin表
            permissionMapper.insertPermission(name, password, readable, writable);

            // 重新创建MySQL用户
            permissionMapper.createUser(name, password);

            // 获取combinedReadableList中的表格名称
            List<String> readableTableNames = permissionMapper.getTableNamesByIds(combinedReadableList);

            // 获取writableList中的表格名称
            List<String> writableTableNames = permissionMapper.getTableNamesByIds(writableList);

            // 为用户授予SELECT权限
            for (String tableName : readableTableNames) {
                permissionMapper.grantSelectPermission(tableName, name);
            }

            // 为用户授予INSERT和UPDATE权限
            for (String tableName : writableTableNames) {
                permissionMapper.grantInsertUpdatePermission(tableName, name);
            }
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

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
        Integer tableId = dataBaseMapper.getTableId(request.getName()); //新表的名称叫tableId
        if(!request.getPermission()){
            //如果是共享表
            List<Admin> admins = permissionMapper.getAdmins();
            for(Admin admin:admins){
                //对于每个叫admin的管理员
                String readable = admin.getReadable() + "," + tableId;
                updatePermission(admin.getName(), admin.getPassword(), readable, admin.getWritable());
            }
        }
    }
    public void deleteTable(String tableName) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist");
        }
        // 删除表格
        dataBaseMapper.deleteTable(tableName);
        boolean permission = dataBaseMapper.getTablePermission(tableName);
        Integer tableId= dataBaseMapper.getTableId(tableName);
        // 删除table_permissions表中的记录
        dataBaseMapper.deleteTablePermission(tableName);
        
    }
    public void addField(String tableName, String columnName, String columnType) {
        String fieldSQL = columnName + " " + columnType;
        dataBaseMapper.addField(tableName, fieldSQL);
    }
    public void removeField(String tableName, String columnName) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist");
        }
        if (dataBaseMapper.columnExists(tableName, columnName) == 0) {
            throw new IllegalArgumentException("Column " + columnName + " does not exist in table " + tableName);
        }
        dataBaseMapper.removeField(tableName, columnName);
    }

    public List<TablePermissions> getAllTables() {
//        List<String> tables = dataBaseMapper.getAllTables();
//        List<TablePermissionResponse> permissionsList = new ArrayList<>();
//        for(String table:tables){
//            boolean permission = dataBaseMapper.getTablePermission(table);
//            permissionsList.add(new TablePermissionResponse(table, permission));
//        }
//        return permissionsList;
        return dataBaseMapper.getAllTablePermission();
    }

    public List<Map<String, Object>> getTableInfo(String tableName) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist");
        }
        return dataBaseMapper.getTableInfo(tableName);
    }
    public List<Map<String, Object>> getTableData(String tableName) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist");
        }
        return dataBaseMapper.getTableData(tableName);
    }

    public void updateTableField(String tableName, String columnName, String columnValue, String primaryKey, String primaryKeyValue) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist");
        }
        if (dataBaseMapper.columnExists(tableName, columnName) == 0) {
            throw new IllegalArgumentException("Column " + columnName + " does not exist in table " + tableName);
        }
        dataBaseMapper.updateTableField(tableName, columnName, columnValue, primaryKey, primaryKeyValue);
    }

    public void updateTableData(String tableName, List<Map<String, Object>> dataToUpdate) {
        if (dataBaseMapper.tableExists(tableName) == 0) {
            throw new IllegalArgumentException("Table " + tableName + " does not exist");
        }

        for (Map<String, Object> row : dataToUpdate) {
            if (row.isEmpty()) {
                throw new IllegalArgumentException("Empty data row provided");
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
                    throw new IllegalArgumentException("Column " + columnName + " does not exist in table " + tableName);
                }

                dataBaseMapper.updateTableField(tableName, columnName, columnValue, primaryKey, primaryKeyValue);
            }
        }
    }

}
