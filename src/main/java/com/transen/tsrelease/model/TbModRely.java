package com.transen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TbModRely {
    private Integer pkid;

    /**
     * 依赖模块id
     */
    private Integer preModId;

    /**
     * 依赖模块名称
     */
    private String preModName;

    /**
     * 依赖模块版本id
     */
    private Integer preVersionid;

    /**
     * 依赖模块版本号
     */
    private String preVersion;

    /**
     * 模块版本表id
     */
    private Integer modVersionId;

}