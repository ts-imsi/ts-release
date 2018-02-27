package cn.trasen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbProModule {
    private Integer modId;

    /**
     * 模块名称
     */
    private String modName;

    /**
     * 拼音码
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer showPx;

    /**
     * 打包顺序
     */
    private Integer releasePx;

    /**
     * 版本前缀
     */
    private String versionFix;

    /**
     * 产品id
     */
    private Integer proId;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 操作人
     */
    private String operator;

    private Integer isVaild;


}