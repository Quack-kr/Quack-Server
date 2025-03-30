package org.quack.QUACKServer.global.security.filter;

import com.google.common.base.CaseFormat;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security.filter
 * @fileName : CamelizedRequestWrapper
 * @date : 25. 3. 29.
 */

public class CamelizedRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> camelizedParameterMap;

    public CamelizedRequestWrapper(HttpServletRequest request) {
        super(request);
        this.camelizedParameterMap = request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, entry.getKey()),
                        Map.Entry::getValue
                ));
    }

    @Override
    public String[] getParameterValues(String name) {
        return camelizedParameterMap.get(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(camelizedParameterMap.keySet());
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return camelizedParameterMap;
    }

    @Override
    public String getParameter(String name) {
        return camelizedParameterMap.containsKey(name) ? camelizedParameterMap.get(name)[0] : null;
    }
}