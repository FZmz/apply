package org.ISCS.services;


import org.ISCS.domain.customize.CustomResult;
import org.ISCS.domain.customize.EUDataGridResult;

public interface ApplyService {
    EUDataGridResult getApplyListByRes(int page, int rows,int applyType,String applyModule,int applyStatus) throws Exception;
    CustomResult insertApply(org.ISCS.domain.Apply apply) throws Exception;
    CustomResult updateApplyStatus(org.ISCS.domain.Apply apply) throws Exception;
    org.ISCS.domain.Apply getApplyInfoById(int applyId) throws Exception;

    EUDataGridResult getApplyList() throws Exception;
}
