package com.example.com.exoplatform.session7tuan.data;

public interface BookDataService {
	public Book addBook(Book b) ;
	public Book updateBook(Book b);
	public int  deleteBook(String bookId);
	public int  countBookData();
	

}
