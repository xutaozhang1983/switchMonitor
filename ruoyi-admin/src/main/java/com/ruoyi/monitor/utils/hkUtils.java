package com.ruoyi.monitor.utils;

import com.alibaba.fastjson2.JSON;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class hkUtils {

    private static final String ARTEMIS_PATH = "/artemis";

    public static String hkRequest(String apiPath,Map<String,String> paramMap){
        ArtemisConfig config = new ArtemisConfig();
        config.setHost("127.0.0.1"); // 代理API网关nginx服务器ip端口
        config.setAppKey("20469790");  // 秘钥appkey
        config.setAppSecret("lofnD6DbnBllHmk5YOyx");// 秘钥appSecret
        String body = JSON.toJSON(paramMap).toString();
        String finalApiPath = ARTEMIS_PATH + apiPath;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", finalApiPath);
            }
        };
        String res = "";
        try {
            res = ArtemisHttpUtil.doPostStringArtemis(config,path, body, null, null, "application/json");
            log.info(res);
        }catch (Exception e) {
            log.error(e);
        }
        return res;
    }
}
