package calzone;

import calzone.model.*;
import jetbrains.buildServer.controllers.BaseController;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static calzone.model.OptionUtils.parameterAsBoolean;
import static java.util.Arrays.asList;

public class ResultsPageController extends BaseController {
    Logger LOG = Logger.getLogger("calzone-plugin");

    private static final String[] FAILURE_FONT_SIZES = new String[]{"18pt", "24pt", "30pt", "36pt", "42pt", "48pt", "60pt", "72pt", "84pt", "96pt", "112pt", "128pt", "144pt", "180pt", "216pt"};
    private static final int[] REFRESH_FREQUENCIES = new int[]{5, 10, 15, 20, 30, 60, 120, 300};
    private static final ValueWithLabel[] DISSOLVE_RATES = new ValueWithLabel[]{
            new ValueWithLabel("direct transition", 1.0),
            new ValueWithLabel("fast (.4s) dissolve", 0.25),
            new ValueWithLabel("medium (1s) dissolve", 0.1),
            new ValueWithLabel("slow (5s) dissolve", 0.02)
    };

    private final CalzoneProjects projectManager;
    private final ResourceLocator resourceLocator;
    private final OptionUtils optionUtils;
    private BuildInfoExtractor buildInfoExtractor;

    public ResultsPageController(ResourceLocator resourceLocator, BuildFormatter buildFormatter, CalzoneProjects calzoneProjects) {
        this.projectManager = calzoneProjects;
        this.resourceLocator = resourceLocator;
        optionUtils = new OptionUtils();
        buildInfoExtractor = new BuildInfoExtractor(projectManager, buildFormatter);
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OptionsFormModel options = optionUtils.fromRequest(request);

        HashSet<String> buildsToDisplaySet = asSet(options.getBuildsToDisplay());
        HashSet<String> projectsToDisplaySet = asSet(options.getProjectsToDisplay());

        if (options.isDebug()) {
            LOG.debug(String.format("Requested Projects::\n%s", formatSet(projectsToDisplaySet)));
            LOG.debug(String.format("Requested Builds::\n%s", formatSet(buildsToDisplaySet)));
        }

        Map<String, Object> model = new HashMap<String, Object>();
        model.putAll(optionUtils.toMap(options));

        if (parameterAsBoolean(request, "fragment")) {

            Set<ProjectInfo> projects = buildInfoExtractor.getProjectInfos(options, buildsToDisplaySet, projectsToDisplaySet);

            model.put("results", projects);
            model.put("now", new Date());
            model.put("options", options);
            return new ModelAndView(resourceLocator.results(), model);
        } else {
            model.put("projectNames", projectManager.allProjectNames());
            model.put("buildNames", projectManager.allBuildNames());
            model.put("failFontSizes", FAILURE_FONT_SIZES);
            model.put("frequencies", REFRESH_FREQUENCIES);
            model.put("dissolveRates", DISSOLVE_RATES);
            model.put("options", options);
            return new ModelAndView(resourceLocator.options(), model);
        }
    }


    private String formatSet(Set<String> buildsToDisplaySet) {
        return buildsToDisplaySet.toString()
                .replaceAll("[\\[\\]]", "")
                .replaceAll(",\\s?", "\n");
    }

    private HashSet<String> asSet(String[] dataAsArray) {
        HashSet<String> dataAsSet = new HashSet<String>(asList(dataAsArray));
        for (String s : dataAsArray) {
            dataAsSet.add(s.toLowerCase());
        }
        return dataAsSet;
    }

}