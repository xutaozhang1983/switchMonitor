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
import com.ruoyi.monitor.domain.TbDeviceItem;
import com.ruoyi.monitor.service.ITbDeviceItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备监控指标Controller
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@RestController
@RequestMapping("/monitor/item")
public class TbDeviceItemController extends BaseController
{
    @Autowired
    private ITbDeviceItemService tbDeviceItemService;

    /**
     * 查询设备监控指标列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbDeviceItem tbDeviceItem)
    {
        startPage();
        List<TbDeviceItem> list = tbDeviceItemService.selectTbDeviceItemList(tbDeviceItem);
        return getDataTable(list);
    }

    /**
     * 导出设备监控指标列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:item:export')")
    @Log(title = "设备监控指标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbDeviceItem tbDeviceItem)
    {
        List<TbDeviceItem> list = tbDeviceItemService.selectTbDeviceItemList(tbDeviceItem);
        ExcelUtil<TbDeviceItem> util = new ExcelUtil<TbDeviceItem>(TbDeviceItem.class);
        util.exportExcel(response, list, "设备监控指标数据");
    }

    /**
     * 获取设备监控指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:item:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbDeviceItemService.selectTbDeviceItemById(id));
    }

    /**
     * 新增设备监控指标
     */
    @PreAuthorize("@ss.hasPermi('monitor:item:add')")
    @Log(title = "设备监控指标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbDeviceItem tbDeviceItem)
    {
        return toAjax(tbDeviceItemService.insertTbDeviceItem(tbDeviceItem));
    }

    /**
     * 修改设备监控指标
     */
    @PreAuthorize("@ss.hasPermi('monitor:item:edit')")
    @Log(title = "设备监控指标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbDeviceItem tbDeviceItem)
    {
        return toAjax(tbDeviceItemService.updateTbDeviceItem(tbDeviceItem));
    }

    /**
     * 删除设备监控指标
     */
    @PreAuthorize("@ss.hasPermi('monitor:item:remove')")
    @Log(title = "设备监控指标", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbDeviceItemService.deleteTbDeviceItemByIds(ids));
    }
}
