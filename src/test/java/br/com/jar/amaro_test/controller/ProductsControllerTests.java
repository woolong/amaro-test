package br.com.jar.amaro_test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerTests {

	@Autowired
	private MockMvc mockMvc;

	private static String url(String _url) {
		String newUrl = _url;
		newUrl = newUrl.startsWith("/")
										? newUrl
										: "/" + newUrl;
		newUrl = newUrl.endsWith("/")
									  ? newUrl
									  : newUrl + "/";
		return newUrl;
	}

	@Test
	public void all_statusOK() throws Exception {
		this.mockMvc //
				.perform(get(url(ProductsController.URL_FINDALL))) //
//				.andDo(print()) //
				.andExpect(status().isOk());
	}

	@Test
	public void allTagsVector_statusOK() throws Exception {
		this.mockMvc //
				.perform(get(url(ProductsController.URL_TAGSVECTOR))) //
//				.andDo(print()) //
				.andExpect(status().isOk());
	}

	@Test
	public void similarity_statusOK() throws Exception {
		final String allTagsVector = this.mockMvc //
				.perform(get(url(ProductsController.URL_TAGSVECTOR))) //
				.andReturn() //
				.getResponse() //
				.getContentAsString();

		final String _url = url(ProductsController.URL_SIMILARITY_BY_TAGS.replaceAll("\\{id\\}", "7533"));
		this.mockMvc //
				.perform(post(_url).contentType(MediaType.APPLICATION_JSON).content(allTagsVector)) //
//				.andDo(print()) //
				.andExpect(status().isOk()) //
				.andReturn();
	}

}
