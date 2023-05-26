package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SysLicense;
import com.ruoyi.system.mapper.SysLicenseMapper;
import com.ruoyi.system.service.ISysLicenseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLicenseImpl implements ISysLicenseService {

    @Resource
    private SysLicenseMapper licenseMapper;

    @Override
    public SysLicense selectLicenseById(Long licenseId) {
        return licenseMapper.selectLicenseById(20230501L);
    }

    @Override
    public int updateLicense(SysLicense license) {
        return licenseMapper.updateLicense(license);
    }
}
