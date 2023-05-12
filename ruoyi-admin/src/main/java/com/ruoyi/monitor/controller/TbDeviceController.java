package com.ruoyi.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.monitor.domain.TbDeviceGroup;
import com.ruoyi.monitor.domain.vo.DeviceStat;
import com.ruoyi.monitor.domain.vo.DeviceVO;
import com.ruoyi.monitor.enums.AlarmEnum;
import com.ruoyi.monitor.service.ITbDeviceGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.monitor.domain.TbDevice;
import com.ruoyi.monitor.service.ITbDeviceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备列Controller
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@RestController
@RequestMapping("/monitor/device")
public class TbDeviceController extends BaseController
{
    @Autowired
    private ITbDeviceService tbDeviceService;

    @Autowired
    private ITbDeviceGroupService groupService;
    /**
     * 查询设备列列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:device:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbDevice tbDevice)
    {
        startPage();
        List<DeviceVO> list = tbDeviceService.selectTbDeviceList(tbDevice);
        return getDataTable(list);
    }

    /**
     * 导出设备列列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:device:export')")
    @Log(title = "设备列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbDevice tbDevice)
    {
        List<DeviceVO> list = tbDeviceService.selectTbDeviceList(tbDevice);
        ExcelUtil<DeviceVO> util = new ExcelUtil<DeviceVO>(DeviceVO.class);
        util.exportExcel(response, list, "设备列数据");
    }

    /**
     * 获取设备列详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:device:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbDeviceService.selectTbDeviceById(id));
    }

    /**
     * 新增设备列
     */
    @PreAuthorize("@ss.hasPermi('monitor:device:add')")
    @Log(title = "设备列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbDevice tbDevice)
    {
        return toAjax(tbDeviceService.insertTbDevice(tbDevice));
    }

    /**
     * 修改设备列
     */
    @PreAuthorize("@ss.hasPermi('monitor:device:edit')")
    @Log(title = "设备列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbDevice tbDevice)
    {
        return toAjax(tbDeviceService.updateTbDevice(tbDevice));
    }

    /**
     * 删除设备列
     */
    @PreAuthorize("@ss.hasPermi('monitor:device:remove')")
    @Log(title = "设备列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbDeviceService.deleteTbDeviceByIds(ids));
    }

    @ApiOperation("设备启用禁用")
    @PutMapping("/changeEnable")
    public AjaxResult changeEnable(@RequestBody TbDevice tbDevice)
    {
        TbDevice device = tbDeviceService.selectTbDeviceById(tbDevice.getId());
        device.setEnable(tbDevice.getEnable());
        device.setUpdateBy(getUsername());
        return toAjax(tbDeviceService.updateTbDevice(device));
    }

    @GetMapping("/status/stat")
    public AjaxResult statusStat() {
        DeviceStat stat = new DeviceStat();
        List<TbDevice> devices = tbDeviceService.selectDeviceList(new TbDevice());

       stat.setAll(devices.size());
        for (TbDevice device:devices){
            if(device.getEnable() == 0){
                if (device.getStatus().equals(AlarmEnum.OK.getCode())){
                    stat.setOk(stat.getOk()+1);
                }else{
                    stat.setErr(stat.getErr()+1);
                }
            }else{
                stat.setUnknow(stat.getUnknow()+1);
            }
        }

        return AjaxResult.success(stat);
    }
    @GetMapping("/status/groupStat")
    public AjaxResult groupStatusStat() {
        Map<String,Object> resultMap = new HashMap();
        List<TbDeviceGroup> groups = groupService.selectTbDeviceGroupList(new TbDeviceGroup());
        for (TbDeviceGroup grp:groups){
            TbDevice tbDevice = new TbDevice();
            tbDevice.setGroupId(grp.getId());
            List<TbDevice> devices = tbDeviceService.selectDeviceList(tbDevice);
            DeviceStat stat = new DeviceStat();

            for (TbDevice device:devices){
                if(device.getEnable() == 0){
                    if (device.getStatus().equals(AlarmEnum.OK.getCode())){
                        stat.setOk(stat.getOk()+1);
                    }else{
                        stat.setErr(stat.getErr()+1);
                    }
                }else{
                    stat.setUnknow(stat.getUnknow()+1);
                }
            }
            stat.setAll(devices.size());
            resultMap.put(grp.getGroupName(),stat);
        }
        return AjaxResult.success(resultMap);
    }
}
