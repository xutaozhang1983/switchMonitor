package com.ruoyi.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.ruoyi.monitor.domain.TbDevice;
import lombok.extern.log4j.Log4j2;
import org.snmp4j.Target;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class hkUtils {

    TbDevice tbDevice;
    private static ArtemisConfig config;

    public hkUtils(TbDevice tbDevice){
        this.tbDevice = tbDevice;
    }
    public  void init(){
        this.config.setHost(tbDevice.getDeviceIp()); // 代理API网关nginx服务器ip端口
        this.config.setAppKey(tbDevice.getAppKey());  // 秘钥appkey
        this.config.setAppSecret(tbDevice.getAppSecret());// 秘钥appSecret
    }

    private static final String ARTEMIS_PATH = "/artemis";

    public static String hkRequest(String apiPath,Map<String,String> paramMap){

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

            JSONObject jsonObject = new JSONObject();

        }catch (Exception e) {
            log.error(e);
        }
        return res;
    }
}
