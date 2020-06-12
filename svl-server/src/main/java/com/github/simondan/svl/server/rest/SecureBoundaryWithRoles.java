package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.communication.auth.EUserRole;
import jakarta.ws.rs.NameBinding;

import java.lang.annotation.*;

/**
 * Webservice method annotation to prevent unauthorized access.
 * Every HTTPS request has to provide a JWT (Json web token) to verify their valid identities.
 *
 * @author Simon Danner, 20.09.2019
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NameBinding
public @interface SecureBoundaryWithRoles
{
  /**
   * A caller is required to own one of the given roles to cross the annotated boundary/method.
   *
   * @return roles that are allowed to use the annotated method (empty means no restriction)
   */
  EUserRole[] requiresOneOfTheseRoles() default {};
}
