package com.transen.tsrelease.controller;

import com.github.pagehelper.PageInfo;
import com.transen.tsrelease.model.TbJfRank;
import com.transen.tsrelease.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2017/9/13
 */
@RestController
@RequestMapping(value="/record")
public class RecordController {

    private final static Logger logger = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private RecordService recordService;

    @RequestMapping(value="/getRecordRankList",method = RequestMethod.POST)
    public Map<String,Object> getRecordRankList(@RequestBody Map<String,String> params){
        Map<String,Object> param=new HashMap<String,Object>();
        if(params.get("page")==null||params.get("rows")==null){
            param.put("messges","参数错误");
            param.put("success",false);
            return param;
        }
        PageInfo<TbJfRank> tbJfRankPageInfo= recordService.getRecordRankList(Integer.valueOf(params.get("page")),Integer.valueOf(params.get("rows")));
        logger.info("数据查询条数"+tbJfRankPageInfo.getList().size());
        param.put("totalPages",tbJfRankPageInfo.getPages());
        param.put("pageNo",tbJfRankPageInfo.getPageNum());
        param.put("totalCount",tbJfRankPageInfo.getTotal());
        param.put("pageSize",tbJfRankPageInfo.getPageSize());
        param.put("list",tbJfRankPageInfo.getList());
        return param;
    }

    @RequestMapping(value="/getRecordRankList/{page}/{rows}",method = RequestMethod.GET)
    public Map<String,Object> getRecordRankList(@PathVariable String page, @PathVariable String rows){
        Map<String,Object> param=new HashMap<String,Object>();
        if(page==null||rows==null){
            param.put("messges","参数错误");
            param.put("success",false);
            return param;
        }
        PageInfo<TbJfRank> tbJfRankPageInfo= recordService.getRecordRankList(Integer.valueOf(page),Integer.valueOf(rows));
        logger.info("数据查询条数"+tbJfRankPageInfo.getList().size());
        param.put("totalPages",tbJfRankPageInfo.getPages());
        param.put("pageNo",tbJfRankPageInfo.getPageNum());
        param.put("totalCount",tbJfRankPageInfo.getTotal());
        param.put("pageSize",tbJfRankPageInfo.getPageSize());
        param.put("list",tbJfRankPageInfo.getList());
        return param;
    }
}
