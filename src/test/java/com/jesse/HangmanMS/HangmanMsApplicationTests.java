package com.jesse.HangmanMS;

import com.jesse.HangmanMS.controller.HangmanController;
import com.jesse.HangmanMS.model.HangmanGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WebMvcTest(HangmanController.class)
class HangmanMsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		//ame.setWord("apple");
	}


	@Test
	@GetMapping("/")
	public void shouldReturnDefaultMessage(Model model) throws Exception {

		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("apple")));
	}

	@Test
	public void contextLoads() {
	}
}
