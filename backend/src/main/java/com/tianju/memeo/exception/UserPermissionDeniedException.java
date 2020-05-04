package com.tianju.memeo.exception;

public class UserPermissionDeniedException extends RuntimeException {
  private int code;
  public UserPermissionDeniedException(String message) {
    super(message);
    this.code = 4000;
  }
  public UserPermissionDeniedException(int code, String message) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
