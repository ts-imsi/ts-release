package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.util.MyMapper;
import cn.trasen.tsrelease.model.TbFile;

import java.util.List;
import java.util.Map;

public interface TbFileMapper extends MyMapper<TbFile> {

    void saveFile(Map<String,Object> param);

    TbFile getFileToUrl(String url);

    void saveFileOne(TbFile tbFile);

    void saveFileOneByPkid(TbFile tbFile);

    List<TbFile> getFileByPkid(List<String> ids);
}