package com.oes.server.exam.basic.config;

import com.oes.server.exam.basic.interceptor.ExamInfoScopeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author chachae
 * @since 2020/05/11 22：02
 */
@Configuration
public class ExamBasicDataSourceConfig {

  /**
   * 注册数据权限
   */
  @Bean
  @Order(-1)
  public ExamInfoScopeInterceptor dataPermissionInterceptor() {
    return new ExamInfoScopeInterceptor();
  }
}