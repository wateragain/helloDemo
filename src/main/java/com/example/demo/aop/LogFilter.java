package com.example.demo.aop;

import com.example.demo.dto.base.RequestLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@Component
@WebFilter
@Slf4j
public class LogFilter implements Filter {

    @Value("${spring.application.name}")
    private String serverName;

    @Value("#{'${notLogPathStart}'.split(',')}")
    private List<String> notLogPathStart;

    private static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("呐呐呐呐,开始啦这场旅程");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getRequestURI();
        boolean needLog = true;
        //todo 优化
        if (notLogPathStart != null) {
            for (var start : notLogPathStart) {
                if (path.startsWith(start)) {
                    needLog = false;
                }
            }
        }
        if (needLog) {
            var start = System.currentTimeMillis();
            //转换成代理类-不然获取requestBodey的内容很麻烦-取出来还得再塞回去
            ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
            ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);

            RequestLog requestLog = new RequestLog();
            requestLog.setServerName(serverName);
            requestLog.setUrl(wrapperRequest.getRequestURL().toString());
            requestLog.setRequestMethod(wrapperRequest.getMethod());
            requestLog.setRequestHeaders(wrapperRequest.getHeaderNames().toString());

            Enumeration<String> headers = wrapperRequest.getHeaderNames();
            StringBuffer sb = new StringBuffer();
            while (headers.hasMoreElements()) {
                String headerKey = headers.nextElement();
                sb.append(headerKey).append(":").append(wrapperRequest.getHeader(headerKey)).append(";");
            }
            requestLog.setRequestHeaders(sb.toString());

            Enumeration<String> parameterNames = wrapperRequest.getParameterNames();
            sb = new StringBuffer();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                sb.append(parameterName).append("=").append(request.getParameter(parameterName)).append(";");
            }
            requestLog.setRequestForm(sb.toString());

            try {
                chain.doFilter(wrapperRequest, wrapperResponse);
            } finally {
                long end = System.currentTimeMillis();
                requestLog.setCostTime((end - start) / 1000.0);

                requestLog.setResponseStatus(wrapperResponse.getStatus());

                requestLog.setRequestBody(new String(wrapperRequest.getContentAsByteArray()));

                String contentType = response.getContentType();
                if ("application/json".equals(contentType)) {
                    String result = new String(wrapperResponse.getContentAsByteArray());
                    requestLog.setResponseBody(result);
                }
                log.info(MAPPER.writeValueAsString(requestLog));

                response.getOutputStream().write(wrapperResponse.getContentAsByteArray());
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        log.info("我们的旅程这里就结束了哦");
    }
}
