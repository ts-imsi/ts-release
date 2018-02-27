package com.transen.tsrelease;

import com.transen.tsrelease.model.TbProModule;
import com.transen.tsrelease.service.ProModuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ProModuleServiceTest {

    @Autowired
    ProModuleService proModuleService;

    @Test
    @Rollback(true)
    public void test() {
        // 测试成功
//        proModuleService.selectProModule();
        TbProModule proModule = new TbProModule();
        proModule.setModId(2);
        proModule.setModName("12");
        // proModuleService.deleteProModule(proModule);
//        proModuleService.insertProModule(proModule);

        proModuleService.updateProModule(proModule);
    }

}
