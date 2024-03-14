package input;

import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.EOFException;
import java.io.Serializable;

public class Book implements Serializable {
/**
 * Books class for serializable
 */
	String title;
	String name;
	String author;
	String price;
	String isbn;
	String genre;
	String year;

	public Book(String title, String name, String author, String price, String isbn, String genre, String year) {
		this.title = title;
		this.name = title;
		this.author = author;
		this.price = price;
		this.isbn = isbn;
		this.genre = genre;
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public boolean equals(Object x) {
		if (this == x) {
			return true;
		}

		if (x == null && x.getClass() != this.getClass()) {
			return false;
		} else {
			Book Books = (Book) x;
			return this.title == Books.title && this.author == Books.author && this.price == Books.price
					&& this.isbn == Books.isbn && this.genre == Books.genre && this.year == Books.year;
		}
	}

	public String toString() {
		return name + ", " + author + ", " + price + ", " + isbn + ", " + genre + ", " + year;
	}
}
