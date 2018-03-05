package cn.trasen.tsrelease.controller;

import cn.trasen.core.entity.Result;
import cn.trasen.tsrelease.model.TbProModule;
import cn.trasen.tsrelease.model.TreeVo;
import com.github.pagehelper.PageInfo;
import cn.trasen.tsrelease.model.TbProduct;
import cn.trasen.tsrelease.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/23
 */
@RestController
@RequestMapping(value = "/product")
public class ProductController {


    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    /**
     * @author luoyun
     * @Description 产品模块树
     */
    @PostMapping(value = "/getProductTree")
    public Result getProductTree() {
        Result result = new Result();
        try {
            TbProduct tbProduct = productService.selectProParent();
            TreeVo treeVo = productService.selectProTree(tbProduct);
            List<TreeVo> list = new ArrayList<>();
            list.add(treeVo);
            result.setObject(list);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("产品模块树查询失败" + e.getMessage(), e);
            result.setSuccess(false);
            result.setMessage("产品模块树查询失败");
        }
        return result;
    }

    /**
     * @author luoyun
     * @Description 产品树查询
     */
    @PostMapping(value = "/selectProList")
    public Result selectProList() {
        Result result = new Result();
        try {
            TbProduct tbProduct = productService.selectProParent();
            TreeVo treeVo = productService.selectProList(tbProduct);
            List<TreeVo> list = new ArrayList<>();
            list.add(treeVo);
            result.setObject(list);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("产品树查询失败" + e.getMessage(), e);
            result.setSuccess(false);
            result.setMessage("产品树查询失败");
        }
        return result;
    }

    @PostMapping(value = "/getProModuleList")
    public Result getProModuleList() {
        Result result = new Result();
        try {
            List<TbProModule> tbProModules = productService.getProModuleList();
            result.setObject(tbProModules);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error("模块数据获取失败" + e.getMessage(), e);
            result.setMessage("模块数据获取失败");
            result.setSuccess(false);
        }
        return result;
    }

    @PostMapping(value = "/selectModList")
    public Map<String, Object> selectModList(@RequestBody Map<String, String> params) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (params.get("page") == null || params.get("rows") == null) {
            param.put("message", "参数错误");
            param.put("success", false);
            return param;
        }
        try {
            TbProduct product = new TbProduct();
            product.setType(params.get("type"));
            // name 在此做模糊查询用
            if (params.get("name") != null) {
                product.setName(params.get("name"));
                product.setType(null);
            }
            PageInfo<TbProduct> mod_list = productService.selectProduct(Integer.valueOf(params.get("page")), Integer.valueOf(params.get("rows")), product);
            param.put("数据查询条数", mod_list.getSize());
            param.put("totalPages", mod_list.getPages());
            param.put("pageNo", mod_list.getPageNum());
            param.put("totalCount", mod_list.getTotal());
            param.put("pageSize", mod_list.getPageSize());
            param.put("list", mod_list.getList());
            param.put("success", true);
        }catch (Exception e) {
            logger.error("数据获取失败" + e.getMessage(), e);
            param.put("message","数据获取失败");
            param.put("success", false);
        }
        return param;
    }

    /**
     * 负责查询 产品和模块
     */
    @PostMapping("/selectProMod")
    public Map<String, Object> selectProMod(@RequestBody Map<String, String> params) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (params.get("type") == null) {
            param.put("message", "参数错误");
            param.put("success", false);
            return param;
        }
        try{
            TbProduct product = new TbProduct();
            product.setType(params.get("type"));
            if (params.get("parent") != null) {
                product.setParent(Integer.valueOf(params.get("parent")));
            }
            List<TbProduct> proModList = productService.selectProduct(product);
            param.put("proModList", proModList);
            param.put("success", true);
            param.put("messges", "查询成功");
        }catch (Exception e) {
            logger.error("数据获取失败" + e.getMessage(), e);
            param.put("message","数据获取失败");
            param.put("success", false);
        }
        return param;
    }

    /**
     * 产品保存
     */
    @PostMapping("/updateProduct")
    public Map<String, Object> updatePro(@RequestBody TbProduct product) {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = 0;
        String message = "";
        Date date = new Date();
        try {
            if (product.getPkid() != null) {
                product.setUpdated(date);
                i = productService.updateProduct(product);
                message = "修改";
            } else {
                product.setCreated(date);
                i = productService.insertProduct(product);
                message = "新增";
            }
            if (i > 0) {
                param.put("success", true);
                param.put("message", message + "成功");
            } else {
                param.put("success", false);
                param.put("message", message + "失败");
            }
        }catch (Exception e) {
            logger.error("数据编辑失败" + e.getMessage(), e);
            param.put("message","数据编辑失败");
            param.put("success", false);
        }
        return param;
    }

    /**
     * 删除产品 但当存在子模块时，取消删除
     */
    @PostMapping("deletePro")
    public Map<String, Object> updateProVaild(@RequestBody TbProduct product) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (product.getPkid() == null) {
            param.put("message", "参数错误");
            param.put("success", false);
            return param;
        } else {
            int cout = product.getPkid();// 记录产品主键
            TbProduct product_new = new TbProduct(); //该产品为了查询子模块个数
            product_new.setParent(cout);
            try {
                List<TbProduct> moduleList = productService.selectProduct(product_new);
                cout = moduleList.size();// 统计模块个数
                if (cout > 0) {
                    param.put("message", "该产品有" + cout + "个模块，不能删除");
                    param.put("success", false);
                } else {
                    product.setIsVaild(0);
                    product.setUpdated(new Date());
                    productService.updateProduct(product);
                    param.put("message", "删除成功");
                    param.put("success", true);
                }
            }catch (Exception e) {
                logger.error("数据编辑失败" + e.getMessage(), e);
                param.put("message","数据编辑失败");
                param.put("success", false);
            }
            return param;
        }
    }
}
