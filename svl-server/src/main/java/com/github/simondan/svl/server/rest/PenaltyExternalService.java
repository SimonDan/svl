package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.communication.auth.EUserRole;
import com.github.simondan.svl.communication.penalty.AbstractPenalty;
import com.github.simondan.svl.communication.penalty.type.AbstractPenaltyType;
import com.github.simondan.svl.server.penalty.IPenaltyService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Simon Danner, 16.02.2020
 */
@Path("/penalty")
@ApplicationScoped
public class PenaltyExternalService
{
  @Inject
  private IPenaltyService penaltyService;

  @Path("/registerNewPenaltyType")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @SecureBoundaryWithRoles(requiresOneOfTheseRoles = {EUserRole.ADMINISTRATOR, EUserRole.SUPERVISOR})
  public Response registerNewPenaltyType(AbstractPenaltyType pNewPenaltyType)
  {
    return _noResultAction(() -> penaltyService.registerPenaltyType(pNewPenaltyType));
  }

  @Path("/deletePenaltyType")
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @SecureBoundaryWithRoles(requiresOneOfTheseRoles = {EUserRole.ADMINISTRATOR, EUserRole.SUPERVISOR})
  public Response deletePenaltyType(AbstractPenaltyType pPenaltyType)
  {
    return _noResultAction(() -> penaltyService.deletePenaltyType(pPenaltyType));
  }

  @Path("/newPenaltyForMe")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @SecureBoundaryWithRoles
  public Response newPenaltyForMe(AbstractPenalty<?> pPenaltyForMe)
  {
    return _noResultAction(() -> penaltyService.newPenaltyForMe(pPenaltyForMe));
  }

  @Path("/newPenalty")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @SecureBoundaryWithRoles(requiresOneOfTheseRoles = {EUserRole.ADMINISTRATOR, EUserRole.SUPERVISOR})
  public Response newPenalty(AbstractPenalty<?> pPenalty)
  {
    return _noResultAction(() -> penaltyService.newPenalty(pPenalty));
  }

  @Path("/deletePenalty")
  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @SecureBoundaryWithRoles(requiresOneOfTheseRoles = {EUserRole.ADMINISTRATOR, EUserRole.SUPERVISOR, EUserRole.CASH_MASTER})
  public Response deletePenalty(AbstractPenalty<?> pPenalty)
  {
    return _noResultAction(() -> penaltyService.deletePenalty(pPenalty));
  }

  @Path("/deleteAllPenaltiesOfUser")
  @DELETE
  @Consumes(MediaType.TEXT_PLAIN)
  @SecureBoundaryWithRoles(requiresOneOfTheseRoles = {EUserRole.ADMINISTRATOR, EUserRole.CASH_MASTER})
  public Response deleteAllPenaltiesOfUser(String pUserMail)
  {
    return _noResultAction(() -> penaltyService.deleteAllPenaltiesOfUser(pUserMail));
  }

  @Path("/getAllPenaltyTypes")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @SecureBoundaryWithRoles
  public Response getAllPenaltyTypes()
  {
    return _resultAction(() -> penaltyService.getAllPenaltyTypes());
  }

  @Path("/getPenaltiesForUser")
  @POST
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  @SecureBoundaryWithRoles
  public Response getPenaltiesForUser(String pUserMail)
  {
    return _resultAction(() -> penaltyService.getPenaltiesForUser(pUserMail));
  }

  private Response _noResultAction(_ThrowingNoResultAction pCall)
  {
    try
    {
      pCall.perform();
      return Response.ok().build();
    }
    catch (Exception pException)
    {
      return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), pException.getMessage()).build();
    }
  }

  private <RESULT> Response _resultAction(_ThrowingResultSupplier<RESULT> pCall)
  {
    try
    {
      final RESULT result = pCall.retrieveResult();
      return Response.ok()
          .entity(Entity.entity(result, MediaType.APPLICATION_JSON))
          .build();
    }
    catch (Exception pException)
    {
      return Response.status(Response.Status.BAD_REQUEST.getStatusCode(), pException.getMessage()).build();
    }
  }

  @FunctionalInterface
  private interface _ThrowingNoResultAction
  {
    void perform() throws Exception;
  }

  @FunctionalInterface
  private interface _ThrowingResultSupplier<RESULT>
  {
    RESULT retrieveResult() throws Exception;
  }
}
