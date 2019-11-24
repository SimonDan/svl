package com.github.simondan.svl.server.security;

import com.github.simondan.svl.communication.auth.EUserRole;

import javax.ws.rs.NameBinding;
import java.lang.annotation.*;

/**
 * Webservice method annotation to prevent unauthorized access.
 * Every HTTP request has to provide a JWT (Json web token) to verify their valid identities.
 *
 * @author Simon Danner, 20.09.2019
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NameBinding
public @interface SecureBoundary
{
  /**
   * A caller identify requires to own one of the given roles to cross the annotated boundary/method.
   *
   * @return roles that are allowed to use the annotated method (empty means no restriction)
   */
  EUserRole[] requiresOneOfTheseRoles() default {};
}
