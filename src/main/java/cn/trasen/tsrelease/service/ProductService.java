package cn.trasen.tsrelease.service;

import cn.trasen.tsrelease.dao.TbProModuleMapper;
import cn.trasen.tsrelease.model.TbProModule;
import cn.trasen.tsrelease.model.TreeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.trasen.tsrelease.dao.TbProductMapper;
import cn.trasen.tsrelease.model.TbProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private TbProductMapper productMapper;

    @Autowired
    TbProModuleMapper tbProModuleMapper;

    @Transactional(rollbackFor = Exception.class)
    public int insertProduct(TbProduct product) {
        int i = 0;
        if (product != null) {
            i = productMapper.insert(product);
        }
        return i;
    }

    /**
     * 模糊查询模块和 分页查看模块
     */

    public PageInfo<TbProduct> selectProduct(int page, int rows, TbProduct product) {
        PageHelper.startPage(page, rows);
        List<TbProduct> product_list = productMapper.selective(product);
        PageInfo<TbProduct> pagehelper = new PageInfo<TbProduct>(product_list);
        return pagehelper;
    }

    public TbProduct selectProParent(){
        return productMapper.selectProParent();
    }

    /**
     * @author luoyun
     * @Description 查询产品模块树
     */
    public TreeVo selectProTree(TbProduct tbProduct){
        TreeVo productTreeVo=new TreeVo();
        List<TbProduct> tbProducts=productMapper.selectProTree(tbProduct.getPkid());
        if(tbProduct!=null){
            List<TreeVo> children = new ArrayList<>();
            productTreeVo.setLabel(tbProduct.getName());
            productTreeVo.setData(tbProduct);
            tbProducts.stream().forEach(n-> {
                TreeVo proTree=selectProTree(n);
                children.add(proTree);
            });
            productTreeVo.setChildren(children);
        }
        return productTreeVo;
    }

    /**
     * @author luoyun
     * @Description 查询产品树
     */
    public TreeVo selectProList(TbProduct tbProduct){
        TreeVo productTreeVo=new TreeVo();
        List<TbProduct> tbProducts=productMapper.selectProList(tbProduct.getPkid());
        if(tbProduct!=null){
            List<TreeVo> children = new ArrayList<>();
            productTreeVo.setLabel(tbProduct.getName());
            productTreeVo.setData(tbProduct);
            tbProducts.stream().forEach(n-> {
                TreeVo proTree=selectProList(n);
                children.add(proTree);
            });
            productTreeVo.setChildren(children);
        }
        return productTreeVo;
    }

    public List<TbProModule> getProModuleList(){
        return tbProModuleMapper.getProModuleList(null);
    }

    public int updateProduct(TbProduct tbProduct) {
        int i = productMapper.updateSelective(tbProduct);
        return i;
    }

    public int deleteProduct(TbProduct tbProduct) {
        int i = productMapper.deleteProduct(tbProduct.getPkid());
        return i;
    }

    /**
     * 查询产品和模块(不分页可模糊查询)
     */
    public List<TbProduct> selectProduct(TbProduct product) {
        return productMapper.selective(product);
    }
}
