package com.transen.tsrelease.dao;


import com.transen.tsrelease.model.TwfDict;
import com.transen.tsrelease.util.MyMapper;

public interface TwfDictMapper extends MyMapper<TwfDict> {
     int saveTwfDic(TwfDict twfDict);
}