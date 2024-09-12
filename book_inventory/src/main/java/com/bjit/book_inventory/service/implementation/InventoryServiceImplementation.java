package com.bjit.book_inventory.service.implementation;

import com.bjit.book_inventory.entity.BookInventoryEntity;
import com.bjit.book_inventory.mapper.BookMapper;
import com.bjit.book_inventory.model.BookInventoryModel;
import com.bjit.book_inventory.repository.InventoryRepository;
import com.bjit.book_inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImplementation implements InventoryService {
    //@Autowired
    public final InventoryRepository inventoryRepository;

//    @Override
//    public ResponseEntity<Object> update(BookInventoryModel requestModel) {
//        Long bookId = requestModel.getBookId();
//
//        // Check if the book inventory exists
//        Optional<BookInventoryEntity> existingInventoryOptional = inventoryRepository.findByBookId(bookId);
//
//        if (existingInventoryOptional.isPresent()) {
//            // Book inventory exists, update the data
//            BookInventoryEntity existingInventory = existingInventoryOptional.get();
//            existingInventory.setPrice(requestModel.getPrice());
//            existingInventory.setQuantity(requestModel.getQuantity());
//
//            // Save the updated book inventory in the database
//            inventoryRepository.save(existingInventory);
//
//            // Return a success response
//            return ResponseEntity.ok(existingInventory);
//        } else {
//            // Book inventory does not exist, create new data
//            BookInventoryEntity newInventory = new BookInventoryEntity();
//            newInventory.setBookId(bookId);
//            newInventory.setPrice(requestModel.getPrice());
//            newInventory.setQuantity(requestModel.getQuantity());
//
//            // Save the new book inventory in the database
//            inventoryRepository.save(newInventory);
//
//            // Return a success response
//            return ResponseEntity.ok(newInventory);
//        }
//    }



//    @Override

//    public ResponseEntity<Object> getBookDetails(BookInventoryModel requestModel) {
//        Long bookId = requestModel.getBookId();
//
//        // Check if the book inventory exists
//        Optional<BookInventoryEntity> inventoryOptional = inventoryRepository.findByBookId(bookId);
//
//        if (inventoryOptional.isEmpty()) {
//            // Book inventory not found, return an error response
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book inventory not found");
//        }
//
//        // Retrieve the actual BookInventoryEntity object from the Optional
//        BookInventoryEntity inventory = inventoryOptional.get();
//
//        // Create a response model to hold the price and quantity information
//        BookInventoryModel responseModel = new BookInventoryModel();
//        responseModel.setBookId(inventory.getBookId());
//        responseModel.setPrice(inventory.getPrice());
//        responseModel.setQuantity(inventory.getQuantity());
//
//        // Return the response model as the response
//        return ResponseEntity.ok(responseModel);
//    }

    @Override
    public ResponseEntity<Object> update(BookInventoryModel updatedModel) {
        Optional<BookInventoryEntity> bookInventoryEntity = inventoryRepository.findByBookId(updatedModel.getBookId());
        if (bookInventoryEntity.isPresent()){
            BookInventoryEntity existingBook = bookInventoryEntity.get();
            existingBook.setBookId(updatedModel.getBookId());
            existingBook.setPrice(updatedModel.getPrice());
            existingBook.setQuantity(updatedModel.getQuantity());
            BookInventoryEntity saveEntity = inventoryRepository.save(existingBook);
            BookInventoryModel bookMapper = BookMapper.mapToBookModel(saveEntity);
            return new ResponseEntity<>(bookMapper, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }

//    @Override
//    public ResponseEntity<Object> getBookDetailsList(List<BookInventoryModel> inventoryModels) {
//        // Extract the bookIds from the request models
//        List<Long> bookIds = inventoryModels.stream()
//                .map(BookInventoryModel::getBookId)
//                .collect(Collectors.toList());
//
//        // Check if the book inventories exist for the provided book IDs
//        List<BookInventoryEntity> inventories = inventoryRepository.findAllByBookId();
//
//        if (inventories.isEmpty()) {
//            // No book inventories found, return an error response
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No book inventories found");
//        }
//
//        // Create a list to hold the response models
//        List<BookInventoryModel> responseModels = new ArrayList<>();
//
//        // Iterate over the book inventories and map the price and quantity information to response models
//        for (BookInventoryEntity inventory : inventories) {
//            BookInventoryModel responseModel = new BookInventoryModel();
//            responseModel.setBookId(inventory.getBookId());
//            responseModel.setPrice(inventory.getPrice());
//            responseModel.setQuantity(inventory.getQuantity());
//            responseModels.add(responseModel);
//        }
//
//        // Return the list of response models as the response
//        return ResponseEntity.ok(responseModels);
//    }


    @Override
    public ResponseEntity<Object> deleteBookDetails(BookInventoryModel requestModel) {
        Long bookId = requestModel.getBookId();

        // Check if the book inventory exists
        Optional<BookInventoryEntity> inventoryOptional = inventoryRepository.findByBookId(bookId);

        if (inventoryOptional.isEmpty()) {
            // Book inventory not found, return an error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book inventory not found");
        }

        // Retrieve the actual BookInventoryEntity object from the Optional
        BookInventoryEntity inventory = inventoryOptional.get();

        // Delete the book inventory from the database
        inventoryRepository.delete(inventory);

        Map<String, Object> response = new HashMap<>();
        response.put("book", inventory);
        response.put("message", "Book inventory deleted successfully");

        // Return the response entity
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> getDetailById(Long bookID) {
        Optional<BookInventoryEntity> inventoryEntity = inventoryRepository.getBookByBookId(bookID);
        if (inventoryEntity.isPresent()){
            BookInventoryEntity existingDetail = inventoryEntity.get();
            return new ResponseEntity<>(existingDetail, HttpStatus.OK);
        }
        return null;
    }

    @Override
    public ResponseEntity<Object> create(BookInventoryModel bookInventoryModel) {
        Optional<BookInventoryEntity> bookInventoryEntity = inventoryRepository.findByBookId(bookInventoryModel.getBookId());
        if(bookInventoryEntity.isPresent()){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        } else {
            BookInventoryEntity inventoryEntity = BookInventoryEntity.builder()
                    .bookId(bookInventoryModel.getBookId())
                    .price(bookInventoryModel.getPrice())
                    .quantity(bookInventoryModel.getQuantity())
                    .build();
            BookInventoryEntity saveEntity = inventoryRepository.save(inventoryEntity);
            BookInventoryModel bookMapper = BookMapper.mapToBookModel(saveEntity);
            return new ResponseEntity<>(bookMapper, HttpStatus.OK);
        }

    }

    @Override
    public List<BookInventoryModel> getAll(List<BookInventoryModel> list) {
        return null;
    }
}
