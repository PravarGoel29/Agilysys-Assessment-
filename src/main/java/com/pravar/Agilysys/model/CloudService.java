package com.pravar.Agilysys.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Data
@Component
public class CloudService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceID;
    private String serviceName;
    private String plan;
    private boolean isPaused;
    private String serviceStartDate;
    private String servicePauseDate;

    @PrePersist
    public void prePersist() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        this.serviceStartDate = LocalDate.now().format(formatter).toString();
        this.servicePauseDate = null;
        this.isPaused = false;
    }

}
