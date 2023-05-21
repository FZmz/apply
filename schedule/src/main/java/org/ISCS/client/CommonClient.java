package org.ISCS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("commonservice")
public interface CommonClient {
    @GetMapping("/getPermissionList")
    public List<String> getPermissionList();
}
