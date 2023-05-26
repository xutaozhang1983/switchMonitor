package com.ruoyi.monitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.monitor.mapper.TbDeviceCameraMapper;
import com.ruoyi.monitor.domain.TbDeviceCamera;
import com.ruoyi.monitor.service.ITbDeviceCameraService;

/**
 * 摄像头Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
@Service
public class TbDeviceCameraServiceImpl implements ITbDeviceCameraService 
{
    @Autowired
    private TbDeviceCameraMapper tbDeviceCameraMapper;

    /**
     * 查询摄像头
     * 
     * @param id 摄像头主键
     * @return 摄像头
     */
    @Override
    public TbDeviceCamera selectTbDeviceCameraById(Long id)
    {
        return tbDeviceCameraMapper.selectTbDeviceCameraById(id);
    }

    /**
     * 查询摄像头列表
     * 
     * @param tbDeviceCamera 摄像头
     * @return 摄像头
     */
    @Override
    public List<TbDeviceCamera> selectTbDeviceCameraList(TbDeviceCamera tbDeviceCamera)
    {
        return tbDeviceCameraMapper.selectTbDeviceCameraList(tbDeviceCamera);
    }

    /**
     * 新增摄像头
     * 
     * @param tbDeviceCamera 摄像头
     * @return 结果
     */
    @Override
    public int insertTbDeviceCamera(TbDeviceCamera tbDeviceCamera)
    {
        tbDeviceCamera.setCreateTime(DateUtils.getNowDate());
        return tbDeviceCameraMapper.insertTbDeviceCamera(tbDeviceCamera);
    }

    /**
     * 修改摄像头
     * 
     * @param tbDeviceCamera 摄像头
     * @return 结果
     */
    @Override
    public int updateTbDeviceCamera(TbDeviceCamera tbDeviceCamera)
    {
        tbDeviceCamera.setUpdateTime(DateUtils.getNowDate());
        return tbDeviceCameraMapper.updateTbDeviceCamera(tbDeviceCamera);
    }

    /**
     * 批量删除摄像头
     * 
     * @param ids 需要删除的摄像头主键
     * @return 结果
     */
    @Override
    public int deleteTbDeviceCameraByIds(Long[] ids)
    {
        return tbDeviceCameraMapper.deleteTbDeviceCameraByIds(ids);
    }

    /**
     * 删除摄像头信息
     * 
     * @param id 摄像头主键
     * @return 结果
     */
    @Override
    public int deleteTbDeviceCameraById(Long id)
    {
        return tbDeviceCameraMapper.deleteTbDeviceCameraById(id);
    }
}
