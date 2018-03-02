package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbIndividualityMapper;
import cn.trasen.tsrelease.model.TbFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.trasen.tsrelease.model.TbIndividuality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 个性化业务类
 * @date 2018/2/24
 */
@Service
public class IndividualityService {

    private final static Logger logger = LoggerFactory.getLogger(IndividualityService.class);


    @Autowired
    TbIndividualityMapper tbIndividualityMapper;

    @Autowired
    FileService fileService;

    @Autowired
    Environment env;

    /**
     * @author luoyun
     * @Description 根据医院名称查询个性化数据，并分页
     */
    public PageInfo<TbIndividuality> getIndividualityList(Integer page,Integer rows,String hospitalName){
        PageHelper.startPage(page,rows);
        List<TbIndividuality> tbIndividualities=tbIndividualityMapper.getIndividualityList(hospitalName);
        PageInfo<TbIndividuality> pagehelper = new PageInfo<TbIndividuality>(tbIndividualities);
        return pagehelper;
    }

    /**
     * @author luoyun
     * @Description 保存数据到个性化表
     */
    public void saveIndividuality(TbIndividuality tbIndividuality){
         tbIndividualityMapper.saveIndividuality(tbIndividuality);
    }

    /**
     * @author luoyun
     * @Description 上传文件保存到服务器路径，保存个性化数据到数据库
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveFileAndInviduality(MultipartFile[] files,String type,TbIndividuality tbIndividuality){
        boolean boo=false;
        String pkid="";
        Long size=0L;
        if(files!=null){
            for(MultipartFile file:files){
                TbFile tbFile=fileService.saveFileOne(file,type,tbIndividuality.getName());
                if(tbFile!=null){
                    pkid=pkid+tbFile.getPkid()+",";
                    size=tbFile.getSize()+size;
                }else{
                    return boo;
                }
            }
        }
        tbIndividuality.setSize(size.intValue());
        tbIndividuality.setFileId(pkid);
        //todo 鉴权
        tbIndividuality.setOperator("system");
        saveIndividuality(tbIndividuality);
        boo=true;
        return boo;
    }

    /**
     * @author luoyun
     * @Description 根据主键查询个性化表
     */
    public TbIndividuality getIndividuality(Integer pkid){
        return tbIndividualityMapper.getIndividuality(pkid);
    }

    public void deleteIndividuality(Integer pkid){
        tbIndividualityMapper.deleteIndividuality(pkid);
    }

    /**
     * @author luoyun
     * @Description 删除数据和文件信息
     * @param idList tbFile表主键集合
     * @param pkid 个性化过程主键
     * @param type 文件存储个性化文件类型
     * @param name 个性化过程文件夹名称
     * @throws  Exception 文件删除失败
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFileIndividuality(List<String> idList,Integer pkid,String type,String name) throws Exception {
        boolean boo=true;
        deleteIndividuality(pkid);
        idList.stream().forEach(n->fileService.deleteFileByPkid(Integer.valueOf(n)));
        String url=env.getProperty("saveFileUrl");
        String filePath=url+type+"/"+name;
        boo=fileService.deleteDirectory(filePath);
        if(!boo){
            logger.error("文件删除失败");
            throw new Exception("文件删除失败");
        }
        return boo;
    }
}
