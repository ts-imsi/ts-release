package cn.trasen.tsrelease;

import cn.trasen.tsrelease.service.ModRelyService;
import cn.trasen.tsrelease.model.TbModRely;
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
public class ModRelyServiceTest {
    @Autowired
    private ModRelyService services;

    @Test
    @Rollback(true)
    public void test(){
        // 测试成功
       // services.select();
        TbModRely modRely = new TbModRely();
//        modRely.setPkid(1);
        modRely.setPreModName("xxx");
//        modRely.set
//        services.deleteModRely(modRely);
//        services.updateModRely(modRely);
//        services.insertModRely(modRely);
    }
}
