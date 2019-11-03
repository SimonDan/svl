package com.github.simondan.svl.server.rest;

import com.github.simondan.svl.communication.auth.AuthenticationResponse;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.util.concurrent.*;

/**
 * @author Simon Danner, 30.09.2019
 */
public class ClientTest
{
  public static void main(String[] args) throws ExecutionException, InterruptedException
  {
    Form form1 = new Form();
    form1.param("firstName", "Simon")
        .param("lastName", "Danner")
        .param("email", "simon.danner@gmx.de");

    Form form2 = new Form();
    form2.param("firstName", "Simon")
        .param("lastName", "Danner")
        .param("password", "test");

    final Client client = ClientBuilder.newClient().register(GsonSerializationProvider.class);

    WebTarget target1 = client.target("http://localhost:8080/svl-server/dummy/test");
    WebTarget target2 = client.target("http://localhost:8080/svl-server/authentication");

    Future<AuthenticationResponse> response1 = target2
        .request(MediaType.APPLICATION_FORM_URLENCODED)
        .accept(MediaType.APPLICATION_JSON)
        .buildPut(Entity.form(form1))
        .submit(AuthenticationResponse.class);

    Future<AuthenticationResponse> response2 = target2
        .request(MediaType.APPLICATION_FORM_URLENCODED)
        .accept(MediaType.APPLICATION_JSON)
        .buildPost(Entity.form(form2))
        .submit(AuthenticationResponse.class);

    AuthenticationResponse response;
    try
    {
      response = response1.get();
    }
    catch (Exception pE)
    {
      response = response2.get();
    }

    System.out.println(response.getToken());
    System.out.println(response.getNextPassword());

    final Future<Response> response3 = target1
        .request(MediaType.TEXT_PLAIN_TYPE)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken())
        .buildGet().submit();

    final Response r = response3.get();

    System.out.println(r);
  }
}
