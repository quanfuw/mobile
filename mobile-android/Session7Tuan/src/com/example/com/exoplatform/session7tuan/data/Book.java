package com.example.com.exoplatform.session7tuan.data;

public class Book {

	private String id;
	private String name;
	private String title;
	private String category;
	private String author;
	private int    pages;
	private String preFaceUrl;
	
	
	public Book() {
		
		
	}
	
	public Book(String id, String name, String title, String category, String author, int page, String preFaceUrl) {
		
		this.id = id;
		this.name = name ;
		this.title = title; 
		this.category = category;
		this.author = author;
		this.pages = page ;
		this.preFaceUrl = preFaceUrl;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public String getPreFaceUrl() {
		return preFaceUrl;
	}
	public void setPreFaceUrl(String preFaceUrl) {
		this.preFaceUrl = preFaceUrl;
	}
	
	

}
