package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbPublicFileMapper;
import cn.trasen.tsrelease.model.TbFile;
import cn.trasen.tsrelease.model.TbIndividuality;
import cn.trasen.tsrelease.model.TbPublicFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/3/5
 */
@Service
public class PublicFileService {

    private final static Logger logger= LoggerFactory.getLogger(PublicFileService.class);

    @Autowired
    TbPublicFileMapper tbPublicFileMapper;

    @Autowired
    FileService fileService;

    public PageInfo<TbPublicFile> selectPublicFileList(Integer page,Integer rows,String name){
        PageHelper.startPage(page,rows);
        List<TbPublicFile> tbPublicFiles=tbPublicFileMapper.selectPublicFileList(name);
        PageInfo<TbPublicFile> pagehelper = new PageInfo<TbPublicFile>(tbPublicFiles);
        return pagehelper;
    }

    /**
     * @author luoyun
     * @Description 保存数据到公共文件表
     */
    public void savePublicFile(TbPublicFile tbPublicFile){
        tbPublicFileMapper.savePublicFile(tbPublicFile);
    }

    /**
     * @author luoyun
     * @Description 上传文件保存到服务器路径，保存公共资源到数据库
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveFileAndPublicFile(MultipartFile[] files, String type, TbPublicFile tbPublicFile){
        boolean boo=false;
        String pkid="";
        Long size=0L;
        if(files!=null){
            for(MultipartFile file:files){
                TbFile tbFile=fileService.saveFileOne(file,type,tbPublicFile.getName());
                if(tbFile!=null){
                    pkid=pkid+tbFile.getPkid()+",";
                    size=tbFile.getSize()+size;
                }else{
                    return boo;
                }
            }
        }
        tbPublicFile.setSize(size.intValue());
        tbPublicFile.setFileId(pkid);
        //todo 鉴权
        tbPublicFile.setOperator("system");
        savePublicFile(tbPublicFile);
        boo=true;
        return boo;
    }

}
