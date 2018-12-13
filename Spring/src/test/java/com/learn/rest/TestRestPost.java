package com.learn.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO Description
 * @author Kot
 * @ created 19.06.2018
 * @ $Author$
 * @ $Revision$
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestContext.class, WebApplicationContext.class})
//@WebAppConfiguration
public class TestRestPost
{

	private MockMvc mockMvc;

	@InjectMocks
	private RestPost todoServiceMock;

	//Add WebApplicationContext field here

	//The setUp() method is omitted.

	@Before
	public void setup() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup().build();
	}

	@Test
	public void findById_TodoEntryNotFound_ShouldRender404View() throws Exception
	{
		try
		{

			//when(todoServiceMock.findById(1L)).thenThrow(new TodoNotFoundException(""));

			mockMvc.perform(post("/rest/post/service/dss/v1/DSSResultOfConfirmOperation").
				contentType(MediaType.APPLICATION_JSON).
				content("{\"par1\":\"val1\"}"))
			.andExpect(status().isOk()).andReturn();

			int fff = 0;
		}
		catch (Throwable t)
		{
			t = null;
		}
	}
}
