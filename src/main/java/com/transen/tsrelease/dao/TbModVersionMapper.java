package com.transen.tsrelease.dao;

import com.transen.tsrelease.model.TbModVersion;
import com.transen.tsrelease.util.MyMapper;

import java.util.List;

public interface TbModVersionMapper extends MyMapper<TbModVersion> {
    public int insertive(TbModVersion modVersion);

    public int updative(TbModVersion modVersion);

    public List<TbModVersion> selective(TbModVersion modVersion);

    public int deletive(int pkid);
}