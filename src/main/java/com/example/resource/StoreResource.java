package com.example.resource;

import com.example.dto.PaginatedResponse;
import com.example.model.Store;
import com.example.service.StoreService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.NotFoundException;

/**
 * REST resource for Store-related endpoints.
 */
@Path("/api/stores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StoreResource {

    @Inject
    StoreService storeService;

    /**
     * Get all Stores with pagination.
     *
     * @param page the page number (0-based, default 0)
     * @param size the page size (default 20)
     * @return a response containing Stores and pagination metadata
     */
    @GET
    public PaginatedResponse<Store> getAllStores(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        return storeService.getAllStores(page, size);
    }

    /**
     * Get a Store by its ID.
     *
     * @param id the Store ID
     * @return the Store if found, or 404 if not found
     */
    @GET
    @Path("/{id}")
    public Store getStoreById(@PathParam("id") Integer id) {
        return storeService.getStoreById(id)
                .orElseThrow(() -> new NotFoundException("Store with id " + id + " not found"));
    }
}
