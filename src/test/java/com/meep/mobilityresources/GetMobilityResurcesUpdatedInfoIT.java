package com.meep.mobilityresources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.meep.mobilityresources.domain.repository.MobilityResourceRepository;
import com.meep.mobilityresources.domain.usecase.GetMobilityResourceUpdateInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GetMobilityResurcesUpdatedInfoIT {

  @Autowired
  private GetMobilityResourceUpdateInfo getVehiclesInfo;

  @Autowired
  private MobilityResourceRepository repository;

  @Test
  void getMobilityResourcesUpdateInfoTest(){

   var resourceUpdated = getVehiclesInfo.apply();
   var bbddResources = repository.getAllMobilityResources();
   assertNotNull(resourceUpdated);
   assertNotNull(bbddResources);
   assertEquals(resourceUpdated.getCurrentMobilityResources(),bbddResources);
  }

}
