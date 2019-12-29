package UrlShortenerApp.controller;

import UrlShortenerApp.constants.URLConstants;
import UrlShortenerApp.service.URLService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/url/shortener")
public class URLController {

    private static final Logger logger = LoggerFactory.getLogger(URLController.class);

    @Autowired
    private URLService urlService;

    /**
     * Get shortened url for any given URL
     *
     * @param longUrl
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCompactUrl", method = RequestMethod.POST)
    public ResponseEntity getShortenedUrl(@RequestBody final String longUrl, HttpServletRequest request) throws Exception {
        logger.info("Url Received=" + longUrl);
        String shortenedUrl = "";
        if (UrlValidator.getInstance().isValid(longUrl) && longUrl != "") {
            try {
                shortenedUrl = urlService.getShortUrlId(longUrl);
                shortenedUrl = URLConstants.BASE_URL + shortenedUrl;
            } catch (Exception e) {
                throw new Exception("Exception ocurred while fetching compact url using long url", e);
            }
        } else {
            throw new Exception("The URL input is Invalid. Please check and retry!");
        }
        return new ResponseEntity(shortenedUrl, HttpStatus.OK);
    }

    /**
     * Get the actual long url corresponding to any given shortened url
     *
     * @param shortUrl
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLongUrl/", method = RequestMethod.POST)
    public ResponseEntity getLongUrl(@RequestBody final String shortUrl, HttpServletRequest request) throws Exception {
        String longUrl = "";
        String userAgent = request.getHeader("User-Agent");
        if (shortUrl.contains(URLConstants.BASE_URL)) {
            try {
                longUrl = urlService.getLongUrl(shortUrl, userAgent);
                if (StringUtils.isBlank(longUrl)) {
                    return new ResponseEntity("No Long Url found corresponding to the provided compact URL", HttpStatus.NO_CONTENT);
                }
            } catch (Exception e) {
                throw new Exception("Exception ocurred while fetching long url using compact url", e);
            }
        } else {
            throw new Exception("The URL input is Invalid. Please check and retry!");
        }
        return new ResponseEntity(longUrl, HttpStatus.OK);
    }

    /**
     * Get the actual long url corresponding to the key of any compact url
     * (for only testing purpose from postman)
     *
     * @param key
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getLongUrlByKey/{key}", method = RequestMethod.GET)
    public ResponseEntity getLongUrlByKey(@PathVariable final String key, HttpServletRequest request) throws Exception {
        String longUrl = "";
        String userAgent = request.getHeader("User-Agent");
        try {
            longUrl = urlService.getLongUrlByUrlKey(key, userAgent);
            if (StringUtils.isBlank(longUrl)) {
                return new ResponseEntity("No Long Url found corresponding to the provided compact URL key", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            throw new Exception("Exception ocurred while fetching long url using urlKey", e);
        }

        return new ResponseEntity(longUrl, HttpStatus.OK);
    }

    /**
     * Get the browser statistics for any given compact url
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getStatisticsByUrl", method = RequestMethod.POST)
    public ResponseEntity getAnalyticsByUrlKey(@RequestBody final String compactUrl, HttpServletRequest request) throws Exception {
        List<?> analytics = new ArrayList<>();
        try {
            analytics = urlService.getAnalyticsByUrlKey(compactUrl);
            if (CollectionUtils.isEmpty(analytics)) {
                return new ResponseEntity("No browser statistics found corresponding to the provided compact URL", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            throw new Exception("Exception ocurred while fetching browser statistics using the provided urlKey", e);
        }

        return new ResponseEntity(analytics, HttpStatus.OK);
    }

}


