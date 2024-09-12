package com.bjit.book_inventory.service;

import com.bjit.book_inventory.model.BookInventoryModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InventoryService {
    ResponseEntity<Object> update(BookInventoryModel updatedModel);

//    ResponseEntity<Object> getBookDetails(BookInventoryModel requestModel);

    //ResponseEntity<Object> getBookDetailsList(List<BookInventoryModel> inventoryModels);

    ResponseEntity<Object> deleteBookDetails(BookInventoryModel requestModel);

    ResponseEntity<Object> getDetailById(Long bookID);

    ResponseEntity<Object> create(BookInventoryModel bookInventoryModel);

    List<BookInventoryModel> getAll(List<BookInventoryModel> list);
}
