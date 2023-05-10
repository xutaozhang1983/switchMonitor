package com.ruoyi.monitor.service;

import java.util.List;
import com.ruoyi.monitor.domain.TbEvents;

/**
 * 报警信息Service接口
 * 
 * @author ruoyi
 * @date 2023-05-09
 */
public interface ITbEventsService 
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
     * 批量删除报警信息
     * 
     * @param ids 需要删除的报警信息主键集合
     * @return 结果
     */
    public int deleteTbEventsByIds(Long[] ids);

    /**
     * 删除报警信息信息
     * 
     * @param id 报警信息主键
     * @return 结果
     */
    public int deleteTbEventsById(Long id);

    int saveEvent(TbEvents tbEvent);


    TbEvents selectEvent(Long deviceId,Long itemId,String status);
}
