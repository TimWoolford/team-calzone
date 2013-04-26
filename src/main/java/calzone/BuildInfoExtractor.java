package calzone;

import calzone.model.BuildInfo;
import calzone.model.OptionsFormModel;
import calzone.model.ProjectInfo;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public final class BuildInfoExtractor {
    private static final Logger LOG = Logger.getLogger("calzone-plugin");

    private final CalzoneProjects projectManager;
    private final BuildFormatter buildFormatter;

    public BuildInfoExtractor(CalzoneProjects projectManager, BuildFormatter buildFormatter) {
        this.projectManager = projectManager;
        this.buildFormatter = buildFormatter;
    }

    public Set<ProjectInfo> getProjectInfos(OptionsFormModel options, HashSet<String> buildsToDisplaySet, HashSet<String> projectsToDisplaySet) {
        Set<ProjectInfo> projects = new TreeSet<ProjectInfo>();
        for (SProject sProject : projectManager.activeProjects()) {
            if (projectsToDisplaySet.isEmpty() || projectsToDisplaySet.contains(sProject.getName().toLowerCase())) {
                LOG.debug(String.format("Scanning Project : %s", sProject.getName()));
                Set<BuildInfo> builds = getBuildInfos(sProject, buildsToDisplaySet, options);
                if (!builds.isEmpty()) {
                    projects.add(new ProjectInfo(sProject.getName(), builds));
                }
            }
        }
        return projects;
    }

    private Set<BuildInfo> getBuildInfos(SProject sProject, HashSet<String> buildsToDisplaySet, OptionsFormModel options) {
        Set<BuildInfo> builds = new TreeSet<BuildInfo>();
        for (SBuildType sBuildType : sProject.getBuildTypes()) {
            LOG.debug(String.format("Scanning Build : %s", sBuildType.getName()));
            StringBuilder debugString = new StringBuilder(String.format("Project '%s'; Build '%s' :=", sProject.getName(), sBuildType.getName()));
            if (buildsToDisplaySet.isEmpty() || buildsToDisplaySet.contains(sBuildType.getName().toLowerCase())) {
                debugString.append(" DISPLAYED");
                builds.add(buildFormatter.format(sBuildType));
            } else {
                debugString.append(" NOT SHOWN");
            }
            if (options.isDebug()) {
                LOG.debug(debugString.toString());
            }
        }
        return builds;
    }
}
