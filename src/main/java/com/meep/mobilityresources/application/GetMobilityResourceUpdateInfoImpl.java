package com.meep.mobilityresources.application;

import static java.util.stream.Collectors.partitioningBy;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.domain.entity.ReportUpdate;
import com.meep.mobilityresources.domain.infrastructure.MobilityResourceClient;
import com.meep.mobilityresources.domain.repository.MobilityResourceRepository;
import com.meep.mobilityresources.domain.usecase.GetMobilityResourceUpdateInfo;
import com.meep.mobilityresources.domain.usecase.PublishMobilityResourceUpdateInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetMobilityResourceUpdateInfoImpl implements GetMobilityResourceUpdateInfo {

  private final MobilityResourceClient mobilityResourceClient;

  private final MobilityResourceRepository mobilityResourceRepository;

  private final PublishMobilityResourceUpdateInfo publishMobilityResourceUpdateInfo;

  @Value("${mobility.params.location}")
  private String location;

  @Override
  public ReportUpdate apply() {
    ReportUpdate reportUpdate = createReportUpdate();
    log.info("GetMobilityResourceUpdateInfoImpl.apply start");
    try {

      List<MobilityResource> apiMobilityResources = mobilityResourceClient.getMobilityResourcesUpdateInfo();
      List<MobilityResource> allMobilityResources = mobilityResourceRepository.getAllMobilityResources();

      var partitionedMobilityResources = apiMobilityResources.stream()
          .collect(partitioningBy(newResource ->
              allMobilityResources.stream()
                  .anyMatch(oldResource -> oldResource.getId().equals(newResource.getId()))));
      List<MobilityResource> differentMobilityResourceList = partitionedMobilityResources.get(false);
      mergeUpdateResourceInfoList(reportUpdate, apiMobilityResources, allMobilityResources, differentMobilityResourceList);
      reportUpdate.setCurrentMobilityResources(mobilityResourceRepository.getAllMobilityResources());

      log.info("------ Updating report ------");
      log.info("mobility resources added -----");
      if (!reportUpdate.getMobilityResourceAdded().isEmpty()) {
        reportUpdate.getMobilityResourceAdded().forEach(resource -> {
          log.info(resource.toString());
        });
      }
      log.info("mobility resources deleted -----");
      if (!reportUpdate.getMobilityResourceDeleted().isEmpty()) {
        reportUpdate.getMobilityResourceDeleted().forEach(resource -> log.info(resource.toString()));
      }

      log.info("current mobility resources  -----");
      if (!reportUpdate.getCurrentMobilityResources().isEmpty()) {
        reportUpdate.getCurrentMobilityResources().forEach(resource -> log.info(resource.toString()));
      }
      log.info("GetMobilityResourceUpdateInfoImpl.apply end");

      if (!reportUpdate.getMobilityResourceDeleted().isEmpty() || !reportUpdate.getMobilityResourceAdded().isEmpty()) {
        publishMobilityResourceUpdateInfo.apply(reportUpdate);
      }
    } catch (Exception e) {
      log.error("GetMobilityResourceUpdateInfoImpl.apply" + e.getMessage());
    }
    return reportUpdate;
  }

  private ReportUpdate createReportUpdate() {
    return ReportUpdate.builder()
        .mobilityResourceAdded(new ArrayList<>())
        .mobilityResourceDeleted(new ArrayList<>())
        .currentMobilityResources(new ArrayList<>()).build();
  }

  private void mergeUpdateResourceInfoList(ReportUpdate reportUpdate, List<MobilityResource> apilist, List<MobilityResource> redisList,
      List<MobilityResource> differentMobilityResourceList) {
    if (!differentMobilityResourceList.isEmpty()) {
      reportUpdate.setMobilityResourceAdded(differentMobilityResourceList);
      differentMobilityResourceList.forEach(mobilityResourceRepository::updateMobilityResource);
    } else {
      List<MobilityResource> deleted = new ArrayList<>();
      List<String> stringList = apilist.stream().map(MobilityResource::getId).collect(Collectors.toList());
      redisList.stream().map(MobilityResource::getId)
          .filter(element -> !stringList.contains(element))
          .collect(Collectors.toList()).forEach(resourceToDelete -> {
            log.info(resourceToDelete);
            deleted.add(mobilityResourceRepository.getMobilityResourceById(resourceToDelete));
            mobilityResourceRepository.deleteMobilityResourceById(resourceToDelete);
          });
      reportUpdate.setMobilityResourceDeleted(deleted);
    }
  }
}
