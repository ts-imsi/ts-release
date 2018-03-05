package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.model.TbPublicFile;
import cn.trasen.tsrelease.util.MyMapper;

import java.util.List;

public interface TbPublicFileMapper extends MyMapper<TbPublicFile> {
    List<TbPublicFile> selectPublicFileList(String name);

    void savePublicFile(TbPublicFile tbPublicFile);
}