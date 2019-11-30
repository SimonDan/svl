package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.communication.auth.*;
import com.github.simondan.svl.server.auth.*;
import com.github.simondan.svl.server.auth.exceptions.*;
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

  @Path("/auth")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response authUser(@FormParam("firstName") String pFirstName, @FormParam("lastName") String pLastName,
                           @FormParam("password") String pPassword)
  {
    try
    {
      final User user = userService.authenticateUser(UserName.of(pFirstName, pLastName), pPassword);
      return _createAuthResponse(user);
    }
    catch (BadCredentialsException pE)
    {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    catch (BadUserNameException pE)
    {
      return _exceptionResponse(pE);
    }
  }

  @Path("/register")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response registerUser(@FormParam("firstName") String pFirstName, @FormParam("lastName") String pLastName,
                               @FormParam("email") String pEmail)
  {
    try
    {
      final User user = userService.registerNewUser(UserName.of(pFirstName, pLastName), pEmail);
      return _createAuthResponse(user);
    }
    catch (BadUserNameException | UserAlreadyExistsException pE)
    {
      return _exceptionResponse(pE);
    }
  }

  @Path("/requestCode")
  @PUT
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public Response requestRestoreCode(@FormParam("firstName") String pFirstName, @FormParam("lastName") String pLastName,
                                     @FormParam("email") String pEmail)
  {
    try
    {
      userService.requestPasswordRestoreCodeByMail(UserName.of(pFirstName, pLastName), pEmail);
      return Response.ok().build();
    }
    catch (BadUserNameException | MailNotMatchingException pE)
    {
      return _exceptionResponse(pE);
    }
  }

  @Path("/restore")
  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response restoreUser(@FormParam("firstName") String pFirstName, @FormParam("lastName") String pLastName,
                              @FormParam("restoreCode") String pRestoreCode)
  {
    try
    {
      final User user = userService.restorePassword(UserName.of(pFirstName, pLastName), pRestoreCode);
      return _createAuthResponse(user);
    }
    catch (BadUserNameException | BadRestoreCodeException | UserNotFoundException pE)
    {
      return _exceptionResponse(pE);
    }
  }

  private Response _createAuthResponse(User pUser)
  {
    final String jwt = JWTUtil.createJwtForUser(pUser);
    final String nextPassword = pUser.getValue(User.PASSWORD);
    final EUserRole userRole = pUser.getValue(User.ROLE);

    return Response.ok(new AuthenticationResponse(jwt, nextPassword, userRole)).build();
  }

  private Response _exceptionResponse(Exception pException)
  {
    return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), pException.getMessage()).build();
  }
}
