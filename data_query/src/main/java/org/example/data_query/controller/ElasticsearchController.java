package org.example.data_query.controller;

import org.example.data_query.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/es")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @GetMapping("/query")
    public List<Map<String, Object>> search(
            @RequestParam String index,
            @RequestParam String value,
            @RequestParam(required = false, defaultValue = "false") boolean pattern) throws IOException {
        return elasticsearchService.search(index, value, pattern);
    }
}
