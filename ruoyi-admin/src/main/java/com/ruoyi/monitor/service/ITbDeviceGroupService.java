package com.ruoyi.monitor.service;

import java.util.List;
import com.ruoyi.monitor.domain.TbDeviceGroup;
import com.ruoyi.monitor.domain.vo.DeviceGroupVO;

/**
 * 设备列Service接口
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public interface ITbDeviceGroupService 
{
    /**
     * 查询设备列
     * 
     * @param id 设备列主键
     * @return 设备列
     */
    public TbDeviceGroup selectTbDeviceGroupById(Long id);

    List<DeviceGroupVO>  deviceGroupList(TbDeviceGroup tbDeviceGroup);

    /**
     * 查询设备列列表
     * 
     * @param tbDeviceGroup 设备列
     * @return 设备列集合
     */
    public List<TbDeviceGroup> selectTbDeviceGroupList(TbDeviceGroup tbDeviceGroup);

    /**
     * 新增设备列
     * 
     * @param tbDeviceGroup 设备列
     * @return 结果
     */
    public int insertTbDeviceGroup(TbDeviceGroup tbDeviceGroup);

    /**
     * 修改设备列
     * 
     * @param tbDeviceGroup 设备列
     * @return 结果
     */
    public int updateTbDeviceGroup(TbDeviceGroup tbDeviceGroup);

    /**
     * 批量删除设备列
     * 
     * @param ids 需要删除的设备列主键集合
     * @return 结果
     */
    public int deleteTbDeviceGroupByIds(Long[] ids);

    /**
     * 删除设备列信息
     * 
     * @param id 设备列主键
     * @return 结果
     */
    public int deleteTbDeviceGroupById(Long id);
}
