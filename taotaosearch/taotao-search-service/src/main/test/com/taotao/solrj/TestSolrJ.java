package com.taotao.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class TestSolrJ {

    @Test
    public void testAddDocument() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.122.1:8983/solr/core_test");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test_3");
        document.addField("item_title","测试商品");
        document.addField("item_price",10042);
        solrServer.add(document);
        solrServer.commit();
    }

    @Test
    public void deleteDocumentById() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.122.1:8983/solr/core_test");
        solrServer.deleteById("123");
        solrServer.commit();
    }

    @Test
    public void deleteDocumentByTitle() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://192.168.122.1:8983/solr/core_test");
        solrServer.deleteByQuery("item_title:测试商品");
        solrServer.commit();
    }

    @Test
    public void searchDocument() throws SolrServerException {
        //创建一个SolrServer对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.122.1:8983/solr/core_test");
        //创建一个SolrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件、过滤条件、分页条件、排序条件、高亮
        //query.set("q", "*:*");
        query.setQuery("双卡双待");
        //分页条件
        query.setStart(0);
        query.setRows(10);
        //设置默认搜索域
        query.set("df", "item_keywords");
        //设置高亮
        query.setHighlight(true);
        //高亮显示的域
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<div>");
        query.setHighlightSimplePost("</div>");
        //执行查询，得到一个Response对象
        QueryResponse response = solrServer.query(query);
        //取查询结果
        SolrDocumentList solrDocumentList = response.getResults();
        //取查询结果总记录数
        System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = "";
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            System.out.println(itemTitle);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
            System.out.println("=============================================");
        }
    }
}
