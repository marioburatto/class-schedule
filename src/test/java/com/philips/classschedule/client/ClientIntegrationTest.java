package com.philips.classschedule.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philips.classschedule.controller.dto.CourseDto;
import com.philips.classschedule.controller.dto.DepartmentDto;
import com.philips.classschedule.controller.dto.ProfessorDto;
import com.philips.classschedule.controller.dto.ScheduleDto;
import com.philips.classschedule.repository.ScheduleRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import reactor.test.StepVerifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    void cleanUp() {
        scheduleRepository.deleteAll().block();
    }

    @Test
    void testLoadExampleData() throws IOException {
        loadExampleData();
        scheduleRepository.count()
                .as(StepVerifier::create)
                .expectNextCount(10L);

        JsonNode search = get("/search", JsonNode.class);
        JsonNode expected = new ObjectMapper().readTree(
                readFile("pre-load/search_result.json")
                        .collect(Collectors.joining("\n")));

        MatcherAssert.assertThat(
                search.toString(),
                Matchers.equalTo(expected.toString()));
    }

    void loadExampleData() throws IOException {
        Map<Integer, DepartmentDto> departmentIdMapping = readDepartments()
                .collect(
                        Collectors.toMap(
                                DepartmentDto::getId,
                                it -> post("/department",
                                        it.toBuilder().id(null).build(),
                                        it.getClass())
                        )
                );
        Map<Integer, ProfessorDto> professorIdMapping = readProfessors()
                .map(it -> it.toBuilder()
                        .departmentId(
                                departmentIdMapping
                                        .get(it.getDepartmentId())
                                        .getId())
                        .build())
                .collect(
                        Collectors.toMap(
                                ProfessorDto::getId,
                                it -> post("/professor",
                                        it.toBuilder().id(null).build(),
                                        it.getClass())
                        )
                );
        Map<Integer, CourseDto> courseIdMapping = readCourses()
                .map(it -> it.toBuilder()
                        .departmentId(
                                departmentIdMapping
                                        .get(it.getDepartmentId())
                                        .getId())
                        .build())
                .collect(
                        Collectors.toMap(
                                CourseDto::getId,
                                it -> post("/course",
                                        it.toBuilder().id(null).build(),
                                        it.getClass())
                        )
                );
        readSchedules()
                .map(it -> it.toBuilder()
                        .professorId(
                                professorIdMapping
                                        .get(it.getProfessorId())
                                        .getId())
                        .courseId(
                                courseIdMapping
                                        .get(it.getCourseId())
                                        .getId())
                        .build())
                .forEach(it -> post("/schedule", it, it.getClass()));
    }

    <T> T get(String url, Class<T> responseClass) {
        return restTemplate.getForEntity(
                        "http://localhost:" + port + url,
                        responseClass)
                .getBody();
    }

    <T> T post(String url, Object entity, Class<T> responseClass) {
        return restTemplate.postForEntity(
                        "http://localhost:" + port + url,
                        entity,
                        responseClass)
                .getBody();
    }

    private Stream<DepartmentDto> readDepartments() throws IOException {
        return readFile("pre-load/departments.csv")
                .skip(1)
                .map(it -> it.split(";"))
                .map(it -> DepartmentDto.builder()
                        .id(Integer.parseInt(it[0]))
                        .name(it[1])
                        .build()
                );
    }

    private Stream<ProfessorDto> readProfessors() throws IOException {
        return readFile("pre-load/professors.csv")
                .skip(1)
                .map(it -> it.split(";"))
                .map(it -> ProfessorDto.builder()
                        .id(Integer.parseInt(it[0]))
                        .name(it[1])
                        .departmentId(Integer.parseInt(it[2]))
                        .build()
                );
    }

    private Stream<CourseDto> readCourses() throws IOException {
        return readFile("pre-load/courses.csv")
                .skip(1)
                .map(it -> it.split(";"))
                .map(it -> CourseDto.builder()
                        .id(Integer.parseInt(it[0]))
                        .name(it[1])
                        .credits(Integer.parseInt(it[3]))
                        .departmentId(Integer.parseInt(it[2]))
                        .build()
                );
    }

    private Stream<ScheduleDto> readSchedules() throws IOException {
        return readFile("pre-load/schedules.csv")
                .skip(1)
                .map(it -> it.split(";"))
                .map(row -> ScheduleDto.builder()
                        .professorId(Integer.parseInt(row[0]))
                        .courseId(Integer.parseInt(row[1]))
                        .semester(Integer.parseInt(row[2]))
                        .year(Integer.parseInt(row[3]))
                        .build());
    }

    private Stream<String> readFile(String fileName) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(is))
                .lines();
    }

    public static void main(String[] args) throws IOException {
        ClientIntegrationTest client = new ClientIntegrationTest();
        client.port = 8080;
        client.loadExampleData();
    }
}
