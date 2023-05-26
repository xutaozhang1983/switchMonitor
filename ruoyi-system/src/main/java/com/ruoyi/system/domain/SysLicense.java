package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

@Data
public class SysLicense extends BaseEntity {
    private Long licenseId;
    private String license;
}
