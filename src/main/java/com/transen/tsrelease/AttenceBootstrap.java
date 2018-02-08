package com.transen.tsrelease;

import com.transen.tsrelease.task.JiraHospitalTask;
import com.transen.tsrelease.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/8
 */
@Component
@Scope("singleton")
public class AttenceBootstrap {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    Environment environment;

    @Autowired
    JiraHospitalTask jiraHospitalTask;

    public  void startService(){
        try {
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(9);
            String count_time = environment.getProperty("");
            Integer time = 5;
            if (count_time != null) {
                time = Integer.parseInt(count_time);
            }
            scheduler.scheduleAtFixedRate(jiraHospitalTask, 0, time, TimeUnit.MINUTES);
            logger.info("===========jiraHospitalTask 任务启动完成=========间隔=" + (time * 60 * 1000));
        }catch (Exception e){
            logger.error("启动失败"+e.getMessage(),e);
        }
    }

}
