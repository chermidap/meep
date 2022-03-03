package com.meep.mobilityresources.application;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.domain.entity.ReportUpdate;
import com.meep.mobilityresources.domain.usecase.PublishMobilityResourceUpdateInfo;
import com.meep.mobilityresources.infrastructure.rabbitmq.dispatcher.EventDispatcher;
import com.meep.mobilityresources.infrastructure.rabbitmq.event.MobilityResourceEvent;
import com.meep.mobilityresources.infrastructure.rabbitmq.event.MobilityResourceUpdateEvent;
import com.meep.mobilityresources.infrastructure.rabbitmq.mapper.MobilityResourceEventMapper;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PublishMobilityResourceUpdateInfoImplTest {
  @Mock
  private EventDispatcher eventDispatcher;
  @Spy
  private MobilityResourceEventMapper mapper;

  @InjectMocks
  private  PublishMobilityResourceUpdateInfoImpl publishMobilityResourceUpdateInfo;

  @Test
  void given_ReportUpdate_when_PublishInfo_then_EventIsSent() {

    doNothing().when(eventDispatcher).send(any(MobilityResourceUpdateEvent.class));
    publishMobilityResourceUpdateInfo.apply(ReportUpdate.builder().build());
    verify(eventDispatcher, times(1)).send(any(MobilityResourceUpdateEvent.class));

  }

  private ReportUpdate mockNoUpdatesReport(){
   return ReportUpdate.builder().mobilityResourceAdded(List.of()).mobilityResourceAdded(List.of()).build();
  }

  private ReportUpdate mockAddNewReport(){
    return ReportUpdate.builder().mobilityResourceAdded(List.of()).mobilityResourceAdded(List.of()).build();
  }

}
