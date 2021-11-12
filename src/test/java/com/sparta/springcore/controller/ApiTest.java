package com.sparta.springcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import com.sparta.springcore.dto.SignupRequestDto;
import com.sparta.springcore.dto.UserDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApiTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    static public String token = "";

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(MockMvcResultHandlers.print())
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    @Order(1)
    public void 회원가입() throws Exception {
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUsername("abc");
        dto.setPassword("1234");
        dto.setEmail("abc@naver.com");
        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(dto);

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user/signup",
                        requestFields(
                                fieldWithPath("username").description("유저명"),
                                fieldWithPath("password").description("패스워드"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("admin").description("관리자 유무"),
                                fieldWithPath("adminToken").description("관리자 토큰")
                        )
                ));
    }

    @Test
    @Order(2)
    public void 로그인() throws Exception {
        UserDto dto = new UserDto();
        dto.setUsername("abc");
        dto.setPassword("1234");
        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(dto);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user/login",
                        requestFields(
                                fieldWithPath("username").description("유저명"),
                                fieldWithPath("password").description("패스워드")
                        )
                ));
    }

}
