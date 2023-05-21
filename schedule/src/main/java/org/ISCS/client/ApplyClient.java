package org.ISCS.client;

import org.ISCS.domain.customize.CustomResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("applyservice")
@Component
public interface ApplyClient {
    @PostMapping("/apply/toApply")
    CustomResult toApply(@RequestParam Integer applyType, @RequestParam String applyModule, @RequestParam String applyRoute, @RequestParam String data);
}
