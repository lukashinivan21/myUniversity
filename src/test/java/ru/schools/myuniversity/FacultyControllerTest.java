package ru.schools.myuniversity;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.schools.myuniversity.controllers.FacultyController;
import ru.schools.myuniversity.model.Faculty;
import ru.schools.myuniversity.repositories.AvatarRepository;
import ru.schools.myuniversity.repositories.FacultyRepository;
import ru.schools.myuniversity.repositories.StudentRepository;
import ru.schools.myuniversity.service.AvatarServiceImpl;
import ru.schools.myuniversity.service.FacultyServiceImpl;
import ru.schools.myuniversity.service.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentServiceImpl studentService;
    @SpyBean
    private FacultyServiceImpl facultyService;
    @SpyBean
    private AvatarServiceImpl avatarService;

    @InjectMocks
    private FacultyController facultyController;

    private final long id = 0L;
    private final String name = "Gryffindor";
    private final String color = "Red";

    private final Faculty faculty = new Faculty();
    private final JSONObject facultyObject = new JSONObject();

    @BeforeEach
    public void setUp() throws Exception {
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        Mockito.when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
    }

    @Test
    public void checkCreateFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void checkGetFacultyInfo() throws Exception {
        Mockito.when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void checkUpdateFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void checkDeleteFaculty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/faculty/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetFacultyWithColor() throws Exception {
        Mockito.when(facultyRepository.findFacultyByColor(any(String.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/facultyWithColor" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void checkGetFacultyWithNameOrColor() throws Exception {
        Mockito.when(facultyRepository.findFacultyByNameIgnoreCaseOrColorIgnoreCase(name, color)).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/facultyWithNameOrColor?name=" + name + "&color=" + color)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

}
