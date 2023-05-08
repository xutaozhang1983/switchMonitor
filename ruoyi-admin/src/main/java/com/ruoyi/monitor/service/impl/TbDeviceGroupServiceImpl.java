package com.ruoyi.monitor.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.monitor.domain.TbDeviceGroup;
import com.ruoyi.monitor.domain.vo.DeviceGroupVO;
import com.ruoyi.monitor.mapper.TbDeviceGroupMapper;
import com.ruoyi.monitor.service.ITbDeviceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备列Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@Service
public class TbDeviceGroupServiceImpl implements ITbDeviceGroupService
{
    @Autowired
    private TbDeviceGroupMapper tbDeviceGroupMapper;

    /**
     * 查询设备列
     * 
     * @param id 设备列主键
     * @return 设备列
     */
    @Override
    public TbDeviceGroup selectTbDeviceGroupById(Long id)
    {
        return tbDeviceGroupMapper.selectTbDeviceGroupById(id);
    }

    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<DeviceGroupVO> deviceGroupList(TbDeviceGroup tbDeviceGroup) {
        return tbDeviceGroupMapper.deviceGroupList(tbDeviceGroup);
    }

    /**
     * 查询设备列列表
     * 
     * @param tbDeviceGroup 设备列
     * @return 设备列
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<TbDeviceGroup> selectTbDeviceGroupList(TbDeviceGroup tbDeviceGroup)
    {
        return tbDeviceGroupMapper.selectTbDeviceGroupList(tbDeviceGroup);
    }

    /**
     * 新增设备列
     * 
     * @param tbDeviceGroup 设备列
     * @return 结果
     */
    @Override
    public int insertTbDeviceGroup(TbDeviceGroup tbDeviceGroup)
    {
        tbDeviceGroup.setCreateTime(DateUtils.getNowDate());
        return tbDeviceGroupMapper.insertTbDeviceGroup(tbDeviceGroup);
    }

    /**
     * 修改设备列
     * 
     * @param tbDeviceGroup 设备列
     * @return 结果
     */
    @Override
    public int updateTbDeviceGroup(TbDeviceGroup tbDeviceGroup)
    {
        tbDeviceGroup.setUpdateTime(DateUtils.getNowDate());
        return tbDeviceGroupMapper.updateTbDeviceGroup(tbDeviceGroup);
    }

    /**
     * 批量删除设备列
     * 
     * @param ids 需要删除的设备列主键
     * @return 结果
     */
    @Override
    public int deleteTbDeviceGroupByIds(Long[] ids)
    {
        return tbDeviceGroupMapper.deleteTbDeviceGroupByIds(ids);
    }

    /**
     * 删除设备列信息
     * 
     * @param id 设备列主键
     * @return 结果
     */
    @Override
    public int deleteTbDeviceGroupById(Long id)
    {
        return tbDeviceGroupMapper.deleteTbDeviceGroupById(id);
    }
}
