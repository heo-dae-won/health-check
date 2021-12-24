package com.heo.dae.won.healthcheck.health;

import com.heo.dae.won.healthcheck.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUser(String user);
}
