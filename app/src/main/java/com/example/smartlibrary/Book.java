package com.example.smartlibrary;


import android.net.Uri;
import android.widget.EditText;

public class Book {
    String bookId,bookName,bookAuthor,bookPublication,bookYear,bookQuantity,bookCategory,bookLanguage,profileImage;

    public Book(String bookId, String bookName, String bookAuthor, String bookPublication, String bookYear, String bookQuantity, String bookCategory, String bookDepartment, String bookLanguage, String profileImage) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPublication = bookPublication;
        this.bookYear = bookYear;
        this.bookQuantity = bookQuantity;
        this.bookCategory = bookCategory;

        this.bookLanguage = bookLanguage;
        this.profileImage = profileImage;
    }

    public Book(EditText book_id, EditText book_name, EditText book_author, EditText book_publication, EditText book_year, EditText book_quantity, EditText book_category, EditText book_language, Uri uri) {

    }

    public String getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookPublication() {
        return bookPublication;
    }

    public String getBookYear() {
        return bookYear;
    }

    public String getBookQuantity() {
        return bookQuantity;
    }

    public String getBookCategory() {
        return bookCategory;
    }



    public String getBookLanguage() {
        return bookLanguage;
    }

    public String getProfileImage() {
        return profileImage;
    }
}