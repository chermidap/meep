package com.meep.mobilityresources.domain.usecase;

import com.meep.mobilityresources.domain.entity.ReportUpdate;

public interface PublishMobilityResourceUpdateInfo {

   void apply(ReportUpdate reportUpdate);
}
