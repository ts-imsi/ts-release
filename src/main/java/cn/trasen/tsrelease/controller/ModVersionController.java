package cn.trasen.tsrelease.controller;

import cn.trasen.tsrelease.model.TbModVersion;
import cn.trasen.tsrelease.model.TbProduct;
import cn.trasen.tsrelease.service.ModVersionService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(HospitalController.class);

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
        TbProduct product = new TbProduct();
        TbModVersion modVersion = new TbModVersion();
        int page = Integer.valueOf(params.get("page"));
        int rows = Integer.valueOf(params.get("rows"));
        PageInfo<TbModVersion> tbModVersionList = null;

        try {
            // 根据产品id分类查看
            if (params.get("proId") != null) {
                product.setParent(Integer.valueOf(params.get("proId")));
                tbModVersionList = modVersionService.selectModVersion(page, rows, product);
            }
            // 根据模块id查看
            if (params.get("modId") != null) {
                modVersion.setModId(Integer.valueOf(params.get("modId")));
                tbModVersionList = modVersionService.selectModVersion(page, rows, modVersion);
            }
            // 查看所有
            if (params.get("modId") == null && params.get("proId") == null) {
                //modVersion.setVersion(params.get("version"));// 设置模糊查询的条件(未设置)
                tbModVersionList = modVersionService.selectModVersionById(page, rows, null);
            }
            param.put("数据查询条数", tbModVersionList.getSize());
            param.put("totalPages", tbModVersionList.getPages());
            param.put("pageNo", tbModVersionList.getPageNum());
            param.put("totalCount", tbModVersionList.getTotal());
            param.put("pageSize", tbModVersionList.getPageSize());
            param.put("list", tbModVersionList.getList());
            param.put("success", true);
        } catch (Exception e) {
            logger.error("数据查询失败" + e.getMessage(), e);
            param.put("success", false);
            param.put("messges", "数据查询失败");
        }
        return param;
    }

}
