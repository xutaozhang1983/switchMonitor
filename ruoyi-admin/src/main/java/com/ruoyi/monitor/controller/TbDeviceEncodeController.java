package com.ruoyi.monitor.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.monitor.domain.TbDeviceEncode;
import com.ruoyi.monitor.service.ITbDeviceEncodeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 编码设备Controller
 * 
 * @author ruoyi
 * @date 2023-05-25
 */
@RestController
@RequestMapping("/monitor/encode")
public class TbDeviceEncodeController extends BaseController
{
    @Autowired
    private ITbDeviceEncodeService tbDeviceEncodeService;

    /**
     * 查询编码设备列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:encode:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbDeviceEncode tbDeviceEncode)
    {
        startPage();
        List<TbDeviceEncode> list = tbDeviceEncodeService.selectTbDeviceEncodeList(tbDeviceEncode);
        return getDataTable(list);
    }

    /**
     * 导出编码设备列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:encode:export')")
    @Log(title = "编码设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbDeviceEncode tbDeviceEncode)
    {
        List<TbDeviceEncode> list = tbDeviceEncodeService.selectTbDeviceEncodeList(tbDeviceEncode);
        ExcelUtil<TbDeviceEncode> util = new ExcelUtil<TbDeviceEncode>(TbDeviceEncode.class);
        util.exportExcel(response, list, "编码设备数据");
    }

    /**
     * 获取编码设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:encode:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbDeviceEncodeService.selectTbDeviceEncodeById(id));
    }

    /**
     * 新增编码设备
     */
    @PreAuthorize("@ss.hasPermi('monitor:encode:add')")
    @Log(title = "编码设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbDeviceEncode tbDeviceEncode)
    {
        return toAjax(tbDeviceEncodeService.insertTbDeviceEncode(tbDeviceEncode));
    }

    /**
     * 修改编码设备
     */
    @PreAuthorize("@ss.hasPermi('monitor:encode:edit')")
    @Log(title = "编码设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbDeviceEncode tbDeviceEncode)
    {
        return toAjax(tbDeviceEncodeService.updateTbDeviceEncode(tbDeviceEncode));
    }

    /**
     * 删除编码设备
     */
    @PreAuthorize("@ss.hasPermi('monitor:encode:remove')")
    @Log(title = "编码设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbDeviceEncodeService.deleteTbDeviceEncodeByIds(ids));
    }
}
