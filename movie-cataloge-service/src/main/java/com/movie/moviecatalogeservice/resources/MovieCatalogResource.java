package com.movie.moviecatalogeservice.resources;

import com.movie.moviecatalogeservice.models.CatalogItem;
import com.movie.moviecatalogeservice.models.Movie;
import com.movie.moviecatalogeservice.models.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        RestTemplate restTemplate = new RestTemplate();
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 3)
        );

        //Get All Rated Movie IDS

        return ratings.stream().map( rating -> {
             Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());

        //For each movieId, call movie info service and get details

        //Put them all together.
        //return Collections.singletonList(new CatalogItem("Terminator", "Test", 4));
    }
}
