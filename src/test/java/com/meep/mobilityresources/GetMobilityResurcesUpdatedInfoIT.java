package com.meep.mobilityresources;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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

  @Test
  void getMobilityResourcesUpdateInfoTest() throws Exception {

   var resourceUpdated = getVehiclesInfo.apply();
   assertNotNull(resourceUpdated);
  }

}
