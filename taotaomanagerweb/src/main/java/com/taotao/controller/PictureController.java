package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.managerweb.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    /*@RequestMapping("/pic/upload")
    @ResponseBody
    public Map picUpload(MultipartFile uploadFile){
        try {

            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            System.out.println("###############33"+extName);

            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resources/client.conf");
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
            url = IMAGE_SERVER_URL+url;
            System.out.println(url+"***************************");

            Map result = new HashMap<>();
            result.put("error",0);
            result.put("url",url);

            return result;
        }catch (Exception e){
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error",1);
            result.put("message","图片上船失败");
            return result;
        }
    }*/

    @RequestMapping("/pic/upload")
    @ResponseBody
    public String picUpload(MultipartFile uploadFile){
        try {

            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);

            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resources/client.conf");
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
            url = IMAGE_SERVER_URL+url;

            Map result = new HashMap<>();
            result.put("error",0);
            result.put("url",url);

            return JsonUtils.objectToJson(result);
        }catch (Exception e){
            e.printStackTrace();
            Map result = new HashMap();
            result.put("error",1);
            result.put("message","图片上船失败");
            return JsonUtils.objectToJson(result);
        }
    }
}
