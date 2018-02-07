package com.transen.tsrelease.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.transen.tsrelease.dao.TbJfRankMapper;
import com.transen.tsrelease.dao.TwfDictMapper;
import com.transen.tsrelease.model.TwfDict;
import com.transen.tsrelease.model.TbJfRank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2017/9/13
 */
@Service
public class RecordService {
    @Autowired
    private TbJfRankMapper tbJfRankMapper;

    @Autowired
    private TwfDictMapper twfDictMapper;

    public PageInfo<TbJfRank> getRecordRankList(int page, int rows){
        PageHelper.startPage(page,rows);
        List<TbJfRank> tbJfRankList=tbJfRankMapper.getRecordRankList();
        PageInfo<TbJfRank> pagehelper = new PageInfo<TbJfRank>(tbJfRankList);
        return pagehelper;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public int saveRecordRank(TbJfRank tbJfRank){
        tbJfRankMapper.saveRecordRank(tbJfRank);
        TwfDict twfDict=new TwfDict();
        twfDict.setCode("name");
        twfDict.setIsVaild(1);
        twfDict.setName("name");
        twfDict.setType(1);
        twfDictMapper.saveTwfDic(twfDict);
        return 0;
    }
}
