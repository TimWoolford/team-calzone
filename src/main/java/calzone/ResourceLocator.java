package calzone;

import jetbrains.buildServer.web.openapi.PluginDescriptor;

class ResourceLocator {
    private final PluginDescriptor pluginDescriptor;

    ResourceLocator(PluginDescriptor pluginDescriptor) {
        this.pluginDescriptor = pluginDescriptor;
    }

    public String results() {
        return pluginDescriptor.getPluginResourcesPath("results.jsp");
    }

    public String options() {
        return pluginDescriptor.getPluginResourcesPath("calzone.jsp");
    }
}
