package com.ruoyi.monitor.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.monitor.domain.dto.DeviceGroupVO;
import com.ruoyi.monitor.service.ITbDeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
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
import com.ruoyi.monitor.domain.TbDeviceGroup;
import com.ruoyi.monitor.service.ITbDeviceGroupService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备列Controller
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@RestController
@RequestMapping("/monitor/group")
public class TbDeviceGroupController extends BaseController
{
    @Autowired
    private ITbDeviceGroupService tbDeviceGroupService;

    @Autowired
    private ITbDeviceService deviceService;
    /**
     * 查询设备列列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:group:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbDeviceGroup tbDeviceGroup)
    {
        startPage();
        List<DeviceGroupVO> list = tbDeviceGroupService.deviceGroupList(tbDeviceGroup);
        return getDataTable(list);
    }

    /**
     * 导出设备列列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:group:export')")
    @Log(title = "设备列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbDeviceGroup tbDeviceGroup)
    {
        List<DeviceGroupVO> list = tbDeviceGroupService.deviceGroupList(tbDeviceGroup);
        ExcelUtil<DeviceGroupVO> util = new ExcelUtil<DeviceGroupVO>(DeviceGroupVO.class);
        util.exportExcel(response, list, "设备列数据");
    }

    /**
     * 获取设备列详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:group:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        TbDeviceGroup group = tbDeviceGroupService.selectTbDeviceGroupById(id);
        DeviceGroupVO groupDTO = new DeviceGroupVO();
        BeanUtils.copyProperties(group,groupDTO,DeviceGroupVO.class);
        return success(groupDTO);
    }

    /**
     * 新增设备列
     */
    @PreAuthorize("@ss.hasPermi('monitor:group:add')")
    @Log(title = "设备列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbDeviceGroup tbDeviceGroup)
    {
        return toAjax(tbDeviceGroupService.insertTbDeviceGroup(tbDeviceGroup));
    }

    /**
     * 修改设备列
     */
    @PreAuthorize("@ss.hasPermi('monitor:group:edit')")
    @Log(title = "设备列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbDeviceGroup tbDeviceGroup)
    {
        return toAjax(tbDeviceGroupService.updateTbDeviceGroup(tbDeviceGroup));
    }

    /**
     * 删除设备列
     */
    @PreAuthorize("@ss.hasPermi('monitor:group:remove')")
    @Log(title = "设备列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbDeviceGroupService.deleteTbDeviceGroupByIds(ids));
    }
}
