package com.meep.vehiclesupdate.infrastructure.redis.repository;

import com.meep.vehiclesupdate.domain.entity.MobilityResource;
import com.meep.vehiclesupdate.domain.exception.MobilityResourceRepositoryException;
import com.meep.vehiclesupdate.domain.repository.MobilityResourceRepository;
import com.meep.vehiclesupdate.infrastructure.redis.mapper.MobilityResourceModelMapper;
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
      return mapper.asVehicle(repository.findById(id).orElseThrow());
    } catch (Exception e) {
      throw new MobilityResourceRepositoryException(String.format("Error retrieving Vehicle for id [%s]", id), e);
    }

  }

  @Override
  public void updateMobilityResource(MobilityResource mobilityResource) {
    try {
      var modelOptional = repository.findById(mobilityResource.getId());
      if (modelOptional.isEmpty()) {
        log.info("updateVehicle saved for vehicle id: {} ", mobilityResource.getId());
        var model= mapper.asVehicleModel(mobilityResource);
        model.setCreationDate(OffsetDateTime.now());
        repository.save(model);
      }
    } catch (Exception e) {
      throw new MobilityResourceRepositoryException(String.format("Error updating vehicle with Id [%s]", mobilityResource.getId()), e);
    }
  }

  @Override
  public List<MobilityResource> getAllMobilityResources() {
    //mapper VehicleModel to Vehicle
    List<MobilityResource> list = new ArrayList<>();
    try {
      repository.findAll().forEach(vehicleModel -> {
        list.add(mapper.asVehicle(vehicleModel));
      });
    } catch (Exception e) {
      throw new MobilityResourceRepositoryException("Error getting all vehicles", e);
    }
    return list;
  }

  @Override
  public void deleteMobilityResource(MobilityResource mobilityResource) {
    repository.delete(mapper.asVehicleModel(mobilityResource));
  }

  @Override
  public void deleteMobilityResourceById(String id) {
    repository.deleteById(id);
  }

}
