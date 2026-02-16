package com.alipay.usercenter.core.exception;

import com.alipay.usercenter.common.service.facade.enums.UserResultCode;

public class UserException extends RuntimeException {

  private static final long seralVersionUID = 9187623791824214L;

  private UserResultCode resultCode;

  public UserException(UserResultCode resultCode, String message) {
    super(message);
    this.resultCode = resultCode;
  }

  public UserException(UserResultCode resultCode) {
    this(resultCode, resultCode.getDescription());
  }

  public UserResultCode getResultCode() {
    return resultCode;
  }

  public void setResultCode(UserResultCode resultCode) {
    this.resultCode = resultCode;
  }
}
