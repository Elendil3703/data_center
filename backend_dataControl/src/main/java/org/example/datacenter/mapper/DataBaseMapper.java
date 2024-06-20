package org.example.datacenter.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.datacenter.model.TablePermissions;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataBaseMapper {
    void createTable(@Param("tableName") String tableName, @Param("createSQL") String createSQL);
    void insertTablePermission(@Param("tableName") String tableName, @Param("permission") boolean permission);
    void deleteTable(@Param("tableName") String tableName);
    void deleteTablePermission(@Param("tableName") String tableName);
    void addField(@Param("tableName") String tableName, @Param("fieldSQL") String fieldSQL);
    void removeField(@Param("tableName") String tableName, @Param("columnName") String columnName);
    // 检查表格是否存在
    Integer tableExists(@Param("tableName") String tableName);
    // 检查字段是否存在
    Integer columnExists(@Param("tableName") String tableName, @Param("columnName") String columnName);
    // 查询所有表格
    List<String> getAllTables();
    // 查询表格的所有信息
    List<Map<String, Object>> getTableInfo(@Param("tableName") String tableName);
    // 查询表格中的所有数据
    List<Map<String, Object>> getTableData(@Param("tableName") String tableName);

    List<Map<String, Object>> filterTableData(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("minValue") String minValue, @Param("maxValue") String maxValue);
    void insertData(@Param("tableName") String tableName, @Param("data") Map<String, Object> data);
    void updateTableField(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("columnValue") String columnValue, @Param("primaryKey") String primaryKey, @Param("primaryKeyValue") String primaryKeyValue);
    boolean getTablePermission(@Param("tableName") String tableName);
    List<TablePermissions> getAllTablePermission();
    Integer getTableId(@Param("tableName") String tableName); //从permissions表里面的到表格的id
    void changeTableState(@Param("tableName") String tableName, @Param("permission") boolean permission);
}

