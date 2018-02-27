package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbProModuleMapper;
import cn.trasen.tsrelease.model.TbProModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProModuleService {
    @Autowired
    private TbProModuleMapper proModuleMapper;

    public TbProModule selectProModule(){
        return proModuleMapper.selective();
    }

    public int deleteProModule(TbProModule proModule){
        int i = 0;
        if(proModule!=null){
            i = proModuleMapper.deletive(proModule.getModId());
        }
        return i;
    }

    public int insertProModule(TbProModule proModule){
        int i = 0;
        if(proModule!=null){
            i = proModuleMapper.insertive(proModule);
        }
        return i;
    }

    public int updateProModule(TbProModule proModule){
        int i = 0;
        if(proModule!=null){
            i = proModuleMapper.updative(proModule);
        }
        return i;
    }
}
