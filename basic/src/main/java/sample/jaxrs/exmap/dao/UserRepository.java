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

package sample.jaxrs.exmap.dao;

import sample.jaxrs.exmap.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    public UserRepository() {
    }

    public void create(User user) throws UserDAOException {
        users.put(user.getId(), user);
    }

    public void update(User user) throws UserDAOException {
        find(user.getId());
        users.put(user.getId(), user);
    }

    public void delete(int id) throws UserDAOException {
        users.remove(id);
    }

    public User find(int id) throws UserDAOException {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public Collection<User> findAll() throws UserDAOException {
        return users.values();
    }
}
