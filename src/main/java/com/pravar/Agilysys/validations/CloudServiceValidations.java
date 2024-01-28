package com.pravar.Agilysys.validations;

import com.pravar.Agilysys.model.CloudService;
import com.pravar.Agilysys.model.Customer;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CloudServiceValidations {

    @Autowired
    CloudService cloudService;

    public boolean isServiceNameValid(@NotNull CloudService cloudService) {
        if (cloudService.getServiceName() == null) {
            return false;
        }
        List<String> servicesNames = new ArrayList<>();

        servicesNames.add("S3");
        servicesNames.add("EC2");
        servicesNames.add("RDS");
        servicesNames.add("Lambda");
        servicesNames.add("DynamoDB");
        servicesNames.add("SQS");
        servicesNames.add("SNS");
        servicesNames.add("Kinesis");
        servicesNames.add("ECS");
        servicesNames.add("CloudFront");
        servicesNames.add("CloudWatch");

        if (!servicesNames.contains(cloudService.getServiceName())) {
            return false;
        }
        return true;
    }

    public boolean isPlanValid(@NotNull CloudService cloudService) {
        if (cloudService.getPlan() == null) {
            return false;
        }

        List<String> plans = new ArrayList<>();

        switch (cloudService.getServiceName()) {
            case "S3":
                plans.add("Standard Storage");
                plans.add("Standard-IA");
                plans.add("One Zone-IA");
                plans.add("Glacier");
                break;
            case "EC2":
                plans.add("On-Demand Instances");
                plans.add("Reserved Instances");
                plans.add("Spot Instances");
                break;
            case "RDS":
                plans.add("General Purpose");
                plans.add("Provisioned IOPS");
                plans.add("Magnetic");
                break;
            case "Lambda":
                plans.add("Pay-per-Use");
                plans.add("Provisioned Concurrency");
                break;
            case "DynamoDB":
                plans.add("On-Demand Capacity");
                plans.add("Provisioned Capacity");
                plans.add("Auto Scaling");
                break;
            case "SQS":
                plans.add("Standard Queue");
                plans.add("FIFO Queue");
                break;
            case "SNS":
                plans.add("Publish-Subscribe Model");
                plans.add("Topic Subscriptions");
                break;
            case "Kinesis":
                plans.add("Kinesis Data Streams");
                plans.add("Kinesis Data Firehose");
                plans.add("Kinesis Data Analytics");
                break;
            case "ECS":
                plans.add("Fargate Launch Type");
                plans.add("EC2 Launch Type");
                plans.add("Task and Service Definitions");
                break;
            case "CloudFront":
                plans.add("Web Distribution");
                plans.add("RTMP Distribution");
                plans.add("Custom SSL Certificates");
                break;
            case "CloudWatch":
                plans.add("Basic Monitoring");
                plans.add("Standard Monitoring");
                plans.add("Alarms and Notifications");
                plans.add("Logs Monitoring");
                break;
            default:
                return false;
        }

        if (!plans.contains(cloudService.getPlan())) {
            return false;
        }
        return true;
    }
}
