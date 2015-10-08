/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package sample.jaxrs.security;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;

import javax.ws.rs.core.Response;

public class AuthenticationHandler implements RequestHandler {

    private static final String AUTH_TYPE_BASIC = "Basic";


    public Response handleRequest(Message message, ClassResourceInfo resourceClass) {

        AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
        if (policy != null && AUTH_TYPE_BASIC.equals(policy.getAuthorizationType())) {
            return handleBasicAuth(policy);
        }
        return authenticationFail(AUTH_TYPE_BASIC);
    }

    private Response handleBasicAuth(AuthorizationPolicy policy) {
        String username = policy.getUserName();
        String password = policy.getPassword();
        if (isAuthenticated(username, password)) {
            return null;
        }
        return authenticationFail(AUTH_TYPE_BASIC);
    }

    private Response authenticationFail(String authType) {
        return Response.status(Response.Status.UNAUTHORIZED).header("WWW-Authenticate", authType).build();
    }

    private boolean isAuthenticated(String username, String password) {
        if ("sagara".equals(username) && "1234".equals(password)) {
            return true;
        }
        return false;
    }
}
