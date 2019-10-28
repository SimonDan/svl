package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.communication.auth.IAuthResponse;
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

  @POST
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
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
      return _responseBadUserName(pE);
    }
  }

  @PUT
  public Response registerUser(@FormParam("firstName") String pFirstName, @FormParam("lastName") String pLastName,
                               @FormParam("email") String pEmail)
  {
    try
    {
      final User user = userService.registerNewUser(UserName.of(pFirstName, pLastName), pEmail);
      return _createAuthResponse(user);
    }
    catch (BadUserNameException pE)
    {
      return _responseBadUserName(pE);
    }
    catch (UserAlreadyExistsException pE)
    {
      return _responseUserAlreadyExists(pE);
    }
  }

  private Response _createAuthResponse(User pUser)
  {
    final String jwt = JWTUtil.createJwtForUser(pUser);

    final IAuthResponse response = new IAuthResponse()
    {
      @Override
      public String getToken()
      {
        return jwt;
      }

      @Override
      public String getNextPassword()
      {
        return pUser.getValue(User.PASSWORD);
      }
    };

    return Response.ok(response).build();
  }

  private Response _responseBadUserName(BadUserNameException pException)
  {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(pException.getMessage())
        .build();
  }

  private Response _responseUserAlreadyExists(UserAlreadyExistsException pException)
  {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(pException.getMessage())
        .build();
  }
}
