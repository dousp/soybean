package com.dsp.soy.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SoyAuthApplicationTests {

	String noScopesToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiaWF0IjoxNTE2MjM5MDIyfQ.eB2c9xtg5wcCZxZ-o-sH4Mx1JGkqAZwH4_WS0UcDbj_nen0NPBj6CqOEPhr_LZDagb4mM6HoAPJywWWG8b_Ylnn5r2gWDzib2mb0kxIuAjnvVBrpzusw4ItTVvP_srv2DrwcisKYiKqU5X_3ka7MSVvKtswdLY3RXeCJ_S2W9go";
	String messageReadToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiaWF0IjoxNTE2MjM5MDIyLCJzY29wZSI6Im1lc3NhZ2U6cmVhZCJ9.bsRCpUEaiWnzX4OqNxTBqwUD4vxxtPp-CHKTw7XcrglrvZ2lvYXaiZZbCp-hcPhuzMEzEAFuH6s4GZZOWVIX-wT47GdTz9cfA-Z4QPjS2RxePKphFXgBI3jHEpQo94Qya2fJdV4LvgBmA1uM_RTnYY1UbmeYuHKnXrZoGyV8QQQ";

	@Autowired
	MockMvc mvc;

	// @Test
	public void performWhenValidBearerTokenThenAllows()
			throws Exception {

		this.mvc.perform(get("/").with(bearerToken(this.noScopesToken)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, subject!")));
	}

	// @Test
	public void performWhenValidBearerTokenThenScopedRequestsAlsoWork()
			throws Exception {

		this.mvc.perform(get("/message").with(bearerToken(this.messageReadToken)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("secret message")));
	}

	// @Test
	public void performWhenInsufficientlyScopedBearerTokenThenDeniesScopedMethodAccess()
			throws Exception {

		this.mvc.perform(get("/message").with(bearerToken(this.noScopesToken)))
				.andExpect(status().isForbidden())
				.andExpect(header().string(HttpHeaders.WWW_AUTHENTICATE,
						containsString("Bearer error=\"insufficient_scope\"")));
	}

	private static class BearerTokenRequestPostProcessor implements RequestPostProcessor {
		private String token;

		BearerTokenRequestPostProcessor(String token) {
			this.token = token;
		}

		@Override
		public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
			request.addHeader("Authorization", "Bearer " + this.token);
			return request;
		}
	}

	private static BearerTokenRequestPostProcessor bearerToken(String token) {
		return new BearerTokenRequestPostProcessor(token);
	}


	/*
	 * 授权服务
	 * */

	// @Test
	public void requestTokenWhenUsingPasswordGrantTypeThenOk()
			throws Exception {

		this.mvc.perform(post("/oauth/token")
				.param("grant_type", "password")
				.param("username", "subject")
				.param("password", "password")
				.header("Authorization", "Basic cmVhZGVyOnNlY3JldA=="))
				.andExpect(status().isOk());
	}

	// @Test
	public void requestJwkSetWhenUsingDefaultsThenOk()
			throws Exception {

		this.mvc.perform(get("/.well-known/jwks.json"))
				.andExpect(status().isOk());
	}

}
