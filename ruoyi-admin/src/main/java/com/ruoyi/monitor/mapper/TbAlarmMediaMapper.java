package com.ruoyi.monitor.mapper;

import java.util.List;
import com.ruoyi.monitor.domain.TbAlarmMedia;

/**
 * smtp 服务配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-13
 */
public interface TbAlarmMediaMapper 
{
    /**
     * 查询smtp 服务配置
     * 
     * @param id smtp 服务配置主键
     * @return smtp 服务配置
     */
    public TbAlarmMedia selectTbAlarmMediaById(Long id);

    /**
     * 查询smtp 服务配置列表
     * 
     * @param tbAlarmMedia smtp 服务配置
     * @return smtp 服务配置集合
     */
    public List<TbAlarmMedia> selectTbAlarmMediaList(TbAlarmMedia tbAlarmMedia);

    /**
     * 新增smtp 服务配置
     * 
     * @param tbAlarmMedia smtp 服务配置
     * @return 结果
     */
    public int insertTbAlarmMedia(TbAlarmMedia tbAlarmMedia);

    /**
     * 修改smtp 服务配置
     * 
     * @param tbAlarmMedia smtp 服务配置
     * @return 结果
     */
    public int updateTbAlarmMedia(TbAlarmMedia tbAlarmMedia);

    /**
     * 删除smtp 服务配置
     * 
     * @param id smtp 服务配置主键
     * @return 结果
     */
    public int deleteTbAlarmMediaById(Long id);

    /**
     * 批量删除smtp 服务配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbAlarmMediaByIds(Long[] ids);

    TbAlarmMedia selectAlarmMediaByType(String mediaType);
}
