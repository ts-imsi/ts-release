package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbIndividualityMapper;
import cn.trasen.tsrelease.model.TbFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.trasen.tsrelease.model.TbIndividuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/24
 */
@Service
public class IndividualityService {

    @Autowired
    TbIndividualityMapper tbIndividualityMapper;

    @Autowired
    FileService fileService;

    public PageInfo<TbIndividuality> getIndividualityList(Integer page,Integer rows,String hospitalName){
        PageHelper.startPage(page,rows);
        List<TbIndividuality> tbIndividualities=tbIndividualityMapper.getIndividualityList(hospitalName);
        PageInfo<TbIndividuality> pagehelper = new PageInfo<TbIndividuality>(tbIndividualities);
        return pagehelper;
    }

    public void saveIndividuality(TbIndividuality tbIndividuality){
         tbIndividualityMapper.saveIndividuality(tbIndividuality);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean saveFileAndInviduality(MultipartFile[] files,String type,TbIndividuality tbIndividuality){
        boolean boo=false;
        String pkid="";
        Long size=0L;
        if(files!=null){
            for(MultipartFile file:files){
                TbFile tbFile=fileService.saveFileOne(file,type);
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
}
