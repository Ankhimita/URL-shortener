package UrlShortenerApp.utils;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

public class URLUtility {

    public static UserAgentStringParser parser = UADetectorServiceFactory.getResourceModuleParser();

    public static String getBrowserName(String userAgent) {
        ReadableUserAgent readableUserAgent = parser.parse(userAgent);
        return readableUserAgent.getName();
    }
}
