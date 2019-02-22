package com.taotao.search.mapper;

import com.taotao.common.pojo.TbItemSearch;

import java.util.List;

public interface SearchItemMapper {
    List<TbItemSearch> getItemList();

    TbItemSearch getItemById(Long itemId);
}
