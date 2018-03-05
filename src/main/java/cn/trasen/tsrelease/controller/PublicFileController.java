package cn.trasen.tsrelease.controller;

import cn.trasen.core.entity.Result;
import cn.trasen.tsrelease.model.TbIndividuality;
import cn.trasen.tsrelease.model.TbPublicFile;
import cn.trasen.tsrelease.service.PublicFileService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/3/5
 */
@RestController
@RequestMapping(value="/publicFile")
public class PublicFileController {

    private final static Logger logger = LoggerFactory.getLogger(PublicFileController.class);

    @Autowired
    PublicFileService publicFileService;

    @PostMapping(value="/selectPublicFileList")
    public Map<String,Object> selectPublicFileList(@RequestBody Map<String,String> param){
        Map<String,Object> result=new HashMap<>();
        try{
            if(param.get("page")==null||param.get("rows")==null){
                result.put("message","参数错误");
                result.put("success",false);
                return result;
            }
            PageInfo<TbPublicFile> tbPublicFilePageInfo=publicFileService.selectPublicFileList(Integer.valueOf(param.get("page")),Integer.valueOf(param.get("rows")),param.get("name"));
            logger.info("数据查询条数"+tbPublicFilePageInfo.getList().size());
            result.put("totalPages",tbPublicFilePageInfo.getPages());
            result.put("pageNo",tbPublicFilePageInfo.getPageNum());
            result.put("totalCount",tbPublicFilePageInfo.getTotal());
            result.put("pageSize",tbPublicFilePageInfo.getPageSize());
            result.put("list",tbPublicFilePageInfo.getList());
            result.put("success",true);
        }catch (Exception e){
            logger.error("数据查询失败"+e.getMessage(),e);
            result.put("success",false);
            result.put("message","数据查询失败");
        }
        return result;
    }

    @RequestMapping(value = "/publicFileUpload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result publicFileUpload(@RequestParam(value = "files") MultipartFile[] files,
                                   HttpServletRequest request, HttpServletResponse response){
        Result result = new Result();
        try {
            Optional<String> reOp=Optional.ofNullable(request.getParameter("remark"));
            Optional<String> nameOp=Optional.ofNullable(request.getParameter("name"));
            Optional<String> typeOp=Optional.ofNullable(request.getParameter("fileType"));
            TbPublicFile tbPublicFile=new TbPublicFile();

            tbPublicFile.setName(nameOp.get());
            tbPublicFile.setCreated(new Date());
            tbPublicFile.setRemark(reOp.get());
            boolean boo=publicFileService.saveFileAndPublicFile(files,typeOp.get(),tbPublicFile);
            if(boo){
                result.setMessage("数据保存成功");
                result.setSuccess(true);
            }else{
                result.setMessage("数据保存失败");
                result.setSuccess(false);
            }
        } catch (Exception e) {

            logger.error("数据保存失败"+e.getMessage(),e);
            result.setMessage("数据保存失败");
            result.setSuccess(false);
        }
        return result;
    }

}
