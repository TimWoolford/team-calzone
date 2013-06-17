package calzone.model;

public class CalzoneFinishedBuild {
    private final FinishedBuildStatus status;
    private final String timeSinceFirstFailure;

    public CalzoneFinishedBuild(FinishedBuildStatus status, String timeSinceFirstFailure) {
        this.status = status;
        this.timeSinceFirstFailure = timeSinceFirstFailure;
    }

    public FinishedBuildStatus getStatus() {
        return status;
    }

    public String getTimeSinceFirstFailure() {
        return timeSinceFirstFailure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalzoneFinishedBuild that = (CalzoneFinishedBuild) o;

        if (status != that.status) return false;
        if (timeSinceFirstFailure != null ? !timeSinceFirstFailure.equals(that.timeSinceFirstFailure) : that.timeSinceFirstFailure != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (timeSinceFirstFailure != null ? timeSinceFirstFailure.hashCode() : 0);
        return result;
    }
}
