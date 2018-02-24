package com.transen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Getter
@Setter
public class TbIndividuality {
    /**
     * 自增主键
     */
    private Integer pkid;

    /**
     * 个性化名称
     */
    private String name;

    /**
     * 模块id
     */
    private Integer modId;

    /**
     * 模块名称
     */
    private String modName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 上传文件id(以逗号隔开)
     */
    private String fileId;

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
     * 所属医院（以逗号隔开）
     */
    private String hospital;

    private Integer size;

}