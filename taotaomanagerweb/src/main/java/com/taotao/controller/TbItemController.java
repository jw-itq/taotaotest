package com.taotao.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.TbItemService;
import com.taotao.common.pojo.EasyUIDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TbItemController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem findTbItemById(@PathVariable Long itemId){
        TbItem tbItem = tbItemService.findTbItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemsList(Integer page,Integer rows){
        EasyUIDataGridResult result = tbItemService.getItemList(page,rows);
        return result;
    }

    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult addTbItem(TbItem item,String desc){
        TaotaoResult result = tbItemService.addTbItem(item,desc);
        return result;
    }
}
