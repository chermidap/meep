package com.meep.mobilityresources.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.domain.entity.MotoSharing;
import com.meep.mobilityresources.domain.entity.ReportUpdate;
import com.meep.mobilityresources.domain.infrastructure.MobilityResourceClient;
import com.meep.mobilityresources.domain.repository.MobilityResourceRepository;
import com.meep.mobilityresources.domain.usecase.PublishMobilityResourceUpdateInfo;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMobilityResourceUpdateInfoImplTest {

  @Mock
  private MobilityResourceClient mobilityResourceClient;

  @Mock
  private MobilityResourceRepository mobilityResourceRepository;

  @Mock
  private PublishMobilityResourceUpdateInfo publishMobilityResourceUpdateInfo;

  @InjectMocks
  private GetMobilityResourceUpdateInfoImpl getMobilityResourceUpdateInfo;

  @Test
  void given_ResourcesFromBBDD_when_ResourceMobilityClientCall_then_NoChangesReturnResult() {

    when(mobilityResourceClient.getMobilityResourcesUpdateInfo()).thenReturn(mockGetOneMobilityResources());
    when(mobilityResourceRepository.getAllMobilityResources()).thenReturn(mockGetOneMobilityResources());
    var report = getMobilityResourceUpdateInfo.apply();
    verify(mobilityResourceRepository, times(2)).getAllMobilityResources();
    verify(mobilityResourceClient, times(1)).getMobilityResourcesUpdateInfo();
    assertNotNull(report);
    assertTrue(report.getMobilityResourceAdded().isEmpty());
    assertTrue(report.getMobilityResourceDeleted().isEmpty());
  }

  @Test
  void given_SomeResourcesFromBBDD_when_ResourceMobilityClientCall_then_DeleteReturnResult() {

    when(mobilityResourceClient.getMobilityResourcesUpdateInfo()).thenReturn(mockGetOneMobilityResources());
    when(mobilityResourceRepository.getAllMobilityResources()).thenReturn(mockGetSomeMobilityResources());
    when(mobilityResourceRepository.getMobilityResourceById(anyString())).thenReturn(mockGetMobiltiyResource());
    doNothing().when(publishMobilityResourceUpdateInfo).apply(any(ReportUpdate.class));
    doNothing().when(mobilityResourceRepository).deleteMobilityResourceById(any(String.class));
    var report = getMobilityResourceUpdateInfo.apply();
    verify(mobilityResourceRepository, times(2)).getAllMobilityResources();
    verify(mobilityResourceClient, times(1)).getMobilityResourcesUpdateInfo();
    assertNotNull(report);
    assertTrue(report.getMobilityResourceAdded().isEmpty());
    assertFalse(report.getMobilityResourceDeleted().isEmpty());
  }

  @Test
  void given_SomeResourcesFromBBDD_when_ResourceMobilityClientCall_then_AddReturnResult() {

    when(mobilityResourceClient.getMobilityResourcesUpdateInfo()).thenReturn(mockGetSomeMobilityResources());
    when(mobilityResourceRepository.getAllMobilityResources()).thenReturn(mockGetOneMobilityResources());
    doNothing().when(publishMobilityResourceUpdateInfo).apply(any(ReportUpdate.class));
    doNothing().when(mobilityResourceRepository).updateMobilityResource(any(MobilityResource.class));
    var report = getMobilityResourceUpdateInfo.apply();
    verify(mobilityResourceRepository, times(2)).getAllMobilityResources();
    verify(mobilityResourceClient, times(1)).getMobilityResourcesUpdateInfo();
    assertNotNull(report);
    assertFalse(report.getMobilityResourceAdded().isEmpty());
    assertTrue(report.getMobilityResourceDeleted().isEmpty());
  }

  List<MobilityResource> mockGetOneMobilityResources() {

    var mob_sharing = new MobilityResource();
    mob_sharing.setCompanyZoneId(473);
    mob_sharing.setName("11VJ84");
    mob_sharing.setId("PT-LIS-A00114");
    mob_sharing.setAxisY(BigDecimal.valueOf(38.735588));
    mob_sharing.setAxisX(BigDecimal.valueOf(-9.145258));
    mob_sharing.setRealTimeData(true);
    mob_sharing.setMobilityResource(MotoSharing.builder().build());

    return List.of(mob_sharing);
  }

  List<MobilityResource> mockGetSomeMobilityResources() {

    var mob_sharing = new MobilityResource();
    mob_sharing.setCompanyZoneId(473);
    mob_sharing.setName("11VJ84");
    mob_sharing.setId("PT-LIS-A00114");
    mob_sharing.setAxisY(BigDecimal.valueOf(38.735588));
    mob_sharing.setAxisX(BigDecimal.valueOf(-9.145258));
    mob_sharing.setRealTimeData(true);
    mob_sharing.setMobilityResource(MotoSharing.builder().build());

    var mob_sharing2 = new MobilityResource();
    mob_sharing2.setCompanyZoneId(473);
    mob_sharing2.setName("11VJ27");
    mob_sharing2.setId("PT-LIS-A00102");
    mob_sharing2.setAxisY(BigDecimal.valueOf(38.736084));
    mob_sharing2.setAxisX(BigDecimal.valueOf(-9.146189));
    mob_sharing2.setRealTimeData(true);
    mob_sharing2.setMobilityResource(MotoSharing.builder().build());

    return List.of(mob_sharing, mob_sharing2);
  }

  private MobilityResource mockGetMobiltiyResource() {
    var mob_sharing = new MobilityResource();
    mob_sharing.setCompanyZoneId(473);
    mob_sharing.setName("11VJ84");
    mob_sharing.setId("PT-LIS-A00114");
    mob_sharing.setAxisY(BigDecimal.valueOf(38.735588));
    mob_sharing.setAxisX(BigDecimal.valueOf(-9.145258));
    mob_sharing.setRealTimeData(true);
    mob_sharing.setMobilityResource(MotoSharing.builder().build());
    return mob_sharing;
  }

}
