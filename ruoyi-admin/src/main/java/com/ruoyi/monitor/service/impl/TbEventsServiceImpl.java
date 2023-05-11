package com.ruoyi.monitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.monitor.domain.dto.TbEventsDto;
import com.ruoyi.monitor.domain.vo.EventsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.monitor.mapper.TbEventsMapper;
import com.ruoyi.monitor.domain.TbEvents;
import com.ruoyi.monitor.service.ITbEventsService;

import javax.annotation.Resource;

/**
 * 报警信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-09
 */
@Service
public class TbEventsServiceImpl implements ITbEventsService 
{
    @Resource
    private TbEventsMapper tbEventsMapper;

    /**
     * 查询报警信息
     * 
     * @param id 报警信息主键
     * @return 报警信息
     */
    @Override
    public TbEvents selectTbEventsById(Long id)
    {
        return tbEventsMapper.selectTbEventsById(id);
    }

    /**
     * 查询报警信息列表
     * 
     * @param tbEvents 报警信息
     * @return 报警信息
     */
    @Override
    public List<TbEvents> selectTbEventsList(TbEvents tbEvents)
    {
        return tbEventsMapper.selectTbEventsList(tbEvents);
    }

    /**
     * 新增报警信息
     * 
     * @param tbEvents 报警信息
     * @return 结果
     */
    @Override
    public int insertTbEvents(TbEvents tbEvents)
    {
        tbEvents.setCreateTime(DateUtils.getNowDate());
        return tbEventsMapper.insertTbEvents(tbEvents);
    }

    /**
     * 修改报警信息
     * 
     * @param tbEvents 报警信息
     * @return 结果
     */
    @Override
    public int updateTbEvents(TbEvents tbEvents)
    {
        return tbEventsMapper.updateTbEvents(tbEvents);
    }

    /**
     * 批量删除报警信息
     * 
     * @param ids 需要删除的报警信息主键
     * @return 结果
     */
    @Override
    public int deleteTbEventsByIds(Long[] ids)
    {
        return tbEventsMapper.deleteTbEventsByIds(ids);
    }

    /**
     * 删除报警信息信息
     * 
     * @param id 报警信息主键
     * @return 结果
     */
    @Override
    public int deleteTbEventsById(Long id)
    {
        return tbEventsMapper.deleteTbEventsById(id);
    }

    @Override
    public int saveEvent(TbEvents tbEvent) {
       if (StringUtils.isNotNull(tbEvent.getId())){
            return tbEventsMapper.updateTbEvents(tbEvent);
       }else{
           return tbEventsMapper.insertTbEvents(tbEvent);
       }
    }

    @Override
    public TbEvents selectLastEvent(Long deviceId,Long itemId,String status) {
        TbEvents event = new TbEvents();
        event.setStatus(status);
        event.setDeviceId(deviceId);
        event.setItemId(itemId);
        return tbEventsMapper.selectLastEvent(event);
    }

    @Override
    public List<EventsVo> selectEventList(TbEventsDto tbEventsDto) {
        return tbEventsMapper.selectEventList(tbEventsDto);
    }
}
