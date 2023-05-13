package com.ruoyi.monitor.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.monitor.domain.dto.ItemHisDto;
import com.ruoyi.monitor.domain.vo.ItemGraphVo;
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
import com.ruoyi.monitor.domain.TbDeviceItemHis;
import com.ruoyi.monitor.service.ITbDeviceItemHisService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备监控指标Controller
 * 
 * @author ruoyi
 * @date 2023-05-07
 */
@RestController
@RequestMapping("/monitor/item/his")
public class TbDeviceItemHisController extends BaseController
{
    @Autowired
    private ITbDeviceItemHisService tbDeviceItemHisService;

    /**
     * 查询设备监控指标列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:his:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbDeviceItemHis tbDeviceItemHis)
    {
        startPage();
        List<TbDeviceItemHis> list = tbDeviceItemHisService.selectTbDeviceItemHisList(tbDeviceItemHis);

        return getDataTable(list);
    }

    /**
     * 导出设备监控指标列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:his:export')")
    @Log(title = "设备监控指标", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbDeviceItemHis tbDeviceItemHis)
    {
        List<TbDeviceItemHis> list = tbDeviceItemHisService.selectTbDeviceItemHisList(tbDeviceItemHis);
        ExcelUtil<TbDeviceItemHis> util = new ExcelUtil<TbDeviceItemHis>(TbDeviceItemHis.class);
        util.exportExcel(response, list, "设备监控指标数据");
    }

    /**
     * 获取设备监控指标详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:his:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tbDeviceItemHisService.selectTbDeviceItemHisById(id));
    }

    /**
     * 新增设备监控指标
     */
    @PreAuthorize("@ss.hasPermi('monitor:his:add')")
    @Log(title = "设备监控指标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbDeviceItemHis tbDeviceItemHis)
    {
        return toAjax(tbDeviceItemHisService.insertTbDeviceItemHis(tbDeviceItemHis));
    }

    /**
     * 修改设备监控指标
     */
    @PreAuthorize("@ss.hasPermi('monitor:his:edit')")
    @Log(title = "设备监控指标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbDeviceItemHis tbDeviceItemHis)
    {
        return toAjax(tbDeviceItemHisService.updateTbDeviceItemHis(tbDeviceItemHis));
    }

    @Log(title = "设备监控指标", businessType = BusinessType.DELETE)
	@PostMapping("/device/graph")
    public AjaxResult deviceGraph(@RequestBody ItemHisDto itemHisDto)
    {
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (String counter: itemHisDto.getCounters()) {
            Map<String ,Object> map = new HashMap<>();
            String[] c = new String[1];
            c[0] = counter;
            itemHisDto.setCounters(c);
            List<ItemGraphVo>  itemHisVoList = tbDeviceItemHisService.deviceGraph(itemHisDto);
            map.put("values",itemHisVoList);
            map.put("counter",counter);
            mapList.add(map);
        }
        return AjaxResult.success(mapList);
    }

    @ApiOperation("通过itemId,counter获取图标数据")
    @PostMapping("/selectGraph")
    public AjaxResult selectGraph(@RequestBody ItemHisDto itemHisDto){

        if(itemHisDto.getStep() == 0L){
            itemHisDto.setStep(300L);
        }

        List<Map<String,Object>> mapList = new ArrayList<>();
        for (String counter: itemHisDto.getCounters()) {
            Map<String ,Object> map = new HashMap<>();
            String[] c = new String[1];
            c[0] = counter;
            itemHisDto.setCounters(c);
            List<ItemGraphVo>  itemHisVoList = tbDeviceItemHisService.selectGraph(itemHisDto);
            map.put("values",itemHisVoList);
            map.put("itemId",itemHisDto.getItemId());
            map.put("counter",counter);
            mapList.add(map);
        }
        return AjaxResult.success(mapList);
    }
    @ApiOperation("获取top5 流量")
    @GetMapping("/selectItemTop5")
    public AjaxResult selectGraph(){
        Map<String , Object>  res = new HashMap<>();
        for (String counter:"ifIn,ifOut".split(",")){
            ItemHisDto itemHisDto = new ItemHisDto();
            String[] c = new String[1];
            c[0] = counter;
            itemHisDto.setCounters(c);
            System.out.println(itemHisDto);
            res.put(counter,tbDeviceItemHisService.selectItemTop(itemHisDto));
        }
        return AjaxResult.success(res);
    }
}
