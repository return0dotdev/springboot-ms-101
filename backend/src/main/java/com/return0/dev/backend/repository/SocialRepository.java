package com.return0.dev.backend.repository;

import com.return0.dev.backend.entity.Social;
import com.return0.dev.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialRepository extends CrudRepository<Social, String> {

    Optional<Social> findByUser(User user);

}
