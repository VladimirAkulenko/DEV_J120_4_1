/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_1;

/**
 *
 * @author USER
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookStorage {
    private static final BookStorage instanceBookStorage = new BookStorage();
    private List<BookInfo> bookInfoList = new ArrayList<>();
    private Set<String> internalBookCodeSet = new HashSet<>();

    private BookStorage(){
    }

    public void addBook (String internalBookCode, String isbn, String bookName,
                         String authors, int yearPublication){
        if (internalBookCodeSet.contains(internalBookCode))
            throw new IllegalArgumentException("Книга с таким внутрибиблиотечным кодом уже зарегистрирована.");
        bookInfoList.add(new BookInfo(internalBookCode, isbn,bookName, authors, yearPublication));
        internalBookCodeSet.add(internalBookCode);
    }

    public void removeBook(int index){
        BookInfo book = bookInfoList.get(index);
        internalBookCodeSet.remove(book.getInternalBookCode());
        bookInfoList.remove(index);
    }

    public int getBookCount(){
        return bookInfoList.size();
    }

    public BookInfo getBook(int index){
        return bookInfoList.get(index);
    }

    public static BookStorage getBookStorage(){
        return instanceBookStorage;
    }
    public List<BookInfo> getBookList(){
        return bookInfoList;
    }
}
