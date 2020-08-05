package com.oes.server.exam.online.client;

import com.oes.common.core.constant.ServerConstant;
import com.oes.common.core.entity.R;
import com.oes.common.core.exam.entity.ExamViolateLog;
import com.oes.server.exam.online.client.fallback.RemoteExamBasicViolateLogServiceFallback;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chachae
 * @version v1.0
 * @date 2020/6/23 12:00
 */
@FeignClient(
    value = ServerConstant.OES_SERVER_EXAM_BASIC,
    contextId = "remoteExamBasicViolateLogService",
    fallbackFactory = RemoteExamBasicViolateLogServiceFallback.class
)
public interface ViolateLogClient {

  @GetMapping("violatelog/list")
  R<List<ExamViolateLog>> listExamViolateLog(@RequestParam("paperId") Long paperId);
}
