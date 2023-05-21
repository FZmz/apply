package org.ISCS.controller;


import org.ISCS.domain.customize.ActiveUser;
import org.ISCS.utils.CollectionsFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static jdk.net.SocketFlow.Status.NO_PERMISSION;

/**
 * created on 2017年3月25日 
 *
 * 权限判断controller
 *
 * @author  megagao
 * @version  0.0.1
 */
@RestController
public class AuthorityJudgeController {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(AuthorityJudgeController.class);

	@RequestMapping("*/*_judge")
	public Map<String,Object> authorityJudge(HttpServletRequest request) throws Exception{
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) redisTemplate.opsForValue().get("activeUser");
		//根据uri,使用shiro判断相应权限
		String uri = request.getRequestURI();
		System.out.println("uri: " + uri);
		String[] names = uri.split("/");
		String featureName = names[1];
		String operateName = names[2].split("_")[0];
		Map<String,Object> map = CollectionsFactory.newHashMap();
		if(!activeUser.getUserStatus().equals("1")){
			if (logger.isDebugEnabled()) {
				logger.debug("账户已被锁定！", NO_PERMISSION);
			}
			map.put("msg", "您的账户已被锁定，请切换账户登录！");
		}else if(!activeUser.getRoleStatus().equals("1")){
			if (logger.isDebugEnabled()) {
				logger.debug("角色已被锁定!", NO_PERMISSION);
			}
			map.put("msg", "当前角色已被锁定，请切换账户登录！");
		}else{
			if (logger.isDebugEnabled()) {
				logger.debug("没有权限!", NO_PERMISSION);
			}
			if(!subject.isPermitted(featureName+":"+operateName)){
				map.put("msg", "您没有权限，请切换用户登录！");
			}
		}
		return map;
	}
}
