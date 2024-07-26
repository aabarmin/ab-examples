package dev.abarmin.spring.repository;

import dev.abarmin.spring.entity.AuthorityEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface AuthorityRepository extends ListCrudRepository<AuthorityEntity, String> {
}
