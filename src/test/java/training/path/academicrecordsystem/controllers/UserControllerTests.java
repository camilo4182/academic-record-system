package training.path.academicrecordsystem.controllers;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//@WebMvcTest(UserController.class)
public class UserControllerTests {

    /*
    @Autowired
    WebApplicationContext context;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IUserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .build();
    }
    */

    @Test
    void givenValidId_whenFindUserById_thenReturnStatusCodeOkAndUser() throws Exception {
        /*
        long userId = 1L;
        User user = new User();
        user.setName("Juan");
        given(userService.findById(anyLong())).willReturn(user);

        ResultActions response = mockMvc.perform(get("/users/{id}", userId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(user.getName())));
        */
    }

}
