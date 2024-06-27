package org.example.datacenter.controller;
import org.example.datacenter.model.*;
import org.example.datacenter.service.DataBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/modify_database")
public class DataBaseController {
    @Autowired
    private DataBaseService dataBaseService;

    @PostMapping("/create_table")
    public void createTable(@RequestBody CreateTableRequest request) {
        dataBaseService.createTable(request);
    }
    @PostMapping("/delete_table")
    public void deleteTable(@RequestParam String name) {
        dataBaseService.deleteTable(name);
    }
    @PostMapping("/add_field")
    public void addField(@RequestParam String tableName, @RequestParam String columnName, @RequestParam String columnType) {
        dataBaseService.addField(tableName, columnName, columnType);
    }
    @PostMapping("/remove_field")
    public void removeField(@RequestParam String tableName, @RequestParam String columnName) {
        dataBaseService.removeField(tableName, columnName);
    }
    @GetMapping("/tables")
    public List<TablePermissions> getAllTables() {
        return dataBaseService.getAllTables();
    }

    @GetMapping("/table_info")
    public List<Map<String, Object>> getTableInfo(@RequestParam String tableName) {
        return dataBaseService.getTableInfo(tableName);
    }
    @GetMapping("/table_data")
    public List<Map<String, Object>> getTableData(@RequestParam String tableName) {
        return dataBaseService.getTableData(tableName);
    }

    @PostMapping("/filter")
    public List<Map<String, Object>> filterTableData(@RequestBody FilterDataWrapper wrapper) {
        FilterDataRequest params = wrapper.getParams();
        return dataBaseService.filterTableData(params.getTableName(), params.getColumnName(), params.getMinValue(), params.getMaxValue());
    }

    @PostMapping("/insert")
    public void insertTableData(@RequestBody Map<String, Object> requestData){
        String tableName = requestData.get("name").toString();
        Map<String, Object> dataToInsert = (Map<String, Object>) requestData.get("row");
        dataBaseService.insertTableData(tableName, dataToInsert);
    }

    @PostMapping("/update_field")
    public void updateTableField(@RequestParam String tableName, @RequestParam String columnName, @RequestParam String columnValue, @RequestParam String primaryKey, @RequestParam String primaryKeyValue) {
        dataBaseService.updateTableField(tableName, columnName, columnValue, primaryKey, primaryKeyValue);
    }

    @PostMapping("/update_data")//改动数据（新）
    public void updateTableData(@RequestBody Map<String, Object> requestData) {
            String tableName = requestData.get("name").toString();
            List<Map<String, Object>> dataToUpdate = (List<Map<String, Object>>) requestData.get("fields");
            dataBaseService.updateTableData(tableName, dataToUpdate);
    }

    @PostMapping("change_state")
    public void changeTableState(@RequestBody Map<String, Object> requestData) {
        String tableName = requestData.get("tableName").toString();
        Integer permission = (Integer) requestData.get("permission");
        dataBaseService.changeTableState(tableName, permission);
    }

}
