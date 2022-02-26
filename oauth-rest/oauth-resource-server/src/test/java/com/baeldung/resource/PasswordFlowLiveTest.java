package com.baeldung.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

//Before running this live test make sure both authorization server and resource server are running   

public class PasswordFlowLiveTest {

	private final static String AUTH_SERVER = "http://localhost:8083/auth/realms/baeldung/protocol/openid-connect";
	private final static String RESOURCE_SERVER = "http://localhost:8081/resource-server";
	private final static String CLIENT_ID = "newClient";
	private final static String CLIENT_SECRET = "newClientSecret";
	private final static String USERNAME1 = "john@test.com";
	private final static String PASSWORD1 = "123";
	private final static String USERNAME2 = "testuser@baeldung.com";
	private final static String PASSWORD2 = "123";

	@Test
	public void givenUser_whenUseFooClient_fooResourceUserEmailFailed() {
		final String accessToken = obtainAccessToken(CLIENT_ID, USERNAME1, PASSWORD1);

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get(RESOURCE_SERVER + "/api/foos/1");
		assertEquals(401, fooResponse.getStatusCode());
	}

	@Test
	public void givenUser_whenUseFooClient_fooResourceUserEmailSuccess() {
		final String accessToken = obtainAccessToken(CLIENT_ID, USERNAME2, PASSWORD2);

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get(RESOURCE_SERVER + "/api/foos/1");
		assertEquals(200, fooResponse.getStatusCode());
		assertNotNull(fooResponse.jsonPath().get("name"));
	}

	@Test
	public void givenUser_whenUseFooClient_securedFoosResourceUserFailed() {
		final String accessToken = obtainAccessToken(CLIENT_ID, USERNAME1, PASSWORD1);

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get(RESOURCE_SERVER + "/api/secured/foos");
		assertEquals(403, fooResponse.getStatusCode());
	}

	@Test
	public void givenUser_whenUseFooClient_securedFoosResourceUserSuccess() {
		final String accessToken = obtainAccessToken(CLIENT_ID, USERNAME2, PASSWORD2);

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get(RESOURCE_SERVER + "/api/secured/foos");
		assertEquals(200, fooResponse.getStatusCode());
	}

	private String obtainAccessToken(String clientId, String username, String password) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("client_id", clientId);
		params.put("username", username);
		params.put("password", password);
		params.put("scope", "read write");
		final Response response = RestAssured.given().auth().preemptive().basic(clientId, CLIENT_SECRET).and()
				.with().params(params).when().post(AUTH_SERVER + "/token");
		return response.jsonPath().getString("access_token");
	}

}
