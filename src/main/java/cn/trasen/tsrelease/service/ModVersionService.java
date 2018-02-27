package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbModVersionMapper;
import cn.trasen.tsrelease.model.TbModVersion;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModVersionService {
    @Autowired
    private TbModVersionMapper mapper;

    public int insertive(TbModVersion modVersion) {
        return mapper.insertive(modVersion);
    }

    public PageInfo<TbModVersion> selectModVersion(int page,int  rows, TbModVersion modVersion) {
        PageHelper.startPage(page, rows);
        List<TbModVersion> modVersionList =  mapper.selective(modVersion);
        PageInfo<TbModVersion> pageinfo = new PageInfo(modVersionList);
        return pageinfo;
    }

    public int deleteModVersion(TbModVersion modVersion) {
        int i = 0;
        if (modVersion != null) {
            i = mapper.deletive(modVersion.getPkid());
        }
        return i;
    }

    public int updateModVersion(TbModVersion modVersion) {
        int i = 0;
        if (modVersion != null) {
            i = mapper.updative(modVersion);
        }
        return i;
    }
}
