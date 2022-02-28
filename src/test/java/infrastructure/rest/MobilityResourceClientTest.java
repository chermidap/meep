package infrastructure.rest;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.meep.vehiclesupdate.config.MobilityResourcesConnectionConfiguration;
import com.meep.vehiclesupdate.domain.entity.MobilityResource;
import com.meep.vehiclesupdate.domain.entity.MotoSharing;
import com.meep.vehiclesupdate.domain.exception.ResourceClientCommunicationException;
import com.meep.vehiclesupdate.infrastructure.rest.MobilityResourceClientImpl;
import com.meep.vehiclesupdate.infrastructure.rest.MobilityResourceFactory;
import com.meep.vehiclesupdate.infrastructure.rest.dto.ResourceDTO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class MobilityResourceClientTest {

  private RestTemplate restTemplate;

  private MobilityResourceClientImpl mobilityResourceClient;

  private MobilityResourceFactory mobilityResourceFactory;

  private MobilityResourcesConnectionConfiguration configuration;

  @BeforeEach
  void setup() {
    restTemplate = mock(RestTemplate.class);
    configuration = mock(MobilityResourcesConnectionConfiguration.class);
    mobilityResourceFactory = mock(MobilityResourceFactory.class);
    mobilityResourceClient = new MobilityResourceClientImpl(restTemplate,mobilityResourceFactory,configuration);
  }

  @Test
  void given_ConfigurationParamsOnRestClient_when_GetMobilityResources_then_ReturnZero()  {
    var responseEntity = mock(ResponseEntity.class);
    when(configuration.getLocation()).thenReturn("lisboa");
    when(configuration.getCompanyZoneIds()).thenReturn(mockListStoreIds());
    when(mobilityResourceFactory.from(any(ResourceDTO.class))).thenReturn(mockGetMobiltiyResource());
    when(restTemplate.exchange(any(), any(), any(), any(ParameterizedTypeReference.class),anyMap())).thenReturn(responseEntity);
    when(responseEntity.getBody()).thenReturn(mockVehicleDTOList());

    var list = mobilityResourceClient.getMobilityResourcesUpdateInfo();

    assertNotNull(list);
  }

  @Test
  void given_NotFoundExceptionOnRestClient_when_GetMobilityResources_then_ReturnZero() {
    when(configuration.getLocation()).thenReturn("lisboa");
    when(configuration.getCompanyZoneIds()).thenReturn(mockListStoreIds());
    when(restTemplate.exchange(any(), any(), any(), any(ParameterizedTypeReference.class), anyMap()))
        .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND, "", HttpHeaders.EMPTY, new byte[]{1}, null));

    var mobilityResources = mobilityResourceClient.getMobilityResourcesUpdateInfo();

    assertNotNull(mobilityResources);
    assertThat(mobilityResources.size(), is(0));
  }

  @Test
  void given_BadRequestExceptionOnRestClient_when_GetMobilityResources_then_ThowResourceClientCommunicationException() {
    when(configuration.getLocation()).thenReturn("lisboa");
    when(configuration.getCompanyZoneIds()).thenReturn(mockListStoreIds());
    when(restTemplate.exchange(any(), any(), any(), any(ParameterizedTypeReference.class), anyMap()))
        .thenThrow(HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "", HttpHeaders.EMPTY, new byte[]{1}, null));
    Exception exception = assertThrows(ResourceClientCommunicationException.class,
        () -> mobilityResourceClient.getMobilityResourcesUpdateInfo());

    assertEquals(exception.getMessage(), "Error code [400 BAD_REQUEST]");

  }

  @Test
  void given_RuntimeExceptionOnRestClient_when_GetMobilityResources_then_ThowResourceClientCommunicationException() {
    when(configuration.getLocation()).thenReturn("lisboa");
    when(configuration.getCompanyZoneIds()).thenReturn(mockListStoreIds());
    when(restTemplate.exchange(any(), any(), any(), any(ParameterizedTypeReference.class), anyMap()))
        .thenThrow(RuntimeException.class);
    Exception exception = assertThrows(ResourceClientCommunicationException.class,
        () -> mobilityResourceClient.getMobilityResourcesUpdateInfo());

    assertEquals(exception.getMessage(), "Unsupported error");

  }

  private List<ResourceDTO> mockVehicleDTOList(){
    var vehicleDto = new ResourceDTO();
    vehicleDto.setId("");
    vehicleDto.setName("");
    vehicleDto.setStation(false);
    vehicleDto.setRealTimeData(true);

    return List.of(vehicleDto);
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

  private List<String> mockListStoreIds(){
   return List.of("474");
  }

  private String getResponse() {

    return
        "[{\"id\":\"PT-LIS-A00248\",\"name\":\"69VP33\",\"x\":-9.146417,\"y\":38.740987,\"licencePlate\":\"69VP33\",\"range\":21,"
            + "\"batteryLevel\":28,\"helmets\":2,"
            + "\"model\":\"Askoll\",\"resourceImageId\":\"vehicle_gen_ecooltra\",\"resourcesImagesUrls\":[\"vehicle_gen_ecooltra\"],"
            + "\"realTimeData\":true,\"resourceType\":\"MOPED\","
            + "\"companyZoneId\":473},{\"id\":\"PT-LIS-A00203\",\"name\":\"24VO79\",\"x\":-9.166813,\"y\":38.768433,"
            + "\"licencePlate\":\"24VO79\",\"range\":47,\"batteryLevel\":62,"
            + "\"helmets\":2,\"model\":\"Askoll\",\"resourceImageId\":\"vehicle_gen_ecooltra\","
            + "\"resourcesImagesUrls\":[\"vehicle_gen_ecooltra\"],\"realTimeData\":true,"
            + "\"resourceType\":\"MOPED\",\"companyZoneId\":473}]";
  }


}
