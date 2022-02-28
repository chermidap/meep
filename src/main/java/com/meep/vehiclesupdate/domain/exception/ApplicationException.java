package com.meep.vehiclesupdate.domain.exception;

import lombok.Getter;

public class ApplicationException extends RuntimeException{
  private static final long serialVersionUID = 1L;

  @Getter
  private final int code;

  protected ApplicationException(String message, final int code) {
    super(message);
    this.code = code;
  }

  protected ApplicationException(String message, Throwable e, final int code) {
    super(message, e);
    this.code = code;
  }

}
