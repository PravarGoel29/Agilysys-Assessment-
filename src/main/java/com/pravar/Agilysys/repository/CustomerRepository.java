package com.pravar.Agilysys.repository;

import com.pravar.Agilysys.model.CloudService;
import com.pravar.Agilysys.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    List<Customer> findByOnboardingDate(String onboardingDate);

    String deleteByEmail(String email);

    @Query("SELECT DISTINCT c FROM Customer c JOIN c.cloudServices cs WHERE cs.serviceName = :serviceName")
    List<Customer> findByServiceName(@Param("serviceName") String serviceName);
}
