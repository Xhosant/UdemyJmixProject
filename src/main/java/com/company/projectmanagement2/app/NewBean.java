package com.company.projectmanagement2.app;

import com.company.projectmanagement2.entity.Project;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

@Component
public class NewBean {
    private final DataManager dataManager;

    public NewBean(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void newMethod(){
        dataManager.load(Project.class);
    }
}