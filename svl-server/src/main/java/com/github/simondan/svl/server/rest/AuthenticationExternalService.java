package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.server.auth.*;
import com.github.simondan.svl.server.security.JWTUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * @author Simon Danner, 22.09.2019
 */
@Path("/authentication")
public final class AuthenticationExternalService
{
  @Inject
  private IUserService userService;

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response authUser(@FormParam("firstName") String pFirstName, @FormParam("firstName") String pLastName,
                           @FormParam("password") String pPassword)
  {
    try
    {
      final User user = userService.authenticateUser(pFirstName, pLastName, pPassword);
      final String jwt = JWTUtil.createJwtForUser(user);

      return Response.ok(jwt).build();
    }
    catch (BadCredentialsException pE)
    {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
  }

  @PUT
  public Response registerUser()
  {
    return Response.ok().build();
  }
}
