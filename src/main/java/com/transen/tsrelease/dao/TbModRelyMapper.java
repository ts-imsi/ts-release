package com.transen.tsrelease.dao;

import com.transen.tsrelease.model.TbModRely;
import com.transen.tsrelease.util.MyMapper;

public interface TbModRelyMapper extends MyMapper<TbModRely> {
    public int insertSelective(TbModRely rely);
    public int deletive(int pkid);
    public TbModRely selective();
    public int updative(TbModRely rely);

}