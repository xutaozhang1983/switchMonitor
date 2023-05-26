package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysLicense;

/**
 * 参数配置 数据层
 * 
 * @author ruoyi
 */
public interface SysLicenseMapper {

    SysLicense selectLicenseById(Long licenseId);

    int updateLicense(SysLicense license);

}
