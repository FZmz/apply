package org.ISCS.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Apply {
    private Integer applyId;
    private String applyModule;
    private String applyRoute;
    private Integer applyType;
    private String applyData;
    private Integer applyStatus;
    private Date applyDate;
}
