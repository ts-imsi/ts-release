package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbModRelyMapper;
import cn.trasen.tsrelease.model.TbModRely;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModRelyService {
    @Autowired
    private TbModRelyMapper modRelyMapper;

    public TbModRely select() {
        return modRelyMapper.selective();
    }

    public int insertModRely(TbModRely modRely) {
        int i = 0;
        if (modRely != null) {
            i = modRelyMapper.insertSelective(modRely);
        }
        return i;
    }

    public int deleteModRely(TbModRely modRely) {
        int i = 0;
        if (modRely != null) {
            i = modRelyMapper.deletive(modRely.getPkid());
        }
        return i;
    }

    public int updateModRely(TbModRely modRely){
        int i = 0;
        if(modRely != null){
            i = modRelyMapper.updative(modRely);
        }
        return i;
    }

}
