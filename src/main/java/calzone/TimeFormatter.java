package calzone;

import java.util.Date;

public class TimeFormatter {

    public String formatMinutesAndSeconds(long timeInSeconds) {
        return String.format("%d:%02d", timeInSeconds / 60, timeInSeconds % 60);
    }

    public String formatDateSince(Date previousDate) {
        long seconds = (new Date().getTime() - previousDate.getTime()) / 1000;
        long hours = seconds / 3600;

        if (seconds < 3600) {
            return pluralise("Minute", seconds / 60);
        }

        StringBuilder formattedOutput = new StringBuilder();
        if (hours >= 24) {
            formattedOutput.append(pluralise("Day", hours / 24)).append(" ");
        }

        formattedOutput.append(pluralise("Hour", hours % 24));
        return formattedOutput.toString();
    }

    private String pluralise(String unit, long value) {
        return String.format("%d %s%s", value, unit, value != 1 ? "s" : "");
    }
}