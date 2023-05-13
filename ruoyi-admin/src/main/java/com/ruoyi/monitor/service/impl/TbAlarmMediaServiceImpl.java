package com.ruoyi.monitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.monitor.mapper.TbAlarmMediaMapper;
import com.ruoyi.monitor.domain.TbAlarmMedia;
import com.ruoyi.monitor.service.ITbAlarmMediaService;

/**
 * smtp 服务配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-13
 */
@Service
public class TbAlarmMediaServiceImpl implements ITbAlarmMediaService 
{
    @Autowired
    private TbAlarmMediaMapper tbAlarmMediaMapper;

    /**
     * 查询smtp 服务配置
     * 
     * @param id smtp 服务配置主键
     * @return smtp 服务配置
     */
    @Override
    public TbAlarmMedia selectTbAlarmMediaById(Long id)
    {
        return tbAlarmMediaMapper.selectTbAlarmMediaById(id);
    }

    /**
     * 查询smtp 服务配置列表
     * 
     * @param tbAlarmMedia smtp 服务配置
     * @return smtp 服务配置
     */
    @Override
    public List<TbAlarmMedia> selectTbAlarmMediaList(TbAlarmMedia tbAlarmMedia)
    {
        return tbAlarmMediaMapper.selectTbAlarmMediaList(tbAlarmMedia);
    }

    /**
     * 新增smtp 服务配置
     * 
     * @param tbAlarmMedia smtp 服务配置
     * @return 结果
     */
    @Override
    public int insertTbAlarmMedia(TbAlarmMedia tbAlarmMedia)
    {
        tbAlarmMedia.setCreateTime(DateUtils.getNowDate());
        return tbAlarmMediaMapper.insertTbAlarmMedia(tbAlarmMedia);
    }

    /**
     * 修改smtp 服务配置
     * 
     * @param tbAlarmMedia smtp 服务配置
     * @return 结果
     */
    @Override
    public int updateTbAlarmMedia(TbAlarmMedia tbAlarmMedia)
    {
        tbAlarmMedia.setUpdateTime(DateUtils.getNowDate());
        return tbAlarmMediaMapper.updateTbAlarmMedia(tbAlarmMedia);
    }

    /**
     * 批量删除smtp 服务配置
     * 
     * @param ids 需要删除的smtp 服务配置主键
     * @return 结果
     */
    @Override
    public int deleteTbAlarmMediaByIds(Long[] ids)
    {
        return tbAlarmMediaMapper.deleteTbAlarmMediaByIds(ids);
    }

    /**
     * 删除smtp 服务配置信息
     * 
     * @param id smtp 服务配置主键
     * @return 结果
     */
    @Override
    public int deleteTbAlarmMediaById(Long id)
    {
        return tbAlarmMediaMapper.deleteTbAlarmMediaById(id);
    }

    @Override
    public TbAlarmMedia getAlarmMedia(String mediaType) {
        return tbAlarmMediaMapper.selectAlarmMediaByType(mediaType);
    }
}
