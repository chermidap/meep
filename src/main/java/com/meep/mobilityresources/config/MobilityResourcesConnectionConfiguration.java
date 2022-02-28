package com.meep.mobilityresources.config;

import java.util.List;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mobility.params")
@Setter
public class MobilityResourcesConnectionConfiguration {

  protected List<String> companyZoneIds;

  protected String location;

  protected List<String> lowerLeftLatLon;

  protected List<String> upperRightLatLon;

  public List<String> getCompanyZoneIds() {
    return companyZoneIds;
  }

  public String getLocation() {
    return location;
  }

  public List<String> getLowerLeftLatLon() {
    return lowerLeftLatLon;
  }

  public List<String> getUpperRightLatLon() {
    return upperRightLatLon;
  }

}
