package com.example.resource;

import com.example.dto.PaginatedResponse;
import com.example.model.Movie;
import com.example.service.MovieService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

/**
 * REST resource for movie-related endpoints.
 */
@Path("/api/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieService movieService;

    /**
     * Get all movies with pagination.
     *
     * @param page the page number (0-based, default 0)
     * @param size the page size (default 20)
     * @return a response containing movies and pagination metadata
     */
    @GET
    public PaginatedResponse<Movie> getAllMovies(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        return movieService.getAllMovies(page, size);
    }

    /**
     * Get a movie by its ID.
     *
     * @param id the movie ID
     * @return the movie if found, or 404 if not found
     */
    @GET
    @Path("/{id}")
    public Movie getMovieById(@PathParam("id") String id) {
        return movieService.getMovieById(id)
                .orElseThrow(() -> new NotFoundException("Movie with id " + id + " not found"));
    }

    /**
     * Search movies by title.
     *
     * @param title the title to search for
     * @return a response containing a list of matching movies
     */
    @GET
    @Path("/search/title")
    public List<Movie> searchByTitle(@QueryParam("q") String title) {
        return movieService.getMoviesByTitle(title);
    }

    /**
     * Search movies by year.
     *
     * @param year the year to search for
     * @return a response containing a list of matching movies
     */
    @GET
    @Path("/search/year/{year}")
    public List<Movie> searchByYear(@PathParam("year") int year) {
        return movieService.getMoviesByYear(year);
    }

    /**
     * Search movies by genre.
     *
     * @param genre the genre to search for
     * @return a response containing a list of matching movies
     */
    @GET
    @Path("/search/genre/{genre}")
    public List<Movie> searchByGenre(@PathParam("genre") String genre) {
        return movieService.getMoviesByGenre(genre);
    }
}
