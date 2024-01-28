package com.pravar.Agilysys.repository;

import com.pravar.Agilysys.model.CloudService;
import com.pravar.Agilysys.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CloudServiceRepository extends JpaRepository<CloudService, Integer> {

}
