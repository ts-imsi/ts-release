package cn.trasen.tsrelease.controller;

import cn.trasen.core.entity.Result;
import cn.trasen.tsrelease.model.HospitalVo;
import cn.trasen.tsrelease.model.TreeVo;
import cn.trasen.tsrelease.service.HospitalService;
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
 * @date 2018/2/8
 */
@RestController
@RequestMapping("/jiarHos")
public class HospitalController {

    private final static Logger logger = LoggerFactory.getLogger(HospitalController.class);

    @Autowired
    HospitalService hospitalService;

    @PostMapping(value="/selectHospitalList")
    public Result selectHospitalList(){
        Result result=new Result();
        try{
            List<HospitalVo> hospitalVos=hospitalService.selectHospitalList();
            result.setObject(hospitalVos);
            result.setSuccess(true);
        }catch (Exception e){
            logger.error("医院数据查询失败"+e.getMessage(),e);
            result.setSuccess(false);
            result.setMessage("医院数据查询失败");
        }
        return result;
    }

    @PostMapping(value="/selectHospitalTreeList")
    public Result selectHospitalTreeList(){
        Result result=new Result();
        try{
           TreeVo treeVo =hospitalService.selectHospitalTreeList();
            List<TreeVo> list = new ArrayList<>();
            list.add(treeVo);
            result.setObject(list);
            result.setSuccess(true);
        }catch (Exception e){
            logger.error("医院树生成失败"+e.getMessage(),e);
            result.setSuccess(false);
            result.setMessage("医院树生成失败");
        }
        return result;
    }
}
