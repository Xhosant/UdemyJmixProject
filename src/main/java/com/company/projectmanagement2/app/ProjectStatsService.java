package com.company.projectmanagement2.app;

import  com.company.projectmanagement2.entity.Project;
import com.company.projectmanagement2.entity.ProjectStats;
import com.company.projectmanagement2.entity.Task;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ProjectStatsService {
    private final DataManager dataManager;

    public ProjectStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public List<ProjectStats> fetchProjectStatistics(){
        List<Project> projects = dataManager.load(Project.class).all().list();

        List<ProjectStats> projectStats = projects.stream().map(project -> {
            ProjectStats stat = dataManager.create(ProjectStats.class);
            stat.setId(project.getId());
            stat.setProjectName(project.getName());
            stat.setTasksCount(project.getTasks().size());
            Integer plannedEfforts = project.getTasks().stream().map(Task::getEstimation).reduce(0, Integer::sum);
//            List<Task> l = project.getTasks();
//            Stream<Task> s = l.stream();
//            Stream<Integer> is = s.map(Task::getEstimation);
//            plannedEfforts = is.reduce(0, Integer::sum);
            stat.setPlannedEfforts(plannedEfforts);
            stat.setActualEfforts(getActualEffort(project.getId()));
            return stat;
        }).collect(Collectors.toList());
        return projectStats;
    }

    public Integer getActualEffort(UUID projectId){
        return dataManager.loadValue("select SUM(te.timeSpent) from TimeEntry te " +
                "where te.task.project.id = :projectId", Integer.class).parameter("projectId", "")
                .parameter("projectId", projectId).one();
    }
}