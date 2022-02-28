package com.meep.vehiclesupdate.infrastructure.rest;

import static java.lang.String.format;

import com.meep.vehiclesupdate.config.MobilityResourcesConnectionConfiguration;
import com.meep.vehiclesupdate.domain.entity.MobilityResource;
import com.meep.vehiclesupdate.domain.exception.ResourceClientCommunicationException;
import com.meep.vehiclesupdate.domain.infrastructure.MobilityResourceClient;
import com.meep.vehiclesupdate.infrastructure.rest.dto.ResourceDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class MobilityResourceClientImpl implements MobilityResourceClient {

  public static final String REGEX = "\\s|\\[|\\]";

  private static final String LOCATION_QUERY_PARAM = "location";

  private static final String UPPER_RIGHT_LATLON_QUERY_PARAM = "upperRightLatLon";

  private static final String LOWER_LEFT_LATLON_QUERY_PARAM = "lowerLeftLatLon";

  private static final String COMPANY_ZONE_IDS_QUERY_PARAM = "companyZoneIds";

  private final RestTemplate restTemplate;

  private final MobilityResourceFactory mobilityResourceFactory;

  @Value("${get-info-vehicles.url}")
  private String restUrl;

  private final MobilityResourcesConnectionConfiguration mobilityResourcesConnectionConfiguration;

  public List<MobilityResource> getMobilityResourcesUpdateInfo() {
    var list = new ArrayList<MobilityResource>();
    log.info("getMobilityResourcesUpdateInfo call url --> " + restUrl);
    try{
     var location = mobilityResourcesConnectionConfiguration.getLocation();
     var lowerLeftLatLong = mobilityResourcesConnectionConfiguration.getLowerLeftLatLon();
     var upperRightLatLong = mobilityResourcesConnectionConfiguration.getUpperRightLatLon();
     var companyIds = mobilityResourcesConnectionConfiguration.getCompanyZoneIds();

     List<ResourceDTO> resourceDTOList = restTemplate.exchange(restUrl, HttpMethod.GET, null,
          new ParameterizedTypeReference<List<ResourceDTO>>() {
          }, Map.of(LOCATION_QUERY_PARAM, location, LOWER_LEFT_LATLON_QUERY_PARAM, sanitizeParams(lowerLeftLatLong),
             UPPER_RIGHT_LATLON_QUERY_PARAM,sanitizeParams(upperRightLatLong),COMPANY_ZONE_IDS_QUERY_PARAM,sanitizeParams(companyIds))).getBody();
      if (resourceDTOList != null) {
        resourceDTOList.forEach(resource -> {
          list.add(mobilityResourceFactory.from(resource));
        });
      }
    }catch (HttpStatusCodeException e) {
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        log.warn("[getMobilityResourcesUpdateInfo] No mobility resources found", e);
      } else {
        log.error("[getMobilityResourcesUpdateInfo] HTTP error has received", e);
        var message = format("Error code [%s]", e.getStatusCode());
        throw new ResourceClientCommunicationException(message, e);
      }
    } catch (Exception e) {
      log.error("[getMobilityResourcesUpdateInfo] Error has received", e);
      throw new ResourceClientCommunicationException("Unsupported error", e);
    }
    return list;
  }

  private String sanitizeParams(List<String> source){
    var s = source.toString();
    return s.replaceAll(REGEX, "");
  }
}
