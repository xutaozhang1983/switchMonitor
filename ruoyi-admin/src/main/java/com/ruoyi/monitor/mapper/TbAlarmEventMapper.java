package com.ruoyi.monitor.mapper;

import java.util.List;
import com.ruoyi.monitor.domain.TbAlarmEvent;

/**
 * 报警信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public interface TbAlarmEventMapper 
{
    /**
     * 查询报警信息
     * 
     * @param id 报警信息主键
     * @return 报警信息
     */
    public TbAlarmEvent selectTbAlarmEventById(Long id);

    /**
     * 查询报警信息列表
     * 
     * @param tbAlarmEvent 报警信息
     * @return 报警信息集合
     */
    public List<TbAlarmEvent> selectTbAlarmEventList(TbAlarmEvent tbAlarmEvent);

    /**
     * 新增报警信息
     * 
     * @param tbAlarmEvent 报警信息
     * @return 结果
     */
    public int insertTbAlarmEvent(TbAlarmEvent tbAlarmEvent);

    /**
     * 修改报警信息
     * 
     * @param tbAlarmEvent 报警信息
     * @return 结果
     */
    public int updateTbAlarmEvent(TbAlarmEvent tbAlarmEvent);

    /**
     * 删除报警信息
     * 
     * @param id 报警信息主键
     * @return 结果
     */
    public int deleteTbAlarmEventById(Long id);

    /**
     * 批量删除报警信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbAlarmEventByIds(Long[] ids);
}
