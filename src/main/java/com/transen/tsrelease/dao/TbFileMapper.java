package com.transen.tsrelease.dao;

import com.transen.tsrelease.util.MyMapper;
import com.transen.tsrelease.model.TbFile;

import java.util.Map;

public interface TbFileMapper extends MyMapper<TbFile> {

    void saveFile(Map<String,Object> param);

    TbFile getFileToUrl(String url);

    void saveFileOne(TbFile tbFile);
}