package org.ISCS.services.impl;

import org.ISCS.domain.authority.SysPermission;
import org.ISCS.domain.authority.SysPermissionExample;
import org.ISCS.domain.authority.SysUser;
import org.ISCS.domain.authority.SysUserExample;
import org.ISCS.mapper.authority.SysPermissionMapper;
import org.ISCS.mapper.authority.SysPermissionMapperCustom;
import org.ISCS.mapper.authority.SysUserMapper;
import org.ISCS.services.SysService;
import org.ISCS.utils.CollectionsFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created on 2016年9月6日 
 *
 * 认证和授权的服务接口
 *
 * @author  megagao
 * @version  0.0.1
 */
@Service
@MapperScan("org.ISCS.mapper.authority")
public class SysServiceImpl implements SysService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	
	@Override
	public List<SysPermission> findMenuListByUserId(String userId) throws Exception {
		return sysPermissionMapperCustom.findMenuListByUserId(userId);
	}

	@Override
	public List<SysPermission> findPermissionListByUserId(String userId) throws Exception {
		
		String permission = this.sysPermissionMapperCustom.findPermissionByUserId(userId);
		if(permission != null){
			String[] permissionIds = permission.split(",");
			List<Long> ids = CollectionsFactory.newArrayList();
			for(int i=0;i<permissionIds.length;i++){
				ids.add(Long.valueOf(permissionIds[i]));
			}
			SysPermissionExample example = new SysPermissionExample();
			SysPermissionExample.Criteria criteria = example.createCriteria();
			criteria.andIdIn(ids);
			return sysPermissionMapper.selectByExample(example);
		}
		return null;
	}

	@Override
	public SysUser getSysUserByName(String username) throws Exception {
		SysUserExample sysUserExample = new SysUserExample();
		SysUserExample.Criteria criteria = sysUserExample.createCriteria();
		criteria.andUsernameEqualTo(username);
		
		List<SysUser> list = sysUserMapper.selectByExample(sysUserExample);
		
		if(list!=null && list.size()==1){
			return list.get(0);
		}	
		return null;
	}
}
