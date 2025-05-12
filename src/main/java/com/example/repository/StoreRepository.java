package com.example.repository;

import com.example.model.Store;
import io.quarkus.mongodb.panache.PanacheMongoRepositoryBase;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import io.quarkus.panache.common.Sort;

/**
 * Repository for accessing and manipulating Store documents in MongoDB.
 */
@ApplicationScoped
public class StoreRepository implements PanacheMongoRepositoryBase<Store, Integer> {

    /**
     * Find a Store by its ID.
     *
     * @param id the Store ID
     * @return an Optional containing the Store if found
     */
    public Optional<Store> findByIdOptional(Integer id) {
        return Optional.ofNullable(findById(id));
    }

    /**
     * Find all Stores.
     *
     * @return a list of all Stores
     */
    public long countStores() {
        return count();
    }

    /**
     * Find all Stores with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return a list of Stores for the requested page
     */
    public List<Store> findAllPaginated(int page, int size) {
        return findAll().page(Page.of(page, size)).list();
    }

    /**
     * Find all Stores with pagination and sorting.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @param sort the sort criteria
     * @return a list of Stores for the requested page
     */
    public List<Store> findAllPaginated(int page, int size, Sort sort) {
        return findAll(sort).page(Page.of(page, size)).list();
    }
}
