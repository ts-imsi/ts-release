package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbFileMapper;
import cn.trasen.tsrelease.model.TbFile;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by zhangxiahui on 18/2/8.
 */
@Service
public class FileService {

    private final static Logger logger = LoggerFactory.getLogger(FileService.class);

    @Autowired
    TbFileMapper tbFileMapper;

    @Autowired
    Environment env;

    /**
     * 解压指定的ZIP压缩文件
     * <p>
     *
     * @param zipFile 指定的ZIP压缩文件
     * @return 解压后文件数组
     * @throws ZipException 压缩文件有损坏或者解压缩失败抛出
     */
    public TbFile unzip(File zipFile, String dest) throws ZipException,Exception {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset("utf-8");
        File destDir = new File(dest);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }
        zFile.extractAll(dest);
        @SuppressWarnings("unchecked")
        List<FileHeader> headerList = zFile.getFileHeaders();
        List<File> extractedFileList = new ArrayList<>();
        Map<String,TbFile> fileMap = new HashMap<>();
        for(FileHeader fileHeader : headerList) {
            TbFile tbFile = clonFile(fileHeader);
            fileMap.put(tbFile.getUrl(),tbFile);
            if (!fileHeader.isDirectory()) {
                extractedFileList.add(new File(destDir,fileHeader.getFileName()));
            }
        }
        File [] extractedFiles = new File[extractedFileList.size()];
        extractedFileList.toArray(extractedFiles);
        return handleFileMap(fileMap);
    }


    public TbFile clonFile(FileHeader fileHeader){
        TbFile model = new TbFile();
        String [] files = fileHeader.getFileName().split("/");
        String name = files[files.length-1];
        String pName = null;
        if(files.length>1){
            pName = files[files.length-2];
        }
        String url = fileHeader.getFileName();
        String type = "folder";
        if(!"/".equals(url.substring(url.length()-1,url.length()))){
            String [] istype = name.split("\\.");
            if(istype.length>1){
                type = istype[istype.length-1];
            }
        }
        model.setName(name);
        model.setPName(pName);
        model.setLevel(files.length);
        model.setType(type);
        model.setUrl(fileHeader.getFileName());
        model.setSize(fileHeader.getUncompressedSize());
        return model;
    }

    public TbFile handleFileMap(Map<String,TbFile> fileMap){
        Set<String> set = fileMap.keySet();
        String parKey = null;
        for(String key : set){
            TbFile tbFile = fileMap.get(key);
            if(tbFile.getLevel()==1){
                parKey = key;
                continue;
            }
            String url = tbFile.getUrl();
            if ("folder".equals(tbFile.getType())){
                url = url.substring(0,url.length()-1);
            }
            String parentUrl = url.substring(0,url.lastIndexOf("/"))+"/";
            TbFile parentFile = fileMap.get(parentUrl);
            if(parentFile.getFiles()==null){
                List<TbFile> list = new ArrayList<>();
                list.add(tbFile);
                parentFile.setFiles(list);
            }else{
                parentFile.getFiles().add(tbFile);
            }
        }
        return fileMap.get(parKey);
    }

    public TbFile saveZipFile(File zipFile,String type){
        TbFile fileTemp = null;
        // TODO: 18/2/24 从配置文件取值
        //release-file/release-package   发布包路径
        //release-file/version-file      版本包路径
        //release-file/private-file      个性化包路径
        //release-file/public-file       公共包路径
        //release-file/interface-file    接口文件路径
        String destUrl = "/Users/zhangxiahui/Downloads/temp/version-file";
        TbFile tbFile = null;
        try {
             tbFile = unzip(zipFile,destUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(tbFile!=null){
            insertTbFile(tbFile);
            fileTemp = tbFileMapper.getFileToUrl(tbFile.getUrl());
        }
        return fileTemp;
    }

    public void insertTbFile(TbFile tbFile){
        TbFile fileTemp = tbFileMapper.getFileToUrl(tbFile.getUrl());
        if(fileTemp!=null){
            Integer pkid = fileTemp.getPkid();
            if(tbFile.getFiles()!=null&&tbFile.getFiles().size()>0){
                List<TbFile> list = tbFile.getFiles();
                for(TbFile file : list){
                    file.setPId(pkid);
                    // TODO: 18/2/23 做完鉴权
                    file.setOperator("system");
                }
                Map<String,Object> param = new HashMap<>();
                param.put("list",list);
                tbFileMapper.saveFile(param);
                for (TbFile file : list){
                    insertTbFile(file);
                }
            }
        }else{
            tbFileMapper.saveFileOne(tbFile);
            insertTbFile(tbFile);
        }


    }

    @Transactional(rollbackFor = Exception.class)
    public TbFile saveFileOne(MultipartFile file,String type,String name){
        TbFile tbFile=new TbFile();
        String fileName=null;
        String filePath=null;
        try{
            // 获取文件名
             fileName = file.getOriginalFilename();
            // 获取的扩展名
            String extensionName = StringUtils.substringAfter(fileName, ".");
            // 数据库保存的目录
            // 文件路径
            filePath = env.getProperty("saveFileUrl");
            filePath=filePath+type+"/"+name;
            logger.info("文件保存路径为:"+filePath);

            File dest = new File(filePath, fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(dest));
            stream.write(bytes);
            stream.close();
        }catch (Exception e){
            logger.error("文件存储失败"+e.getMessage(),e);
            return tbFile;
        }
        tbFile.setName(fileName);
        tbFile.setCreated(new Date());
        tbFile.setSize(file.getSize());
        tbFile.setLevel(0);
        tbFile.setType(type);
        //todo 鉴权
        tbFile.setOperator("system");
        tbFile.setUrl(filePath+"/"+fileName);
        if(tbFile!=null){
            tbFileMapper.saveFileOneByPkid(tbFile);
        }
        return tbFile;
    }

    /*
    * 多文件下载
    * */
    public void MultipleFileDown(String name,List<String> urls,String fileType,OutputStream out){
        InputStream fis=null;
        String zipFilePath=null;
        try{
            String zipBasePath = env.getProperty("saveFileUrl");
            zipFilePath=zipBasePath+fileType+"/"+name+".zip";

            //创建文件路径的集合，
            List<String> filePath = urls;

            //根据临时的zip压缩包路径，创建zip文件
            File zip = new File(zipFilePath);
            if (!zip.exists()){
                zip.createNewFile();
            }

            //创建zip文件输出流
            FileOutputStream fos = new FileOutputStream(zip);
            ZipOutputStream zos = new ZipOutputStream(fos);

            //循环读取文件路径集合，获取每一个文件的路径
            filePath.stream().forEach(n->{
                File f = new File(n);  //根据文件路径创建文件
                zipFile(f, zos);  //将每一个文件写入zip文件包内，即进行打包
            });
            zos.close();
            fos.close();

            //将打包后的文件写到客户端，输出的方法同上，使用缓冲流输出
            fis= new BufferedInputStream(new FileInputStream(zipFilePath));
            byte[] buff = new byte[4096];
            int size = 0;
            while((size=fis.read(buff)) != -1){
                out.write(buff, 0, size);
            }
        }catch (IOException e){
            logger.error("多个文件下载失败"+e.getMessage(),e);
        }finally {
            try{
                out.flush();
                out.close();
                fis.close();
                new File(zipFilePath).delete();
            }catch (IOException e){
                logger.error("流关闭失败"+e.getMessage(),e);
            }

        }
    }

    /*
    * 单文件下载
    * */
    public void signFileDown(String urs,OutputStream out){
        FileInputStream fis=null;
        BufferedInputStream buff=null;
        try{
            File file = new File(urs);  //创建文件
            fis=new FileInputStream(urs);  //创建文件字节输入流
            buff=new BufferedInputStream(fis); //创建文件缓冲输入流
            byte [] b=new byte[2048];  //设置每次读取大小
            long l=0;  //用于判断是否等同于文件的长度，即文件下载大小
            while(l<file.length()){  //循环读取文件
                int j=buff.read(b,0,2048); //使用缓冲流读取数据,返回缓冲区长度
                l+=j;
                out.write(b,0,j);//将缓存写入客户端
            }
        }catch (IOException e){
            logger.error("单个文件下载失败"+e.getMessage(),e);
        }finally {
            try {
                out.flush();
                out.close();
                fis.close();
                buff.close();
            } catch (IOException e) {
                logger.error("流关闭失败"+e.getMessage(),e);
            }
        }

    }

    /*
    * 封装压缩方法
    * */
    public  void zipFile(File inputFile,ZipOutputStream zipoutputStream) {
        try {
            if(inputFile.exists()) { //判断文件是否存在
                if (inputFile.isFile()) {  //判断是否属于文件，还是文件夹

                    //创建输入流读取文件
                    FileInputStream fis = new FileInputStream(inputFile);
                    BufferedInputStream bis = new BufferedInputStream(fis);

                    //将文件写入zip内，即将文件进行打包
                    ZipEntry ze = new ZipEntry(inputFile.getName()); //获取文件名
                    zipoutputStream.putNextEntry(ze);

                    //写入文件的方法，同上
                    byte [] b=new byte[1024];
                    long l=0;
                    while(l<inputFile.length()){
                        int j=bis.read(b,0,1024);
                        l+=j;
                        zipoutputStream.write(b,0,j);
                    }
                    //关闭输入输出流
                    bis.close();
                    fis.close();
                } else {  //如果是文件夹，则使用穷举的方法获取文件，写入zip
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], zipoutputStream);
                        }
                    } catch (Exception e) {
                        logger.error("多文件压缩失败"+e.getMessage(),e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("多文件压缩失败"+e.getMessage(),e);
        }
    }

    public List<TbFile> getTbFileById(List<String> pkid){
        return tbFileMapper.getFileByPkid(pkid);
    }


}
