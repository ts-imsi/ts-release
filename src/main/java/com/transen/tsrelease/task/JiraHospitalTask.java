package com.transen.tsrelease.task;

import com.transen.tsrelease.common.GlobalCache;
import com.transen.tsrelease.model.HospitalVo;
import com.transen.tsrelease.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/8
 */
@Component
public class JiraHospitalTask implements Runnable{
    private final static Logger logger = LoggerFactory.getLogger(JiraHospitalTask.class);
    private GlobalCache globalCache = GlobalCache.getGlobalCache();

    @Autowired
    HospitalService hospitalService;

    @Override
    public void run() {
        logger.info("======================进入缓存定时任务=====================");
        List<HospitalVo> listH=new ArrayList<>();
        Map<String,Object> hospital = hospitalService.selectHospital();
        if(Boolean.valueOf(hospital.get("success").toString())){
            listH=(List<HospitalVo>)hospital.get("list");
            ConcurrentHashMap<String,List<HospitalVo>> hashap=new ConcurrentHashMap<>();
            hashap.put("hosList",listH);
            globalCache.setHospMap(hashap);
        }else{
            logger.info("===============医院数据接口"+hospital.get("message"));
        }
        logger.info("======================医院数据加入缓存=====================");
    }
}
