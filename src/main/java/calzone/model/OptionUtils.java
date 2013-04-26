package calzone.model;

import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.ServletRequestUtils.*;

public class OptionUtils {
    public static final String FAIL_FONT_SIZE = "failFontSize";
    public static final String HIDE_GREEN_BUILDS = "hideGreenBuilds";
    public static final String RUN_TOGETHER = "runTogether";
    public static final String BLINK = "blink";
    public static final String SHOW_DIVIDERS = "showDividers";
    public static final String SHOW_TIME_REMAINING = "showTimeRemaining";
    public static final String SHOW_TIME_SINCE_FIRST_FAIL = "showTimeSinceFirstFail";
    public static final String RUNNING_IN_ITALICS = "runningInItalics";
    public static final String FREQUENCY = "frequency";
    public static final String DISSOLVE_RATE = "dissolveRate";
    public static final String DEBUG = "debug";

    public static boolean parameterAsBoolean(HttpServletRequest request, String parameterName) throws ServletRequestBindingException {
        String paramString = getStringParameter(request, parameterName);
        return paramString != null && paramString.length() > 0;
    }

    public Map<String, Object> toMap(OptionsFormModel options) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put(FAIL_FONT_SIZE, options.getFailFontSize());
        model.put(HIDE_GREEN_BUILDS, options.isHideGreenBuilds());
        model.put(RUN_TOGETHER, options.isRunTogether());
        model.put(BLINK, options.isBlink());
        model.put(SHOW_DIVIDERS, options.isShowDividers());
        model.put(SHOW_TIME_REMAINING, options.isShowTimeRemaining());
        model.put(SHOW_TIME_SINCE_FIRST_FAIL, options.isShowTimeSinceFirstFail());
        model.put(RUNNING_IN_ITALICS, options.isRunningInItalics());
        model.put(FREQUENCY, options.getFrequency());
        model.put(DISSOLVE_RATE, options.getDissolveRate());
        model.put(DEBUG, options.isDebug());
        return model;
    }

    public OptionsFormModel fromRequest(HttpServletRequest request) throws ServletRequestBindingException {
        String[] buildsToDisplay = getStringParameters(request, "buildsToDisplay");
        String[] projectsToDisplay = getStringParameters(request, "projectsToDisplay");
        String failFontSize = getStringParameter(request, FAIL_FONT_SIZE, "72pt");
        boolean iShouldDebugThisSession = parameterAsBoolean(request, DEBUG);
        boolean runningInItalics = parameterAsBoolean(request, RUNNING_IN_ITALICS);
        boolean dontShowGreenBuilds = parameterAsBoolean(request, HIDE_GREEN_BUILDS);
        boolean runTogether = parameterAsBoolean(request, RUN_TOGETHER);
        boolean blink = parameterAsBoolean(request, BLINK);
        boolean showDividers = parameterAsBoolean(request, SHOW_DIVIDERS);
        boolean showTimeRemaining = parameterAsBoolean(request, SHOW_TIME_REMAINING);
        boolean showTimeSinceFirstFail = parameterAsBoolean(request, SHOW_TIME_SINCE_FIRST_FAIL);
        int frequency = getIntParameter(request, FREQUENCY, 5);
        double dissolveRate = getDoubleParameter(request, DISSOLVE_RATE, 1);

        return new OptionsFormModel(buildsToDisplay, projectsToDisplay, failFontSize, dontShowGreenBuilds, runTogether, blink, showDividers, showTimeRemaining, showTimeSinceFirstFail, runningInItalics, frequency, dissolveRate, iShouldDebugThisSession);
    }
}
