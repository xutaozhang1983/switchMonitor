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
import com.ruoyi.monitor.domain.TbDeviceCamera;
import com.ruoyi.monitor.service.ITbDeviceCameraService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 摄像头Controller
 * 
 * @author ruoyi
 * @date 2023-05-26
 */
@RestController
@RequestMapping("/monitor/camera")
public class TbDeviceCameraController extends BaseController
{
    @Autowired
    private ITbDeviceCameraService tbDeviceCameraService;

    /**
     * 查询摄像头列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:camera:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbDeviceCamera tbDeviceCamera)
    {
        startPage();
        List<TbDeviceCamera> list = tbDeviceCameraService.selectTbDeviceCameraList(tbDeviceCamera);
        return getDataTable(list);
    }

    /**
     * 导出摄像头列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:camera:export')")
    @Log(title = "摄像头", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbDeviceCamera tbDeviceCamera)
    {
        List<TbDeviceCamera> list = tbDeviceCameraService.selectTbDeviceCameraList(tbDeviceCamera);
        ExcelUtil<TbDeviceCamera> util = new ExcelUtil<TbDeviceCamera>(TbDeviceCamera.class);
        util.exportExcel(response, list, "摄像头数据");
    }

    /**
     * 获取摄像头详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:camera:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbDeviceCameraService.selectTbDeviceCameraById(id));
    }

    /**
     * 新增摄像头
     */
    @PreAuthorize("@ss.hasPermi('monitor:camera:add')")
    @Log(title = "摄像头", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbDeviceCamera tbDeviceCamera)
    {
        return toAjax(tbDeviceCameraService.insertTbDeviceCamera(tbDeviceCamera));
    }

    /**
     * 修改摄像头
     */
    @PreAuthorize("@ss.hasPermi('monitor:camera:edit')")
    @Log(title = "摄像头", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbDeviceCamera tbDeviceCamera)
    {
        return toAjax(tbDeviceCameraService.updateTbDeviceCamera(tbDeviceCamera));
    }

    /**
     * 删除摄像头
     */
    @PreAuthorize("@ss.hasPermi('monitor:camera:remove')")
    @Log(title = "摄像头", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbDeviceCameraService.deleteTbDeviceCameraByIds(ids));
    }
}
