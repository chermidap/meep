package com.meep.mobilityresources.domain.infrastructure;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import java.util.List;

public interface MobilityResourceClient {

   List<MobilityResource> getMobilityResourcesUpdateInfo();
}
