package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbProModuleMapper;
import cn.trasen.tsrelease.dao.TbProductMapper;
import cn.trasen.tsrelease.model.TbProModule;
import cn.trasen.tsrelease.model.TbProduct;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProModuleService {
    @Autowired
    private TbProModuleMapper proModuleMapper;
    @Autowired
    private TbProductMapper productMapper;

    public int deleteProModule(TbProModule proModule) {
        int i = 0;
        if (proModule != null) {
            i = proModuleMapper.deletive(proModule.getModId());
        }
        return i;
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertProModule(TbProduct product, TbProModule proModule) {
        int i = 0;
        if (product != null) {
            i = productMapper.insert(product);
        }
        if (proModule != null && i > 0) {
            proModule.setModId(product.getPkid());
            i = proModuleMapper.insertive(proModule);
        }
        return i;
    }

    @Transactional(rollbackFor = Exception.class)
    public int updateProModule(TbProduct product, TbProModule proModule) {
        int i = 0;
        if (product != null) {
            i = productMapper.updateSelective(product);
        }
        if (proModule != null && i > 0) {
            i = proModuleMapper.updative(proModule);
        }
        return i;
    }

    public PageInfo<TbProModule> proModuleList(int page, int rows, TbProModule proModule) {
        PageHelper.startPage(page, rows);
        List<TbProModule> module_list = proModuleMapper.getProModuleList(proModule);
        PageInfo<TbProModule> pagehelper = new PageInfo<TbProModule>(module_list);
        return pagehelper;
    }
}
