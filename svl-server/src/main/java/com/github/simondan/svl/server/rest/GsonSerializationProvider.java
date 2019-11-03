package com.github.simondan.svl.server.rest;

import com.google.gson.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Simon Danner, 03.11.2019
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GsonSerializationProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object>
{
  private static final Gson GSON = new GsonBuilder().create();

  @Override
  public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    return true;
  }

  @Override
  public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                         MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException
  {
    try (InputStreamReader reader = new InputStreamReader(entityStream))
    {
      return GSON.fromJson(reader, type);
    }
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
  {
    return true;
  }

  @Override
  public void writeTo(Object pObject, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws WebApplicationException
  {
    try (PrintWriter writer = new PrintWriter(entityStream))
    {
      writer.write(GSON.toJson(pObject));
    }
  }
}
