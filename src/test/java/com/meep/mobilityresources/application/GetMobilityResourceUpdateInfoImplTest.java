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
import com.meep.mobilityresources.domain.infrastructure.MobilityResourceClient;
import com.meep.mobilityresources.domain.repository.MobilityResourceRepository;
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

  @InjectMocks
  private GetMobilityResourceUpdateInfoImpl getVehiclesInfo;

  //scenarios
  // caso de uso call api get new vehicles respecto a bbdd
  // caso de uso call api get no differences respecto bbdd
  // caso de uso call api get menos vehicles respoecot a bbdd
  // en estos casos comprobar el objeto reporting
  @Test
  void given_ResourcesFromBBDD_when_ResourceMobilityClientCall_then_NoChangesReturnResult() {

    when(mobilityResourceClient.getMobilityResourcesUpdateInfo()).thenReturn(mockGetOneMobilityResources());
    when(mobilityResourceRepository.getAllMobilityResources()).thenReturn(mockGetOneMobilityResources());
    var report = getVehiclesInfo.apply();
    verify(mobilityResourceRepository, times(2)).getAllMobilityResources();
    verify(mobilityResourceClient, times(1)).getMobilityResourcesUpdateInfo();
    assertNotNull(report);
    assertTrue(report.getVehiclesAdded().isEmpty());
    assertTrue(report.getVehiclesDeleted().isEmpty());
  }

  @Test
  void given_SomeResourcesFromBBDD_when_ResourceMobilityClientCall_then_DeleteReturnResult() {

    when(mobilityResourceClient.getMobilityResourcesUpdateInfo()).thenReturn(mockGetOneMobilityResources());
    when(mobilityResourceRepository.getAllMobilityResources()).thenReturn(mockGetSomeMobilityResources());
    when(mobilityResourceRepository.getMobilityResourceById(anyString())).thenReturn(mockGetMobiltiyResource());
    doNothing().when(mobilityResourceRepository).deleteMobilityResourceById(any(String.class));
    var report = getVehiclesInfo.apply();
    verify(mobilityResourceRepository, times(2)).getAllMobilityResources();
    verify(mobilityResourceClient, times(1)).getMobilityResourcesUpdateInfo();
    assertNotNull(report);
    assertTrue(report.getVehiclesAdded().isEmpty());
    assertFalse(report.getVehiclesDeleted().isEmpty());
  }

  @Test
  void given_SomeResourcesFromBBDD_when_ResourceMobilityClientCall_then_AddReturnResult() {

    when(mobilityResourceClient.getMobilityResourcesUpdateInfo()).thenReturn(mockGetSomeMobilityResources());
    when(mobilityResourceRepository.getAllMobilityResources()).thenReturn(mockGetOneMobilityResources());
    doNothing().when(mobilityResourceRepository).updateMobilityResource(any(MobilityResource.class));
    var report = getVehiclesInfo.apply();
    verify(mobilityResourceRepository, times(2)).getAllMobilityResources();
    verify(mobilityResourceClient, times(1)).getMobilityResourcesUpdateInfo();
    assertNotNull(report);
    assertFalse(report.getVehiclesAdded().isEmpty());
    assertTrue(report.getVehiclesDeleted().isEmpty());
  }

  List<MobilityResource> mockGetOneMobilityResources() {

    var mob_sharing = new MobilityResource();
    mob_sharing.setCompanyZoneId(473);
    mob_sharing.setName("11VJ84");
    mob_sharing.setId("PT-LIS-A00114");
    mob_sharing.setAxisY(BigDecimal.valueOf(38.735588));
    mob_sharing.setAxisX(BigDecimal.valueOf(-9.145258));
    mob_sharing.setRealTimeData(true);
    mob_sharing.setResourceType(MotoSharing.builder().build());

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
    mob_sharing.setResourceType(MotoSharing.builder().build());

    var mob_sharing2 = new MobilityResource();
    mob_sharing2.setCompanyZoneId(473);
    mob_sharing2.setName("11VJ27");
    mob_sharing2.setId("PT-LIS-A00102");
    mob_sharing2.setAxisY(BigDecimal.valueOf(38.736084));
    mob_sharing2.setAxisX(BigDecimal.valueOf(-9.146189));
    mob_sharing2.setRealTimeData(true);
    mob_sharing2.setResourceType(MotoSharing.builder().build());

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
    mob_sharing.setResourceType(MotoSharing.builder().build());
    return mob_sharing;
  }

}
