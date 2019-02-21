package com.taotao.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.TbItemService;
import com.taotao.common.pojo.EasyUIDataGridResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TbItemServiceImpl implements TbItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${ITEM_INFO}")
    private String ITEM_INFO;

    @Value("${TIEM_EXPIRE}")
    private Integer TIEM_EXPIRE;

    @Override
    public TbItem findTbItemById(Long id) {
        try {
            String json = jedisClient.get(ITEM_INFO+":"+id+":BASE");
            if(StringUtils.isNotBlank(json)){
                TbItem tbItem = JsonUtils.jsonToPojo(json,TbItem.class);
                return tbItem;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TbItem item = tbItemMapper.selectByPrimaryKey(id);
        try {
            jedisClient.set(ITEM_INFO+":"+id+":BASE",JsonUtils.objectToJson(item));
            jedisClient.expire(ITEM_INFO+":"+id+":BASE",TIEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page,rows);

        TbItemExample example = new TbItemExample();
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(tbItems);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult addTbItem(TbItem item, String desc) {
        Long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        tbItemMapper.insert(item);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
        tbItemDescMapper.insert(tbItemDesc);

        return TaotaoResult.ok();
    }

    @Override
    public TbItemDesc findTbItemDescById(Long itemId) {
        try {
            String json = jedisClient.get(ITEM_INFO+":"+itemId+":DESC");
            if(StringUtils.isNotBlank(json)){
                TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json,TbItemDesc.class);
                return tbItemDesc;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        try {
            jedisClient.set(ITEM_INFO+":"+itemId+":DESC",JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire(ITEM_INFO+":"+itemId+":DESC",TIEM_EXPIRE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tbItemDesc;
    }
}
