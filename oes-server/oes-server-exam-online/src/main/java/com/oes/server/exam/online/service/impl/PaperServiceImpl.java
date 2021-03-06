package com.oes.server.exam.online.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oes.common.core.exam.entity.Paper;
import com.oes.common.core.exam.entity.query.QueryPaperDto;
import com.oes.common.core.util.SecurityUtil;
import com.oes.server.exam.online.client.PaperClient;
import com.oes.server.exam.online.mapper.PaperMapper;
import com.oes.server.exam.online.service.IPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/21 18:04
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

  private final PaperClient remotePaperService;

  @Override
  public IPage<Paper> getNormalPaper(QueryPaperDto entity) {
    entity.setType(Paper.TYPE_NORMAL);
    return filterPaper(entity);
  }

  @Override
  public IPage<Paper> getImitatePaper(QueryPaperDto entity) {
    entity.setType(Paper.TYPE_IMITATE);
    return filterPaper(entity);
  }

  @Override
  public Paper getOnePaper(Long paperId, Long userId) {
    return remotePaperService.getOne(paperId, userId).getData();
  }

  private IPage<Paper> filterPaper(QueryPaperDto entity) {
    // 只查询状态为「开启」的试卷
    entity.setStatus(Paper.STATUS_OPEN);
    // 限制试卷的归属部门（班级）
    entity.setDeptIds(String.valueOf(SecurityUtil.getCurrentUser().getDeptId()));
    return baseMapper.pagePaper(entity, new Page<>(entity.getPageNum(), entity.getPageSize()));
  }
}
