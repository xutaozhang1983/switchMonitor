package com.ruoyi.monitor.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * smtp 服务配置对象 tb_alarm_media
 * 
 * @author ruoyi
 * @date 2023-05-13
 */

@Data
@Getter
@Setter
public class TbAlarmMedia extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 报警媒体类型 */
    @Excel(name = "报警媒体类型")
    private String mediaType;

    /** 名称 */
    @Excel(name = "名称")
    private String mediaName;

    /** smtp主机 */
    @Excel(name = "smtp主机")
    private String host;

    /** sender */
    @Excel(name = "sender")
    private String sender;

    /** 端口 */
    @Excel(name = "端口")
    private Long port;

    private String enableSsl;

    private String enableTls;

    /** 用户名 */
    @Excel(name = "用户名")
    private String username;

    /** 密码 */
    @Excel(name = "密码")
    private String passwd;

    /** Key */
    @Excel(name = "Key")
    private String accessKey;

    /** 密钥 */
    @Excel(name = "密钥")
    private String accessSecret;

    /** regionId */
    @Excel(name = "regionId")
    private String regionId;

    /** 状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private String status;


}
