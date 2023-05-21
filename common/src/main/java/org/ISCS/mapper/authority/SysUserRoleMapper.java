package org.ISCS.mapper.authority;

import org.ISCS.domain.authority.SysRoleExample;
import org.ISCS.domain.authority.SysUserRole;
import org.ISCS.domain.authority.SysUserRoleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SysUserRoleMapper {
	
	//扩展的mapper接口方法
	int deleteBatchByUserId(String[] ids);
	
	
    int countByExample(SysRoleExample example);

    int deleteByExample(SysUserRoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    List<SysUserRole> selectByExample(SysUserRoleExample example);

    SysUserRole selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByExample(@Param("record") SysUserRole record, @Param("example") SysUserRoleExample example);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}