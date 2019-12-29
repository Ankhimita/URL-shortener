package UrlShortenerApp.repository;

import UrlShortenerApp.model.URLMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface URLMapperRepository extends JpaRepository<URLMapper, String> {

    @Query(value= "select u.urlId from URLMapper u where u.longUrl = :longUrl")
    String findUrlKeyByLongUrl(@Param(value = "longUrl") String longUrl);

}
