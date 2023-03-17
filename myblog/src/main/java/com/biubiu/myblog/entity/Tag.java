package com.biubiu.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 标签
 */
@Data
@TableName("t_tag")
@ApiModel(value = "Tag对象", description = "")
public class Tag implements Serializable {

    /**
     * tag(36) => 37960(10)
     */
    private static final long serialVersionUID = 37960L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("标题")
    private String name;

}
