package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${USER_SESSION}")
    private String USER_SESSION;

    @Value("${SESSIOIN_EXPIRE}")
    private Integer SESSIOIN_EXPIRE;
    @Override
    public TaotaoResult login(String username, String password) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = tbUserMapper.selectByExample(example);
        if(list == null || list.size() == 0){
            return TaotaoResult.build(400,"用户名或者密码不正确");
        }
        TbUser user = list.get(0);
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            return TaotaoResult.build(400,"用户名或者密码不正确");
        }
        String token = UUID.randomUUID().toString();
        user.setPassword(null);
        jedisClient.set(USER_SESSION+":"+token, JsonUtils.objectToJson(user));
        jedisClient.expire(USER_SESSION+":"+token,SESSIOIN_EXPIRE);
        return TaotaoResult.ok(token);
    }
}
