package com.transen.tsrelease.service;

import com.transen.tsrelease.dao.TbProductMapper;
import com.transen.tsrelease.model.TbProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private TbProductMapper productMapper;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public int insertProduct(TbProduct product) {
        int i = 0;
        if (product != null) {
            i = productMapper.insert(product);
        }
        return i;
    }

    public List<TbProduct> selectProduct() {
//        List<TbProduct> product_list =  productMapper.select();
//        return product_list;
        return null;
    }

}
