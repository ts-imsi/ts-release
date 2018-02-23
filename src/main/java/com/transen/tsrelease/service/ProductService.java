package com.transen.tsrelease.service;

import com.transen.tsrelease.dao.TbProductMapper;
import com.transen.tsrelease.model.ProductTreeVo;
import com.transen.tsrelease.model.TbProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private TbProductMapper productMapper;

    @Transactional(rollbackFor = Exception.class)
    public int insertProduct(TbProduct product) {
        int i = 0;
        if (product != null) {
            i = productMapper.insert(product);
        }
        return i;
    }

    public List<TbProduct> selectProduct() {
        //List<TbProduct> product_list =  productMapper.select();
      //return product_list;
        return null;
    }

    public TbProduct selectProParent(){
        return productMapper.selectProParent();
    }

    /*
    * 查询产品模块树
    * */
    public ProductTreeVo selectProTree(TbProduct tbProduct){
        ProductTreeVo productTreeVo=new ProductTreeVo();
        List<TbProduct> tbProducts=productMapper.selectProTree(tbProduct.getPkid());
        if(tbProduct!=null){
            List<ProductTreeVo> children = new ArrayList<>();
            productTreeVo.setLabel(tbProduct.getName());
            productTreeVo.setData(tbProduct);
            tbProducts.stream().forEach(n-> {
                ProductTreeVo proTree=selectProTree(n);
                children.add(proTree);
            });
            productTreeVo.setChildren(children);
        }
        return productTreeVo;
    }

    /*
    * 查询产品树
    * */
    public ProductTreeVo selectProList(TbProduct tbProduct){
        ProductTreeVo productTreeVo=new ProductTreeVo();
        List<TbProduct> tbProducts=productMapper.selectProList(tbProduct.getPkid());
        if(tbProduct!=null){
            List<ProductTreeVo> children = new ArrayList<>();
            productTreeVo.setLabel(tbProduct.getName());
            productTreeVo.setData(tbProduct);
            tbProducts.stream().forEach(n-> {
                ProductTreeVo proTree=selectProList(n);
                children.add(proTree);
            });
            productTreeVo.setChildren(children);
        }
        return productTreeVo;
    }
}
