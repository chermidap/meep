package com.meep.mobilityresources.domain.repository;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import java.util.List;

public interface MobilityResourceRepository {

  MobilityResource getMobilityResourceById(String id);

  void updateMobilityResource(MobilityResource emp);

  List<MobilityResource> getAllMobilityResources();

  void deleteMobilityResource(MobilityResource mobilityResource);

  void deleteMobilityResourceById(String id);

}
