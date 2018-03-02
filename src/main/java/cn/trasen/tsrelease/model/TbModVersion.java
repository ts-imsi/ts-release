package cn.trasen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TbModVersion {
    /**
     * 自增主键
     */
    private Integer pkid;

    /**
     * 版本类型(增量：increment；里程碑：milepost)
     */
    private String versionType;

    /**
     * 版本号
     */
    private String version;

    /**
     * 需求处理起始时间
     */
    private String demandStart;

    /**
     * 需求处理结束时间
     */
    private String demandEnd;

    /**
     * 需求编号
     */
    private String demandNum;

    /**
     * 模块id
     */
    private Integer modId;

    /**
     * 文件id（关联文件表）
     */
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
     * 模块版本依赖关系
     */
    private String mod_rely;
}