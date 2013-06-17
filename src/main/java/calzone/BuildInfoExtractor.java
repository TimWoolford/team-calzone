package calzone;

import calzone.model.BuildInfo;
import calzone.model.OptionsFormModel;
import calzone.model.ProjectInfo;
import jetbrains.buildServer.serverSide.SBuildType;
import jetbrains.buildServer.serverSide.SProject;
import org.apache.log4j.Logger;

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

    public Set<ProjectInfo> getProjectInfos(Set<String> projectsToDisplaySet, Set<String> buildsToDisplaySet, OptionsFormModel options) {
        Set<ProjectInfo> projects = new TreeSet<ProjectInfo>();
        for (SProject sProject : projectManager.activeProjects()) {
            if (projectsToDisplaySet.isEmpty() || projectsToDisplaySet.contains(sProject.getName().toLowerCase())) {
                Set<BuildInfo> builds = getBuildInfos(sProject, buildsToDisplaySet, options);
                if (!builds.isEmpty()) {
                    projects.add(new ProjectInfo(sProject.getName(), builds));
                }
            }
        }
        return projects;
    }

    private Set<BuildInfo> getBuildInfos(SProject sProject, Set<String> buildsToDisplaySet, OptionsFormModel options) {
        Set<BuildInfo> builds = new TreeSet<BuildInfo>();
        for (SBuildType sBuildType : sProject.getBuildTypes()) {
            StringBuilder debugString = new StringBuilder(String.format("Project '%s'; Build '%s' :=", sProject.getName(), sBuildType.getName()));
            if (buildsToDisplaySet.isEmpty() || buildsToDisplaySet.contains(sBuildType.getName().toLowerCase())) {
                BuildInfo buildInfo = buildFormatter.format(sBuildType);
                debugString.append(" DISPLAYED; ").append(buildInfo);
                builds.add(buildInfo);
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
