package cn.trasen.tsrelease.controller;

import cn.trasen.core.entity.Result;
import cn.trasen.tsrelease.model.TbFile;
import cn.trasen.tsrelease.service.FileService;
import com.github.pagehelper.PageInfo;
import cn.trasen.tsrelease.model.TbIndividuality;
import cn.trasen.tsrelease.service.IndividualityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/24
 */
@RestController
@RequestMapping(value="/individuality")
public class IndividualityController {
    private final static Logger logger = LoggerFactory.getLogger(IndividualityController.class);


    @Autowired
    IndividualityService individualityService;

    @Autowired
    FileService fileService;

    /**
     * @author luoyun
     * @Description 获取个性化数据，分页
     * @param param 包括 page 当前页，rows 行数  hospitalName 医院名称
     */
    @PostMapping(value="/getIndividualityList")
    public Map<String,Object> getIndividualityList(@RequestBody Map<String,String> param){
        Map<String,Object> result=new HashMap<>();
        try{
            if(param.get("page")==null||param.get("rows")==null||param.get("hospitalName")==null){
                result.put("message","参数错误");
                result.put("success",false);
                return result;
            }
            PageInfo<TbIndividuality> individualityPageInfo=individualityService.getIndividualityList(Integer.valueOf(param.get("page")),Integer.valueOf(param.get("rows")),param.get("hospitalName").toString());
            logger.info("数据查询条数"+individualityPageInfo.getList().size());
            result.put("totalPages",individualityPageInfo.getPages());
            result.put("pageNo",individualityPageInfo.getPageNum());
            result.put("totalCount",individualityPageInfo.getTotal());
            result.put("pageSize",individualityPageInfo.getPageSize());
            result.put("list",individualityPageInfo.getList());
            result.put("success",true);
        }catch (Exception e){
            logger.error("数据查询失败"+e.getMessage(),e);
            result.put("success",false);
            result.put("message","数据查询失败");
        }
        return result;
    }

    /**
     * @author luoyun
     * @Description 个性化文件上传，单个或者多个文件
     */
    @RequestMapping(value = "IndividualityUpload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result upload (@RequestParam(value = "files") MultipartFile[] files,
                          HttpServletRequest request, HttpServletResponse response) {
        Result result = new Result();
        try {
            Optional<String> reOp=Optional.ofNullable(request.getParameter("remark"));
            Optional<String> nameOp=Optional.ofNullable(request.getParameter("name"));
            Optional<String> modIdOp=Optional.ofNullable(request.getParameter("modId"));
            Optional<String> modNameOp=Optional.ofNullable(request.getParameter("modName"));
            Optional<String> hospitalNameOp=Optional.ofNullable(request.getParameter("hospital"));
            Optional<String> typeOp=Optional.ofNullable(request.getParameter("fileType"));
            TbIndividuality tbIndividuality=new TbIndividuality();

            tbIndividuality.setName(nameOp.get());
            tbIndividuality.setCreated(new Date());
            tbIndividuality.setModId(Integer.valueOf(modIdOp.get()));
            tbIndividuality.setModName(modNameOp.get());
            tbIndividuality.setHospital(hospitalNameOp.get());
            tbIndividuality.setRemark(reOp.get());
            boolean boo=individualityService.saveFileAndInviduality(files,typeOp.get(),tbIndividuality);
            if(boo){
                result.setMessage("数据保存成功");
                result.setSuccess(true);
            }else{
                result.setMessage("数据保存失败");
                result.setSuccess(false);
            }
        } catch (Exception e) {
            result.setMessage("数据保存失败");
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @author luoyun
     * @Description 个性化文件下载
     */
    @GetMapping(value="/downloadFile/{pkid}")
    public void downloadFile(@PathVariable Integer pkid, HttpServletRequest request, HttpServletResponse response){

        try{
            TbIndividuality tbIndividuality=individualityService.getIndividuality(pkid);
            List<TbFile> tbFiles=new ArrayList<>();
            if(tbIndividuality.getFileId()!=null){
                String ids=tbIndividuality.getFileId();
                String[] idArray=ids.split(",");
                List<String> idList=Arrays.stream(idArray).collect(Collectors.toList());
                tbFiles=fileService.getTbFileById(idList);
            }

            //HttpServletResponse res = getResponse(); //创建response回应
            response.setContentType("text/html; charset=UTF-8"); //设置编码字符
            response.setContentType("application/x-msdownload"); //设置内容类型为下载类型
            OutputStream out = response.getOutputStream();   //创建页面返回方式为输出流，会自动弹出下载框
            List<String> stringList=tbFiles.stream().map(n->n.getUrl()).collect(Collectors.toList());
            if(stringList.size()>1){
                response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(tbIndividuality.getName(),"UTF-8")+".zip");//设置下载的文件名称
                fileService.MultipleFileDown(tbIndividuality.getName(),stringList,tbFiles.get(0).getType(),out);
            }else{

                String extensionName = StringUtils.substringAfter(stringList.get(0), ".");
                response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(tbIndividuality.getName(),"UTF-8")+"."+extensionName);//设置下载的文件名称
                fileService.signFileDown(stringList.get(0),out);
            }

        }catch (Exception e){
            logger.error("个性化过程文件下载失败"+e.getMessage(),e);
        }
    }
}
