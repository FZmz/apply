package org.ISCS.mapper;

import org.ISCS.domain.Apply;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ApplyMapper {
    //扩展的mapper接口方法
    List<Apply> getApplyListByRes(Apply apply);

    int updateApplyStatus(Apply apply);

    int insertApply(Apply record);

    Apply getApplyInfoById(int applyId);

    List<Apply> getApplyList();
}
