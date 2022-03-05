package com.meep.mobilityresources.application;

import com.meep.mobilityresources.domain.entity.ReportUpdate;
import com.meep.mobilityresources.domain.exception.PublishEventException;
import com.meep.mobilityresources.domain.usecase.PublishMobilityResourceUpdateInfo;
import com.meep.mobilityresources.infrastructure.rabbitmq.dispatcher.EventDispatcher;
import com.meep.mobilityresources.infrastructure.rabbitmq.event.MobilityResourceUpdateEvent;
import com.meep.mobilityresources.infrastructure.rabbitmq.mapper.MobilityResourceEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublishMobilityResourceUpdateInfoImpl implements PublishMobilityResourceUpdateInfo {

  private final EventDispatcher eventDispatcher;

  private final MobilityResourceEventMapper mapper;

  @Value("${mobility.params.location}")
  private String location;
  @Override
  public void apply(ReportUpdate reportUpdate) {
    // Communicates the result via Event
    try{
      eventDispatcher.send(
          new MobilityResourceUpdateEvent(location,
              mapper.asMobilityResourceEvents(reportUpdate.getMobilityResourceAdded()),
              mapper.asMobilityResourceEvents(reportUpdate.getMobilityResourceDeleted())));
    }catch (Exception e){
      log.error("Error publishing events ->"+e.getMessage());
      throw new PublishEventException("Error publishing event",e);
    }

  }
}
