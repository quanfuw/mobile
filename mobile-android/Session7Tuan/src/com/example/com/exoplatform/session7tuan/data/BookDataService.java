package com.example.com.exoplatform.session7tuan.data;

import java.util.List;

public interface BookDataService {
	public Book getBook(String id);
	public List<Book> getAllBook();
	public Book addBook(Book b) ;
	public Book updateBook(Book b);
	public int  deleteBook(String bookId);
	public int  countBookData();
	

}
