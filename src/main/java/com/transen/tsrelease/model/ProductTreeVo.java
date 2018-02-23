package com.transen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/23
 */
@Getter
@Setter
public class ProductTreeVo {
    private String label;

    private List<ProductTreeVo> children;

    private Object data;
}
