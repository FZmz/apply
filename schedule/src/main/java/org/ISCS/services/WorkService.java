package org.ISCS.services;



import org.ISCS.domain.plan.Work;
import org.ISCS.domain.customize.CustomResult;
import org.ISCS.domain.customize.EUDataGridResult;
import org.ISCS.domain.plan.WorkVO;

import java.util.List;

public interface WorkService {
	
	List<WorkVO> find() throws Exception;
	
	EUDataGridResult getList(int page, int rows, WorkVO work) throws Exception;

	WorkVO get(String string) throws Exception;
	
	CustomResult delete(String string) throws Exception;

	CustomResult deleteBatch(String[] ids) throws Exception;

	CustomResult insert(Work work) throws Exception;

    //更新全部字段，不判断非空，直接进行更新
    CustomResult updateAll(Work work) throws Exception;
    
    CustomResult update(Work work) throws Exception;
    
	EUDataGridResult searchWorkByWorkId(Integer page, Integer rows,
			String workId) throws Exception;

	EUDataGridResult searchWorkByWorkProduct(Integer page, Integer rows,
			String workProduct) throws Exception;

	EUDataGridResult searchWorkByWorkDevice(Integer page, Integer rows,
			String workDevice) throws Exception;

	EUDataGridResult searchWorkByWorkProcess(Integer page, Integer rows,
			String workProcess) throws Exception;
}
