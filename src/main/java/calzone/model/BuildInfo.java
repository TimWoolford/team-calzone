package calzone.model;

public class BuildInfo implements Comparable<BuildInfo> {
    private final String buildName;
    private final CalzoneFinishedBuild lastFinished;
    private final CalzoneRunningBuild runningBuild;
    private final boolean responsibilityAssigned;

    public BuildInfo(String buildName, CalzoneFinishedBuild finishedBuild, CalzoneRunningBuild runningBuild, boolean responsibilityAssigned) {
        this.buildName = buildName;
        this.lastFinished = finishedBuild;
        this.runningBuild = runningBuild;
        this.responsibilityAssigned = responsibilityAssigned;
    }

    public String getBuildName() {
        return buildName;
    }

    public Boolean isGreen() {
        return lastFinished.getStatus().isGreen() && runningBuild.getStatus().isGreen();
    }

    public boolean isResponsibilityAssigned() {
        return responsibilityAssigned;
    }

    public CalzoneFinishedBuild getLastFinished() {
        return lastFinished;
    }

    public CalzoneRunningBuild getRunningBuild() {
        return runningBuild;
    }

    private boolean isCompileFailure() {
        return lastFinished.getStatus() == FinishedBuildStatus.compilationFailed;
    }

    private String getTimeSinceLastFirstFailure() {
        return lastFinished.getTimeSinceFirstFailure();
    }

    @Override
    public int compareTo(BuildInfo buildInfo) {
        return this.buildName.toLowerCase().compareTo(buildInfo.getBuildName().toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("last finished [%s], running [%s]", lastFinished.getStatus(), runningBuild.getStatus());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildInfo buildInfo = (BuildInfo) o;

        if (responsibilityAssigned != buildInfo.responsibilityAssigned) return false;
        if (!buildName.equals(buildInfo.buildName)) return false;
        if (!lastFinished.equals(buildInfo.lastFinished)) return false;
        if (!runningBuild.equals(buildInfo.runningBuild)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = buildName.hashCode();
        result = 31 * result + lastFinished.hashCode();
        result = 31 * result + runningBuild.hashCode();
        result = 31 * result + (responsibilityAssigned ? 1 : 0);
        return result;
    }
}