/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_1;

import java.time.LocalDate;

/**
 *
 * @author USER
 */
public class BookInfo {
    private final String internalBookCode;
    private String isbn;
    private String bookName;
    private String authors;
    private Integer yearPublication;

    public BookInfo(String internalBookCode, String isbn, String bookName,
                    String authors, Integer yearPublication) throws IllegalArgumentException {
        if (internalBookCode != null && !internalBookCode.trim().isEmpty())
            this.internalBookCode = internalBookCode;
        else
            throw new IllegalArgumentException();
        setIsbn(isbn);
        setBookName(bookName);
        setAuthors(authors);
        setYearPublication(yearPublication);
    }

    public String getInternalBookCode() {
        return internalBookCode;
    }

    public String getIsbn() {
        return isbn;
    }

    public final void setIsbn(String isbn){
        if (isbn != null && (isbn.equals("") || (isbn.trim().length()>=14 && isbn.trim().length()<=17))){
            char[] num = isbn.trim().toCharArray();
            for (char c: num){
                if (Character.isLetter(c))
                    throw new IllegalArgumentException();
            }
            this.isbn = isbn.trim();
        }
        else
            throw new IllegalArgumentException();
    }

    public String getBookName() {
        return bookName;
    }

    public final void setBookName (String bookName){
        if (bookName != null && !bookName.trim().isEmpty())
            this.bookName = bookName.trim();
        else
            throw new IllegalArgumentException();
    }

    public String getAuthors() {
        return authors;
    }

    public final void setAuthors (String authors){
        if (authors != null)
            this.authors = authors;
        else
            throw new IllegalArgumentException();
    }

    public Integer getYearPublication() {
        return yearPublication;
    }


    public void setYearPublication(Integer yearPublication) {
        if (yearPublication > 1475 && yearPublication<= LocalDate.now().getYear())
            this.yearPublication = yearPublication;
        else
            throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        final String cs = "\u0009";
        return internalBookCode + cs + isbn + cs + bookName + cs + authors + cs + yearPublication;
    }
    
}
