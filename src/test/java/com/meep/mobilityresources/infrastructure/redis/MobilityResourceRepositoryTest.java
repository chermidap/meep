package com.meep.mobilityresources.infrastructure.redis;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.domain.exception.MobilityResourceRepositoryException;
import com.meep.mobilityresources.infrastructure.redis.mapper.MobilityResourceModelMapper;
import com.meep.mobilityresources.infrastructure.redis.model.MoibilityResourceModel;
import com.meep.mobilityresources.infrastructure.redis.repository.MobilityResourceRedisRepository;
import com.meep.mobilityresources.infrastructure.redis.repository.MobilityResourceRepositoryImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MobilityResourceRepositoryTest {

  @Mock
  private MobilityResourceRedisRepository repository;
  @Spy
  private MobilityResourceModelMapper mapper = Mappers.getMapper(MobilityResourceModelMapper.class);

  @InjectMocks
  MobilityResourceRepositoryImpl vehiclesInfoRepository;

  @Test
  void given_VehiclesInfo_when_VehiclesCall_then_ReturnProperResult() {

    when(repository.findById(anyString())).thenReturn(Optional.of(MoibilityResourceModel.builder().id("123456").build()));

    var result = vehiclesInfoRepository.getMobilityResourceById("123456");

    assertNotNull(result);
    assertThat(result.getId(), is("123456"));

  }

  @Test
  void given_VehiclesInfo_when_UpdateVehiclesinfo_then_ReturnProperResult() {

    when(repository.findById(anyString())).thenReturn(Optional.empty());
    var vehicle = new MobilityResource();
    vehicle.setId("123456");
    vehiclesInfoRepository.updateMobilityResource(vehicle);
    verify(repository, times(1)).save(any(MoibilityResourceModel.class));
  }

  @Test
  void given_ErrorOnRepository_when_UpdateVehicle_then_ThrowException() {
    var vehicle = new MobilityResource();
    vehicle.setId("123456");
    when(repository.findById(any(String.class))).thenThrow(RuntimeException.class);

    var exceptionThrown = assertThrows(MobilityResourceRepositoryException.class,
        () -> vehiclesInfoRepository.updateMobilityResource(vehicle),
        "Expected VehicleRepositoryException to be thrown, but was not."
    );
    assertThat(exceptionThrown.getMessage(), is("Error updating vehicle with Id [123456]"));
  }

  @Test
  void given_ErrorOnRepository_when_FindById_then_ThrowException() {
    when(repository.findById(any(String.class))).thenThrow(RuntimeException.class);

    var exceptionThrown = assertThrows(MobilityResourceRepositoryException.class,
        () -> vehiclesInfoRepository.getMobilityResourceById("123456"),
        "Expected VehicleRepositoryException to be thrown, but was not."
    );
    assertThat(exceptionThrown.getMessage(), is("Error retrieving Vehicle for id [123456]"));
  }

}
