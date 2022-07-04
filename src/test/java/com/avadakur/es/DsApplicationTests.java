package com.avadakur.es;

import com.alibaba.fastjson.JSON;
import com.avadakur.es.dto.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DsApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     * @throws IOException
     */

    @Test
    public void test() throws IOException {
        // 创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("avadakur_index");
        IndicesClient indices = restHighLevelClient.indices();
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);

    }

    /**
     * 检查文档存在， 删除文档
     * @throws IOException
     */

    @Test
    public void test2() throws IOException {
        // 得到索引请求
        GetIndexRequest createIndexRequest = new GetIndexRequest("avadakur_index");
        boolean exists = restHighLevelClient.indices().exists(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(exists);

        //删除索引
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("avadakur_index");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    /**
     * 添加文档
     * @throws IOException
     */
    @Test
    public void test3() throws IOException {
        // 插入数据
        User user = new User();
        user.setUsername("avadakur");
        user.setPassword("11111");


        IndexRequest indexRequest = new IndexRequest("avadakur_index");
        indexRequest.id("1");
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        indexRequest.timeout("1s");

        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);


        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        System.out.println(index.toString());
        System.out.println(index.status());

    }

    /**
     * 获取文档
     */
    @Test
    void test4() throws IOException {

        GetRequest index = new GetRequest("avadakur_index","1");
        index.fetchSourceContext(new FetchSourceContext(false));
        index.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(index, RequestOptions.DEFAULT);
        System.out.println(exists);

    }





}
