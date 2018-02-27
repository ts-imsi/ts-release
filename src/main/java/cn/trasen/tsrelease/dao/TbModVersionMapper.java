package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.model.TbModVersion;
import cn.trasen.tsrelease.util.MyMapper;

import java.util.List;

public interface TbModVersionMapper extends MyMapper<TbModVersion> {
    public int insertive(TbModVersion modVersion);

    public int updative(TbModVersion modVersion);

    public List<TbModVersion> selective(TbModVersion modVersion);

    public int deletive(int pkid);
}