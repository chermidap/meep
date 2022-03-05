package com.meep.mobilityresources.infrastructure.redis.repository;

import com.meep.mobilityresources.domain.entity.MobilityResource;
import com.meep.mobilityresources.domain.exception.MobilityResourceRepositoryException;
import com.meep.mobilityresources.domain.repository.MobilityResourceRepository;
import com.meep.mobilityresources.infrastructure.redis.mapper.MobilityResourceModelMapper;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MobilityResourceRepositoryImpl implements MobilityResourceRepository {

  private final MobilityResourceRedisRepository repository;

  private final MobilityResourceModelMapper mapper;

  @Override
  public MobilityResource getMobilityResourceById(String id) {
    try {
      return mapper.asMobilityResource(repository.findById(id).orElseThrow());
    } catch (Exception e) {
      throw new MobilityResourceRepositoryException(String.format("Error retrieving mobility resource for id [%s]", id), e);
    }

  }

  @Override
  public void updateMobilityResource(MobilityResource mobilityResource) {
    try {
      var modelOptional = repository.findById(mobilityResource.getId());
      if (modelOptional.isEmpty()) {
        log.info("updateMobilityResource saved for mobility resource id: {} ", mobilityResource.getId());
        var model = mapper.asMobilityResourceModel(mobilityResource);
        model.setCreationDate(OffsetDateTime.now());
        repository.save(model);
      }
    } catch (Exception e) {
      throw new MobilityResourceRepositoryException(String.format("Error updating mobility resource with Id [%s]", mobilityResource.getId()), e);
    }
  }

  @Override
  public List<MobilityResource> getAllMobilityResources() {
    List<MobilityResource> list = new ArrayList<>();
    try {
      repository.findAll().forEach(mobilityResourceModel -> {
        list.add(mapper.asMobilityResource(mobilityResourceModel));
      });
    } catch (Exception e) {
      throw new MobilityResourceRepositoryException("Error getting all mobility resource", e);
    }
    return list;
  }

  @Override
  public void deleteMobilityResourceById(String id) {
    repository.deleteById(id);
  }

}
