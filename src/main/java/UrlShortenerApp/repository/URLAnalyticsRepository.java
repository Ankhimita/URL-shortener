package UrlShortenerApp.repository;

import UrlShortenerApp.model.URLAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface URLAnalyticsRepository extends JpaRepository<URLAnalytics, Long> {

    @Query(value = "select u.browserName, count(u.browserName)" +
            " from URLAnalytics u where u.urlId = :urlId group by u.browserName")
    List<?> findUrlAnalyticsByUrlId(@Param(value = "urlId") String urlId);
}
