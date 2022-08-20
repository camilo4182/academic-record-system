package training.path.academicrecordsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import training.path.academicrecordsystem.controllers.dtos.CareerDTO;
import training.path.academicrecordsystem.controllers.mappers.CareerMapper;
import training.path.academicrecordsystem.model.Career;
import training.path.academicrecordsystem.repositories.implementations.CareerRepository;
import training.path.academicrecordsystem.services.implementations.CareerService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CareerControllerTests {

    @Autowired
    WebApplicationContext context;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CareerService careerService;

    @MockBean
    CareerRepository careerRepository;

    @MockBean
    CareerMapper careerMapper;

    @Test
    void givenCareerDTO_whenSave_thenItReturnsOk() throws Exception {
        CareerDTO careerDTO = CareerDTO.builder().name("System Engineering").build();

        ResultActions response = mockMvc.perform(post("/careers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(careerDTO)));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidCareerDTO_whenSave_thenItReturnsOk() throws Exception {
        CareerDTO careerDTONoName = CareerDTO.builder().name("").build();

        ResultActions response = mockMvc.perform(post("/careers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(careerDTONoName)));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenCareerDTO_whenUpdate_thenItReturnsOk() throws Exception {
        String careerId = UUID.randomUUID().toString();

        Career savedCareer = Career.builder()
                .id(careerId)
                .name("Medicine")
                .build();
        careerService.save(savedCareer);

        CareerDTO careerDTO = CareerDTO.builder()
                .name("Economics")
                .build();

        ResultActions response = mockMvc.perform(put("/careers/{careerId}", savedCareer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(careerDTO)));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidCareerDTO_whenUpdate_thenItThrowsException() throws Exception {
        String careerId = UUID.randomUUID().toString();

        Career savedCareer = Career.builder()
                .id(careerId)
                .name("Medicine")
                .build();
        careerService.save(savedCareer);

        CareerDTO careerDTONoName = CareerDTO.builder()
                .name("")
                .build();

        ResultActions response = mockMvc.perform(put("/careers/{careerId}", savedCareer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(careerDTONoName)));

        response.andDo(print())
                .andExpect(status().isBadRequest());
    }


    void givenNonExistingCareerDTO_whenUpdate_thenItThrowsException() throws Exception {
        String careerId = UUID.randomUUID().toString();

        Career savedCareer = Career.builder()
                .id(careerId)
                .name("Medicine")
                .build();
        careerService.save(savedCareer);

        when(careerRepository.exists(anyString())).thenReturn(false);

        CareerDTO careerDTONoExisting = CareerDTO.builder()
                .name("Null")
                .build();

        ResultActions response = mockMvc.perform(put("/careers/{careerId}", "f557f03e-4bb6-47ef-bfaf-4a7f2dfcf497")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(careerDTONoExisting)));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidId_whenDeleteById_thenItReturnsOk() throws Exception {
        String careerId = UUID.randomUUID().toString();

        ResultActions response = mockMvc.perform(delete("/careers/{careerId}", careerId));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenNonExistingCareer_whenDeleteById_thenItThrowsException() throws Exception {
        String careerId = UUID.randomUUID().toString();

        when(careerRepository.exists(anyString())).thenReturn(false);

        ResultActions response = mockMvc.perform(delete("/careers/{careerId}", "123c"));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }
}
