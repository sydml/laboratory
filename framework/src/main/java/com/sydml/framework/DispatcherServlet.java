package com.sydml.framework;

import com.sydml.common.utils.*;
import com.sydml.framework.ioc.annotation.ResponseBody;
import com.sydml.framework.ioc.bean.Data;
import com.sydml.framework.ioc.bean.Param;
import com.sydml.framework.ioc.bean.View;
import com.sydml.framework.ioc.handler.BeanHandler;
import com.sydml.framework.ioc.handler.ConfigHandler;
import com.sydml.framework.ioc.handler.ControllerHandler;
import com.sydml.framework.ioc.handler.Handler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-12
 * 时间： 22:22
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化handler 类
        HandlerLoader.init();
        // 用于注册servlet
        ServletContext servletContext = servletConfig.getServletContext();
        //注册JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHandler.getJspPath() + "*");
        //注册处理静态资源的默认Servlet
        ServletRegistration defalultServlet = servletContext.getServletRegistration("default");
        defalultServlet.addMapping(ConfigHandler.getAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //请求方法与请求路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        Handler handler = ControllerHandler.getHandler(requestMethod, requestPath);
        if (handler != null) {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHandler.getBean(controllerClass);
            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                String parameterValue = request.getParameter(paramName);
                paramMap.put(paramName, parameterValue);
            }

            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtil.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtil.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }
            Param param = new Param(paramMap);
            Method actionMethod = handler.getActionMethod();
            Object result;
            if (param.isEmpty()) {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            } else {
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            }
            if (result instanceof View) {
                // 如果返回结果是View类型的进行视图跳转
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    if (path.startsWith("/")) {
                        response.sendRedirect(request.getContextPath() + path);
                    } else {
                        Map<String, Object> model = view.getModel();
                        for (Map.Entry<String, Object> entry : model.entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(ConfigHandler.getJspPath() + path).forward(request, response);
                    }
                }
            } else if (result instanceof Data) {
                // 如果返回结果是Data类型的直接通过流返回json
                Data data = (Data) result;
                Object model = data.getModel();
                if (model != null) {
                    printResult(response, model);
                }
            } else if (actionMethod.isAnnotationPresent(ResponseBody.class)) {
                printResult(response, result);
            }

        }
    }

    private void printResult(HttpServletResponse response, Object result) throws IOException {
        response.setContentType("UTF-8");
        PrintWriter writer = response.getWriter();
        String json = JsonUtil.encodeJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
