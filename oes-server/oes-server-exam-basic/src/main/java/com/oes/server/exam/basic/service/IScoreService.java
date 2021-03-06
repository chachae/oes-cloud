package com.oes.server.exam.basic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oes.common.core.exam.entity.Score;
import com.oes.common.core.exam.entity.query.QueryScoreDto;
import com.oes.common.core.exam.entity.vo.StatisticScoreVo;
import java.util.List;

/**
 * @author chachae
 * @since 2020-06-03 16:43:16
 */
public interface IScoreService extends IService<Score> {

  /**
   * 分页查询分数数据
   *
   * @param score 模糊条件
   * @return {@link IPage<Score>} 分页结果集
   */
  IPage<Score> pageScore(QueryScoreDto score);

  /**
   * 通过分数信息查询分数
   *
   * @param score 模糊条件
   * @return 分数列表
   */
  List<Score> getScore(QueryScoreDto score);

  /**
   * 通过分数信息查询分数
   *
   * @param paperId  试卷编号
   * @param username 用户名
   * @return 分数
   */
  Score getScore(String username, Long paperId);
  
  /**
   * 删除学期数据
   *
   * @param username 用户名
   * @param paperId  试卷编号
   */
  void deleteScore(String username, Long paperId);

  /**
   * 更新学期数据
   *
   * @param score 分数信息
   */
  void updateScore(Score score);

  /**
   * 自动评分
   *
   * @param score 分数信息
   */
  void autoMarkScore(Score score);

  /**
   * 增加学期数据
   *
   * @param score 分数信息
   */
  void createScore(Score score);

  /**
   * 通过试卷编号统计成绩数据
   *
   * @param paperIds 试卷编号
   * @return 总数
   */
  Integer countByPaperId(String[] paperIds);

  /**
   * 分数情况统计
   *
   * @param paperId 试卷编号
   */
  StatisticScoreVo statisticScore(Long paperId);
}