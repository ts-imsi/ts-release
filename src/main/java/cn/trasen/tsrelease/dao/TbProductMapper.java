package cn.trasen.tsrelease.dao;

import cn.trasen.tsrelease.model.TbProduct;
import cn.trasen.tsrelease.util.MyMapper;

import java.util.List;

public interface TbProductMapper extends MyMapper<TbProduct> {
    int insert(TbProduct product);
    List<TbProduct> selective(TbProduct product);
    TbProduct selectProParent();
    List<TbProduct> selectProTree(Integer parent);
    int updateSelective(TbProduct product);
    int deleteProduct(int pkid);
    List<TbProduct> selectProList(Integer parent);
}