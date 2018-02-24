package com.transen.tsrelease.controller;

import com.github.pagehelper.PageInfo;
import com.transen.tsrelease.model.TbIndividuality;
import com.transen.tsrelease.service.IndividualityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/24
 */
@RestController
@RequestMapping(value="/individuality")
public class IndividualityController {
    private final static Logger logger = LoggerFactory.getLogger(IndividualityController.class);


    @Autowired
    IndividualityService individualityService;

    @PostMapping(value="/getIndividualityList")
    public Map<String,Object> getIndividualityList(@RequestBody Map<String,String> param){
        Map<String,Object> result=new HashMap<>();
        try{
            if(param.get("page")==null||param.get("rows")==null||param.get("hospitalName")==null){
                result.put("message","参数错误");
                result.put("success",false);
                return result;
            }
            PageInfo<TbIndividuality> individualityPageInfo=individualityService.getIndividualityList(Integer.valueOf(param.get("page")),Integer.valueOf(param.get("rows")),param.get("hospitalName").toString());
            logger.info("数据查询条数"+individualityPageInfo.getList().size());
            result.put("totalPages",individualityPageInfo.getPages());
            result.put("pageNo",individualityPageInfo.getPageNum());
            result.put("totalCount",individualityPageInfo.getTotal());
            result.put("pageSize",individualityPageInfo.getPageSize());
            result.put("list",individualityPageInfo.getList());
            result.put("success",true);
        }catch (Exception e){
            logger.error("数据查询失败"+e.getMessage(),e);
            result.put("success",false);
            result.put("message","数据查询失败");
        }
        return result;
    }
}
