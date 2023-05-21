package org.ISCS.services;


import org.ISCS.domain.plan.Manufacture;
import org.ISCS.domain.customize.CustomResult;
import org.ISCS.domain.customize.EUDataGridResult;
import org.ISCS.domain.plan.ManufactureVO;

import java.util.List;

public interface ManufactureService {
	
	List<ManufactureVO> find() throws Exception;
	
	EUDataGridResult getList(int page, int rows) throws Exception;

	ManufactureVO get(String string) throws Exception;
	
	CustomResult delete(String string) throws Exception;

	CustomResult deleteBatch(String[] ids) throws Exception;

	CustomResult insert(Manufacture manufacture) throws Exception;

	//更新部分字段，用的是updateSelective判断非空的字段进行更新
    CustomResult update(Manufacture manufacture) throws Exception;
    
    //更新全部字段，不判断非空，直接进行更新
    CustomResult updateAll(Manufacture manufacture) throws Exception;
    
	EUDataGridResult searchManufactureByManufactureSn(Integer page,
			Integer rows, String manufactureSn) throws Exception;
	
	EUDataGridResult searchManufactureByManufactureOrderId(Integer page,
			Integer rows, String manufactureOrderId) throws Exception;

	EUDataGridResult searchManufactureByManufactureTechnologyName(Integer page,
			Integer rows, String manufactureTechnologyName) throws Exception;
}
