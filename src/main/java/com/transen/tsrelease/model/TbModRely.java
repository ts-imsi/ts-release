package com.transen.tsrelease.model;

import javax.persistence.*;

@Table(name = "tb_mod_rely")
public class TbModRely {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkid;

    /**
     * 依赖模块id
     */
    @Column(name = "pre_mod_id")
    private Integer preModId;

    /**
     * 依赖模块名称
     */
    @Column(name = "pre_mod_name")
    private String preModName;

    /**
     * 依赖模块版本id
     */
    @Column(name = "pre_versionId")
    private Integer preVersionid;

    /**
     * 依赖模块版本号
     */
    @Column(name = "pre_version")
    private String preVersion;

    /**
     * 模块版本表id
     */
    @Column(name = "mod_version_id")
    private Integer modVersionId;

    /**
     * @return pkid
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * @param pkid
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * 获取依赖模块id
     *
     * @return pre_mod_id - 依赖模块id
     */
    public Integer getPreModId() {
        return preModId;
    }

    /**
     * 设置依赖模块id
     *
     * @param preModId 依赖模块id
     */
    public void setPreModId(Integer preModId) {
        this.preModId = preModId;
    }

    /**
     * 获取依赖模块名称
     *
     * @return pre_mod_name - 依赖模块名称
     */
    public String getPreModName() {
        return preModName;
    }

    /**
     * 设置依赖模块名称
     *
     * @param preModName 依赖模块名称
     */
    public void setPreModName(String preModName) {
        this.preModName = preModName;
    }

    /**
     * 获取依赖模块版本id
     *
     * @return pre_versionId - 依赖模块版本id
     */
    public Integer getPreVersionid() {
        return preVersionid;
    }

    /**
     * 设置依赖模块版本id
     *
     * @param preVersionid 依赖模块版本id
     */
    public void setPreVersionid(Integer preVersionid) {
        this.preVersionid = preVersionid;
    }

    /**
     * 获取依赖模块版本号
     *
     * @return pre_version - 依赖模块版本号
     */
    public String getPreVersion() {
        return preVersion;
    }

    /**
     * 设置依赖模块版本号
     *
     * @param preVersion 依赖模块版本号
     */
    public void setPreVersion(String preVersion) {
        this.preVersion = preVersion;
    }

    /**
     * 获取模块版本表id
     *
     * @return mod_version_id - 模块版本表id
     */
    public Integer getModVersionId() {
        return modVersionId;
    }

    /**
     * 设置模块版本表id
     *
     * @param modVersionId 模块版本表id
     */
    public void setModVersionId(Integer modVersionId) {
        this.modVersionId = modVersionId;
    }
}