package com.transen.tsrelease.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_mod_version")
public class TbModVersion {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pkid;

    /**
     * 版本类型(增量：increment；里程碑：milepost)
     */
    @Column(name = "version_type")
    private String versionType;

    /**
     * 需求处理起始时间
     */
    @Column(name = "demand_start")
    private String demandStart;

    /**
     * 需求处理结束时间
     */
    @Column(name = "demand_end")
    private String demandEnd;

    /**
     * 需求编号
     */
    @Column(name = "demand_num")
    private String demandNum;

    /**
     * 模块id
     */
    @Column(name = "mod_id")
    private Integer modId;

    /**
     * 文件id（关联文件表）
     */
    @Column(name = "file_id")
    private Integer fileId;

    /**
     * 备注
     */
    private String remark;

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
     * 获取自增主键
     *
     * @return pkid - 自增主键
     */
    public Integer getPkid() {
        return pkid;
    }

    /**
     * 设置自增主键
     *
     * @param pkid 自增主键
     */
    public void setPkid(Integer pkid) {
        this.pkid = pkid;
    }

    /**
     * 获取版本类型(增量：increment；里程碑：milepost)
     *
     * @return version_type - 版本类型(增量：increment；里程碑：milepost)
     */
    public String getVersionType() {
        return versionType;
    }

    /**
     * 设置版本类型(增量：increment；里程碑：milepost)
     *
     * @param versionType 版本类型(增量：increment；里程碑：milepost)
     */
    public void setVersionType(String versionType) {
        this.versionType = versionType;
    }

    /**
     * 获取需求处理起始时间
     *
     * @return demand_start - 需求处理起始时间
     */
    public String getDemandStart() {
        return demandStart;
    }

    /**
     * 设置需求处理起始时间
     *
     * @param demandStart 需求处理起始时间
     */
    public void setDemandStart(String demandStart) {
        this.demandStart = demandStart;
    }

    /**
     * 获取需求处理结束时间
     *
     * @return demand_end - 需求处理结束时间
     */
    public String getDemandEnd() {
        return demandEnd;
    }

    /**
     * 设置需求处理结束时间
     *
     * @param demandEnd 需求处理结束时间
     */
    public void setDemandEnd(String demandEnd) {
        this.demandEnd = demandEnd;
    }

    /**
     * 获取需求编号
     *
     * @return demand_num - 需求编号
     */
    public String getDemandNum() {
        return demandNum;
    }

    /**
     * 设置需求编号
     *
     * @param demandNum 需求编号
     */
    public void setDemandNum(String demandNum) {
        this.demandNum = demandNum;
    }

    /**
     * 获取模块id
     *
     * @return mod_id - 模块id
     */
    public Integer getModId() {
        return modId;
    }

    /**
     * 设置模块id
     *
     * @param modId 模块id
     */
    public void setModId(Integer modId) {
        this.modId = modId;
    }

    /**
     * 获取文件id（关联文件表）
     *
     * @return file_id - 文件id（关联文件表）
     */
    public Integer getFileId() {
        return fileId;
    }

    /**
     * 设置文件id（关联文件表）
     *
     * @param fileId 文件id（关联文件表）
     */
    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
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