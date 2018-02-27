package cn.trasen.tsrelease.controller;

import cn.trasen.core.entity.Result;
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

    @PostMapping(value = "/selectModList")
    public Map<String, Object> selectModList(@RequestBody Map<String, String> params) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (params.get("page") == null || params.get("rows") == null) {
            param.put("message", "参数错误");
            param.put("success", false);
            return param;
        }
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
        return param;
    }

    @PostMapping("/deletePro")
    public Map<String, Object> deletePro(@RequestBody TbProduct tbProduct) {
        Map<String, Object> param = new HashMap<String, Object>();

        param.put("pkid", tbProduct.getPkid());
        param.put("success", true);
        param.put("message", "删除成功");
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
        TbProduct product = new TbProduct();
        product.setType(params.get("type"));
        if (params.get("parent") != null) {
            product.setParent(Integer.valueOf(params.get("parent")));
        }
        List<TbProduct> proModList = productService.selectProduct(product);
        param.put("proModList", proModList);
        param.put("success", true);
        param.put("messges", "查询成功");
        return param;
    }

    /**
     * 产品编辑
     */
    @PostMapping("/updateProduct")
    public Map<String, Object> updatePro(@RequestBody TbProduct product) {
        Map<String, Object> param = new HashMap<String, Object>();
        int i = 0;
        String message = "";
        if (product.getPkid() != null) {
            product.setUpdated(new Date());
            i = productService.updateProduct(product);
            message = "修改";
        } else {
            product.setCreated(new Date());
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
        return param;
    }

}
