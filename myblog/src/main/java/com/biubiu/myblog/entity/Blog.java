package com.biubiu.myblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 博文
 */
@Data
@Accessors(chain = true)
@TableName("t_blog")
@ApiModel(value = "Blog对象", description = "")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户ID")
    private Integer uid;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String body;

    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("评论数量")
    private Integer discussCount;

    @ApiModelProperty("浏览次数")
    private Integer blogViews;

    @ApiModelProperty("标签id,多个用_分割")
    private String tagId;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
    /**
     * 博文状态--0删除 1正常
     */
    @ApiModelProperty("状态")
    private Integer state;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 所属用户
     */
    @TableField(exist = false)
    private User user;
    /**
     * 博文对应的标签
     */
    @TableField(exist = false)
    private List<Tag> tags;

}
