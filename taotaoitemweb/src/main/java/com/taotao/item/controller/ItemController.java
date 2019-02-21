package com.taotao.item.controller;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model){
        TbItem tbItem = tbItemService.findTbItemById(itemId);
        Item item = new Item(tbItem);
        TbItemDesc tbItemDesc = tbItemService.findTbItemDescById(itemId);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",tbItemDesc);
        return "item";
    }
}
