package com.ruoyi.monitor.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.ruoyi.utils.EmailService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.monitor.domain.TbAlarmMedia;
import com.ruoyi.monitor.service.ITbAlarmMediaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * smtp 服务配置Controller
 * 
 * @author ruoyi
 * @date 2023-05-13
 */
@RestController
@RequestMapping("/monitor/media")
public class TbAlarmMediaController extends BaseController
{
    @Autowired
    private ITbAlarmMediaService tbAlarmMediaService;

    @Autowired
    private EmailService emailService;
    /**
     * 查询smtp 服务配置列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:media:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbAlarmMedia tbAlarmMedia)
    {
        startPage();
        List<TbAlarmMedia> list = tbAlarmMediaService.selectTbAlarmMediaList(tbAlarmMedia);
        return getDataTable(list);
    }


    /**
     * 获取smtp 服务配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:media:query')")
    @GetMapping(value = "/{mediaType}")
    public AjaxResult getInfo(@PathVariable("mediaType") String mediaType)
    {
        return success(tbAlarmMediaService.getAlarmMedia(mediaType));
    }

    /**
     * 新增smtp 服务配置
     */
    @PreAuthorize("@ss.hasPermi('monitor:media:add')")
    @Log(title = "smtp 服务配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbAlarmMedia tbAlarmMedia)
    {
        return toAjax(tbAlarmMediaService.insertTbAlarmMedia(tbAlarmMedia));
    }

    /**
     * 修改smtp 服务配置
     */
    @PreAuthorize("@ss.hasPermi('monitor:media:edit')")
    @Log(title = "smtp 服务配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbAlarmMedia tbAlarmMedia)
    {
        return toAjax(tbAlarmMediaService.updateTbAlarmMedia(tbAlarmMedia));
    }

    /**
     * 删除smtp 服务配置
     */
    @PreAuthorize("@ss.hasPermi('monitor:media:remove')")
    @Log(title = "smtp 服务配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbAlarmMediaService.deleteTbAlarmMediaByIds(ids));
    }


    @GetMapping("/emailTest")
    public AjaxResult remove(@RequestParam String receive)
    {
        String sub = "测试邮件";
        String content ="测试邮件内容";
        return emailService.SendEmail(receive,sub,content);
    }
}
