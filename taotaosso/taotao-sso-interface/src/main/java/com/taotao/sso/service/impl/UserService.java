package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {

    TaotaoResult login(String username,String password);

    TaotaoResult checkData(String data,int type);

    TaotaoResult register(TbUser user);

}
