package com.ruoyi.monitor.mapper;

import java.util.List;
import com.ruoyi.monitor.domain.TbDeviceGroup;
import com.ruoyi.monitor.domain.dto.DeviceGroupDTO;

/**
 * 设备列Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
public interface TbDeviceGroupMapper 
{
    /**
     * 查询设备列
     * 
     * @param id 设备列主键
     * @return 设备列
     */
    public TbDeviceGroup selectTbDeviceGroupById(Long id);

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
     * 删除设备列
     * 
     * @param id 设备列主键
     * @return 结果
     */
    public int deleteTbDeviceGroupById(Long id);

    /**
     * 批量删除设备列
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbDeviceGroupByIds(Long[] ids);

    List<DeviceGroupDTO> deviceGroupList(TbDeviceGroup tbDeviceGroup);
}
