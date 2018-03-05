package cn.trasen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbPublicFile {

    private Integer pkid;

    private String name;

    private Integer size;

    private String operator;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * 文件对应id
     */
    private String fileId;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 说明
     */
    private String remark;

}