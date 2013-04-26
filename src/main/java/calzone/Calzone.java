package calzone;

import jetbrains.buildServer.serverSide.SBuildServer;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import jetbrains.buildServer.web.openapi.WebControllerManager;

public class Calzone {
    public Calzone(SBuildServer server, WebControllerManager webControllerManager, PluginDescriptor pluginDescriptor) {
        TimeFormatter timeFormatter = new TimeFormatter();
        BuildFormatter buildFormatter = new BuildFormatter(new FinishedBuildMaker(server, timeFormatter), new RunningBuildMaker(timeFormatter));

        webControllerManager.registerController("/calzone/*", new ResultsPageController(new ResourceLocator(pluginDescriptor), buildFormatter, new CalzoneProjects(server.getProjectManager())));
    }

}