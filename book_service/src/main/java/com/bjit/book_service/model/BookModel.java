package com.bjit.book_service.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookModel {
	private Long bookID;
	@NotEmpty
	@Size(min=1, message = "The book name should contain at least one character")
	private String bookName;
	private String authorName;
	private String genre;
	@NotNull
	private Double price;
	@NotNull
	private Integer quantity;
}