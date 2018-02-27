package cn.trasen.tsrelease;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2017/9/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class RecordServiceTest {

    @Autowired
//    private RecordService recordService;

    @Test
    @Rollback(false)
    public void save(){
//        TbJfRank tbJfRank=new TbJfRank();
//        tbJfRank.setName("资深");
//        tbJfRank.setType("五级");
//        tbJfRank.setPrmScore(10000);
//        tbJfRank.setScore(1000000);
//        tbJfRank.setPx(20);
//        recordService.saveRecordRank(tbJfRank);
    }
}
