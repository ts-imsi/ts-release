package com.transen.tsrelease.dao;

import com.transen.tsrelease.model.TbProModule;
import com.transen.tsrelease.util.MyMapper;

public interface TbProModuleMapper extends MyMapper<TbProModule> {
    public int insertive(TbProModule proModule);
    public int deletive(int pkid);
    public TbProModule selective();
    public int updative(TbProModule proModule);
}