package com.taotao.cart.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.TbItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Value("${CART_KEY}")
    private String CART_KEY;

    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("cart/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId,
                              @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request,
                              HttpServletResponse response){
        List<TbItem> carItemList = getCartItemList(request);
        boolean flag = false;
        for(TbItem tbItem : carItemList){
            if(tbItem.getId() == itemId.longValue()){
                tbItem.setNum(tbItem.getNum()+num);
                flag = true;
                break;
            }
        }

        if(!flag){
            TbItem tbItem = tbItemService.findTbItemById(itemId);
            tbItem.setNum(num);
            String image = tbItem.getImage();
            if(StringUtils.isNotBlank(image)){
                String[] images = image.split(",");
                tbItem.setImage(images[0]);
            }
            carItemList.add(tbItem);
        }

        CookieUtils.setCookie(request,response,CART_KEY,JsonUtils.objectToJson(carItemList),
                CART_EXPIER,true);
        return "cartSuccess";
    }

    private List<TbItem> getCartItemList(HttpServletRequest request){
        String json = CookieUtils.getCookieValue(request,CART_KEY, true);
        if(StringUtils.isBlank(json)){
            return new ArrayList<>();
        }
        List<TbItem> list = JsonUtils.jsonToList(json,TbItem.class);
        return list;
    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request){
        List<TbItem> cartItemList = getCartItemList(request);
        request.setAttribute("cartList",cartItemList);
        return "cart";
    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult updateItemNum(@PathVariable Long itemId,@PathVariable Integer num,
                                      HttpServletRequest request,HttpServletResponse response){
        List<TbItem> cartList = getCartItemList(request);
        for(TbItem tbItem : cartList){
            if(tbItem.getId() == itemId.longValue()){
                tbItem.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request,response,CART_KEY,JsonUtils.objectToJson(cartList),
                CART_EXPIER,true);
        return TaotaoResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,
                                 HttpServletResponse response){
        List<TbItem> cartItemList = getCartItemList(request);
        for(TbItem tbItem : cartItemList){
            if(tbItem.getId() == itemId.longValue()){
                cartItemList.remove(tbItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,CART_KEY,JsonUtils.objectToJson(cartItemList),
                CART_EXPIER,true);

        return "redirect:/cart/cart.html";
    }
}
