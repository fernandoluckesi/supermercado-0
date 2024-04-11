package br.com.meli.supermercado.controller;

import java.time.LocalDate;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.meli.supermercado.dtos.UserDTO;
import br.com.meli.supermercado.exceptions.ExceptionHandlerController;
import br.com.meli.supermercado.services.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

  private MockMvc mockMvc;

  private MessageSource messageSource;

  @MockBean
  UserService userService;

  private ObjectMapper objectMapper;

  @InjectMocks
  UserController userController;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    userController = new UserController();
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .setControllerAdvice(new ExceptionHandlerController(messageSource)).build();
  }

  @Test
  @DisplayName("should_be_able_to_create_a_new_user")
  public void should_be_able_to_create_a_new_user() throws Exception {

    // given(userService.create(ArgumentMatchers.any())).willAnswer((invocation ->
    // invocation.getArgument(0)));

    UserDTO userDTO = new UserDTO("Fernando Luckesi", "1234567891011", "fernando@gmail.com",
        "42275937862", LocalDate.of(2005, 12, 13));

    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    ResultActions response = mockMvc.perform(post("/user")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(userDTO)));

    response.andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.completeName", CoreMatchers.is(userDTO.getCompleteName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userDTO.getEmail())));
  }

}
