package UrlShortenerApp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "URL_MAPPER")
public class URLMapper {

    @Id
    @Column(name = "url_id")
    private String urlId;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "compact_url")
    private String compactUrl;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "url_id")
    private List<URLAnalytics> urlAnalyticsList;

    public URLMapper(String urlId, String longUrl) {
        this.urlId = urlId;
        this.longUrl = longUrl;
    }

    public URLMapper() {
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getCompactUrl() {
        return compactUrl;
    }

    public void setCompactUrl(String compactUrl) {
        this.compactUrl = compactUrl;
    }

    public List<URLAnalytics> getUrlAnalyticsList() {
        return urlAnalyticsList;
    }

    public void setUrlAnalyticsList(List<URLAnalytics> urlAnalyticsList) {
        this.urlAnalyticsList = urlAnalyticsList;
    }

    @Override
    public String toString() {
        return "UrlMapper{" +
                "urlId='" + urlId + '\'' +
                ", longUrl='" + longUrl + '\'' +
                ", compactUrl='" + compactUrl + '\'' +
                ", urlAnalyticsList=" + urlAnalyticsList +
                '}';
    }
}
