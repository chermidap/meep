package com.meep.mobilityresources.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.meep.mobilityresources.domain.entity.ReportUpdate;
import com.meep.mobilityresources.domain.exception.PublishEventException;
import com.meep.mobilityresources.infrastructure.rabbitmq.dispatcher.EventDispatcher;
import com.meep.mobilityresources.infrastructure.rabbitmq.event.MobilityResourceUpdateEvent;
import com.meep.mobilityresources.infrastructure.rabbitmq.mapper.MobilityResourceEventMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PublishMobilityResourceUpdateInfoImplTest {

  @Mock
  private EventDispatcher eventDispatcher;

  @Mock
  private MobilityResourceEventMapper mapper;

  @InjectMocks
  private PublishMobilityResourceUpdateInfoImpl publishMobilityResourceUpdateInfo;

  @Test
  void given_ReportUpdate_when_PublishInfo_then_EventIsSent() {

    doNothing().when(eventDispatcher).send(any(MobilityResourceUpdateEvent.class));
    publishMobilityResourceUpdateInfo.apply(ReportUpdate.builder().build());
    verify(eventDispatcher, times(1)).send(any(MobilityResourceUpdateEvent.class));

  }

  @Test
  void given_SomeErrorFormDipatcher_when_ResourceSendEvent_then_NoEventIsPublish() {

    doThrow(RuntimeException.class).when(eventDispatcher).send(any(MobilityResourceUpdateEvent.class));
    var exceptionThrown = assertThrows(PublishEventException.class,
        () -> publishMobilityResourceUpdateInfo.apply(ReportUpdate.builder().build()),
        "Expected PublishEventException to be thrown"
    );
    assertThat(exceptionThrown.getMessage(), is("Error publishing event"));
  }

}
