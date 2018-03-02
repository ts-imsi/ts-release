package cn.trasen.tsrelease;

import cn.trasen.tsrelease.model.TbProModule;
import cn.trasen.tsrelease.model.TbProduct;
import cn.trasen.tsrelease.service.ProModuleService;
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
        TbProduct product = new TbProduct();
        product.setName("oa");
        product.setParent(17);
        product.setPkid(37);
        TbProModule proModule = new TbProModule();
       // proModule.setModId(2);
        proModule.setModName("oa");
        proModule.setProId(17);
        proModule.setModId(37);
//        proModuleService.insertProModule(product, proModule);
        // proModuleService.deleteProModule(proModule);
//        proModuleService.insertProModule(proModule);
        proModuleService.updateProModule(product, proModule);
    }

}
