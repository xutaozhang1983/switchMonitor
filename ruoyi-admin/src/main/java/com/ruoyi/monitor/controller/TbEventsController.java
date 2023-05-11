package com.ruoyi.monitor.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.monitor.domain.dto.TbEventsDto;
import com.ruoyi.monitor.domain.vo.EventsVo;
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
import com.ruoyi.monitor.domain.TbEvents;
import com.ruoyi.monitor.service.ITbEventsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报警信息Controller
 * 
 * @author ruoyi
 * @date 2023-05-09
 */
@RestController
@RequestMapping("/monitor/events")
public class TbEventsController extends BaseController
{
    @Autowired
    private ITbEventsService tbEventsService;

    /**
     * 查询报警信息列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:events:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbEventsDto tbEvents)
    {
        startPage();
        List<EventsVo> list = tbEventsService.selectEventList(tbEvents);
        return getDataTable(list);
    }

    /**
     * 导出报警信息列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:events:export')")
    @Log(title = "报警信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbEvents tbEvents)
    {
        List<TbEvents> list = tbEventsService.selectTbEventsList(tbEvents);
        ExcelUtil<TbEvents> util = new ExcelUtil<TbEvents>(TbEvents.class);
        util.exportExcel(response, list, "报警信息数据");
    }

    /**
     * 获取报警信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:events:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbEventsService.selectTbEventsById(id));
    }

    /**
     * 新增报警信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:events:add')")
    @Log(title = "报警信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbEvents tbEvents)
    {
        return toAjax(tbEventsService.insertTbEvents(tbEvents));
    }

    /**
     * 修改报警信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:events:edit')")
    @Log(title = "报警信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbEvents tbEvents)
    {
        return toAjax(tbEventsService.updateTbEvents(tbEvents));
    }

    /**
     * 删除报警信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:events:remove')")
    @Log(title = "报警信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbEventsService.deleteTbEventsByIds(ids));
    }

    @ApiOperation("事件关闭")
    @PostMapping("/close")
    public AjaxResult close(@RequestBody TbEvents tbEvents)
    {
        tbEvents.setClosedAt(DateUtils.getNowDate());
        tbEvents.setClosedUser(getUsername());
        tbEvents.setStatus("1");
        return toAjax(tbEventsService.updateTbEvents(tbEvents));
    }
}
