package com.meep.mobilityresources.infrastructure.redis.model;

import com.meep.mobilityresources.domain.entity.MobilityResourceTypeEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("MobilityResource")
public class MobilityResourceModel implements Serializable {

  private String id;

  private String name;

  private BigDecimal axisX;

  private BigDecimal axisY;

  private Boolean realTimeData;

  private Long companyZoneId;

  private String localization;

  private MobilityResourceTypeEnum kindOfResource;

  private OffsetDateTime creationDate;
}
