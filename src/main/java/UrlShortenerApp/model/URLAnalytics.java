package UrlShortenerApp.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "URL_ANALYTICS")
public class URLAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "browser_name")
    private String browserName;

    @Column(name = "url_id")
    private String urlId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ts")
    private Date ts;


    public URLAnalytics(long id, String browserName, Date ts) {
        this.id = id;
        this.browserName = browserName;
        this.ts = ts;
    }

    public URLAnalytics() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public Date getTimestamp() {
        return ts;
    }

    public void setTimestamp(Date timestamp) {
        this.ts = timestamp;
    }

    @Override
    public String toString() {
        return "UrlAnalytics{" +
                "id=" + id +
                ", browserName='" + browserName + '\'' +
                ", ts=" + ts +
                '}';
    }
}
