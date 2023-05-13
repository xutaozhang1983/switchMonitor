package com.ruoyi.monitor.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.monitor.domain.TbDeviceItemHis;
import com.ruoyi.monitor.domain.dto.ItemHisDto;
import com.ruoyi.monitor.domain.vo.ItemGraphVo;
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
        tbDeviceItemHis.setClock(DateUtils.timestamp());
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
        tbDeviceItemHis.setClock(DateUtils.timestamp());
        return tbDeviceItemHisMapper.updateTbDeviceItemHis(tbDeviceItemHis);
    }

    /**
     * 批量删除设备监控指标
     * 
     * @param ids 需要删除的设备监控指标主键
     * @return 结果
     */

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
    public List<ItemGraphVo> selectGraph(ItemHisDto itemHisDto) {
        return tbDeviceItemHisMapper.selectGraph(itemHisDto);
    }

    @Override
    public List<ItemGraphVo> deviceGraph(ItemHisDto itemHisDto) {
        LocalDateTime now = LocalDateTime.now();

        // 获取两个小时前的时间
        LocalDateTime start = now.minus(2, ChronoUnit.HOURS);
        itemHisDto.setStartClock(DateUtils.toDate(start));
        itemHisDto.setEndClock(DateUtils.getNowDate());
        return tbDeviceItemHisMapper.selectDeviceGraph(itemHisDto);
    }

    @Override
    public void insertBatch(List<TbDeviceItemHis> itemHisList) {
        tbDeviceItemHisMapper.insertBatch(itemHisList);
    }

    @Override
    public List<ItemGraphVo> selectItemTop(ItemHisDto itemHisDto) {
        Date startClock = DateUtils.minusHours(new Date(),4);
        itemHisDto.setStartClock(startClock);
        return tbDeviceItemHisMapper.selectTop(itemHisDto);
    }
}
