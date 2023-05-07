package com.ruoyi.monitor.mapper;

import java.util.List;
import com.ruoyi.monitor.domain.TbSmtpConf;

/**
 * smtp 服务配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public interface TbSmtpConfMapper 
{
    /**
     * 查询smtp 服务配置
     * 
     * @param id smtp 服务配置主键
     * @return smtp 服务配置
     */
    public TbSmtpConf selectTbSmtpConfById(Long id);

    /**
     * 查询smtp 服务配置列表
     * 
     * @param tbSmtpConf smtp 服务配置
     * @return smtp 服务配置集合
     */
    public List<TbSmtpConf> selectTbSmtpConfList(TbSmtpConf tbSmtpConf);

    /**
     * 新增smtp 服务配置
     * 
     * @param tbSmtpConf smtp 服务配置
     * @return 结果
     */
    public int insertTbSmtpConf(TbSmtpConf tbSmtpConf);

    /**
     * 修改smtp 服务配置
     * 
     * @param tbSmtpConf smtp 服务配置
     * @return 结果
     */
    public int updateTbSmtpConf(TbSmtpConf tbSmtpConf);

    /**
     * 删除smtp 服务配置
     * 
     * @param id smtp 服务配置主键
     * @return 结果
     */
    public int deleteTbSmtpConfById(Long id);

    /**
     * 批量删除smtp 服务配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbSmtpConfByIds(Long[] ids);
}
