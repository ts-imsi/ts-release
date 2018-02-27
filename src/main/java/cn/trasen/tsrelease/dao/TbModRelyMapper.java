package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.model.TbModRely;
import cn.trasen.tsrelease.util.MyMapper;

public interface TbModRelyMapper extends MyMapper<TbModRely> {
    public int insertSelective(TbModRely rely);
    public int deletive(int pkid);
    public TbModRely selective();
    public int updative(TbModRely rely);

}