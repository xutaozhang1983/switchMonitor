package com.ruoyi.monitor.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.monitor.domain.TbDevice;
import com.ruoyi.monitor.domain.TbDeviceItem;
import com.ruoyi.monitor.domain.vo.DeviceVO;
import com.ruoyi.monitor.mapper.TbDeviceItemMapper;
import com.ruoyi.monitor.mapper.TbDeviceMapper;
import com.ruoyi.monitor.service.ITbDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 设备列Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Service
public class TbDeviceServiceImpl implements ITbDeviceService
{
    @Resource
    private TbDeviceMapper tbDeviceMapper;

    @Resource
    TbDeviceItemMapper tbDeviceItemMapper;

    /**
     * 查询设备列
     * 
     * @param id 设备列主键
     * @return 设备列
     */
    @Override
    public TbDevice selectTbDeviceById(Long id)
    {
        return tbDeviceMapper.selectTbDeviceById(id);
    }

    @Override
    public List<TbDevice> selectDeviceList(TbDevice tbDevice) {
        return tbDeviceMapper.selectTbDeviceList(tbDevice);
    }

    /**
     * 查询设备列列表
     * 
     * @param tbDevice 设备列
     * @return 设备列
     */
    @Override
    public List<DeviceVO> selectTbDeviceList(TbDevice tbDevice)
    {
        return tbDeviceMapper.deviceList(tbDevice);
    }

    /**
     * 新增设备列
     * 
     * @param tbDevice 设备列
     * @return 结果
     */
    @Override
    public int insertTbDevice(TbDevice tbDevice)
    {
        tbDevice.setCreateTime(DateUtils.getNowDate());
        return tbDeviceMapper.insertTbDevice(tbDevice);
    }

    /**
     * 修改设备列
     * 
     * @param tbDevice 设备列
     * @return 结果
     */
    @Override
    public int updateTbDevice(TbDevice tbDevice)
    {
        tbDevice.setUpdateTime(DateUtils.getNowDate());
        return tbDeviceMapper.updateTbDevice(tbDevice);
    }

    /**
     * 批量删除设备列
     * 
     * @param ids 需要删除的设备列主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteTbDeviceByIds(Long[] ids)
    {
        List<Long> deviceIds = Arrays.asList(ids);
        tbDeviceItemMapper.deleteItemByDeviceIds(deviceIds);
        return tbDeviceMapper.deleteTbDeviceByIds(ids);

    }

    /**
     * 删除设备列信息
     * 
     * @param id 设备列主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteTbDeviceById(Long id)
    {
        List<Long> deviceIds  = new ArrayList<>();
        deviceIds.add(id);
        tbDeviceItemMapper.deleteItemByDeviceIds(deviceIds);
        return tbDeviceMapper.deleteTbDeviceByIds(id);
    }



}
