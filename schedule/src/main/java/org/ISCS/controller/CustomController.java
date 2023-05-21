package org.ISCS.controller;


import com.google.gson.Gson;
import org.ISCS.client.ApplyClient;
import org.ISCS.client.CommonClient;
import org.ISCS.domain.customize.CustomResult;
import org.ISCS.domain.customize.EUDataGridResult;
import org.ISCS.domain.plan.Custom;
import org.ISCS.services.CustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/schedule/custom")
public class CustomController {
	Gson gson = new Gson();

	@Value("${my.global.address}")
	private String myGlobalAddress;
	String applyRouter = "http://www.iscs.com:30003" + "/schedule/custom";
	@Autowired
	private ApplyClient applyClient;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private CustomService customService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CommonClient commonClient;
	@RequestMapping("/get/{customId}")
	@ResponseBody
	public Custom getItemById(@PathVariable String customId) throws Exception{
		Custom custom = customService.get(customId);
		return custom;
	}
	
	@RequestMapping("/add")
	public String add() {
		return "custom_add";
	}
	
	@RequestMapping("/edit")
	public String edit() throws Exception{
		return "custom_edit";
	}
	
	@RequestMapping("/get_data")
	@ResponseBody
	public List<Custom> getData() throws Exception{
		 List<Custom> list = customService.find();
		return list;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows, Custom custom) throws Exception{
		EUDataGridResult result = customService.getList(page, rows, custom);
		return result;
	}
	@RequestMapping("/find")
	public String find(HttpSession session) throws Exception{
		List<String> sysPermissionList = (List<String>) redisTemplate.opsForValue().get("sysPermissionList");
		session.setAttribute("sysPermissionList",sysPermissionList);
		return "custom_list";
	}
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	@ResponseBody
	private CustomResult insert(@Valid Custom custom, BindingResult bindingResult) throws Exception {
		CustomResult result;
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			return CustomResult.build(100, fieldError.getDefaultMessage());
		}
		if(customService.get(custom.getCustomId()) != null){
			result = new CustomResult(0, "该客户编号已经存在，请更换客户编号！", null);
		}else{
			String serializedObj = gson.toJson(custom);
			result = applyClient.toApply(1,"schedule",applyRouter + "/insertDirectly",serializedObj);
		}
		return result;
	}
	@RequestMapping(value="/insertDirectly", method=RequestMethod.POST)
	@ResponseBody
	private CustomResult insertDirectly(@RequestParam String customStr) throws Exception {
		CustomResult result;
		// 使用gson 将JSON字符串反序列化为对象
		Custom custom = gson.fromJson(customStr,Custom.class);
		result = customService.insert(custom);
		return result;
	}
	/**
	 * 申请更新
	 * */
	@RequestMapping(value="/update")
	@ResponseBody
	private CustomResult update(@Valid Custom custom, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			return CustomResult.build(100, fieldError.getDefaultMessage());
		}
		String serializedObj = gson.toJson(custom);
		CustomResult customResult =  applyClient.toApply(3,"schedule",applyRouter + "/updateDirectly",serializedObj);
		return customResult;
	}
	/**
	 * 操作数据库更新部分字段
	 * */
	@RequestMapping(value="/updateDirectly", method=RequestMethod.POST)
	@ResponseBody
	private CustomResult updateDirectly(@RequestParam String customStr) throws Exception {
		CustomResult result;
		// 使用gson 将JSON字符串反序列化为对象
		Custom custom = gson.fromJson(customStr,Custom.class);
		result = customService.update(custom);
		return result;
	}
	@RequestMapping(value="/update_all")
	@ResponseBody
	private CustomResult updateAll(@Valid Custom custom, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			return CustomResult.build(100, fieldError.getDefaultMessage());
		}
		String serializedObj = gson.toJson(custom);
		CustomResult customResult =  applyClient.toApply(3,"schedule",applyRouter + "/updateAllDirectly",serializedObj);
		return customResult;
	}
	/**
	 * 操作数据库更新所有字段
	 * */
	@RequestMapping(value="/updateAllDirectly", method=RequestMethod.POST)
	@ResponseBody
	private CustomResult updateAllDirectly(@RequestParam String customStr) throws Exception {
		CustomResult result;
		// 使用gson 将JSON字符串反序列化为对象
		Custom custom = gson.fromJson(customStr,Custom.class);
		result = customService.updateAll(custom);
		return result;
	}
	@RequestMapping(value="/update_note")
	@ResponseBody
	private CustomResult updateNote(@Valid Custom custom, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			return CustomResult.build(100, fieldError.getDefaultMessage());
		}
		return customService.updateNote(custom);
	}

	/**
	 * 申请删除
	 * */
	@RequestMapping(value="/delete")
	@ResponseBody
	private CustomResult delete(String id) throws Exception {
		CustomResult result = applyClient.toApply(2,"schedule",applyRouter + "/deleteDirectly",id);
		return result;
	}
	/**
	 * 操作数据库直接删除
	 * */
	@RequestMapping(value="/deleteDirectly", method=RequestMethod.POST)
	@ResponseBody
	private CustomResult deleteDirectly(@RequestParam String id) throws Exception {
		CustomResult result = customService.delete(id);
		return result;
	}
	@RequestMapping(value="/delete_batch")
	@ResponseBody
	private CustomResult deleteBatch(String[] ids) throws Exception {
		// 使用gson 将JSON字符串反序列化为对象
		String serializedObj = gson.toJson(ids);
		CustomResult result = applyClient.toApply(2,"schedule",applyRouter + "/deleteBatchDirectly",serializedObj);
		return result;
	}
	@RequestMapping(value="/deleteBatchDirectly", method=RequestMethod.POST)
	@ResponseBody
	private CustomResult deleteBatchDirectly(@RequestParam String customStr) throws Exception {
		String[] idsArr = gson.fromJson(customStr,String[].class);
		CustomResult result = customService.deleteBatch(idsArr);
		return result;
	}
	@RequestMapping(value="/change_status")
	@ResponseBody
	public CustomResult changeStatus(String[] ids) throws Exception{
		CustomResult result = customService.changeStatus(ids);
		return result;
	}
	
	//根据客户id查找
	@RequestMapping("/search_custom_by_customId")
	@ResponseBody
	public EUDataGridResult searchCustomByCustomId(Integer page, Integer rows, String searchValue)
			throws Exception{
		EUDataGridResult result = customService.searchCustomByCustomId(page, rows, searchValue);
		return result;
	}
	
	//根据客户名查找
	@RequestMapping("/search_custom_by_customName")
	@ResponseBody
	public EUDataGridResult searchCustomByCustomName(Integer page, Integer rows, String searchValue) 
			throws Exception{
		EUDataGridResult result = customService.searchCustomByCustomName(page, rows, searchValue);
		return result;
	}
}
