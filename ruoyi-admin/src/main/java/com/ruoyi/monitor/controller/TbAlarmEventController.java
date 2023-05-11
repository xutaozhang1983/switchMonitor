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
import com.ruoyi.monitor.domain.TbAlarmEvent;
import com.ruoyi.monitor.service.ITbAlarmEventService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 事件信息Controller
 * 
 * @author ruoyi
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/monitor/events/alarm")
public class TbAlarmEventController extends BaseController
{
    @Autowired
    private ITbAlarmEventService tbAlarmEventService;

    /**
     * 查询事件信息列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:event:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbAlarmEvent tbAlarmEvent)
    {
        startPage();
        List<TbAlarmEvent> list = tbAlarmEventService.selectTbAlarmEventList(tbAlarmEvent);
        return getDataTable(list);
    }

    /**
     * 导出事件信息列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:event:export')")
    @Log(title = "事件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbAlarmEvent tbAlarmEvent)
    {
        List<TbAlarmEvent> list = tbAlarmEventService.selectTbAlarmEventList(tbAlarmEvent);
        ExcelUtil<TbAlarmEvent> util = new ExcelUtil<TbAlarmEvent>(TbAlarmEvent.class);
        util.exportExcel(response, list, "事件信息数据");
    }

    /**
     * 获取事件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:event:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbAlarmEventService.selectTbAlarmEventById(id));
    }

    /**
     * 新增事件信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:event:add')")
    @Log(title = "事件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbAlarmEvent tbAlarmEvent)
    {
        return toAjax(tbAlarmEventService.insertTbAlarmEvent(tbAlarmEvent));
    }

    /**
     * 修改事件信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:event:edit')")
    @Log(title = "事件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbAlarmEvent tbAlarmEvent)
    {
        return toAjax(tbAlarmEventService.updateTbAlarmEvent(tbAlarmEvent));
    }

    /**
     * 删除事件信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:event:remove')")
    @Log(title = "事件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbAlarmEventService.deleteTbAlarmEventByIds(ids));
    }
}
