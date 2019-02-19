package com.taotao.content.service.impl;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import javafx.scene.shape.Circle;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getCotentCategoryList(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
        for(TbContentCategory tbContentCategory : list){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            resultList.add(node);
        }

        return resultList;
    }

    @Override
    public TaotaoResult addContentCategory(Long parentId, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);

        contentCategory.setStatus(1);

        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());

        tbContentCategoryMapper.insert(contentCategory);

        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parent.getIsParent()){
            parent.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKey(parent);
        }

        return TaotaoResult.ok(contentCategory);


    }

    //用户递归删除的方法
    private void deleteCategoryRecursion(Long parentId){
        //查询这个父节点的集合
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);

        System.out.println("递归里面的list"+list.toString());
        //删除这个list下面的所有节点
        for(TbContentCategory category : list){
            if(category.getIsParent()){
                deleteCategoryRecursion(category.getParentId());
                tbContentCategoryMapper.deleteByPrimaryKey(category.getId());
            }else{
                tbContentCategoryMapper.deleteByPrimaryKey(category.getId());
            }
        }
        return ;
    }



    @Override
    public TaotaoResult deleteContentCategory(Long id) {
        //查询这个父节点的值
        TbContentCategory parent1 = tbContentCategoryMapper.selectByPrimaryKey(id);


        //删除之前查询以下是不是根节点，如果不是根节点就要递归删除
        TbContentCategory sun = tbContentCategoryMapper.selectByPrimaryKey(id);
        System.out.println(sun.getIsParent()+"zhelizheli");
        if(!sun.getIsParent()){
            //删除id的节点
            tbContentCategoryMapper.deleteByPrimaryKey(id);
        }else{
            deleteCategoryRecursion(id);
            tbContentCategoryMapper.deleteByPrimaryKey(id);
        }

        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parent1.getParentId());
        //查询父节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parent.getId());

        //返回一个list集合
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        System.out.println(list.size()+"  这里是李斯特 ");
        //判断list下面是否还有东西,有的话就是父节点，否则就是根节点
        if(list == null || list.size()==0){
            //改变这个父节点为根节点
            parent.setIsParent(false);
            tbContentCategoryMapper.updateByPrimaryKey(parent);
        }
        return TaotaoResult.ok();
    }
}
