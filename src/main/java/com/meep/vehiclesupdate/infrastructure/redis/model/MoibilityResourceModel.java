package com.meep.vehiclesupdate.infrastructure.redis.model;

import com.meep.vehiclesupdate.domain.entity.ResourceType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("Vehicle")
public class MoibilityResourceModel implements Serializable {
  private String id;
  private String name;
  private BigDecimal axisX;
  private BigDecimal axisY;
  private Boolean realTimeData;
  private ResourceType resourceType;
  private Long companyZoneId;
  private String localization;
  private OffsetDateTime creationDate;
  private OffsetDateTime updatedAtDate;
}
