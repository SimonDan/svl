package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.server.auth.IUserService;
import com.github.simondan.svl.server.behindtheapi.UserService;
import com.github.simondan.svl.server.security.SecureRequestBoundary;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * @author Simon Danner, 29.09.2019
 */
@ApplicationPath("/")
public class RestApplication extends ResourceConfig
{
  public RestApplication()
  {
    register(AuthenticationExternalService.class);
    register(DummySecureService.class);
    register(SecureRequestBoundary.class);

    register(new AbstractBinder()
    {
      @Override
      protected void configure()
      {
        bind(UserService.class).to(IUserService.class);
      }
    });
  }
}
