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

package sample.jaxrs.exmap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sample.jaxrs.exmap.dao.UserDAOException;
import sample.jaxrs.exmap.dao.UserNotFoundException;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UserExceptionMapper implements ExceptionMapper<UserDAOException> {

    private final Log log = LogFactory.getLog(UserExceptionMapper.class);

    @Override
    public Response toResponse(UserDAOException exception) {
        log.error(exception);
        if (exception instanceof UserNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
