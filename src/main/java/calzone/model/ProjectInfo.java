package calzone.model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectInfo implements Comparable<ProjectInfo> {

    private static final Pattern NETSTREAM_BRANCH = Pattern.compile("netstream i(\\d+)");

    private final String projectName;
    private final Set<BuildInfo> greenBuilds = new TreeSet<BuildInfo>();
    private final Set<BuildInfo> redBuilds = new TreeSet<BuildInfo>();

    public ProjectInfo(String projectName, Collection<BuildInfo> builds) {
        this.projectName = projectName;
        for (BuildInfo build : builds) {
            if (build.isGreen()) {
                greenBuilds.add(build);
            } else {
                redBuilds.add(build);
            }
        }
    }

    public String getProjectName() {
        return projectName;
    }

    public Collection<BuildInfo> getGreenBuilds() {
        return greenBuilds;
    }

    public Collection<BuildInfo> getRedBuilds() {
        return redBuilds;
    }

    public int compareTo(ProjectInfo projectInfo) {
        String thisProjectName = getProjectName().toLowerCase();
        String otherProjectName = projectInfo.getProjectName().toLowerCase();

        // Netstream Trunk always sorts first
        if (thisProjectName.equals(otherProjectName)) {
            return 0;
        } else if (thisProjectName.equals("netstream trunk")) {
            return -1;
        } else if (otherProjectName.equals("netstream trunk")) {
            return 1;
        }

        // ...then Netstream branches, latest first
        Integer aBranch = parseNetstreamBranch(thisProjectName);
        Integer bBranch = parseNetstreamBranch(otherProjectName);
        if (aBranch != null && bBranch != null) {
            return -(aBranch.compareTo(bBranch));
        } else if (aBranch == null && bBranch != null) {
            return 1;
        } else if (aBranch != null && bBranch == null) {
            return -1;
        }

        // ...then miscellaneous projects, sorted on project name ascending
        return thisProjectName.compareTo(otherProjectName);
    }

    private Integer parseNetstreamBranch(String projectName) {
        Integer branchNumber = null;
        Matcher matcher = NETSTREAM_BRANCH.matcher(projectName);
        if (matcher.matches()) {
            branchNumber = Integer.parseInt(matcher.group(1));
        }
        return branchNumber;
    }
}