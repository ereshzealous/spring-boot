package com.eresh.spring.boot.batch.rest.ws;

import com.eresh.spring.boot.commons.rest.RestResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Getter
@Setter
public class WSGetUsersResponse extends RestResponse {
    private List<WSUser> users  = new ArrayList<WSUser>();
}
