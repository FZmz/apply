package org.ISCS.services.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.ISCS.domain.Apply;
import org.ISCS.domain.customize.CustomResult;
import org.ISCS.domain.customize.EUDataGridResult;
import org.ISCS.mapper.ApplyMapper;
import org.ISCS.services.ApplyService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@MapperScan("org.ISCS.mapper")
public class ApplyServiceImpl implements ApplyService {
    @Autowired
    ApplyMapper applyMapper;

    @Override
    public EUDataGridResult getApplyListByRes(int page, int rows,int applyType,String applyModule,int applyStatus) throws Exception {
        //分页处理
        PageHelper.startPage(page, rows);
        Apply apply = new Apply();
        apply.setApplyStatus(applyStatus);
        apply.setApplyModule(applyModule);
        apply.setApplyType(applyType);
        List<Apply> list = applyMapper.getApplyListByRes(apply);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<Apply> pageInfo = new PageInfo(list);
        result.setTotal(list.size());
        return result;
    }

    @Override
    public CustomResult insertApply(Apply apply) throws Exception {
        int i = applyMapper.insertApply(apply);
        if(i>=0){
            return CustomResult.ok();
        }else{
            return CustomResult.build(101, "新增记录信息失败");
        }
    }

    @Override
    public CustomResult updateApplyStatus(Apply apply) throws Exception {
        int i = applyMapper.updateApplyStatus(apply);
        if(i>0){
            return CustomResult.ok();
        }else{
            return CustomResult.build(101, "更新记录信息失败");
        }
    }

    @Override
    public Apply getApplyInfoById(int applyId) throws Exception {
        Apply apply = applyMapper.getApplyInfoById(applyId);
        //创建一个返回值对象
        return apply;
    }

    @Override
    public EUDataGridResult getApplyList() throws Exception {
        EUDataGridResult result = new EUDataGridResult();
        List<Apply> list = applyMapper.getApplyList();
        result.setRows(list);
        result.setTotal(list.size());
        return result;
    }
}
