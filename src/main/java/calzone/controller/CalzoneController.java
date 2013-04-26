package calzone.controller;


import calzone.ResourceLocator;import jetbrains.buildServer.controllers.BaseController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalzoneController extends BaseController{

    private final ResourceLocator resourceLocator;
    public CalzoneController(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    @Override
    protected ModelAndView doHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        return new ModelAndView(resourceLocator.mainPage());
    }


}
