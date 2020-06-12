package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.communication.auth.*;
import com.github.simondan.svl.communication.penalty.*;
import com.github.simondan.svl.communication.penalty.type.*;
import de.adito.ojcms.persistence.AdditionalPersistAsBaseType;
import de.adito.ojcms.rest.application.OJSecuredRestApplication;
import de.adito.ojcms.rest.auth.api.RegistrationRequest;
import jakarta.ws.rs.ApplicationPath;

import java.util.stream.Stream;

import static de.adito.ojcms.rest.auth.api.RegistrationRequest.*;

/**
 * @author Simon Danner, 29.09.2019
 */
@ApplicationPath("/")
@AdditionalPersistAsBaseType(baseType = AbstractPenalty.class, containerId = "PENALTIES", //
    forSubTypes = {SimplePenalty.class, PenaltyByCount.class, PenaltyByDuration.class})
@AdditionalPersistAsBaseType(baseType = AbstractPenaltyType.class, containerId = "PENALTY_TYPES",
    forSubTypes = {SimplePenaltyType.class, PenaltyTypeByCount.class, PenaltyTypeByDuration.class})
public class SVLRestApplication extends OJSecuredRestApplication<SecureBoundaryWithRoles, SVLUser, RegistrationRequest, //
    SVLAuthenticationResponse>
{
  public SVLRestApplication()
  {
    super(SecureBoundaryWithRoles.class, SVLUser.class, RegistrationRequest.class, PenaltyExternalService.class);
  }

  @Override
  public boolean isUserAllowedToCrossBoundary(SecureBoundaryWithRoles pSecureBoundaryWithRoles, SVLUser pUser)
  {
    final EUserRole[] requiredRoles = pSecureBoundaryWithRoles.requiresOneOfTheseRoles();
    return requiredRoles.length == 0 || Stream.of(requiredRoles).anyMatch(pRole -> pRole == pUser.getValue(SVLUser.ROLE));
  }

  @Override
  public SVLUser createNewUser(RegistrationRequest pRegistrationRequest)
  {
    return new SVLUser(pRegistrationRequest.getValue(USER_MAIL), pRegistrationRequest.getValue(DISPLAY_NAME), EUserRole.DEFAULT);
  }

  @Override
  public SVLAuthenticationResponse createAuthenticationResponse(String pToken, String pNextPassword, SVLUser pUser)
  {
    return new SVLAuthenticationResponse(pToken, pNextPassword, pUser.getValue(SVLUser.ROLE));
  }
}
