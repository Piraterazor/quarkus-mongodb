package com.example.repository;

import com.example.model.Movie;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import io.quarkus.panache.common.Sort;

/**
 * Repository for accessing and manipulating Movie documents in MongoDB.
 */
@ApplicationScoped
public class MovieRepository implements PanacheMongoRepository<Movie> {

    /**
     * Find a movie by its ID.
     *
     * @param id the movie ID
     * @return an Optional containing the movie if found
     */
    public Optional<Movie> findByIdOptional(ObjectId id) {
        return Optional.ofNullable(findById(id));
    }

    /**
     * Find movies by title (case-insensitive partial match).
     *
     * @param title the title to search for
     * @return a list of matching movies
     */
    public List<Movie> findByTitle(String title) {
        return find("{'title': {$regex: ?1, $options: 'i'}}", title).list();
    }

    /**
     * Find movies by year.
     *
     * @param year the year to search for
     * @return a list of matching movies
     */
    public List<Movie> findByYear(int year) {
        return list("year", year);
    }

    /**
     * Find movies by genre.
     *
     * @param genre the genre to search for
     * @return a list of matching movies
     */
    public List<Movie> findByGenre(String genre) {
        return find("{'genres': ?1}", genre).list();
    }

    /**
     * Find all movies with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return a list of movies for the requested page
     */
    public List<Movie> findAllPaginated(int page, int size) {
        return findAll().page(Page.of(page, size)).list();
    }

    /**
     * Get the total count of movies in the collection.
     *
     * @return the total count of movies
     */
    public long countMovies() {
        return count();
    }

    /**
     * Find all movies with pagination and sorting.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @param sort the sort criteria
     * @return a list of movies for the requested page
     */
    public List<Movie> findAllPaginated(int page, int size, Sort sort) {
        return findAll(sort).page(Page.of(page, size)).list();
    }
}
