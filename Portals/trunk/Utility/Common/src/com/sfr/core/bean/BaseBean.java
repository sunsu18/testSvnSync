package com.sfr.core.bean;

import com.sfr.util.ThreadSerialization;

import java.util.List;

public class BaseBean extends ThreadSerialization{

  private static final long serialVersionUID = 1L;
    private List<BusinessError> errorList;
    private String status;

    public void setErrorList(List<BusinessError> errorList) {
        this.errorList = errorList;
    }

    public List<BusinessError> getErrorList() {
        return errorList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
