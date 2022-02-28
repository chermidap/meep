package com.meep.vehiclesupdate.config.scheduler;

import com.meep.vehiclesupdate.domain.usecase.GetMobilityResourceUpdateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class UpdatingMobilityResourceInfoSchedulerConfiguration {

  @Autowired
  private GetMobilityResourceUpdateInfo getVehiclesInfo;

  @Scheduled(cron = "${update-vehicles-info.scheduler-cron}")
  public void startScheduler() {
    getVehiclesInfo.apply();
  }
}
