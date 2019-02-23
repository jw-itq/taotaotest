package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;

public interface UserService {

    TaotaoResult login(String username,String password);
}
