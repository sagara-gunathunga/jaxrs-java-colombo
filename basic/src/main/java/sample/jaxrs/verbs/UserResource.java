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

package sample.jaxrs.verbs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sample.jaxrs.verbs.dao.UserDAOException;
import sample.jaxrs.verbs.dao.UserRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Path("/verbs")
public class UserResource {

    private final Log log = LogFactory.getLog(UserResource.class);

    private UserRepository users;

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") int id) {
        try {
            User user = users.find(id);
            return Response.status(Response.Status.ACCEPTED).entity(user).build();
        } catch (UserDAOException e) {
            log.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        try {
            return Response.status(Response.Status.ACCEPTED).entity(users.findAll()).build();
        } catch (UserDAOException e) {
            log.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users")
    public Response create(User user) {
        try {
            users.create(user);
            URL link = new URL("");
            return Response.created(link.toURI()).build();
        } catch (UserDAOException | MalformedURLException | URISyntaxException e) {
            log.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users")
    public Response update(User user) {
        try {
            users.update(user);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (UserDAOException e) {
            log.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/users/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            users.delete(id);
            return Response.status(Response.Status.GONE).build();
        } catch (UserDAOException e) {
            log.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    public UserRepository getUsers() {
        return users;
    }

    public void setUsers(UserRepository users) {
        this.users = users;
    }
}

