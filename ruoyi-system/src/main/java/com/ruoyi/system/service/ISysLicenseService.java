package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysLicense;

/**
 * 岗位信息 服务层
 * 
 * @author ruoyi
 */
public interface ISysLicenseService
{

    public SysLicense selectLicenseById(Long licenseId);


    public int updateLicense(SysLicense license);
}
