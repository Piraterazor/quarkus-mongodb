package com.example.service;

import com.example.dto.PaginatedResponse;
import com.example.model.Movie;
import com.example.repository.MovieRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

/**
 * Service class for movie-related business logic.
 */
@ApplicationScoped
public class MovieService {

    @Inject
    MovieRepository movieRepository;

    /**
     * Get a movie by its ID.
     *
     * @param id the movie ID as a string
     * @return an Optional containing the movie if found
     */
    public Optional<Movie> getMovieById(String id) {
        try {
            return movieRepository.findByIdOptional(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            // Handle invalid ObjectId format
            return Optional.empty();
        }
    }

    /**
     * Get movies by title.
     *
     * @param title the title to search for
     * @return a list of matching movies
     */
    public List<Movie> getMoviesByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    /**
     * Get movies by year.
     *
     * @param year the year to search for
     * @return a list of matching movies
     */
    public List<Movie> getMoviesByYear(int year) {
        return movieRepository.findByYear(year);
    }

    /**
     * Get movies by genre.
     *
     * @param genre the genre to search for
     * @return a list of matching movies
     */
    public List<Movie> getMoviesByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    /**
     * Get all movies with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return a paginated response containing movies and pagination metadata
     */
    public PaginatedResponse<Movie> getAllMovies(int page, int size) {
        List<Movie> movies = movieRepository.findAllPaginated(page, size, Sort.by("year"));
        long totalCount = movieRepository.countMovies();
        int totalPages = size > 0 ? (int) Math.ceil((double) totalCount / size) : 0;

        return PaginatedResponse.<Movie>builder()
                .data(movies)
                .totalCount(totalCount)
                .page(page)
                .pageSize(size)
                .totalPages(totalPages)
                .build();
    }
}
