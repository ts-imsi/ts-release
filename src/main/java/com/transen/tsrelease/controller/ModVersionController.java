package com.transen.tsrelease.controller;

import com.github.pagehelper.PageInfo;
import com.transen.tsrelease.model.TbModVersion;
import com.transen.tsrelease.service.ModVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/modVersion")
public class ModVersionController {

    @Autowired
    ModVersionService modVersionService;

    @PostMapping(value = "/modVersionList")
    public Map<String, Object> getModVersionList(@RequestBody Map<String, String> params) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (params.get("page") == null || params.get("rows") == null) {
            param.put("messges", "参数错误");
            param.put("success", false);
            return param;
        }
        TbModVersion modVersion = new TbModVersion();
        if(params.get("modId")!=null){
            modVersion.setModId(Integer.valueOf(params.get("modId")));
        }
        modVersion.setVersion(params.get("version"));// 设置模糊查询的条件
        PageInfo<TbModVersion> tbModVersionList = modVersionService.selectModVersion(Integer.valueOf(params.get("page")), Integer.valueOf(params.get("rows")), modVersion);
        param.put("数据查询条数", tbModVersionList.getSize());
        param.put("totalPages", tbModVersionList.getPages());
        param.put("pageNo", tbModVersionList.getPageNum());
        param.put("totalCount", tbModVersionList.getTotal());
        param.put("pageSize", tbModVersionList.getPageSize());
        param.put("list", tbModVersionList.getList());
        param.put("success", true);
        return param;
    }

    public int addModVersion() {
        TbModVersion modVersion = new TbModVersion();
        modVersionService.insertive(modVersion);
        return 1;
    }

    public int updateModVersison() {
        TbModVersion modVersion = new TbModVersion();
        modVersionService.updateModVersion(modVersion);
        return 1;
    }
}
