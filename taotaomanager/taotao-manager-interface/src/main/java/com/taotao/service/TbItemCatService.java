package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;

public interface TbItemCatService {

    List<EasyUITreeNode> getTbItemCatList(Long parentId);
}
