package com.oes.server.exam.online;

import com.oes.common.security.starter.annotation.EnableOesCloudResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chachae
 * @since 2020/4/29 17:53
 */
@EnableAsync
@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
@EnableOesCloudResourceServer
@MapperScan("com.oes.server.exam.online.mapper")
public class ServerExamOnlineApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(ServerExamOnlineApplication.class)
        .web(WebApplicationType.SERVLET)
        .run(args);
  }
}

