package com.transen.tsrelease.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwfDict {
    private Integer pkid;

    private String name;

    private String code;

    private Integer type;

    private Integer isVaild;

    private String remark;
}