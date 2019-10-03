package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.server.security.SecureBoundary;

import javax.ws.rs.*;

/**
 * @author Simon Danner, 27.09.2019
 */
@Path("/dummy")
public class DummySecureService
{
  @GET
  @Path("test")
  @SecureBoundary
  public String test()
  {
    return "test2";
  }
}
