package com.meep.vehiclesupdate.domain.infrastructure;

import com.meep.vehiclesupdate.domain.entity.MobilityResource;
import java.util.List;

public interface MobilityResourceClient {

   List<MobilityResource> getMobilityResourcesUpdateInfo();
}
