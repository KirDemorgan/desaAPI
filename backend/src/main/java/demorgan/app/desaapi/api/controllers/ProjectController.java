package demorgan.app.desaapi.api.controllers;

import demorgan.app.desaapi.api.controllers.helpers.ProjectControllerHelper;
import demorgan.app.desaapi.api.exceptions.BadRequestException;
import demorgan.app.desaapi.api.dto.AnswerDto;
import demorgan.app.desaapi.api.dto.ProjectDto;
import demorgan.app.desaapi.api.factories.ProjectDtoFactory;
import demorgan.app.desaapi.store.entities.ProjectEntity;
import demorgan.app.desaapi.store.repositories.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RequestMapping("/api")
@RestController
public class ProjectController {

    ProjectRepository projectRepository;

    ProjectDtoFactory projectDtoFactory;

    ProjectControllerHelper projectControllerHelper;

    public static final String GET_PROJECTS = "/projects";
    public static final String CREATE_OR_UPDATE_PROJECT = "/projects";
    public static final String DELETE_PROJECT = "/projects/{projectId}";


    @GetMapping(GET_PROJECTS)
    public List<ProjectDto> getProjects(
            @RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName
            ) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }


    @PostMapping(CREATE_OR_UPDATE_PROJECT)
    public ProjectDto createProject(
            @RequestParam(value = "project_id", required = false) Optional<Long> optionalProjectId,
            @RequestParam(value = "project_name", required = false) Optional<String> optionalProjectName){

        boolean isCreateRequest = optionalProjectId.isEmpty();

        optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());

        if (isCreateRequest && optionalProjectName.isEmpty()) {
            throw new BadRequestException("Project name is required");
        }

        ProjectEntity project = optionalProjectId
                .map(projectControllerHelper::getProjectOrThrowException)
                .orElseGet(() -> ProjectEntity.builder().build());

        optionalProjectName
                .ifPresent(
                        projectName -> {
                            projectRepository
                                    .findByName(projectName)
                                    .filter(anotherProject -> !Objects.equals(anotherProject.getId(), project.getId()))
                                    .ifPresent(anotherProject -> {
                                        throw new BadRequestException(
                                                "Project name already exists"
                                        );
                                    });
                            project.setName(projectName);
                        });
        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeProjectDto(savedProject);
    }


    @DeleteMapping(DELETE_PROJECT)
    public AnswerDto deleteProject(@PathVariable("projectId")  Long projectId) {
    projectControllerHelper.getProjectOrThrowException(projectId);

    projectRepository.deleteById(projectId);

    return AnswerDto.builder()
            .answer("Project  with id" + projectId + " deleted successfully")
            .build();
    }
}
