package com.oes.gateway.enhance.service.impl;

import cn.hutool.core.util.StrUtil;
import com.oes.common.core.entity.QueryParam;
import com.oes.common.core.util.DateUtil;
import com.oes.gateway.enhance.entity.RouteUser;
import com.oes.gateway.enhance.mapper.RouteUserMapper;
import com.oes.gateway.enhance.service.RouteUserService;
import com.oes.gateway.enhance.util.PageableExecutionUtil;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author chachae
 */
@Service
@RequiredArgsConstructor
public class RouteUserServiceImpl implements RouteUserService {

  private final PasswordEncoder passwordEncoder;

  private RouteUserMapper routeUserMapper;
  private ReactiveMongoTemplate template;

  @Autowired(required = false)
  public void setRouteUserMapper(RouteUserMapper routeUserMapper) {
    this.routeUserMapper = routeUserMapper;
  }

  @Autowired(required = false)
  public void setTemplate(ReactiveMongoTemplate template) {
    this.template = template;
  }

  @Override
  public Mono<RouteUser> create(RouteUser routeUser) {
    routeUser.setPassword(passwordEncoder.encode(routeUser.getPassword()));
    routeUser.setCreateTime(
        DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN));
    return routeUserMapper.insert(routeUser);
  }

  @Override
  public Mono<RouteUser> update(RouteUser routeUser) {
    return this.routeUserMapper.findById(routeUser.getId())
        .flatMap(u -> {
          u.setRoles(routeUser.getRoles());
          return this.routeUserMapper.save(u);
        });
  }

  @Override
  public Flux<RouteUser> delete(String ids) {
    String[] idArray = StrUtil.split(ids, StrUtil.COMMA);
    return routeUserMapper.deleteByIdIn(Arrays.asList(idArray));
  }

  @Override
  public Mono<RouteUser> findByUsername(String username) {
    return routeUserMapper.findByUsername(username);
  }

  @Override
  public Flux<RouteUser> findPages(QueryParam request, RouteUser routeUser) {
    Query query = getQuery(routeUser);
    return PageableExecutionUtil.getPages(query, request, RouteUser.class, template);
  }

  @Override
  public Mono<Long> findCount(RouteUser routeUser) {
    Query query = getQuery(routeUser);
    return template.count(query, RouteUser.class);
  }

  private Query getQuery(RouteUser routeUser) {
    Query query = new Query();
    Criteria criteria = new Criteria();
    if (StrUtil.isNotBlank(routeUser.getUsername())) {
      criteria.and("username").is(routeUser.getUsername());
    }
    query.addCriteria(criteria);
    return query;
  }
}
