package com.zjuqsc.library.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.auth.AuthServiceImpl;
import com.zjuqsc.library.user.dto.CreateUserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;


@RunWith(SpringRunner.class)
@WebAppConfiguration
@Transactional
@SpringBootTest
public class UserControllerTests {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private UserFactory userFactory;

    private MockMvc mockMvc;

    private ObjectWriter objectWriter;

    @Before
    public void setUpMock() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Before
    public void setUpWriter() {
        objectWriter = jacksonObjectMapper.writerWithDefaultPrettyPrinter();
    }

    @Test
    public void registerWithWrongBody() throws Exception {
        CreateUserDto newReqObject = new CreateUserDto();
        newReqObject.setEmail("hex");
        newReqObject.setUsername("hexileee@gmail.com");
        newReqObject.setName("");
        newReqObject.setPassword("");
        final String reqBody = objectWriter.writeValueAsString(newReqObject);
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(reqBody)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        ErrorInfoDto<String> result = jacksonObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorInfoDto.class);
        Assert.assertEquals("Request data invalid", result.getMessage());
        Assert.assertEquals(4, result.getErrors().size());
    }

    @Test
    public void registerWithRightBody() throws Exception {
        CreateUserDto newReqObject = new CreateUserDto();
        newReqObject.setEmail("hexileee@gmail.com");
        newReqObject.setUsername("hex");
        newReqObject.setName("Li Chenxi");
        newReqObject.setPassword("123456");
        final String reqBody = objectWriter.writeValueAsString(newReqObject);
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(reqBody)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        HashMap<String, String> result = jacksonObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), HashMap.class);
        Assert.assertNotNull(result.get("token"));
        Assert.assertNotNull(result.get("expiresIn"));
    }

    @Test
    public void registerWithDuplicatedBody() throws Exception {
        CreateUserDto newReqObject = new CreateUserDto();
        newReqObject.setEmail("hexileee@gmail.com");
        newReqObject.setUsername("hex");
        newReqObject.setName("Li Chenxi");
        newReqObject.setPassword("123456");
        authService.register(userFactory.create(newReqObject));
        final String reqBody = objectWriter.writeValueAsString(newReqObject);
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(reqBody)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();
        ErrorInfoDto<String> result = jacksonObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorInfoDto.class);
        Assert.assertEquals("Data conflict", result.getMessage());
        Assert.assertEquals(1, result.getErrors().size());
    }
}
