package com.transen.tsrelease.dao;

import com.transen.tsrelease.model.TbProduct;
import com.transen.tsrelease.util.MyMapper;

public interface TbProductMapper extends MyMapper<TbProduct> {
    int insert(TbProduct product);
}