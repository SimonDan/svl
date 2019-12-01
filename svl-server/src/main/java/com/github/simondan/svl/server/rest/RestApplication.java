package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.server.auth.IUserService;
import com.github.simondan.svl.server.behindtheapi.*;
import com.github.simondan.svl.server.security.*;
import de.adito.ojcms.persistence.OJPersistence;
import de.adito.ojcms.sqlbuilder.platform.EEmbeddedDatabasePlatform;
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
    OJPersistence.configure(pFactory -> pFactory.forEmbeddedDatabase(EEmbeddedDatabasePlatform.DERBY));

    register(AuthenticationExternalService.class);
    register(DummySecureService.class);
    register(SecureRequestBoundary.class);
    register(GsonSerializationProvider.class);

    register(new AbstractBinder()
    {
      @Override
      protected void configure()
      {
        bind(UserService.class).to(IUserService.class);
        bindAsContract(RequestSecurityContext.class);
        bindAsContract(MailSender.class);
        bindAsContract(Config.class);
      }
    });
  }
}
