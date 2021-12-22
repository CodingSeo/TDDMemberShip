package com.codingseo.tddmembership.repositories.master;

import com.codingseo.tddmembership.entities.master.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(final String userName);
}
