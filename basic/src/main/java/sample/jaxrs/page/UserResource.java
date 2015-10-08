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

package sample.jaxrs.page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sample.jaxrs.page.dao.UserDAOException;
import sample.jaxrs.page.dao.UserRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Path("/page")
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
    public Response findAll(@QueryParam("offset") @DefaultValue("0") int offset, @QueryParam("limit") @DefaultValue
            ("10") int limit, @Context UriInfo uriInfo) {
        try {
            Page page = new Page(users.findAll(offset, limit));
            addPagination(page, uriInfo, offset, limit);
            return Response.status(Response.Status.ACCEPTED).entity(page).build();
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
    @Path("page/users/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            users.delete(id);
            return Response.status(Response.Status.GONE).build();
        } catch (UserDAOException e) {
            log.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    private void addPagination(Page page, UriInfo uriInfo, int offset, int limit) {

        int nextOffset = offset + limit;
        int prevOffset = offset - limit;

        URI currentLink = UriBuilder.fromUri(uriInfo.getBaseUri()).path("/page/users").queryParam("offset", offset)
                .queryParam("limit", limit).build();
        page.setCurrent(getStringURL(currentLink));

        if (page.getSize() > 0) {
            URI nextLink = UriBuilder.fromUri(uriInfo.getBaseUri()).path("/page/users").queryParam("offset", nextOffset)
                    .queryParam("limit", limit).build();
            page.setNext(getStringURL(nextLink));
        }
        if (prevOffset >= 0) {
            URI prevLink = UriBuilder.fromUri(uriInfo.getBaseUri()).path("/page/users").queryParam("offset", prevOffset)
                    .queryParam("limit", limit).build();
            page.setPrev(getStringURL(prevLink));
        }

    }

    private String getStringURL(URI uri) {
        try {
            return uri.toURL().toString();
        } catch (MalformedURLException e) {
            log.error(e);
        }
        return null;
    }

    public UserRepository getUsers() {
        return users;
    }

    public void setUsers(UserRepository users) {
        this.users = users;
    }
}

