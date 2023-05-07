package com.ruoyi.monitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.monitor.mapper.TbEventsMapper;
import com.ruoyi.monitor.domain.TbEvents;
import com.ruoyi.monitor.service.ITbEventsService;

/**
 * 事件信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Service
public class TbEventsServiceImpl implements ITbEventsService 
{
    @Autowired
    private TbEventsMapper tbEventsMapper;

    /**
     * 查询事件信息
     * 
     * @param id 事件信息主键
     * @return 事件信息
     */
    @Override
    public TbEvents selectTbEventsById(Long id)
    {
        return tbEventsMapper.selectTbEventsById(id);
    }

    /**
     * 查询事件信息列表
     * 
     * @param tbEvents 事件信息
     * @return 事件信息
     */
    @Override
    public List<TbEvents> selectTbEventsList(TbEvents tbEvents)
    {
        return tbEventsMapper.selectTbEventsList(tbEvents);
    }

    /**
     * 新增事件信息
     * 
     * @param tbEvents 事件信息
     * @return 结果
     */
    @Override
    public int insertTbEvents(TbEvents tbEvents)
    {
        tbEvents.setCreateTime(DateUtils.getNowDate());
        return tbEventsMapper.insertTbEvents(tbEvents);
    }

    /**
     * 修改事件信息
     * 
     * @param tbEvents 事件信息
     * @return 结果
     */
    @Override
    public int updateTbEvents(TbEvents tbEvents)
    {
        tbEvents.setUpdateTime(DateUtils.getNowDate());
        return tbEventsMapper.updateTbEvents(tbEvents);
    }

    /**
     * 批量删除事件信息
     * 
     * @param ids 需要删除的事件信息主键
     * @return 结果
     */
    @Override
    public int deleteTbEventsByIds(Long[] ids)
    {
        return tbEventsMapper.deleteTbEventsByIds(ids);
    }

    /**
     * 删除事件信息信息
     * 
     * @param id 事件信息主键
     * @return 结果
     */
    @Override
    public int deleteTbEventsById(Long id)
    {
        return tbEventsMapper.deleteTbEventsById(id);
    }
}
