package com.eresh.spring.boot.batch.rest;

import com.eresh.spring.boot.batch.persistance.domain.BatchDetails;
import com.eresh.spring.boot.batch.persistance.domain.User;
import com.eresh.spring.boot.batch.service.DataProcessingService;
import com.eresh.spring.boot.commons.rest.BaseRestApiImpl;
import com.eresh.spring.boot.commons.rest.RestApiException;
import com.eresh.spring.boot.commons.rest.RestRequest;
import com.eresh.spring.boot.commons.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@RestController
@RequestMapping("/api")
public class DataProcessingResource extends BaseRestApiImpl {
    @Autowired
    private DataProcessingService dataProcessingService;

    @PostMapping("/data")
    public ResponseEntity<RestResponse> postData() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            dataProcessingService.saveUserData();
            return new ResponseEntity<RestResponse>(new RestResponse(), HttpStatus.OK);
        });
    }

    @PostMapping("/data1")
    public ResponseEntity<RestResponse> postData1() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            dataProcessingService.saveUserData1();
            return new ResponseEntity<RestResponse>(new RestResponse(), HttpStatus.OK);
        });
    }

    @PostMapping("/data2")
    public ResponseEntity<RestResponse> postData2() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            dataProcessingService.saveUserDataAsync();
            return new ResponseEntity<RestResponse>(new RestResponse(), HttpStatus.OK);
        });
    }

    @PostMapping("/data3")
    public ResponseEntity<RestResponse> postData3() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            dataProcessingService.saveUserDataAsynchWithRetry();
            return new ResponseEntity<RestResponse>(HttpStatus.OK);
        });
    }

    @GetMapping("/users")
    public ResponseEntity<RestResponse> getUsers() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            List<User> users = dataProcessingService.getUsers();
            return new ResponseEntity<RestResponse>(new RestResponse(), HttpStatus.OK);
        });
    }

    @GetMapping("/batchdetails")
    public ResponseEntity<Object> getBatches() {
        List<BatchDetails> batchDetails = dataProcessingService.getBatchDetails();
        return new ResponseEntity<Object>(null, HttpStatus.OK);
    }
}
