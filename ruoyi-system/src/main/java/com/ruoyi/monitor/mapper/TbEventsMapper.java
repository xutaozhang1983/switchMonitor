package com.ruoyi.monitor.mapper;

import java.util.List;
import com.ruoyi.monitor.domain.TbEvents;

/**
 * 事件信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public interface TbEventsMapper 
{
    /**
     * 查询事件信息
     * 
     * @param id 事件信息主键
     * @return 事件信息
     */
    public TbEvents selectTbEventsById(Long id);

    /**
     * 查询事件信息列表
     * 
     * @param tbEvents 事件信息
     * @return 事件信息集合
     */
    public List<TbEvents> selectTbEventsList(TbEvents tbEvents);

    /**
     * 新增事件信息
     * 
     * @param tbEvents 事件信息
     * @return 结果
     */
    public int insertTbEvents(TbEvents tbEvents);

    /**
     * 修改事件信息
     * 
     * @param tbEvents 事件信息
     * @return 结果
     */
    public int updateTbEvents(TbEvents tbEvents);

    /**
     * 删除事件信息
     * 
     * @param id 事件信息主键
     * @return 结果
     */
    public int deleteTbEventsById(Long id);

    /**
     * 批量删除事件信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbEventsByIds(Long[] ids);
}
