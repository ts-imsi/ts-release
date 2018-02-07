package com.transen.tsrelease.dao;



import com.transen.tsrelease.util.MyMapper;
import com.transen.tsrelease.model.TbJfRank;

import java.util.List;

public interface TbJfRankMapper extends MyMapper<TbJfRank> {
    List<TbJfRank> getRecordRankList();
    int saveRecordRank(TbJfRank tbJfRank);
}