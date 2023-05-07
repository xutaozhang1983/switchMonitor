package com.ruoyi.monitor.service;

import java.util.List;
import com.ruoyi.monitor.domain.TbDeviceItemHis;

/**
 * 设备监控指标Service接口
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public interface ITbDeviceItemHisService 
{
    /**
     * 查询设备监控指标
     * 
     * @param id 设备监控指标主键
     * @return 设备监控指标
     */
    public TbDeviceItemHis selectTbDeviceItemHisById(Long id);

    /**
     * 查询设备监控指标列表
     * 
     * @param tbDeviceItemHis 设备监控指标
     * @return 设备监控指标集合
     */
    public List<TbDeviceItemHis> selectTbDeviceItemHisList(TbDeviceItemHis tbDeviceItemHis);

    /**
     * 新增设备监控指标
     * 
     * @param tbDeviceItemHis 设备监控指标
     * @return 结果
     */
    public int insertTbDeviceItemHis(TbDeviceItemHis tbDeviceItemHis);

    /**
     * 修改设备监控指标
     * 
     * @param tbDeviceItemHis 设备监控指标
     * @return 结果
     */
    public int updateTbDeviceItemHis(TbDeviceItemHis tbDeviceItemHis);

    /**
     * 批量删除设备监控指标
     * 
     * @param ids 需要删除的设备监控指标主键集合
     * @return 结果
     */
    public int deleteTbDeviceItemHisByIds(Long[] ids);

    /**
     * 删除设备监控指标信息
     * 
     * @param id 设备监控指标主键
     * @return 结果
     */
    public int deleteTbDeviceItemHisById(Long id);
}
