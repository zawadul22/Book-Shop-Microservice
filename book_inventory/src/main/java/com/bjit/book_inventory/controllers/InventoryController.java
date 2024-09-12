package com.bjit.book_inventory.controllers;

import com.bjit.book_inventory.entity.BookInventoryEntity;
import com.bjit.book_inventory.model.BookInventoryModel;
import com.bjit.book_inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public List<BookInventoryModel> getAll(@RequestBody List<BookInventoryModel> list){
        return inventoryService.getAll(list);
    }
    @PostMapping("/create")
    public ResponseEntity<Object> create (@RequestBody BookInventoryModel bookInventoryModel){
        return inventoryService.create(bookInventoryModel);
    }
    @PutMapping("/update/book-id")
    public ResponseEntity<Object> update (@RequestBody BookInventoryModel updatedModel){
        return inventoryService.update(updatedModel);
    }

    @GetMapping("/book-id/{bookId}")
       public ResponseEntity<Object> getDetailById (@PathVariable ("bookID") Long bookID){
        return inventoryService.getDetailById(bookID);
    }
//    public ResponseEntity<Object> getBookDetails(@RequestBody BookInventoryModel requestModel){
//        return inventoryService.getBookDetails(requestModel);
//    }

//    @GetMapping()
//    public ResponseEntity<Object> getBookDetailsList(@RequestBody List<BookInventoryModel> inventoryModels){
//        return inventoryService.getBookDetailsList(inventoryModels);
//    }

    @DeleteMapping("/delete/book-id")
    public ResponseEntity<Object> deleteBookDetails(@RequestBody BookInventoryModel requestModel){
        return inventoryService.deleteBookDetails(requestModel);
    }
    
}
