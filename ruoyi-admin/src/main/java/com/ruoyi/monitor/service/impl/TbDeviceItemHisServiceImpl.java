package com.ruoyi.monitor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.monitor.domain.TbDeviceItemHis;
import com.ruoyi.monitor.domain.dto.ItemHisDto;
import com.ruoyi.monitor.domain.vo.ItemHisVo;
import com.ruoyi.monitor.mapper.TbDeviceItemHisMapper;
import com.ruoyi.monitor.service.ITbDeviceItemHisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备监控指标Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Service
public class TbDeviceItemHisServiceImpl implements ITbDeviceItemHisService
{
    @Autowired
    private TbDeviceItemHisMapper tbDeviceItemHisMapper;

    /**
     * 查询设备监控指标
     * 
     * @param id 设备监控指标主键
     * @return 设备监控指标
     */
    @Override
    public TbDeviceItemHis selectTbDeviceItemHisById(Long id)
    {
        return tbDeviceItemHisMapper.selectTbDeviceItemHisById(id);
    }

    /**
     * 查询设备监控指标列表
     * 
     * @param tbDeviceItemHis 设备监控指标
     * @return 设备监控指标
     */
    @Override
    public List<TbDeviceItemHis> selectTbDeviceItemHisList(TbDeviceItemHis tbDeviceItemHis)
    {
        return tbDeviceItemHisMapper.selectTbDeviceItemHisList(tbDeviceItemHis);
    }

    /**
     * 新增设备监控指标
     * 
     * @param tbDeviceItemHis 设备监控指标
     * @return 结果
     */
    @Override
    public int insertTbDeviceItemHis(TbDeviceItemHis tbDeviceItemHis)
    {
        tbDeviceItemHis.setCreateTime(DateUtils.getNowDate());
        return tbDeviceItemHisMapper.insertTbDeviceItemHis(tbDeviceItemHis);
    }

    /**
     * 修改设备监控指标
     * 
     * @param tbDeviceItemHis 设备监控指标
     * @return 结果
     */
    @Override
    public int updateTbDeviceItemHis(TbDeviceItemHis tbDeviceItemHis)
    {
        return tbDeviceItemHisMapper.updateTbDeviceItemHis(tbDeviceItemHis);
    }

    /**
     * 批量删除设备监控指标
     * 
     * @param ids 需要删除的设备监控指标主键
     * @return 结果
     */
    @Override
    public int deleteTbDeviceItemHisByIds(Long[] ids)
    {
        return tbDeviceItemHisMapper.deleteTbDeviceItemHisByIds(ids);
    }

    /**
     * 删除设备监控指标信息
     * 
     * @param id 设备监控指标主键
     * @return 结果
     */
    @Override
    public int deleteTbDeviceItemHisById(Long id)
    {
        return tbDeviceItemHisMapper.deleteTbDeviceItemHisById(id);
    }

    @Override
    public List<ItemHisVo> selectGraph(ItemHisDto itemHisDto) {
        return tbDeviceItemHisMapper.selectGraph(itemHisDto);
    }
}
