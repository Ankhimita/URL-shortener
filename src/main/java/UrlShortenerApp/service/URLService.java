package UrlShortenerApp.service;

import UrlShortenerApp.constants.URLConstants;
import UrlShortenerApp.model.URLAnalytics;
import UrlShortenerApp.model.URLMapper;
import UrlShortenerApp.repository.URLAnalyticsRepository;
import UrlShortenerApp.repository.URLMapperRepository;
import UrlShortenerApp.utils.URLUtility;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class URLService {

    private static final Logger logger = LoggerFactory.getLogger(URLService.class);

    private static int id = 0;

    @Autowired
    private URLMapperRepository urlMapperRepository;

    @Autowired
    private URLAnalyticsRepository urlAnalyticsRepository;

    public String getShortUrlId(String longUrl) {

        String responseKey = urlMapperRepository.findUrlKeyByLongUrl(longUrl);
        if (!StringUtils.isBlank(responseKey)) {
            return responseKey;
        }

        int urlHashcode = Math.abs(longUrl.hashCode());
        String calculatorKey = urlHashcode + String.valueOf(id);
        long calculatorId = Long.parseLong(calculatorKey);

        String encryptedUrlId = KeyService.generateEncryptedUrlId(calculatorId);

        URLMapper urlMapperRequest = new URLMapper();
        urlMapperRequest.setUrlId(encryptedUrlId);
        urlMapperRequest.setCompactUrl(URLConstants.BASE_URL + encryptedUrlId);
        urlMapperRequest.setLongUrl(longUrl);
        urlMapperRepository.save(urlMapperRequest);
        id++;
        return encryptedUrlId;
    }

    public String getLongUrl(String shortUrl, String userAgent) {
        String encryptedUrlId = shortUrl.replace(URLConstants.BASE_URL, "");
        URLMapper urlMapper = findLongUrlFromDB(encryptedUrlId);
        if (urlMapper != null) {

            return urlMapper.getLongUrl();
        }
        return null;
    }

    public String getLongUrlByUrlKey(String encryptedUrlId, String userAgent) {
        URLMapper urlMapper = findLongUrlFromDB(encryptedUrlId);
        if (urlMapper != null) {

            URLAnalytics urlAnalytics = new URLAnalytics();

            String browserName = URLUtility.getBrowserName(userAgent);
            urlAnalytics.setBrowserName(browserName);

            List<URLAnalytics> urlAnalyticsList = urlMapper.getUrlAnalyticsList();
            urlAnalyticsList.add(urlAnalytics);
            urlMapper.setUrlAnalyticsList(urlAnalyticsList);

            urlMapperRepository.save(urlMapper);

            return urlMapper.getLongUrl();
        }
        return null;
    }

    public List<?> getAnalyticsByUrlKey(String compactUrl) {
        String encrptedUrlId = compactUrl.replace(URLConstants.BASE_URL, "");
        List<?> response = urlAnalyticsRepository.findUrlAnalyticsByUrlId(encrptedUrlId);
        return response;
    }


    private URLMapper findLongUrlFromDB(String encryptedUrlId) {
        return urlMapperRepository.findById(encryptedUrlId).orElse(null);
    }
}
