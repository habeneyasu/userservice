package com.ecommerce.userservice;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@ExtendWith( SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserserviceApplicationTests {

	@Autowired
	private MockMvc mocMvc;

	@Test
	public void testGetHelloWorld() throws Exception {
		mocMvc.perform(MockMvcRequestBuilders.get("/api/users/welcome"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().string("Welcome to the implementation of Spring Boot Security."));
	}


	@Test
	void contextLoads() {
	}

}
