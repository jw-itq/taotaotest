package com.taotao.search.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.pojo.TbItemSearch;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemMapper searchItemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public TaotaoResult importItemsToIndex() {

       try {
           List<TbItemSearch> list = searchItemMapper.getItemList();

           for(TbItemSearch itemSearch : list){
               SolrInputDocument document = new SolrInputDocument();

               document.addField("id",itemSearch.getId());
               document.addField("item_titile",itemSearch.getTitle());
               document.addField("item_sell_point",itemSearch.getSell_point());
               document.addField("item_price",itemSearch.getPrice());
               document.addField("item_image",itemSearch.getImage());
               document.addField("item_category_name",itemSearch.getCategory_name());
               document.addField("item_desc",itemSearch.getItem_desc());

               solrServer.add(document);
           }
       }catch (Exception e){
           e.printStackTrace();
           return TaotaoResult.build(500,"数据导入失败，出问题");
       }

       return TaotaoResult.ok();
    }
}
