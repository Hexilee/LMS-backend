package com.zjuqsc.library.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.auth.AuthServiceImpl;
import com.zjuqsc.library.token.dto.LoginDto;
import com.zjuqsc.library.user.UserFactory;
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
public class TokenControllerTests {

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
    public void loginWithWrongBody() throws Exception {
        LoginDto newReqObject = new LoginDto();
        newReqObject.setUsernameOrEmail("");
        newReqObject.setPassword("");
        final String reqBody = objectWriter.writeValueAsString(newReqObject);
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(reqBody)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        ErrorInfoDto result = jacksonObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorInfoDto.class);
        Assert.assertEquals("Request data invalid", result.getMessage());
        Assert.assertEquals(2, result.getErrors().size());
    }

    @Test
    public void loginWithRightBody() throws Exception {
        CreateUserDto userDto = new CreateUserDto();
        final String email = "hexileee@gmail.com";
        final String username = "hex";
        final String password = "123456";
        userDto.setEmail(email);
        userDto.setUsername(username);
        userDto.setName("Li Chenxi");
        userDto.setPassword(password);
        authService.register(userFactory.create(userDto));

        // with username
        LoginDto newReqObject = new LoginDto();
        newReqObject.setUsernameOrEmail(username);
        newReqObject.setPassword(password);
        final String reqBodyWithUsername = objectWriter.writeValueAsString(newReqObject);
        final MvcResult mvcResultWithUsername = mockMvc.perform(MockMvcRequestBuilders
                .post("/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(reqBodyWithUsername)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        HashMap<?, ?> resultWithUsername = jacksonObjectMapper.readValue(mvcResultWithUsername.getResponse().getContentAsString(), HashMap.class);
        Assert.assertNotNull(resultWithUsername.get("token"));
        Assert.assertNotNull(resultWithUsername.get("expiresIn"));

        // with email
        newReqObject.setUsernameOrEmail(email);
        final String reqBodyWithEmail = objectWriter.writeValueAsString(newReqObject);
        final MvcResult mvcResultWithEmail = mockMvc.perform(MockMvcRequestBuilders
                .post("/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(reqBodyWithEmail)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        HashMap<?, ?> resultWithEmail = jacksonObjectMapper.readValue(mvcResultWithEmail.getResponse().getContentAsString(), HashMap.class);
        Assert.assertNotNull(resultWithEmail.get("token"));
        Assert.assertNotNull(resultWithEmail.get("expiresIn"));
    }

    @Test
    public void loginWithNotExistBody() throws Exception {
        final String username = "hex";
        final String password = "123456";
        LoginDto newReqObject = new LoginDto();
        newReqObject.setUsernameOrEmail(username);
        newReqObject.setPassword(password);
        final String reqBody = objectWriter.writeValueAsString(newReqObject);
        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/token")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(reqBody)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn();
        ErrorInfoDto<?> result = jacksonObjectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorInfoDto.class);
        Assert.assertEquals("Data conflict", result.getMessage());
        Assert.assertEquals(1, result.getErrors().size());
    }
}
