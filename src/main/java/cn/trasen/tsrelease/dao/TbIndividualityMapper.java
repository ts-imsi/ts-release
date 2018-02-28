package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.model.TbIndividuality;
import cn.trasen.tsrelease.util.MyMapper;

import java.util.List;

public interface TbIndividualityMapper extends MyMapper<TbIndividuality> {
    List<TbIndividuality> getIndividualityList(String hospitalName);
    void saveIndividuality(TbIndividuality tbIndividuality);
    TbIndividuality getIndividuality(Integer pkid);
}