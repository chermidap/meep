package com.meep.mobilityresources.infrastructure.rabbitmq.dispatcher;

import com.meep.mobilityresources.infrastructure.rabbitmq.event.MobilityResourceUpdateEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

  private final RabbitTemplate rabbitTemplate;

  // The exchange to use to send anything related to
  private final String mobilityResourceExchange;
  // The routing key to use to send this particular event
  private final String mobilityResourceUpdateRoutingKey;

  @Autowired
  EventDispatcher(final RabbitTemplate rabbitTemplate, @Value("${mobilityresource.exchange}") final String mobilityResourceExchange,
      @Value("${mobilityresource.updated.key}") final String mobilityResourceUpdateRoutingKey) {
    this.rabbitTemplate = rabbitTemplate;
    this.mobilityResourceExchange = mobilityResourceExchange;
    this.mobilityResourceUpdateRoutingKey = mobilityResourceUpdateRoutingKey;
  }

  public void send(final MobilityResourceUpdateEvent mobilityResourceUpdateEvent) {
    rabbitTemplate.convertAndSend(
        mobilityResourceExchange,
        mobilityResourceUpdateRoutingKey,
        mobilityResourceUpdateEvent);
  }
}
