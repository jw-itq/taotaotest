package com.taotao.test;

import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDFSTest {

    @Test
    public void uploadFile() throws Exception{
        ClientGlobal.init("/home/IDEA/taotaotest/taotaomanagerweb/src/main/resources/resources/client.conf");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        String[] strings = storageClient.upload_file("/home/shiwanming/视频/【阶段17】taotao商城综合项目/参考资料/广告图片/21.jpg","jpg",null);
        for(String s : strings){
            System.out.println(s);
        }
    }
}
