package com.taotao.portal.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AD1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_HEIGHTB}")
    private Integer AD1_HEIGHTB;
    @Value("${AD1_WIDTHB}")
    private Integer AD1_WIDTHB;

    @Autowired
    private ContentService contentService;
    @RequestMapping("/index")
    public String indexShow(Model model){
        List<TbContent> list = contentService.getContentByCid(AD1_CATEGORY_ID);
        List<AD1Node> ad1Nodes = new ArrayList<>();
        for(TbContent tbContent : list){
            AD1Node node = new AD1Node();
            node.setAlt(tbContent.getTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHTB);
            node.setHref(tbContent.getUrl());
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTHB);
            ad1Nodes.add(node);

        }

        String ad1Json = JsonUtils.objectToJson(ad1Nodes);
        model.addAttribute("ad1",ad1Json);
        return "index";
    }
}
