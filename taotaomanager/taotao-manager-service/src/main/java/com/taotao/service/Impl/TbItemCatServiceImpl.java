package com.taotao.service.Impl;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.service.TbItemCatService;
import com.taotao.common.pojo.EasyUITreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUITreeNode> getTbItemCatList(Long parentId) {

        TbItemCatExample tbItemCatExample = new TbItemCatExample();

        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();

        criteria.andParentIdEqualTo(parentId);

        List<TbItemCat> list = tbItemCatMapper.selectByExample(tbItemCatExample);

        List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();

        for(TbItemCat tbItemCat : list){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            resultList.add(node);
        }
        return resultList;
    }
}
