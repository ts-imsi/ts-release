package cn.trasen.tsrelease.controller;

import cn.trasen.tsrelease.model.TbModVersion;
import cn.trasen.tsrelease.model.TbProModule;
import cn.trasen.tsrelease.model.TbProduct;
import cn.trasen.tsrelease.service.ModVersionService;
import cn.trasen.tsrelease.service.ProModuleService;
import cn.trasen.tsrelease.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.tools.doclint.Entity.nu;

@RestController
@RequestMapping("/proModule")
public class ProModuleController {

    @Autowired
    ProModuleService proModuleService;
    @Autowired
    ProductService productService;
    @Autowired
    ModVersionService modVersionService;

    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("saveProModule")
    public Map<String, Object> saveProModule(@RequestBody TbProModule proModule) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (proModule == null) {
            // 若没有获取到模块的标识，则不进行后面的操作
            param.put("success", false);
            param.put("message", "没有获取到模块的信息");
        }
        int i = 0;
        String mess = "";
        // 查询父产品的信息
        TbProduct module_parent = new TbProduct();
        module_parent.setPkid(proModule.getProId());
        try{
            List<TbProduct> productList = productService.selectProduct(module_parent);
            if (productList != null) {
                module_parent = productList.get(0);
                // 组装product表的模块
                TbProduct product_module = new TbProduct();
                product_module.setPkid(proModule.getModId());
                product_module.setName(proModule.getModName());
                product_module.setParent(proModule.getProId());
                product_module.setType("mod");
                product_module.setLevel(2);
                product_module.setDepId(module_parent.getDepId());
                product_module.setDepName(module_parent.getDepName());
                product_module.setOperator(proModule.getOperator());
                product_module.setCreated(proModule.getCreated());
                product_module.setIsVaild(1);

                // 操作
                proModule.setIsVaild(1);
                Date date = new Date();
                if (proModule.getModId() != null) {
                    product_module.setUpdated(date);
                    proModule.setUpdated(date);
                    i = proModuleService.updateProModule(product_module, proModule);
                    mess = "编辑";
                } else {
                    product_module.setCreated(date);
                    proModule.setCreated(date);
                    i = proModuleService.insertProModule(product_module, proModule);
                    mess = "新增";
                }
            } else {
                i = -1;
                mess = "操作";
            }

            if (i > 0) {
                param.put("success", true);
                param.put("message", mess + "模块成功");
            } else {
                param.put("success", false);
                param.put("message", mess + "模块失败");
            }
        }catch (Exception e) {
            logger.error("数据编辑失败" + e.getMessage(), e);
            param.put("message","数据编辑失败");
            param.put("success", false);
        }
        return param;
    }

    @PostMapping("proModuleList")
    public Map<String, Object> moduleList(@RequestBody Map<String, String> params) {
        //模块列表查询带搜索(名称和拼音码)和分页
        Map<String, Object> param = new HashMap<String, Object>();
        if (params.get("page") == null || params.get("rows") == null) {
            param.put("message", "参数错误");
            param.put("success", false);
            return param;
        }
        TbProModule proModule = new TbProModule();
        proModule.setModName(params.get("modName"));
        if(params.get("proid")!=null){
            proModule.setProId(Integer.valueOf(params.get("proid")));
        }
        try {
            PageInfo<TbProModule> proModulePageInfo = proModuleService.proModuleList(Integer.valueOf(params.get("page")), Integer.valueOf(params.get("rows")), proModule);
            param.put("数据查询条数", proModulePageInfo.getSize());
            param.put("totalPages", proModulePageInfo.getPages());
            param.put("pageNo", proModulePageInfo.getPageNum());
            param.put("totalCount", proModulePageInfo.getTotal());
            param.put("pageSize", proModulePageInfo.getPageSize());
            param.put("list", proModulePageInfo.getList());
            param.put("success", true);
        }catch (Exception e) {
            logger.error("数据查询失败" + e.getMessage(), e);
            param.put("message","数据查询失败");
            param.put("success", false);
        }
        return param;
    }

    /**
     * 删除模块，但存在模块版本时，取消删除
     */
    @PostMapping("delteModule")
    public Map<String, Object> deleteModule(@RequestBody TbProModule proModule) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (proModule == null) {
            param.put("message", "参数错误");
            param.put("success", false);
            return param;
        } else {
            int modId = proModule.getModId();
            TbModVersion modVersion = new TbModVersion();
            modVersion.setModId(modId);
            try {
                List<TbModVersion> modVersionList = modVersionService.selectModVersion(modVersion);
                if (modVersionList.size() > 0) {
                    // 该模块有版本关联 不予处理
                    param.put("message", "该模块有" + modVersionList.size() + "个版本，不能删除");
                    param.put("success", false);
                    return param;
                } else {
                    TbProduct module = new TbProduct();
                    module.setPkid(modId);
                    // 查询产品表的模块
                    List<TbProduct> moduleList = productService.selectProduct(module);
                    if (moduleList.size() > 0) {
                        // 修改两个模块信息
                        module = moduleList.get(0);
                        module.setIsVaild(0);
                        proModule.setIsVaild(0);
                        int i = proModuleService.updateProModule(module, proModule);
                        if (i > 0) {
                            param.put("success", true);
                            param.put("message", "删除成功");
                        } else {
                            param.put("success", false);
                            param.put("message", "删除失败");
                        }
                        return param;
                    }
                }
            }catch (Exception e) {
                logger.error("数据编辑失败" + e.getMessage(), e);
                param.put("message","数据编辑失败");
                param.put("success", false);
            }
        }


        return param;
    }
}
