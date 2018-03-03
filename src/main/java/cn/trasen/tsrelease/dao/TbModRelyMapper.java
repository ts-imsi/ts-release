package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.model.TbModRely;
import cn.trasen.tsrelease.util.MyMapper;

import java.util.List;

public interface TbModRelyMapper extends MyMapper<TbModRely> {
    public int insertSelective(TbModRely rely);
    public int deletive(int pkid);
    public List<TbModRely> selective(TbModRely rely);
    public int updative(TbModRely rely);

}