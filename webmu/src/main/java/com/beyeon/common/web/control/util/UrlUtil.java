package com.beyeon.common.web.control.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public final class UrlUtil {
    //========================================================================================================
	
    public static String buildBaseRequestUrl(HttpServletRequest r) {
    	return buildBaseRequestUrl(r.getScheme(), r.getServerName(), r.getServerPort(), r.getContextPath());
    }

	public static String buildBaseRequestUrl(String scheme, String serverName, int serverPort, String contextPath) {
        scheme = scheme.toLowerCase();
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ("http".equals(scheme)) {
            if (serverPort != 80) {
                url.append(":").append(serverPort);
            }
        } else if ("https".equals(scheme)) {
            if (serverPort != 443) {
                url.append(":").append(serverPort);
            }
        }

        if (!"/".equals(contextPath)) {
            url.append(contextPath);
        }

        return url.toString();
    }
	
    public static String buildFullRequestUrl(HttpServletRequest r) {
        return buildFullRequestUrl(r.getScheme(), r.getServerName(), r.getServerPort(), r.getRequestURI(),
                r.getQueryString());
    }

    public static String buildFullRequestUrl(String scheme, String serverName, int serverPort, String requestURI,
            String queryString) {

        scheme = scheme.toLowerCase();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ("http".equals(scheme)) {
            if (serverPort != 80) {
                url.append(":").append(serverPort);
            }
        } else if ("https".equals(scheme)) {
            if (serverPort != 443) {
                url.append(":").append(serverPort);
            }
        }

        url.append(requestURI);

        if (queryString != null) {
            url.append("?").append(queryString);
        }

        return url.toString();
    }

    public static String buildRequestUrl(HttpServletRequest r) {
        return buildRequestUrl(r.getServletPath(), r.getRequestURI(), r.getContextPath(), r.getPathInfo(),
            r.getQueryString());
    }

    private static String buildRequestUrl(String servletPath, String requestURI, String contextPath, String pathInfo,
            String queryString) {

        StringBuilder url = new StringBuilder();

        if (servletPath != null) {
            url.append(servletPath);
            if (pathInfo != null) {
                url.append(pathInfo);
            }
        } else {
            url.append(requestURI.substring(contextPath.length()));
        }

        if (queryString != null) {
            url.append("?").append(queryString);
        }

        return url.toString();
    }

    public static boolean isValidRedirectUrl(String url) {
        return url != null && url.startsWith("/") || isAbsoluteUrl(url);
    }

    public static boolean isAbsoluteUrl(String url) {
        final Pattern ABSOLUTE_URL = Pattern.compile("\\A[a-z.+-]+://.*", Pattern.CASE_INSENSITIVE);

        return ABSOLUTE_URL.matcher(url).matches();
    }
}
