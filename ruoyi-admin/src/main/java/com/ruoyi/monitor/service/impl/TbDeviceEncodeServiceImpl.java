package com.ruoyi.monitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.monitor.mapper.TbDeviceEncodeMapper;
import com.ruoyi.monitor.domain.TbDeviceEncode;
import com.ruoyi.monitor.service.ITbDeviceEncodeService;

/**
 * 编码设备Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-25
 */
@Service
public class TbDeviceEncodeServiceImpl implements ITbDeviceEncodeService 
{
    @Autowired
    private TbDeviceEncodeMapper tbDeviceEncodeMapper;

    /**
     * 查询编码设备
     * 
     * @param id 编码设备主键
     * @return 编码设备
     */
    @Override
    public TbDeviceEncode selectTbDeviceEncodeById(Long id)
    {
        return tbDeviceEncodeMapper.selectTbDeviceEncodeById(id);
    }

    /**
     * 查询编码设备列表
     * 
     * @param tbDeviceEncode 编码设备
     * @return 编码设备
     */
    @Override
    public List<TbDeviceEncode> selectTbDeviceEncodeList(TbDeviceEncode tbDeviceEncode)
    {
        return tbDeviceEncodeMapper.selectTbDeviceEncodeList(tbDeviceEncode);
    }

    /**
     * 新增编码设备
     * 
     * @param tbDeviceEncode 编码设备
     * @return 结果
     */
    @Override
    public int insertTbDeviceEncode(TbDeviceEncode tbDeviceEncode)
    {
        tbDeviceEncode.setCreateTime(DateUtils.getNowDate());
        return tbDeviceEncodeMapper.insertTbDeviceEncode(tbDeviceEncode);
    }

    /**
     * 修改编码设备
     * 
     * @param tbDeviceEncode 编码设备
     * @return 结果
     */
    @Override
    public int updateTbDeviceEncode(TbDeviceEncode tbDeviceEncode)
    {
        tbDeviceEncode.setUpdateTime(DateUtils.getNowDate());
        return tbDeviceEncodeMapper.updateTbDeviceEncode(tbDeviceEncode);
    }

    /**
     * 批量删除编码设备
     * 
     * @param ids 需要删除的编码设备主键
     * @return 结果
     */
    @Override
    public int deleteTbDeviceEncodeByIds(Long[] ids)
    {
        return tbDeviceEncodeMapper.deleteTbDeviceEncodeByIds(ids);
    }

    /**
     * 删除编码设备信息
     * 
     * @param id 编码设备主键
     * @return 结果
     */
    @Override
    public int deleteTbDeviceEncodeById(Long id)
    {
        return tbDeviceEncodeMapper.deleteTbDeviceEncodeById(id);
    }
}
