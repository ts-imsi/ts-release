package com.transen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class TbJfRank {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkid;

    /**
     * 大职级
     */
    private String name;

    /**
     * 初始分值
     */
    private Integer score;

    /**
     * 晋级分值
     */
    @Column(name = "prm_score")
    private Integer prmScore;

    /**
     * 小职级
     */
    private String type;

    /**
     * 职级顺序
     */
    private Integer px;
}