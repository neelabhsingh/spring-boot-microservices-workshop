package com.movie.moviecatalogeservice.resources;

import com.movie.moviecatalogeservice.models.CatalogItem;
import com.movie.moviecatalogeservice.models.Movie;
import com.movie.moviecatalogeservice.models.Rating;
import com.movie.moviecatalogeservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);
        //Get All Rated Movie IDS
        //For each movieId, call movie info service and get details
        return userRating.getUserRating().stream().map( rating -> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            //Put them all together.
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());




        //return Collections.singletonList(new CatalogItem("Terminator", "Test", 4));
                    /*
            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();
             */
    }
}
