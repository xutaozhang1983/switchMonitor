package com.ruoyi.monitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.monitor.domain.TbAlarmEvent;
import com.ruoyi.monitor.mapper.TbAlarmEventMapper;
import com.ruoyi.monitor.service.ITbAlarmEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 报警信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Service
public class TbAlarmEventServiceImpl implements ITbAlarmEventService
{
    @Autowired
    private TbAlarmEventMapper tbAlarmEventMapper;

    /**
     * 查询报警信息
     * 
     * @param id 报警信息主键
     * @return 报警信息
     */
    @Override
    public TbAlarmEvent selectTbAlarmEventById(Long id)
    {
        return tbAlarmEventMapper.selectTbAlarmEventById(id);
    }

    /**
     * 查询报警信息列表
     * 
     * @param tbAlarmEvent 报警信息
     * @return 报警信息
     */
    @Override
    public List<TbAlarmEvent> selectTbAlarmEventList(TbAlarmEvent tbAlarmEvent)
    {
        return tbAlarmEventMapper.selectTbAlarmEventList(tbAlarmEvent);
    }

    /**
     * 新增报警信息
     * 
     * @param tbAlarmEvent 报警信息
     * @return 结果
     */
    @Override
    public int insertTbAlarmEvent(TbAlarmEvent tbAlarmEvent)
    {
        tbAlarmEvent.setCreateTime(DateUtils.getNowDate());
        return tbAlarmEventMapper.insertTbAlarmEvent(tbAlarmEvent);
    }

    /**
     * 修改报警信息
     * 
     * @param tbAlarmEvent 报警信息
     * @return 结果
     */
    @Override
    public int updateTbAlarmEvent(TbAlarmEvent tbAlarmEvent)
    {
        tbAlarmEvent.setUpdateTime(DateUtils.getNowDate());
        return tbAlarmEventMapper.updateTbAlarmEvent(tbAlarmEvent);
    }

    /**
     * 批量删除报警信息
     * 
     * @param ids 需要删除的报警信息主键
     * @return 结果
     */
    @Override
    public int deleteTbAlarmEventByIds(Long[] ids)
    {
        return tbAlarmEventMapper.deleteTbAlarmEventByIds(ids);
    }

    /**
     * 删除报警信息信息
     * 
     * @param id 报警信息主键
     * @return 结果
     */
    @Override
    public int deleteTbAlarmEventById(Long id)
    {
        return tbAlarmEventMapper.deleteTbAlarmEventById(id);
    }
}
