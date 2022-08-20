package training.path.academicrecordsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import training.path.academicrecordsystem.controllers.dtos.RequestBodyCourseClassDTO;
import training.path.academicrecordsystem.controllers.implementations.CourseClassController;
import training.path.academicrecordsystem.controllers.mappers.CourseClassMapper;
import training.path.academicrecordsystem.exceptions.ResourceNotFoundException;
import training.path.academicrecordsystem.model.Course;
import training.path.academicrecordsystem.model.CourseClass;
import training.path.academicrecordsystem.model.Professor;
import training.path.academicrecordsystem.services.interfaces.ICourseClassService;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseClassController.class)
public class CourseClassControllerTests {

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ICourseClassService service;

    @MockBean
    CourseClassMapper courseClassMapper;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void givenValidId_whenFindById_thenItReturnsOkAndCourseClass() throws Exception {
        String classId = UUID.randomUUID().toString();

        Course course = Course.builder()
                .id(UUID.randomUUID().toString())
                .name("Algebra")
                .credits(3)
                .build();

        Professor professor = Professor.builder()
                .id(UUID.randomUUID().toString())
                .name("Carlos")
                .email("carlos@email.com")
                .salary(30000)
                .build();

        CourseClass courseClass = CourseClass.builder()
                .id(classId)
                .capacity(30)
                .course(course)
                .professor(professor)
                .build();

        given(service.findById(anyString())).willReturn(courseClass);

        ResultActions response = mockMvc.perform(get("/classes/{classId}", classId));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(classId)));
    }


    @Test
    void givenNonExistingClass_whenFindById_thenItThrowsException() throws Exception {
        String id = "1234";

        given(service.findById(anyString())).willThrow(new ResourceNotFoundException("Class with id " + id + " was not found"));

        ResultActions response = mockMvc.perform(get("/classes/{classId}", id));

        response.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is("Class with id " + id + " was not found")));
    }

    @Test
    void givenValidCourseClassDTO_whenSave_thenItReturnsOk() throws Exception {
        String classId = UUID.randomUUID().toString();
        String professorId = UUID.randomUUID().toString();
        String courseId = UUID.randomUUID().toString();

        RequestBodyCourseClassDTO courseClassDTO = RequestBodyCourseClassDTO.builder()
                .courseId(courseId)
                .professorId(professorId)
                .capacity(30)
                .available(true)
                .build();

        Course course = Course.builder()
                .id(courseId)
                .name("Algebra")
                .credits(3)
                .build();

        Professor professor = Professor.builder()
                .id(professorId)
                .name("Carlos")
                .email("carlos@email.com")
                .salary(30000)
                .build();

        CourseClass courseClass = CourseClass.builder()
                .id(classId)
                .capacity(courseClassDTO.getCapacity())
                .course(course)
                .professor(professor)
                .enrolledStudents(0)
                .available(courseClassDTO.isAvailable())
                .build();

        try (MockedStatic<CourseClassMapper> classMapper = Mockito.mockStatic(CourseClassMapper.class)) {
            classMapper.when(() -> CourseClassMapper.createEntity(courseClassDTO))
                    .thenReturn(courseClass);

            doNothing().when(service).save(any());

            ResultActions response = mockMvc.perform(post("/classes/", courseClassDTO));
            response.andExpect(status().isOk())
                    .andDo(print());
        }
    }
}
