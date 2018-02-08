package com.transen.tsrelease.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.transen.tsrelease.common.GlobalCache;
import com.transen.tsrelease.model.HospitalVo;
import com.transen.tsrelease.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/8
 */
@Service
public class HospitalService {

    private final static Logger logger = LoggerFactory.getLogger(HospitalService.class);

    @Autowired
    Environment environment;

    private GlobalCache globalCache = GlobalCache.getGlobalCache();

    public List<HospitalVo> selectHospitalList(){
        List<HospitalVo> list=new ArrayList<>();
        if(globalCache.getHospMap()==null){
            Map<String,Object> hostMap=selectHospital();
            if(Boolean.valueOf(hostMap.get("success").toString())){
                list=(List<HospitalVo>)hostMap.get("list");
                ConcurrentHashMap<String,List<HospitalVo>> hashMap=new ConcurrentHashMap<>();
                hashMap.put("hosList",list);
                globalCache.setHospMap(hashMap);

            }else{
                logger.info("===============医院数据接口"+hostMap.get("message"));
            }
        }else{
            list= globalCache.getHospMap().get("hosList");
        }
        return list;
    }

    public Map<String,Object> selectHospital(){
        Map<String,Object> hosMap=new HashMap<>();
        String jira_hospital=environment.getProperty("jira_hospital");
        Optional<String> jiHos=Optional.ofNullable(jira_hospital);
        List<HospitalVo> list=new ArrayList<>();
        if(jiHos.isPresent()){
            String json=HttpUtil.connectURL(jiHos.get(),"","POST");
            JSONObject jsonObject= (JSONObject)JSONObject.parse(json);
            if(jsonObject.getBoolean("success")){
                String  jsonString=jsonObject.getString("object");
                if(jsonString!=null) {
                    list = JSON.parseArray(jsonString, HospitalVo.class);
                    hosMap.put("list",list);
                    hosMap.put("success",true);
                }else{
                    hosMap.put("message","医院接口数据为空");
                    hosMap.put("success",false);
                }

            }else{
                hosMap.put("success",false);
                hosMap.put("message","数据查询失败");
            }
        }else{
            hosMap.put("success",false);
            hosMap.put("message","jira_hospital变量配置失败");
        }
        return hosMap;
    }
}
