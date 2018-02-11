package com.transen.tsrelease;

import com.transen.tsrelease.service.HospitalService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class HospitalServiceTest {

    @Autowired
    HospitalService hospitalService;

    @Test
    @Rollback(false)
    public void selectHospital(){
        Map<String,Object> tem=hospitalService.selectHospital();
        System.out.println(tem.get("success"));
    }
}
