package com.oes.ai.function.supplier.baidu.remote;

import com.oes.ai.function.ocr.baidu.entity.AccessTokenInfo;
import com.oes.ai.function.supplier.baidu.remote.fallback.RemoteAccessTokenServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/29 15:55
 */
@FeignClient(
    name = "BaiduAccessToken",
    url = "${oes.ai.baidu.auth-url}",
    fallbackFactory = RemoteAccessTokenServiceFallback.class
)
public interface IRemoteAccessTokenService {

  /**
   * 获取应用AccessToken
   *
   * @return AccessToken 信息
   */
  @PostMapping
  AccessTokenInfo getAccessToken(@RequestParam("grant_type") String grantType,
      @RequestParam("client_id") String apiKey, @RequestParam("client_secret") String secretKey);

}
