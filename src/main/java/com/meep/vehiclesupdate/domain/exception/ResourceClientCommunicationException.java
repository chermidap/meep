package com.meep.vehiclesupdate.domain.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceClientCommunicationException extends ApplicationException{

  public ResourceClientCommunicationException(String message, Throwable e) {
    super(message, e,1);
    log.error(message, e);
  }
}
