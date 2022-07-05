/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_1;

/**
 *
 * @author USER
 */
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class BookStorageTableModel implements TableModel {
    private static final  String[] COLUMN_HEADERS = new String[]{
            "Код книги", 
            "ISBN", 
            "Название книги", 
            "Авторы", 
            "Год издания"
    };
    private final Set<TableModelListener> modelListeners = new HashSet<>();
    @Override
    public int getRowCount() {
        return BookStorage.getBookStorage().getBookCount();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_HEADERS.length;
    }

    @Override
    public String  getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                return String.class;
            case 5:
                return Integer.class;
        }
        throw new IllegalArgumentException("Нет столбца с этим номером.");

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BookInfo book = getBook(rowIndex);
        switch (columnIndex){
            case 0:
                return book.getInternalBookCode();
            case 1:
                return book.getIsbn();
            case 2:
                return book.getBookName();
            case 3:
                return book.getAuthors();
            case 4:
                return book.getYearPublication();
        }
        throw new IllegalArgumentException("Нет столбца с этим номером.");

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        modelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        modelListeners.remove(l);
    }

    public BookInfo getBook(int rowIndex){
        return BookStorage.getBookStorage().getBook(rowIndex);
    }

    public void addBook(String internalBookCode, String isbn, String bookName, String authors, int yearPublication){
        BookStorage.getBookStorage().addBook(internalBookCode, isbn,bookName, authors, yearPublication);
        int rowNum = BookStorage.getBookStorage().getBookCount() -1;
        fireTableModelEvent(rowNum, TableModelEvent.INSERT);
    }

    public void bookChanged(int index){
        fireTableModelEvent(index, TableModelEvent.UPDATE);
    }

    public void removeBook(int index){
        BookStorage.getBookStorage().removeBook(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
    }

    private void fireTableModelEvent(int rowNum, int evtType) {
        TableModelEvent tableModelEvent = new TableModelEvent(this,rowNum, rowNum, TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener tableModelListener: modelListeners){
            tableModelListener.tableChanged(tableModelEvent);
        }
    }

    public static void setJTableColumnsWidth (JTable table, int tablePreferredWidth, double... percentages){
        double total = 0;
        for (int i =0; i< table.getColumnModel().getColumnCount(); i++){
            total += percentages[i];
        }
        for (int i =0; i< table.getColumnModel().getColumnCount(); i++){
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int) (tablePreferredWidth*(percentages[i]/total)));
        }
    }


    public static void alignCenter(JTable table, int column) {
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(column).setCellRenderer(cellRenderer);
    }
}
