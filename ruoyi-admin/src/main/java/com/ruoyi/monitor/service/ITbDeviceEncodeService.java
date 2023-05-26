package com.ruoyi.monitor.service;

import java.util.List;
import com.ruoyi.monitor.domain.TbDeviceEncode;

/**
 * 编码设备Service接口
 * 
 * @author ruoyi
 * @date 2023-05-25
 */
public interface ITbDeviceEncodeService 
{
    /**
     * 查询编码设备
     * 
     * @param id 编码设备主键
     * @return 编码设备
     */
    public TbDeviceEncode selectTbDeviceEncodeById(Long id);

    /**
     * 查询编码设备列表
     * 
     * @param tbDeviceEncode 编码设备
     * @return 编码设备集合
     */
    public List<TbDeviceEncode> selectTbDeviceEncodeList(TbDeviceEncode tbDeviceEncode);

    /**
     * 新增编码设备
     * 
     * @param tbDeviceEncode 编码设备
     * @return 结果
     */
    public int insertTbDeviceEncode(TbDeviceEncode tbDeviceEncode);

    /**
     * 修改编码设备
     * 
     * @param tbDeviceEncode 编码设备
     * @return 结果
     */
    public int updateTbDeviceEncode(TbDeviceEncode tbDeviceEncode);

    /**
     * 批量删除编码设备
     * 
     * @param ids 需要删除的编码设备主键集合
     * @return 结果
     */
    public int deleteTbDeviceEncodeByIds(Long[] ids);

    /**
     * 删除编码设备信息
     * 
     * @param id 编码设备主键
     * @return 结果
     */
    public int deleteTbDeviceEncodeById(Long id);
}
