package org.quack.QUACKServer.global.security.filter;

import com.google.common.base.CaseFormat;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : jung-kwanhee
 * @description :
 * @packageName : org.quack.QUACKServer.global.security.filter
 * @fileName : CamelizedRequestWrapper
 * @date : 25. 4. 24.
 */
public class CamelizedRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> camelizedParameterMap;

    public CamelizedRequestWrapper(HttpServletRequest request) {
        super(request);
        this.camelizedParameterMap = convertToCamelCaseMap(request.getParameterMap());
    }


    private Map<String, String[]> convertToCamelCaseMap(Map<String, String[]> originalMap) {
        Map<String, String[]> result = new LinkedHashMap<>();
        for (Map.Entry<String, String[]> entry : originalMap.entrySet()) {
            String originalKey = entry.getKey();
            String camelKey = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, originalKey);
            result.putIfAbsent(camelKey, entry.getValue());
        }
        return result;
    }

    @Override
    public String[] getParameterValues(String name) {
        return camelizedParameterMap.getOrDefault(name, null);
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
        String[] values = camelizedParameterMap.get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }
}
