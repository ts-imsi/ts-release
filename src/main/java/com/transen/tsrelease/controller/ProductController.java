package com.transen.tsrelease.controller;

import cn.trasen.core.entity.Result;
import com.transen.tsrelease.model.TreeVo;
import com.transen.tsrelease.model.TbProduct;
import com.transen.tsrelease.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/23
 */
@RestController
@RequestMapping(value="/product")
public class ProductController {


    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;

    @PostMapping(value="/getProductTree")
    public Result getProductTree(){
        Result result=new Result();
        try{
            TbProduct tbProduct = productService.selectProParent();
            TreeVo treeVo = productService.selectProTree(tbProduct);
            List<TreeVo> list = new ArrayList<>();
            list.add(treeVo);
            result.setObject(list);
            result.setSuccess(true);
        }catch (Exception e){
            logger.error("产品模块树查询失败"+e.getMessage(),e);
            result.setSuccess(false);
            result.setMessage("产品模块树查询失败");
        }
        return result;
    }

    @PostMapping(value="/selectProList")
    public Result selectProList(){
        Result result=new Result();
        try{
            TbProduct tbProduct = productService.selectProParent();
            TreeVo treeVo = productService.selectProList(tbProduct);
            List<TreeVo> list = new ArrayList<>();
            list.add(treeVo);
            result.setObject(list);
            result.setSuccess(true);
        }catch (Exception e){
            logger.error("产品树查询失败"+e.getMessage(),e);
            result.setSuccess(false);
            result.setMessage("产品树查询失败");
        }
        return result;
    }

}
