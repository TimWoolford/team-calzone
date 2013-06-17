package calzone;

import jetbrains.buildServer.serverSide.ProjectManager;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;

import java.util.*;

public class CalzoneProjects {
    private final ProjectManager projectManager;

    CalzoneProjects(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }


    public List<SProject> activeProjects() {
        return projectManager.getActiveProjects();
    }

    public Collection<String> allBuildNames() {
        Set<String> buildNames = new TreeSet<String>();
        for (SBuildType sBuildType : projectManager.getActiveBuildTypes()) {
            buildNames.add(sBuildType.getName());
        }
        return sortIt(buildNames);
    }

    public Collection<String> allProjectNames() {
        Set<String> projectNames = new HashSet<String>();
        for (SProject sProject : projectManager.getActiveProjects()) {
            projectNames.add(sProject.getName());
        }
        return sortIt(projectNames);
    }

    private <T extends Comparable<T>> Collection<T> sortIt(Set<T> unsorted) {
        List<T> sorted = new ArrayList<T>(unsorted);
        Collections.sort(sorted);
        return sorted;
    }
}