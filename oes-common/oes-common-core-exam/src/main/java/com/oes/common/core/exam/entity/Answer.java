package com.oes.common.core.exam.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 问题表实体类
 *
 * @author chachae
 * @since 2020-06-03 16:43:11
 */
@Data
@TableName("t_answer")
public class Answer implements Serializable {

  private static final long serialVersionUID = -2379218847487357896L;

  public static final Integer STATUS_CORRECT = 1;

  public static final Integer STATUS_NOT_CORRECT = 0;

  /**
   * 学生答题主键（id）
   */
  @TableId(type = IdType.AUTO)
  private Long answerId;
  /**
   * 学生答题数据
   */
  private String answerContent;
  /**
   * 学生编号（id）
   */
  private Long studentId;
  /**
   * 试卷编号（id）
   */
  private Long paperId;
  /**
   * 试题编号（id）
   */
  private Long questionId;
  /**
   * 题目的分
   */
  private Integer score;
  /**
   * 创建时间
   */
  private Date createTime;
  /**
   * 更新时间
   */
  private Date updateTime;
  /**
   * 答案状态（1：批改，0：未批改）
   */
  private Integer status;
  /**
   * 试卷名称
   */
  @TableField(exist = false)
  private String paperName;
  /**
   * 题干
   */
  @TableField(exist = false)
  private String questionName;
  /**
   * 题目类型编号
   */
  @TableField(exist = false)
  private Integer typeId;
  /**
   * 正确答案
   */
  @TableField(exist = false)
  private String rightKey;
  /**
   * 学生姓名
   */
  @TableField(exist = false)
  private String studentName;
  /**
   * 学期名称
   */
  @TableField(exist = false)
  private String termName;
}