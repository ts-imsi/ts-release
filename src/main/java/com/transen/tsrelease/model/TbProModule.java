package com.transen.tsrelease.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_pro_module")
public class TbProModule {
    @Id
    @Column(name = "mod_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer modId;

    /**
     * 模块名称
     */
    @Column(name = "mod_name")
    private String modName;

    /**
     * 拼音码
     */
    private String code;

    /**
     * 显示顺序
     */
    @Column(name = "show_px")
    private Integer showPx;

    /**
     * 打包顺序
     */
    @Column(name = "release_px")
    private Integer releasePx;

    /**
     * 版本前缀
     */
    @Column(name = "version_fix")
    private String versionFix;

    /**
     * 产品id
     */
    @Column(name = "pro_id")
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

    /**
     * @return mod_id
     */
    public Integer getModId() {
        return modId;
    }

    /**
     * @param modId
     */
    public void setModId(Integer modId) {
        this.modId = modId;
    }

    /**
     * 获取模块名称
     *
     * @return mod_name - 模块名称
     */
    public String getModName() {
        return modName;
    }

    /**
     * 设置模块名称
     *
     * @param modName 模块名称
     */
    public void setModName(String modName) {
        this.modName = modName;
    }

    /**
     * 获取拼音码
     *
     * @return code - 拼音码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置拼音码
     *
     * @param code 拼音码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取显示顺序
     *
     * @return show_px - 显示顺序
     */
    public Integer getShowPx() {
        return showPx;
    }

    /**
     * 设置显示顺序
     *
     * @param showPx 显示顺序
     */
    public void setShowPx(Integer showPx) {
        this.showPx = showPx;
    }

    /**
     * 获取打包顺序
     *
     * @return release_px - 打包顺序
     */
    public Integer getReleasePx() {
        return releasePx;
    }

    /**
     * 设置打包顺序
     *
     * @param releasePx 打包顺序
     */
    public void setReleasePx(Integer releasePx) {
        this.releasePx = releasePx;
    }

    /**
     * 获取版本前缀
     *
     * @return version_fix - 版本前缀
     */
    public String getVersionFix() {
        return versionFix;
    }

    /**
     * 设置版本前缀
     *
     * @param versionFix 版本前缀
     */
    public void setVersionFix(String versionFix) {
        this.versionFix = versionFix;
    }

    /**
     * 获取产品id
     *
     * @return pro_id - 产品id
     */
    public Integer getProId() {
        return proId;
    }

    /**
     * 设置产品id
     *
     * @param proId 产品id
     */
    public void setProId(Integer proId) {
        this.proId = proId;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 获取更新时间
     *
     * @return updated - 更新时间
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * 设置更新时间
     *
     * @param updated 更新时间
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }
}