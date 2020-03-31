package com.trucku.driverreporter.restapi;

import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trucku.driverreporter.domain.DriverLocation;
import com.trucku.driverreporter.domain.Location;

import org.apache.kafka.common.errors.TimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/drivers")
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class DriverReporterApi {

    private static Logger log = LogManager.getLogger(DriverReporterApi.class);

    private KafkaTemplate<Integer, String> kafkaProducer;

    @PostMapping("/location")
    public ResponseEntity<Void> reportDriverLocation(@RequestBody Location location) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            DriverLocation driverLocation = new DriverLocation(location);
            driverLocation.setDriver("somebody3");
            
            String serializedDriver = mapper.writeValueAsString(driverLocation);
            
            log.info("Sending...");
            kafkaProducer.send("driver-locations", serializedDriver).get();
        } catch(ExecutionException | TimeoutException | InterruptedException | JsonProcessingException e) {
            log.error("Error encountered sending Kafka message to topic [driver-locations]: %s", e);
        }
        
        return ResponseEntity.ok().build();
    }

}