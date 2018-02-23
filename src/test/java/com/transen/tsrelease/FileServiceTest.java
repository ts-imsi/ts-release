package com.transen.tsrelease;

import com.transen.tsrelease.model.TbFile;
import com.transen.tsrelease.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * Created by zhangxiahui on 18/2/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class FileServiceTest {

    @Autowired
    FileService fileService;

    @Test
    public void unzip(){
        File file = new File("/Users/zhangxiahui/Downloads/temp/打包平台.zip");
        try {
            TbFile tbFile = fileService.unzip(file,"/Users/zhangxiahui/Downloads/temp/version-file");
            System.out.println(tbFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback(false)
    public void saveFile(){
        File file = new File("/Users/zhangxiahui/Downloads/temp/打包平台.zip");
        fileService.saveFile(file);
        System.out.println("完成");
    }
}
