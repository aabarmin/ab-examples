package dev.abarmin.spring.repository;

import dev.abarmin.spring.entity.UserEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<UserEntity, String> {
}
