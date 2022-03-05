package com.meep.mobilityresources.domain.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublishEventException extends ApplicationException{

  public PublishEventException(String message, Throwable e) {
    super(message, e);
    log.error(message, e);
  }
}
