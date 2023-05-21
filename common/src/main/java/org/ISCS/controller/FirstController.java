package org.ISCS.controller;

import org.ISCS.domain.authority.SysPermission;
import org.ISCS.domain.customize.ActiveUser;
import org.ISCS.services.SysService;
import org.ISCS.utils.CollectionsFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.ISCS.common.Constants.ACTIVE_USER;
import static org.ISCS.common.Constants.ERROR_HAPPENS;


@Controller
public class FirstController {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private static final Logger logger = LoggerFactory.getLogger(FirstController.class);
//	@Autowired
//	private CustomRealm myRealm;
	@Autowired
	private SysService sysService;
	
	//跳转登录
	@RequestMapping(value={"/","/first","/login"})
	public String first(Model model)throws Exception{
		System.out.println(model);
		return "login";
	}
	// 退出
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "login";
	}
	//首页
	@RequestMapping("/home")
	public String home(HttpSession session, Model model)throws Exception{
//		SecurityManager securityManager = new DefaultSecurityManager(myRealm);
//		ThreadContext.bind(securityManager);
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser)subject.getPrincipal();
		List<SysPermission> permissionList = null;
		try {
			permissionList = sysService.findPermissionListByUserId(activeUser.getUserid());
		} catch (Exception e) {
			logger.error(ERROR_HAPPENS, e.getMessage());
		}
		List<String> sysPermissionList = CollectionsFactory.newArrayList();
		if(permissionList != null){
			for(int i=0;i<permissionList.size();i++){
				sysPermissionList.add(permissionList.get(i).getPercode());
			}
		}
		model.addAttribute(ACTIVE_USER, activeUser);
		session.setAttribute("sysPermissionList", sysPermissionList);
		redisTemplate.opsForValue().set("sysPermissionList", sysPermissionList);
		redisTemplate.opsForValue().set("roleName",activeUser.getRolename());
		return "home";
	}
	@GetMapping("/getPermissionList")
	@ResponseBody
	public List<String> getPermissionList(HttpSession session) throws Exception {
		/*List<SysPermission> permissionList = null;
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser)subject.getPrincipal();*/
		List<String> sysPermissionList = CollectionsFactory.newArrayList();
		sysPermissionList = (List<String>) session.getAttribute("sysPermissionList");
		return sysPermissionList;
	}
}	
