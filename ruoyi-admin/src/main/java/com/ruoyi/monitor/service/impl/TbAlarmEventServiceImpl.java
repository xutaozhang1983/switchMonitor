package com.ruoyi.monitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.monitor.mapper.TbAlarmEventMapper;
import com.ruoyi.monitor.domain.TbAlarmEvent;
import com.ruoyi.monitor.service.ITbAlarmEventService;

/**
 * 事件信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-09
 */
@Service
public class TbAlarmEventServiceImpl implements ITbAlarmEventService 
{
    @Autowired
    private TbAlarmEventMapper tbAlarmEventMapper;

    /**
     * 查询事件信息
     * 
     * @param id 事件信息主键
     * @return 事件信息
     */
    @Override
    public TbAlarmEvent selectTbAlarmEventById(Long id)
    {
        return tbAlarmEventMapper.selectTbAlarmEventById(id);
    }

    /**
     * 查询事件信息列表
     * 
     * @param tbAlarmEvent 事件信息
     * @return 事件信息
     */
    @Override
    public List<TbAlarmEvent> selectTbAlarmEventList(TbAlarmEvent tbAlarmEvent)
    {
        return tbAlarmEventMapper.selectTbAlarmEventList(tbAlarmEvent);
    }

    /**
     * 新增事件信息
     * 
     * @param tbAlarmEvent 事件信息
     * @return 结果
     */
    @Override
    public int insertTbAlarmEvent(TbAlarmEvent tbAlarmEvent)
    {
        tbAlarmEvent.setCreateTime(DateUtils.getNowDate());
        return tbAlarmEventMapper.insertTbAlarmEvent(tbAlarmEvent);
    }

    /**
     * 修改事件信息
     * 
     * @param tbAlarmEvent 事件信息
     * @return 结果
     */
    @Override
    public int updateTbAlarmEvent(TbAlarmEvent tbAlarmEvent)
    {
        return tbAlarmEventMapper.updateTbAlarmEvent(tbAlarmEvent);
    }

    /**
     * 批量删除事件信息
     * 
     * @param ids 需要删除的事件信息主键
     * @return 结果
     */
    @Override
    public int deleteTbAlarmEventByIds(Long[] ids)
    {
        return tbAlarmEventMapper.deleteTbAlarmEventByIds(ids);
    }

    /**
     * 删除事件信息信息
     * 
     * @param id 事件信息主键
     * @return 结果
     */
    @Override
    public int deleteTbAlarmEventById(Long id)
    {
        return tbAlarmEventMapper.deleteTbAlarmEventById(id);
    }
}
