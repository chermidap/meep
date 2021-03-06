package com.meep.mobilityresources.config.scheduler;

import com.meep.mobilityresources.domain.usecase.GetMobilityResourceUpdateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class UpdatingMobilityResourceInfoSchedulerConfiguration {

  @Autowired
  private GetMobilityResourceUpdateInfo getMobilityResourceUpdateInfoInfo;

  @Scheduled(cron = "${update-mobility-resources-info.scheduler-cron}")
  public void startScheduler() {
    getMobilityResourceUpdateInfoInfo.apply();
  }
}
