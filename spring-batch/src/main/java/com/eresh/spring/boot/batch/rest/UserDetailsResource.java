package com.eresh.spring.boot.batch.rest;

import com.eresh.spring.boot.batch.rest.ws.WSGetUsersResponse;
import com.eresh.spring.boot.batch.rest.ws.WSUser;
import com.eresh.spring.boot.batch.service.MailService;
import com.eresh.spring.boot.batch.service.UserDataService;
import com.eresh.spring.boot.batch.service.vo.UserVO;
import com.eresh.spring.boot.commons.rest.BaseRestApiImpl;
import com.eresh.spring.boot.commons.rest.RestApiException;
import com.eresh.spring.boot.commons.rest.RestRequest;
import com.eresh.spring.boot.commons.rest.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@RestController
@RequestMapping("/api")
public class UserDetailsResource extends BaseRestApiImpl {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private MailService mailService;

    @GetMapping("/users/name/{firstname}")
    public ResponseEntity<RestResponse> getUsersByName(@PathVariable(name = "firstname") String firstName, @RequestParam(name = "filter", required = true) String filter) throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            Flux<UserVO> userVOS = userDataService.getUserDetailsByFirstName(firstName);
            WSGetUsersResponse response = new WSGetUsersResponse();
            List<WSUser> wsUsers = new ArrayList<WSUser>();
            userVOS.map(WSUser :: new).subscribe(wsUsers::add);
            response.setUsers(wsUsers);
            return new ResponseEntity<RestResponse>(response, HttpStatus.OK);
        });
    }

    @GetMapping("/users/all")
    public ResponseEntity<RestResponse> getAllUsers() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            Flux<UserVO> userVOS = userDataService.getAllUsers();
            WSGetUsersResponse response = new WSGetUsersResponse();
            List<WSUser> wsUsers = new ArrayList<WSUser>();
            userVOS.map(WSUser :: new).subscribe(wsUsers::add);
            response.setUsers(wsUsers);
            return new ResponseEntity<RestResponse>(response, HttpStatus.OK);
        });
    }

    @GetMapping("/test")
    public ResponseEntity<RestResponse> test() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            userDataService.manuplate();
            return new ResponseEntity<RestResponse>(new RestResponse(), HttpStatus.OK);
        });
    }

    @GetMapping("/email")
    public ResponseEntity<RestResponse> sendMail() throws RestApiException {
        return inboundServiceCall(new RestRequest(), service -> {
            mailService.sendEmail();
            return new ResponseEntity<RestResponse>(new RestResponse(), HttpStatus.OK);
        });
    }
}
