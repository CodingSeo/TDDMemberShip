package com.codingseo.tddmembership.repositories.sub;

import com.codingseo.tddmembership.entities.sub.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<MemberShip, Long> {
    MemberShip findByUserNo(final int userNo);
}
