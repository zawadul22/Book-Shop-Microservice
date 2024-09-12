package com.bjit.book_service.service.implementation;

import com.bjit.book_service.entity.BookEntity;
import com.bjit.book_service.exception.NotFoundException;
import com.bjit.book_service.exception.UnsuccessfulException;
import com.bjit.book_service.model.BookModel;
import com.bjit.book_service.model.BookResponseModel;
import com.bjit.book_service.model.InventoryModel;
import com.bjit.book_service.repository.BookRepository;
import com.bjit.book_service.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final InventoryModel inventoryModel;
    private final BookResponseModel bookResponseModel;
    private static String endPoint = "http://localhost:8082/book-inventory";

    @Override
    public ResponseEntity<Object> bookList() {
        List<BookEntity> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<Object> create(BookModel bookModel) {
        BookEntity bookEntity = BookEntity.builder()
                .bookName(bookModel.getBookName())
                .authorName(bookModel.getAuthorName())
                .genre(bookModel.getGenre())
                .build();
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        inventoryModel.setBookId(savedBookEntity.getBookId());
        inventoryModel.setPrice(bookModel.getPrice());
        inventoryModel.setQuantity(bookModel.getQuantity());
        ResponseEntity<InventoryModel> responseEntity = restTemplate.postForEntity(endPoint + "/create", inventoryModel, InventoryModel.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(savedBookEntity, HttpStatus.CREATED);
        } else {
            //return new ResponseEntity<>(savedBookEntity, HttpStatus.BAD_REQUEST);
            throw new UnsuccessfulException("Book entry unsuccessful");
        }
    }

    @Override
    public ResponseEntity<Object> update(BookModel updatedBookModel) {
        Optional<BookEntity> bookEntity = bookRepository.findById(updatedBookModel.getBookID());
        if (bookEntity.isPresent()){
            BookEntity existingBook = bookEntity.get();
            existingBook.setBookName(updatedBookModel.getBookName());
            existingBook.setAuthorName(updatedBookModel.getAuthorName());
            existingBook.setGenre(updatedBookModel.getGenre());
            BookEntity savedUpdate = bookRepository.save(existingBook);

            inventoryModel.setBookId(savedUpdate.getBookId());
            inventoryModel.setPrice(updatedBookModel.getPrice());
            inventoryModel.setQuantity(updatedBookModel.getQuantity());

            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<InventoryModel> requestEntity = new HttpEntity<>(inventoryModel, headers);
            ResponseEntity<InventoryModel> responseEntity = restTemplate.exchange(
                    endPoint+"/update/book-id",
                    HttpMethod.PUT,  // Or the appropriate HTTP method
                    requestEntity,
                    InventoryModel.class  // The expected response object type
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK){
                bookResponseModel.setBookName(savedUpdate.getBookName());
                bookResponseModel.setAuthorName(savedUpdate.getAuthorName());
                bookResponseModel.setGenre(savedUpdate.getGenre());
                bookResponseModel.setPrice(Objects.requireNonNull(responseEntity.getBody()).getPrice());
                bookResponseModel.setQuantity(responseEntity.getBody().getQuantity());
                return new ResponseEntity<>(bookResponseModel, HttpStatus.OK);
            }
            else{
                //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                throw new NotFoundException("Book not found");
            }
        }
        else{
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            throw new UnsuccessfulException("Update unsuccessful");
        }
    }

    @Override
    public ResponseEntity<Object> delete(BookModel bookModel) {

        // Check if the book exists
        Optional<BookEntity> existingBookOptional = bookRepository.findByBookID(bookModel.getBookID());

        if (existingBookOptional.isEmpty()) {
            // Book not found, return an error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }

        // Retrieve the actual BookEntity object from the Optional
        BookEntity existingBook = existingBookOptional.get();

        // Delete the book from the database
        bookRepository.delete(existingBook);

        Map<String, Object> response = new HashMap<>();
        response.put("book", existingBook);
        response.put("message", "Book deleted successfully");

        // Return the response entity
        return ResponseEntity.ok(response);
        //return null;
    }

    @Override
    public ResponseEntity<Object> getBookByID(Long bookID) {
        Optional<BookEntity> bookEntity = bookRepository.findById(bookID);
        if (bookEntity.isPresent()){
            BookEntity existingBook = bookEntity.get();
            inventoryModel.setBookId(existingBook.getBookId());

            ResponseEntity<InventoryModel> responseEntity = restTemplate.getForEntity(endPoint + "/book-id/"+bookID, InventoryModel.class);
            if (responseEntity.getStatusCode()==HttpStatus.OK){
                bookResponseModel.setBookName(existingBook.getBookName());
                bookResponseModel.setAuthorName(existingBook.getAuthorName());
                bookResponseModel.setGenre(existingBook.getGenre());
                bookResponseModel.setPrice(Objects.requireNonNull(responseEntity.getBody()).getPrice());
                bookResponseModel.setQuantity(responseEntity.getBody().getQuantity());
            }
            return new ResponseEntity<>(bookResponseModel,HttpStatus.OK);
        }
        else{
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            throw new NotFoundException("Book not found");
        }
    }

    @Override
    public ResponseEntity<Object> buyBook(BookModel requestModel) {
        return null;
    }
}
