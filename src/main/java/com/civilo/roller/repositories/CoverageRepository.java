package com.civilo.roller.repositories;

import com.civilo.roller.Entities.CoverageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverageRepository extends CrudRepository<CoverageEntity, Long> {

}
