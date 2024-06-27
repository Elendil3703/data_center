package org.example.data_query.service;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchService {

    @Autowired
    private RestHighLevelClient client;

    public List<Map<String, Object>> search(String index, String queryContent, boolean exactMatch) throws IOException {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        if (exactMatch) {
            // 精确查询
            sourceBuilder.query(QueryBuilders.queryStringQuery(queryContent));
        } else {
            // 模糊查询
            sourceBuilder.query(QueryBuilders.queryStringQuery("*" + queryContent + "*"));
        }

        // 设置查询源和请求
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        // 处理查询结果
        List<Map<String, Object>> results = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            results.add(hit.getSourceAsMap());
        }

        return results;
    }
}
