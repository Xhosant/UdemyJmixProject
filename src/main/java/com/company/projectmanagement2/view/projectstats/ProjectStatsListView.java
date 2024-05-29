package com.company.projectmanagement2.view.projectstats;

import com.company.projectmanagement2.app.ProjectStatsService;
import com.company.projectmanagement2.entity.ProjectStats;
import com.company.projectmanagement2.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.LoadContext;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@Route(value = "projectStats", layout = MainView.class)
@ViewController("ProjectStats.list")
@ViewDescriptor("project-stats-list-view.xml")
@LookupComponent("projectStatsDataGrid")
@DialogMode(width = "50em")
public class ProjectStatsListView extends StandardListView<ProjectStats> {

    @Autowired
    private ProjectStatsService projectStatsService;

    @Install(to = "projectStatsDl", target = Target.DATA_LOADER)
    protected List<ProjectStats> projectStatsDlLoadDelegate(LoadContext<ProjectStats> loadContext) {
        // Here you can load entities from an external storage.
        // Set the loaded entities to the not-new state using EntityStates.setNew(entity, false).
        return projectStatsService.fetchProjectStatistics();
    }

//    @Install(to = "projectStatsDataGrid.remove", subject = "delegate")
//    private void projectStatsDataGridRemoveDelegate(final Collection<ProjectStats> collection) {
//        for (ProjectStats entity : collection) {
//            // Here you can remove entities from an external storage
//        }
//    }
}
