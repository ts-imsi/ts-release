package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.model.TbProModule;
import cn.trasen.tsrelease.util.MyMapper;

import java.util.List;

public interface TbProModuleMapper extends MyMapper<TbProModule> {
    public int insertive(TbProModule proModule);
    public int deletive(int pkid);
    public int updative(TbProModule proModule);
    List<TbProModule> getProModuleList(TbProModule proModule);
}