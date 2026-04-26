package com.alipay.usercenter.core.context;

import com.alipay.usercenter.core.enums.IdigitalriskActionTypeEnum;

import java.util.Date;

public class IdigitalriskContext {

    private static final long serialVersionUID = 1L;

    private Date time;
    private IdigitalriskActionTypeEnum action;
    private String operatorId;
    private String operatorName;


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public IdigitalriskActionTypeEnum getAction() {
        return action;
    }

    public void setAction(IdigitalriskActionTypeEnum action) {
        this.action = action;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public IdigitalriskContext(IdigitalriskActionTypeEnum action, Date time, String operatorId, String operatorName) {
        this.action = action;
        this.time = time;
        this.operatorId = operatorId;
        this.operatorName = operatorName;
    }
}
