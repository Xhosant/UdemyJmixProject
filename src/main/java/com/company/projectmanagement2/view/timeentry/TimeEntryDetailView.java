package com.company.projectmanagement2.view.timeentry;

import com.company.projectmanagement2.entity.TimeEntry;

import com.company.projectmanagement2.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.core.TimeSource;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "timeEntries/:id", layout = MainView.class)
@ViewController("TimeEntry.detail")
@ViewDescriptor("time-entry-detail-view.xml")
@EditedEntityContainer("timeEntryDc")
public class TimeEntryDetailView extends StandardDetailView<TimeEntry> {
    @Autowired
    private TimeSource timeSource;

    @Subscribe
    public void onInitEntity(final InitEntityEvent<TimeEntry> event) {
        event.getEntity().setEntryDate(timeSource.now().toLocalDateTime());
    }
}