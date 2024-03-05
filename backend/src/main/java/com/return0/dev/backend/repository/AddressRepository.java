package com.return0.dev.backend.repository;

import com.return0.dev.backend.entity.Address;
import com.return0.dev.backend.entity.Social;
import com.return0.dev.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, String> {

    List<Address> findByUser(User user);

}
