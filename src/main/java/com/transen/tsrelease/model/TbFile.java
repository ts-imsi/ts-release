package com.transen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Getter
@Setter
public class TbFile {
    /**
     * 自增主键
     */
    private Integer pkid;

    /**
     * 文件名
     */
    private String name;

    /**
     * 父级文件ID
     */
    private Integer pId;

    private String pName;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 大小
     */
    private Long size;

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
     * 文件路径
     */
    private String url;

    private Integer level;


    @Transient
    private List<TbFile> files;


}