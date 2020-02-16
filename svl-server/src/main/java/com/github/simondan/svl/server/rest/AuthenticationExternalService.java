package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.communication.auth.*;
import com.github.simondan.svl.server.auth.*;
import com.github.simondan.svl.server.auth.exceptions.*;
import com.github.simondan.svl.server.security.JWTUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * @author Simon Danner, 22.09.2019
 */
@Path("/authentication")
@ApplicationScoped
public class AuthenticationExternalService
{
  @Inject
  private IUserService userService;

  @Path("/auth")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response authUser(IAuthenticationRequest pAuthenticationRequest)
  {
    try
    {
      final User user = userService.authenticateUser(pAuthenticationRequest);
      return _createAuthResponse(user);
    }
    catch (BadCredentialsException pE)
    {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
  }

  @Path("/register")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response registerUser(IRegistrationRequest pRegistrationRequest)
  {
    try
    {
      final User user = userService.registerNewUser(pRegistrationRequest);
      return _createAuthResponse(user);
    }
    catch (UserAlreadyExistsException | BadMailAddressException pE)
    {
      return _exceptionResponse(pE);
    }
  }

  @Path("/requestCode")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response requestRestoreCode(IRegistrationRequest pRegistrationData)
  {
    try
    {
      userService.requestPasswordRestoreCodeByMail(pRegistrationData);
      return Response.ok().build();
    }
    catch (MailNotMatchingException pE)
    {
      return _exceptionResponse(pE);
    }
  }

  @Path("/restore")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response restoreUser(IRestoreAuthRequest pRestoreAuthRequest)
  {
    try
    {
      final User user = userService.restorePassword(pRestoreAuthRequest);
      return _createAuthResponse(user);
    }
    catch (BadRestoreCodeException | UserNotFoundException pE)
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
