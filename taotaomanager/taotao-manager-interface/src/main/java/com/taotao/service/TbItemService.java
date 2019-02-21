package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.pojo.TbItemDesc;

public interface TbItemService {
    public TbItem findTbItemById(Long id);

    public EasyUIDataGridResult getItemList(int page,int rows);

    public TaotaoResult addTbItem(TbItem item, String desc);

    public TbItemDesc findTbItemDescById(Long itemId);
}
