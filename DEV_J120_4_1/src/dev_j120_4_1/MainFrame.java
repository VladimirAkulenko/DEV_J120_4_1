/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_1;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author USER
 */
public class MainFrame extends JFrame{
    private final BookStorageTableModel bookStorageTableModel = new BookStorageTableModel();
    private final File file = new File();
    private final JTable booksTable = new JTable();
    private final BookDialog bookDialog = new BookDialog(this);

    public MainFrame(){
        super("Учёт книг библиотеки");
        initMenu();
        initLayout();
        setBounds(500,150,600,400);
        startApp();
        closeApp();
    }

    private void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Меню");
        menu.setMnemonic('O');
        menuBar.add(menu);

        addMenuItemTO(menu, "Добавить", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK), e -> addBook());

        addMenuItemTO(menu, "Редактировать", 'C',
                KeyStroke.getKeyStroke('C',InputEvent.ALT_DOWN_MASK), e -> changeBook());

        addMenuItemTO(menu, "Удалить", 'D',
                KeyStroke.getKeyStroke('D',InputEvent.ALT_DOWN_MASK), e -> deleteBook());

        setJMenuBar(menuBar);
    }

    private void addMenuItemTO (JMenu parent, String text, char mnemonic, KeyStroke accelerator,
                                ActionListener actionListener){
        JMenuItem menuItem = new JMenuItem(text, mnemonic);
        menuItem.setAccelerator(accelerator);
        menuItem.addActionListener(actionListener);
        parent.add(menuItem);
    }

    private void initLayout(){
        booksTable.setModel(bookStorageTableModel);
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        booksTable.setIntercellSpacing(new Dimension(5,5));
        booksTable.setRowHeight(27);
        BookStorageTableModel.alignCenter(booksTable,0);
        BookStorageTableModel.alignCenter(booksTable,1);
        BookStorageTableModel.alignCenter(booksTable,4);
        BookStorageTableModel.setJTableColumnsWidth(booksTable, 800,31, 38, 60, 60, 31);
        add(booksTable.getTableHeader(), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addBook() {
        bookDialog.prepareForAdd();
        while (bookDialog.showModal()){
            try {
                bookStorageTableModel.addBook(bookDialog.getInternalBookCode(),bookDialog.getIsbn(),
                        bookDialog.getBookName(), bookDialog.getAuthors(), bookDialog.getYearPublication());
                return;
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,"Регистрационная форма заполнена неправильно.",
                        "Ошибка регистрации книги",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeBook() {
        int seldRow = booksTable.getSelectedRow();
        if (seldRow == -1)
            return;

        BookInfo bookInfo = bookStorageTableModel.getBook(seldRow);
        bookDialog.prepareForChange(bookInfo);
        if (bookDialog.showModal()){
            try {
                bookInfo.setIsbn(bookDialog.getIsbn());
                bookInfo.setBookName(bookDialog.getBookName());
                bookInfo.setAuthors(bookDialog.getAuthors());
                bookInfo.setYearPublication(bookDialog.getYearPublication());
                bookStorageTableModel.bookChanged(seldRow);
            }catch (IllegalArgumentException ie){
                JOptionPane.showMessageDialog(this, "Данные были отредактированы неправильно.",
                        "Ошибка измения данных книги",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteBook() {
        int seldRow = booksTable.getSelectedRow();
        if (seldRow == -1)
            return;

        BookInfo bookInfo = bookStorageTableModel.getBook(seldRow);
        if (JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить книгу\n" +
                "с внутренним кодом книги " + bookInfo.getInternalBookCode() + "?", "Подтверждение удаления",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            bookStorageTableModel.removeBook(seldRow);
        }
    }

    private void closeApp(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane.showConfirmDialog(e.getWindow(),"Закрыть приложение?",
                        "Подтверждение закрытия", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (n == 0){
                    List<BookInfo> list = BookStorage.getBookStorage().getBookList();
                    try {
                        file.saveBooksFile(list);
                    }catch (IOException ex){
                        JOptionPane.showMessageDialog(findLatestWindow(),
                                "Произошла ошибка при записи файла. Данные таблицы могут быть потеряны.",
                                "Ошибка. Приложение будет остановлено.",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    e.getWindow().setVisible(false);
                    System.exit(0);
                }
                else
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            }
        });
    }

    private void startApp(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                List<String> sourceList = new ArrayList<>();
                try {
                    sourceList = file.extractBooksFromFile();
                } catch (IOException ex){
                    JOptionPane.showMessageDialog(findLatestWindow(), "Файл отсутствует или чтение из него невозможно.",
                            "Ошибка. Произошла ошибка при чтении файла.", JOptionPane.ERROR_MESSAGE);
                }
                sourceList.forEach(x -> {
                    String[] temp = x.split("\u0009");
                    bookStorageTableModel.addBook(temp[0], temp[1], temp[2], temp[3], Integer.parseInt(temp[4]));

                });
            }
        });
    }

    static Window findLatestWindow(){
        Window result = null;
        for (Window window: Window.getWindows()){
            if (window.isVisible()){
                result = window;
            }
        }
        return result;
    }
}
