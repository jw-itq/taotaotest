package com.taotao.search.dao;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TbItemSearch;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws Exception{
        QueryResponse response = solrServer.query(query);

        SolrDocumentList solrDocumentList = response.getResults();

        long numFound = solrDocumentList.getNumFound();
        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);
        List<TbItemSearch> itemList = new ArrayList<>();

        for(SolrDocument solrDocument : solrDocumentList){
            TbItemSearch item = new TbItemSearch();
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setId((String) solrDocument.get("id"));

            String image = (String) solrDocument.get("item_image");
            if(StringUtils.isNotBlank(image)){
                image = image.split(",")[0];
            }
            item.setImage(image);

            item.setPrice((Long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));

            Map<String,Map<String,List<String>>> hightlighting = response.getHighlighting();
            List<String> list = hightlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if(list !=null && list.size()>0){
                title = list.get(0);
            }else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            itemList.add(item);
        }

        result.setItemList(itemList);

        return result;
    }
}
