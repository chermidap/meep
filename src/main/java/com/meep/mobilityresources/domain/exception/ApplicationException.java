package com.meep.mobilityresources.domain.exception;

public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  protected ApplicationException(String message, Throwable e) {
    super(message, e);
  }

}
