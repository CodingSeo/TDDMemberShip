package com.codingseo.tddmembership.repositories;

import com.codingseo.tddmembership.entities.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<MemberShip, Long> {
}
