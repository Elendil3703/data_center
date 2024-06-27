package org.example.data_query.controller;
import org.example.data_query.service.DorisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doris")
public class DorisController {
    @Autowired
    private DorisService dorisService;

    @GetMapping("/simple_query")
    public List<Map<String, Object>> simpleQuery(@RequestParam String table) throws IOException {
        return dorisService.simpleQuery(table);
    }

    @GetMapping("/query")
    public  List<Map<String, Object>> query(@RequestParam String table, @RequestParam String field, @RequestParam String value, @RequestParam Boolean pattern) throws IOException{
        return dorisService.query(table, field, value, pattern);
    }


    @GetMapping("/range_query")
    public  List<Map<String, Object>> range_query(@RequestParam String table, @RequestParam String field, @RequestParam String start, @RequestParam String end) throws IOException{
        return dorisService.range_query(table, field, start, end);
    }

    @GetMapping("/aggregate")
    public List<Map<String, Object>> aggregate(
            @RequestParam String table,
            @RequestParam String field,
            @RequestParam String aggregationType,
            @RequestParam String groupBy) throws IOException {
        return dorisService.aggregate(table, field, aggregationType, groupBy);
    }

    @GetMapping("/tables")
    public List<String> getAllTables(@RequestParam String databaseName) {
        return dorisService.getAllTables(databaseName);
    }
}
