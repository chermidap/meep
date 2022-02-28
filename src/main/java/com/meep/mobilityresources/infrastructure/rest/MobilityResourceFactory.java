package com.meep.mobilityresources.infrastructure.rest;

import com.meep.mobilityresources.domain.entity.BikeStation;
import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.domain.entity.MobilityResourceTypeEnum;
import com.meep.mobilityresources.domain.entity.MotoSharing;
import com.meep.mobilityresources.infrastructure.rest.dto.ResourceDTO;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class MobilityResourceFactory {

  public MobilityResource from(final ResourceDTO resource) {
    var mobilityResource =  new MobilityResource();
    if(!resource.isStation()){
      var moto = MotoSharing.builder()
          .batteryLevel(resource.getBatteryLevel())
          .helmets(resource.getHelmets())
          .licencePlate(resource.getLicencePlate())
          .model(resource.getModel())
          .resourceImageId(resource.getResourceImageId())
          .resourcesImagesUrls(resource.getResourcesImagesUrls())
          .range(resource.getRange()).build();
      mobilityResource.setResourceType(moto);
      mobilityResource.setKindOfResource(MobilityResourceTypeEnum.MOTOSHARING);
    }else{
      var bikeStation = BikeStation.builder()
          .station(resource.isStation())
          .allowDropoff(resource.isAllowDropoff())
          .availableResources(resource.getAvailableResources())
          .bikesAvailable(resource.isBikesAvailable()).spacesAvailable(resource.getSpacesAvailable()).build();
      mobilityResource.setResourceType(bikeStation);
      mobilityResource.setKindOfResource(MobilityResourceTypeEnum.BIKESTATION);
    }

    mobilityResource.setAxisX(resource.getAxisX());
    mobilityResource.setAxisY(resource.getAxisY());
    mobilityResource.setId(resource.getId());
    mobilityResource.setName(resource.getName());
    mobilityResource.setRealTimeData(resource.isRealTimeData());
    mobilityResource.setCompanyZoneId(resource.getCompanyZoneId());

    return mobilityResource;
  }

}
