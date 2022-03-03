package com.meep.mobilityresources.infrastructure.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.meep.mobilityresources.config.MobilityResourcesConnectionConfiguration;
import com.meep.mobilityresources.domain.exception.ResourceClientCommunicationException;
import com.meep.mobilityresources.infrastructure.rest.dto.ResourceDTO;
import java.util.List;
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

  private MobilityResourcesConnectionConfiguration configuration;

  private MobilityResourceMapper mapper;

  @BeforeEach
  void setup() {
    restTemplate = mock(RestTemplate.class);
    configuration = mock(MobilityResourcesConnectionConfiguration.class);
    mapper = mock(MobilityResourceMapper.class);
    mobilityResourceClient = new MobilityResourceClientImpl(restTemplate,mapper,configuration);
  }

  @Test
  void given_ConfigurationParamsOnRestClient_when_GetMobilityResources_then_ReturnZero()  {
    var responseEntity = mock(ResponseEntity.class);
    when(configuration.getLocation()).thenReturn("lisboa");
    when(configuration.getCompanyZoneIds()).thenReturn(mockListStoreIds());
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

  private List<String> mockListStoreIds(){
   return List.of("474");
  }

}
