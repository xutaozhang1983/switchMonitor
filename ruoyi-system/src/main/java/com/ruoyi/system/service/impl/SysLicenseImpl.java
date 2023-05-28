package com.ruoyi.system.service.impl;

import cn.hutool.core.net.NetUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.des.DesUtil;
import com.ruoyi.system.domain.SysLicense;
import com.ruoyi.system.mapper.SysLicenseMapper;
import com.ruoyi.system.service.ISysLicenseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLicenseImpl implements ISysLicenseService {

    @Resource
    private SysLicenseMapper licenseMapper;

    private String key = "";
    @Override
    public SysLicense selectLicenseById(Long licenseId) {
        return licenseMapper.selectLicenseById(20230501L);
    }

    @Override
    public int updateLicense(SysLicense license) {
        return licenseMapper.updateLicense(license);
    }


    public String getKey(){
        String mac =  NetUtil.getLocalMacAddress();
        if(StringUtils.isNotNull(mac)){
            return DesUtil.generateMD5(mac);
        }
        return DesUtil.generateMD5(NetUtil.getMacAddress(NetUtil.getLocalhost()));
    }

}
