package org.ISCS.controller;


import org.ISCS.domain.Apply;
import org.ISCS.domain.ApplyExample;
import org.ISCS.domain.customize.CustomResult;
import org.ISCS.domain.customize.EUDataGridResult;
import org.ISCS.services.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 添加申请
     */
    @RequestMapping(value = "/toApply", method = RequestMethod.POST)
    @ResponseBody
    public CustomResult toApply(@RequestParam Integer applyType, @RequestParam String applyModule, @RequestParam String applyRoute, @RequestParam String data) throws Exception {
        CustomResult customResult;
        Apply apply = new Apply();
        apply.setApplyType(applyType);
        apply.setApplyModule(applyModule);
        apply.setApplyRoute(applyRoute);
        apply.setApplyStatus(1); // 1表示待审核
        apply.setApplyDate(new Date());
        apply.setApplyData(data);
        customResult = applyService.insertApply(apply);
        return customResult;
    }
    /**
     * 更改申请记录
     */
    @RequestMapping(value = "/toUpdateApplyStatus", method = RequestMethod.POST)
    @ResponseBody
    public CustomResult updateApplyStatus(ApplyExample applyExample) throws Exception {
        CustomResult customResult;
        Apply apply = new Apply();
        apply.setApplyId(applyExample.getApplyId());
        apply.setApplyStatus(applyExample.getApplyStatus());
        customResult = applyService.updateApplyStatus(apply);
        if (applyExample.getApplyStatus() == 2) {
            // 1. 根据id查询申请数据 applyData
            Apply apply1 = applyService.getApplyInfoById(applyExample.getApplyId());
            String applyData = apply1.getApplyData();
            String applyRoute = apply1.getApplyRoute();
             // 2. 利用RestTemplate发起http请求
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("customStr", applyData);
            // 组装请求体
            HttpEntity<MultiValueMap<String, String>> request =
                    new HttpEntity<MultiValueMap<String, String>>(map, headers);
            restTemplate.postForObject(applyRoute, request, String.class);
        }
        return customResult;
    }

    /**
     * 根据applyType、applyModule、applyStatus查询apply记录 详细查询
     */
    @RequestMapping(value = "/toGetApplyListByRes", method = RequestMethod.GET)
    @ResponseBody
    public EUDataGridResult getApplyListByRes(int applyType, String applyModule, int applyStatus) throws Exception {
        return applyService.getApplyListByRes(10, 1, applyType, applyModule, applyStatus);
    }

    @RequestMapping("/list/find")
    public String find(HttpSession session) throws Exception {
        return "apply_list";
    }
    @RequestMapping(value="/getAllApply",method = RequestMethod.GET)
    @ResponseBody
    public EUDataGridResult getApplyList() throws Exception {
        return applyService.getApplyList();
    }
}
