package com.meep.vehiclesupdate.application;

import static java.util.stream.Collectors.partitioningBy;

import com.meep.vehiclesupdate.domain.entity.MobilityResource;
import com.meep.vehiclesupdate.domain.entity.ReportUpdate;
import com.meep.vehiclesupdate.domain.infrastructure.MobilityResourceClient;
import com.meep.vehiclesupdate.domain.repository.MobilityResourceRepository;
import com.meep.vehiclesupdate.domain.usecase.GetMobilityResourceUpdateInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetMobilityResourceUpdateInfoImpl implements GetMobilityResourceUpdateInfo {

  private final MobilityResourceClient mobilityResourceClient;

  private final MobilityResourceRepository mobilityResourceRepository;

  @Override
  public ReportUpdate apply() {
    var reportUpdate = ReportUpdate.builder()
        .vehiclesAdded(new ArrayList<>())
        .vehiclesDeleted(new ArrayList<>())
        .currentMobilityResources(new ArrayList<>()).build();
    log.info("GetMobilityResourceUpdateInfoImpl.apply start");
    try {

      List<MobilityResource> apilist = mobilityResourceClient.getMobilityResourcesUpdateInfo();
      List<MobilityResource> redisList = mobilityResourceRepository.getAllMobilityResources();

      var partitionedVehicles = apilist.stream()
          .collect(partitioningBy(newVehicle ->
              redisList.stream()
                  .anyMatch(oldVehicle -> oldVehicle.getId().equals(newVehicle.getId()))));
      List<MobilityResource> differentMobilityResourceList = partitionedVehicles.get(false);
      mergeUpdateResourceIngoList(reportUpdate, apilist, redisList, differentMobilityResourceList);
      reportUpdate.setCurrentMobilityResources(mobilityResourceRepository.getAllMobilityResources());
    } catch (Exception e) {
      log.error("GetMobilityResourceUpdateInfoImpl.apply" + e.getMessage());
    }
    log.info("------ Updating report ------");
    log.info("vehicles added -----");
    if (!reportUpdate.getVehiclesAdded().isEmpty()) {
      reportUpdate.getVehiclesAdded().forEach(vehicle -> {
        log.info(vehicle.toString());
      });
    }
    log.info("vehicles deleted -----");
    if (!reportUpdate.getVehiclesDeleted().isEmpty()) {
      reportUpdate.getVehiclesDeleted().forEach(vehicle -> log.info(vehicle.toString()));
    }

    log.info("Current vehicles -----");
    if (!reportUpdate.getCurrentMobilityResources().isEmpty()) {
      reportUpdate.getCurrentMobilityResources().forEach(vehicle -> log.info(vehicle.toString()));
    }
    log.info("GetMobilityResourceUpdateInfoImpl.apply end");
    return reportUpdate;
  }

  private void mergeUpdateResourceIngoList(ReportUpdate reportUpdate, List<MobilityResource> apilist, List<MobilityResource> redisList,
      List<MobilityResource> differentMobilityResourceList) {
    if (!differentMobilityResourceList.isEmpty()) {
      reportUpdate.setVehiclesAdded(differentMobilityResourceList);
      differentMobilityResourceList.forEach(mobilityResourceRepository::updateMobilityResource);
    } else {
      List<MobilityResource> deleted = new ArrayList<>();
      List<String> stringList = apilist.stream().map(MobilityResource::getId).collect(Collectors.toList());
      redisList.stream().map(MobilityResource::getId)
          .filter(element -> !stringList.contains(element))
          .collect(Collectors.toList()).forEach(vehicleToDelete -> {
            log.info(vehicleToDelete);
            deleted.add(mobilityResourceRepository.getMobilityResourceById(vehicleToDelete));
            mobilityResourceRepository.deleteMobilityResourceById(vehicleToDelete);
          });
      reportUpdate.setVehiclesDeleted(deleted);
    }
  }
}
