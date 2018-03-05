package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbModRelyMapper;
import cn.trasen.tsrelease.dao.TbModVersionMapper;
import cn.trasen.tsrelease.dao.TbProductMapper;
import cn.trasen.tsrelease.model.TbModRely;
import cn.trasen.tsrelease.model.TbModVersion;
import cn.trasen.tsrelease.model.TbProModule;
import cn.trasen.tsrelease.model.TbProduct;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModVersionService {
    @Autowired
    private TbModVersionMapper mapper;
    @Autowired
    private TbModRelyMapper modRelyMapper;
    @Autowired
    private TbProductMapper productMapper;

    public int insertive(TbModVersion modVersion) {
        return mapper.insertive(modVersion);
    }

    /**
     * 将模块版本的前置依赖存入
     */
    public void setModRely(List<TbModVersion> modVersionList) {
        if (modVersionList != null) {
            modVersionList
                .stream().forEach(modV -> {
                    TbModRely modRely = new TbModRely();
                    modRely.setModVersionId(modV.getPkid());
                    List<TbModRely> modRelyList = modRelyMapper.selective(modRely);
                    modRelyList.stream()
                        .forEach(modRe -> {
                            String mod_rely_one = modRe.getPreModName() + modRe.getPreVersion(); //单一的模块版本依赖名称
                            String modR = modV.getModRely() != null ? modV.getModRely() : ""; // 获取原模块版本中的前置依赖
                            String modRel = modR + "," + mod_rely_one; // 字符串拼接成 符合格式的前置依赖
                            if (modRel.startsWith(",")) {
                                modRel = modRel.substring(1);
                            }
                            modV.setModRely(modRel);
                        });
            });
        }
    }

    /**
     * 传入产品id 查询模块列表 这里将模块的ID 遍历
     * @return
     */
    public PageInfo<TbModVersion> selectModVersion(int page, int rows, TbProduct product) {
        List<Integer> mod_ids = new ArrayList<>(); // 模块id列表
        List<TbProduct> mod_list = productMapper.selective(product);
        mod_list.stream()
            .forEach(
                mod -> {
                    mod_ids.add(mod.getPkid());
                }
            );
        PageInfo<TbModVersion> pageinfo = selectModVersionById(page, rows, mod_ids);
        return pageinfo;
    }

    /**
     * 传入模块信息进行遍历(改成只可以查询准确的信息 例如：根据模块id查询版本)
     */
    public PageInfo<TbModVersion> selectModVersion(int page, int rows, TbModVersion modVersion) {
        PageHelper.startPage(page, rows);
        List<Integer> mod_ids = new ArrayList<Integer>();
        mod_ids.add(modVersion.getModId());
        PageInfo<TbModVersion> pageinfo = selectModVersionById(page, rows, mod_ids);
        return pageinfo;
    }

    /**
     * 传入模块列表id进行遍历
     */
    public PageInfo<TbModVersion> selectModVersionById(int page, int rows,List<Integer> mod_ids) {
        PageHelper.startPage(page, rows);
        List<TbModVersion> modVersionList = mapper.getModVBymodId(mod_ids);
        setModRely(modVersionList);
        PageInfo<TbModVersion> pageinfo = new PageInfo(modVersionList);
        return pageinfo;
    }

    /**
     * 查询版本列表(可以根据mod_id模块查询版本)
     */
    public List<TbModVersion> selectModVersion(TbModVersion modVersion) {
        List<TbModVersion> modVersionList = mapper.selective(modVersion);
        return modVersionList;
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
