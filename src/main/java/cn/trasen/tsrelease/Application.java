package cn.trasen.tsrelease;


import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/2
 */
@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = "cn.trasen.tsrelease.dao")
public class Application implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    AttenceBootstrap attenceBootstrap;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       // attenceBootstrap.startService();
        logger.info("服务启动完成!");
    }
}
