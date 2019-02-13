package com.eresh.spring.boot;

import com.eresh.spring.boot.commons.rest.BaseRestApiImpl;
import com.eresh.spring.boot.commons.rest.RestApiException;
import com.eresh.spring.boot.commons.rest.RestRequest;
import com.eresh.spring.boot.commons.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ResourceTest extends BaseRestApiImpl {

    @Autowired
    private MailService mailService;

    @GetMapping("/email")
    public ResponseEntity<RestResponse> sendMail() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            mailService.sendEmail();
            return new ResponseEntity<RestResponse>(new RestResponse(), HttpStatus.OK);
        });
    }
}
