package com.ruoyi.monitor.service;

import java.util.List;
import com.ruoyi.monitor.domain.TbDeviceCamera;

/**
 * 摄像头Service接口
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
public interface ITbDeviceCameraService 
{
    /**
     * 查询摄像头
     * 
     * @param id 摄像头主键
     * @return 摄像头
     */
    public TbDeviceCamera selectTbDeviceCameraById(Long id);

    /**
     * 查询摄像头列表
     * 
     * @param tbDeviceCamera 摄像头
     * @return 摄像头集合
     */
    public List<TbDeviceCamera> selectTbDeviceCameraList(TbDeviceCamera tbDeviceCamera);

    /**
     * 新增摄像头
     * 
     * @param tbDeviceCamera 摄像头
     * @return 结果
     */
    public int insertTbDeviceCamera(TbDeviceCamera tbDeviceCamera);

    /**
     * 修改摄像头
     * 
     * @param tbDeviceCamera 摄像头
     * @return 结果
     */
    public int updateTbDeviceCamera(TbDeviceCamera tbDeviceCamera);

    /**
     * 批量删除摄像头
     * 
     * @param ids 需要删除的摄像头主键集合
     * @return 结果
     */
    public int deleteTbDeviceCameraByIds(Long[] ids);

    /**
     * 删除摄像头信息
     * 
     * @param id 摄像头主键
     * @return 结果
     */
    public int deleteTbDeviceCameraById(Long id);
}
