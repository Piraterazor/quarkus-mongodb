package com.example.service;

import com.example.dto.PaginatedResponse;
import com.example.model.Store;
import com.example.repository.StoreRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

/**
 * Service class for Store-related business logic.
 */
@ApplicationScoped
public class StoreService {

    @Inject
    StoreRepository storeRepository;

    /**
     * Get a Store by its ID.
     *
     * @param id the Store ID as a string
     * @return an Optional containing the Store if found
     */
    public Optional<Store> getStoreById(Integer id) {
        try {
            return storeRepository.findByIdOptional(id);
        } catch (IllegalArgumentException e) {
            // Handle invalid ObjectId format
            return Optional.empty();
        }
    }

    /**
     * Get all Stores with pagination.
     *
     * @param page the page number (0-based)
     * @param size the page size
     * @return a paginated response containing Stores and pagination metadata
     */
    public PaginatedResponse<Store> getAllStores(int page, int size) {
        List<Store> stores = storeRepository.findAllPaginated(page, size, Sort.by("year"));
        long totalCount = storeRepository.countStores();
        int totalPages = size > 0 ? (int) Math.ceil((double) totalCount / size) : 0;

        return PaginatedResponse.<Store>builder()
                .data(stores)
                .totalCount(totalCount)
                .page(page)
                .pageSize(size)
                .totalPages(totalPages)
                .build();
    }
}
