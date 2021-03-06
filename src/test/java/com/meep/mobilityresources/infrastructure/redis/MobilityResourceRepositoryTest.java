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
import com.meep.mobilityresources.infrastructure.redis.model.MobilityResourceModel;
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
  MobilityResourceRepositoryImpl mobilityResourceRepository;

  @Test
  void given_MobilityResourceInfo_when_MobilityResourcesCall_then_ReturnProperResult() {

    when(repository.findById(anyString())).thenReturn(Optional.of(MobilityResourceModel.builder().id("123456").build()));

    var result = mobilityResourceRepository.getMobilityResourceById("123456");

    assertNotNull(result);
    assertThat(result.getId(), is("123456"));

  }

  @Test
  void given_MobilityResourceInfo_when_UpdateMobilityResourcesInfo_then_ReturnProperResult() {

    when(repository.findById(anyString())).thenReturn(Optional.empty());
    var mobilityResource = new MobilityResource();
    mobilityResource.setId("123456");
    mobilityResourceRepository.updateMobilityResource(mobilityResource);
    verify(repository, times(1)).save(any(MobilityResourceModel.class));
  }

  @Test
  void given_ErrorOnRepository_when_UpdateMobilityResource_then_ThrowException() {
    var mobilityResource = new MobilityResource();
    mobilityResource.setId("123456");
    when(repository.findById(any(String.class))).thenThrow(RuntimeException.class);

    var exceptionThrown = assertThrows(MobilityResourceRepositoryException.class,
        () -> mobilityResourceRepository.updateMobilityResource(mobilityResource),
        "Expected MobilityResourceRepositoryException to be thrown"
    );
    assertThat(exceptionThrown.getMessage(), is("Error updating mobility resource with Id [123456]"));
  }

  @Test
  void given_ErrorOnRepository_when_FindById_then_ThrowException() {
    when(repository.findById(any(String.class))).thenThrow(RuntimeException.class);

    var exceptionThrown = assertThrows(MobilityResourceRepositoryException.class,
        () -> mobilityResourceRepository.getMobilityResourceById("123456"),
        "Expected MobilityResourceRepositoryException to be thrown"
    );
    assertThat(exceptionThrown.getMessage(), is("Error retrieving mobility resource for id [123456]"));
  }

}
