package com.ruoyi.monitor.mapper;

import java.util.List;
import com.ruoyi.monitor.domain.TbEvents;
import com.ruoyi.monitor.domain.dto.TbEventsDto;
import com.ruoyi.monitor.domain.vo.EventsVo;

/**
 * 报警信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-09
 */
public interface TbEventsMapper 
{
    /**
     * 查询报警信息
     * 
     * @param id 报警信息主键
     * @return 报警信息
     */
    public TbEvents selectTbEventsById(Long id);

    /**
     * 查询报警信息列表
     * 
     * @param tbEvents 报警信息
     * @return 报警信息集合
     */
    public List<TbEvents> selectTbEventsList(TbEvents tbEvents);

    /**
     * 新增报警信息
     * 
     * @param tbEvents 报警信息
     * @return 结果
     */
    public int insertTbEvents(TbEvents tbEvents);

    /**
     * 修改报警信息
     * 
     * @param tbEvents 报警信息
     * @return 结果
     */
    public int updateTbEvents(TbEvents tbEvents);

    /**
     * 删除报警信息
     * 
     * @param id 报警信息主键
     * @return 结果
     */
    public int deleteTbEventsById(Long id);

    /**
     * 批量删除报警信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbEventsByIds(Long[] ids);

    TbEvents selectLastEvent(TbEvents tbEvents);

    List<EventsVo> selectEventList(TbEventsDto eventsDto);
}
