package com.transen.tsrelease.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.transen.tsrelease.dao.TbIndividualityMapper;
import com.transen.tsrelease.model.TbIndividuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/24
 */
@Service
public class IndividualityService {

    @Autowired
    TbIndividualityMapper tbIndividualityMapper;

    public PageInfo<TbIndividuality> getIndividualityList(Integer page,Integer rows,String hospitalName){
        PageHelper.startPage(page,rows);
        List<TbIndividuality> tbIndividualities=tbIndividualityMapper.getIndividualityList(hospitalName);
        PageInfo<TbIndividuality> pagehelper = new PageInfo<TbIndividuality>(tbIndividualities);
        return pagehelper;
    }
}
