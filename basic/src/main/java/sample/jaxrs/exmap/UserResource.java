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
import sample.jaxrs.exmap.dao.UserRepository;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/exmap")
public class UserResource {

    private final Log log = LogFactory.getLog(UserResource.class);

    private UserRepository users;

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("id") int id) throws UserDAOException {
        return users.find(id);
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> findAll() throws UserDAOException {
        return users.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users")
    public void create(User user, @Context final HttpServletResponse response) throws UserDAOException {
        users.create(user);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.addHeader("Location", "");
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users")
    public void update(User user) throws UserDAOException {
        users.update(user);
    }

    @DELETE
    @Path("/users/{id}")
    public void delete(@PathParam("id") int id) throws UserDAOException {
        users.delete(id);
    }

    public UserRepository getUsers() {
        return users;
    }

    public void setUsers(UserRepository users) {
        this.users = users;
    }
}