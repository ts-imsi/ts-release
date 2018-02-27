package com.transen.tsrelease.dao;

import com.transen.tsrelease.model.TbIndividuality;
import com.transen.tsrelease.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbIndividualityMapper extends MyMapper<TbIndividuality> {
    List<TbIndividuality> getIndividualityList(String hospitalName);
    void saveIndividuality(TbIndividuality tbIndividuality);
}