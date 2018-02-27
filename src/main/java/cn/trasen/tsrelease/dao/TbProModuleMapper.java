package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.model.TbProModule;
import cn.trasen.tsrelease.util.MyMapper;

public interface TbProModuleMapper extends MyMapper<TbProModule> {
    public int insertive(TbProModule proModule);
    public int deletive(int pkid);
    public TbProModule selective();
    public int updative(TbProModule proModule);
}