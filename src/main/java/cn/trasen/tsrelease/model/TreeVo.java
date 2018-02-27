package cn.trasen.tsrelease.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author luoyun
 * @ClassName: IntelliJ IDEA
 * @Description: 操作类型
 * @date 2018/2/23
 */
@Getter
@Setter
public class TreeVo {
    private String label;

    private List<TreeVo> children;

    private Object data;
}
