package com.github.simondan.svl.server.rest;

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
    Form form = new Form();
    form.param("firstName", "Alyssa")
        .param("lastName", "William")
        .param("password", "1021 Hweitt Street");

    final Client client = ClientBuilder.newClient();
    WebTarget target1 = client.target("http://localhost:8080/svl-server/dummy/test");
    WebTarget target2 = client.target("http://localhost:8080/svl-server/authentication");

    Future<String> response1 = target2
        .request(MediaType.APPLICATION_FORM_URLENCODED)
        .accept(MediaType.TEXT_PLAIN)
        .buildPost(Entity.form(form)).submit(String.class);

    final String token = response1.get();

    System.out.println(token);

    final Future<Response> response2 = target1
        .request(MediaType.TEXT_PLAIN_TYPE)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .buildGet().submit();

    final Response r = response2.get();

    System.out.println(r);
  }
}
