package com.transen.tsrelease.dao;

import com.transen.tsrelease.model.TbProduct;
import com.transen.tsrelease.util.MyMapper;

import java.util.List;

public interface TbProductMapper extends MyMapper<TbProduct> {
    int insert(TbProduct product);
    TbProduct selectProParent();
    List<TbProduct> selectProTree(Integer parent);
}