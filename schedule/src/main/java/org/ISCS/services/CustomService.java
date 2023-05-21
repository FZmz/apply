package org.ISCS.services;

import org.ISCS.domain.plan.Custom;
import org.ISCS.domain.customize.CustomResult;
import org.ISCS.domain.customize.EUDataGridResult;

import java.util.List;

public interface CustomService {
	
	List<Custom> find() throws Exception;
	
	EUDataGridResult getList(int page, int rows, Custom custom) throws Exception;
	
	Custom get(String string) throws Exception;
	
	CustomResult delete(String string) throws Exception;

	CustomResult deleteBatch(String[] ids) throws Exception;

	CustomResult insert(Custom custom) throws Exception;

	//更新部分字段，用的是updateSelective判断非空的字段进行更新
    CustomResult update(Custom custom) throws Exception;
    
    //更新全部字段，不判断非空，直接进行更新
    CustomResult updateAll(Custom custom) throws Exception;
    
    //更新备注
    CustomResult updateNote(Custom custom) throws Exception;

    CustomResult changeStatus(String[] ids) throws Exception;
    
    EUDataGridResult searchCustomByCustomName(int page, int rows, String customName) throws Exception;

    EUDataGridResult searchCustomByCustomId(int page, int rows, String customId) throws Exception;
}
