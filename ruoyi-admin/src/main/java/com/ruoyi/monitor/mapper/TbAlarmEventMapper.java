package com.ruoyi.monitor.mapper;

import java.util.List;
import com.ruoyi.monitor.domain.TbAlarmEvent;
import com.ruoyi.monitor.domain.dto.AlarmEventDto;
import com.ruoyi.monitor.domain.vo.AlarmEventVo;

/**
 * 事件信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-09
 */
public interface TbAlarmEventMapper 
{
    /**
     * 查询事件信息
     * 
     * @param id 事件信息主键
     * @return 事件信息
     */
    public TbAlarmEvent selectTbAlarmEventById(Long id);

    /**
     * 查询事件信息列表
     * 
     * @param tbAlarmEvent 事件信息
     * @return 事件信息集合
     */
    public List<AlarmEventVo> selectTbAlarmEventList(AlarmEventDto tbAlarmEvent);

    /**
     * 新增事件信息
     * 
     * @param tbAlarmEvent 事件信息
     * @return 结果
     */
    public int insertTbAlarmEvent(TbAlarmEvent tbAlarmEvent);

    /**
     * 修改事件信息
     * 
     * @param tbAlarmEvent 事件信息
     * @return 结果
     */
    public int updateTbAlarmEvent(TbAlarmEvent tbAlarmEvent);

    /**
     * 删除事件信息
     * 
     * @param id 事件信息主键
     * @return 结果
     */
    public int deleteTbAlarmEventById(Long id);

    /**
     * 批量删除事件信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbAlarmEventByIds(Long[] ids);
}
