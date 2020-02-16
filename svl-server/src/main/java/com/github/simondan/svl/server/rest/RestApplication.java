package com.github.simondan.svl.server.rest;

import org.glassfish.jersey.server.ResourceConfig;

import javax.enterprise.inject.Vetoed;
import javax.ws.rs.ApplicationPath;

/**
 * @author Simon Danner, 29.09.2019
 */
@ApplicationPath("/")
@Vetoed
public class RestApplication extends ResourceConfig
{
  public RestApplication()
  {
    register(GsonSerializationProvider.class);
    register(AuthenticationExternalService.class);
  }
}
