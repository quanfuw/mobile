package com.example.com.exoplatform.session7tuan.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

	private String id;
	private String name;
	private String title;
	private String category;
	private String author;
	private int    pages;
	private String preFaceUrl;
	
	
	public Book() {
		
		
	}
	
	public Book(String name, String author) {
		
		this.name = name ;
		this.author = author;
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
	
	public Book(Parcel source) {
		id = source.readString();
		name = source.readString();
		title = source.readString();
		category = source.readString();
		author = source.readString();
		pages = source.readInt();
		preFaceUrl = source.readString();
		
		
		 
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

	
	
	public static final Creator<Book> CREATOR = new Creator<Book>() {

		@Override
		public Book createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Book(source);
		}

		@Override
		public Book[] newArray(int size) {
			// TODO Auto-generated method stub
			return new  Book[size]; 
		}
		
		
	}; 
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id) ;
		dest.writeString(name) ;
		dest.writeString(title) ;
		dest.writeString(author) ;
		dest.writeString(category) ;
		dest.writeInt(pages) ;
		dest.writeString(preFaceUrl);
		
		
		
	}
	
	

}
