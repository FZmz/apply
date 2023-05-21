package org.ISCS.domain;

import lombok.Data;

@Data
public class ApplyExample {
    private Integer applyId;
    private String applyModule;
    private String applyRoute;
    private Integer applyType;
    private String applyData;
    private Integer applyStatus;
}
