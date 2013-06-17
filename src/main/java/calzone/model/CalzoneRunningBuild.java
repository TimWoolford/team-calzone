package calzone.model;

public class CalzoneRunningBuild {

    private final RunningBuildStatus status;
    private final String timeRemaining;

    public CalzoneRunningBuild(RunningBuildStatus status, String timeRemaining) {
        this.status = status;
        this.timeRemaining = timeRemaining;
    }

    public RunningBuildStatus getStatus() {
        return status;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalzoneRunningBuild that = (CalzoneRunningBuild) o;

        if (status != that.status) return false;
        if (timeRemaining != null ? !timeRemaining.equals(that.timeRemaining) : that.timeRemaining != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (timeRemaining != null ? timeRemaining.hashCode() : 0);
        return result;
    }
}