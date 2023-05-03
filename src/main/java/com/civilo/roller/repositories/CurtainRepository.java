package com.civilo.roller.repositories;

import com.civilo.roller.Entities.CurtainEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurtainRepository extends CrudRepository<CurtainEntity, Long> {
}
