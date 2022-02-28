package com.meep.mobilityresources.domain.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobilityResourceRepositoryException extends ApplicationException{

  public MobilityResourceRepositoryException(String message, Throwable e) {
    super(message, e,1);
    log.error(message, e);
  }

}
