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

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class PageWriter implements MessageBodyWriter<Page> {

    public static final String CONTENT = "content";
    public static final String PAGEING = "pageing";
    public static final String PREVIOUS = "previous";
    public static final String CURRENT = "current";
    public static final String NEXT = "next";

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (Page.class.equals(type)) {
            return true;
        }
        return false;
    }

    @Override
    public long getSize(Page page, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return 0;
    }

    @Override
    public void writeTo(Page page, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {


        JsonFactory f = new JsonFactory();
        JsonGenerator g = f.createJsonGenerator(entityStream);
        g.writeStartObject();
        g.writeFieldName(CONTENT);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(g, page.getUsers());
        g.writeObjectFieldStart(PAGEING);
        if (page.getPrev() != null) {
            g.writeStringField(PREVIOUS, page.getPrev());
        }
        g.writeStringField(CURRENT, page.getCurrent());
        if (page.getNext() != null) {
            g.writeStringField(NEXT, page.getNext());
        }
        g.writeEndObject();
        g.flush();
        g.close();


    }
}
