/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author USER
 */
public class BookDialog extends JDialog{
    private final JTextField internalBookCodeField;
    private final JTextField isbnField;
    private final JTextField bookNameField;
    private final JTextField authorsField;
    private final JTextField yearPublicationField;
    private boolean okPressed;

    public BookDialog(JFrame owner){
        super(owner, true);

        internalBookCodeField = new JTextField();
        isbnField = new JTextField();
        bookNameField = new JTextField();
        authorsField = new JTextField();
        yearPublicationField = new JTextField();

        initLayout();

        setResizable(false);
    }

    private void initLayout() {
        initControls();
        initOkCancelButtons();
    }

        private void initControls() {
        JPanel controlsPane = new JPanel(null);
        controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));
        controlsPane.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));

        JPanel panel1 = new JPanel();
        createPanelProperties(panel1);
        JLabel label1 = new JLabel("Внутрибиблиотечный код книги:");
        label1.setLabelFor(internalBookCodeField);
        panel1.add(Box.createRigidArea(new Dimension(2, 20)));
        panel1.add(label1);
        panel1.add(Box.createRigidArea(new Dimension(5, 20)));
        panel1.add(internalBookCodeField);
        panel1.add(Box.createRigidArea(new Dimension(280,20)));
        controlsPane.add(panel1);

        JPanel panel2 = new JPanel();
        createPanelProperties(panel2);
        JLabel label2 = new JLabel("ISBN:");
        label2.setLabelFor(isbnField);
        panel2.add(Box.createRigidArea(new Dimension(80, 20)));
        panel2.add(label2);
        panel2.add(Box.createRigidArea(new Dimension(5, 20)));
        panel2.add(isbnField);
        panel2.add(Box.createRigidArea(new Dimension(280,20)));
        controlsPane.add(panel2);

        JPanel panel3 = new JPanel();
        createPanelProperties(panel3);
        JLabel label3 = new JLabel("Название книги:");
        label3.setLabelFor(bookNameField);
        panel3.add(Box.createRigidArea(new Dimension(2, 20)));
        panel3.add(label3);
        panel3.add(Box.createRigidArea(new Dimension(20,20)));
        panel3.add(bookNameField);
        controlsPane.add(panel3);

        JPanel panel4 = new JPanel();
        createPanelProperties(panel4);
        JLabel label4 = new JLabel("Авторы:");
        label4.setLabelFor(authorsField);
        panel4.add(Box.createRigidArea(new Dimension(2, 20)));
        panel4.add(label4);
        panel4.add(Box.createRigidArea(new Dimension(65,20)));
        panel4.add(authorsField);
        controlsPane.add(panel4);

        JPanel panel5 = new JPanel();
        createPanelProperties(panel5);
        JLabel label5 = new JLabel("Год издания:");
        label5.setLabelFor(yearPublicationField);
        panel5.add(label5);
        panel5.add(Box.createRigidArea(new Dimension(35, 20)));
        yearPublicationField.setPreferredSize(new Dimension(40,20));
        panel5.add(yearPublicationField);
        panel5.add(Box.createRigidArea(new Dimension(470,20)));
        controlsPane.add(panel5);

        add(controlsPane, BorderLayout.CENTER);
    }

    private void initOkCancelButtons() {
        JPanel buttonsPanel = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            okPressed = true;
            setVisible(false);
        });
        okButton.setDefaultCapable(true);
        buttonsPanel.add(okButton);

        Action cancelDialogAction = new AbstractAction("Отмена") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };
        JButton cancelButton = new JButton(cancelDialogAction);
        buttonsPanel.add(cancelButton);

        add(buttonsPanel, BorderLayout.SOUTH);

        final String esc = "escape";
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), esc);
        getRootPane().getActionMap().put(esc,cancelDialogAction);
    }

    public boolean showModal(){
        pack();
        setLocationRelativeTo(getOwner());
        if (internalBookCodeField.isEnabled())
            internalBookCodeField.requestFocusInWindow();
        else
            bookNameField.requestFocusInWindow();
        okPressed = false;
        setVisible(true);
        return okPressed;
    }

    public void prepareForAdd(){
        setTitle("Регистрация новой книги");

        internalBookCodeField.setText("");
        isbnField.setText("");
        bookNameField.setText("");
        authorsField.setText("");
        yearPublicationField.setText("");

        internalBookCodeField.setEnabled(true);
    }

    public void prepareForChange (BookInfo bookInfo){
        setTitle("Изменение свойств клиента");

        internalBookCodeField.setText(bookInfo.getInternalBookCode());
        isbnField.setText(bookInfo.getIsbn());
        bookNameField.setText(bookInfo.getBookName());
        authorsField.setText(bookInfo.getAuthors());
        yearPublicationField.setText(bookInfo.getYearPublication().toString());

        internalBookCodeField.setEnabled(false);
    }

    public String getInternalBookCode() {
        return internalBookCodeField.getText();
    }

    public String getIsbn() {
        return isbnField.getText();
    }

    public String getBookName() {
        return bookNameField.getText();
    }

    public String getAuthors() {
        return authorsField.getText();
    }

    public Integer getYearPublication() {
        Integer year = 0;
        try {
            year = Integer.valueOf(yearPublicationField.getText());
        } catch (NumberFormatException ne){
            JOptionPane.showMessageDialog(MainFrame.findLatestWindow(), "Необходимо ввести год издания.",
                    "Ошибка при вводе года публикации.", JOptionPane.ERROR_MESSAGE);
        }
        return year;
    }

    private void createPanelProperties(JComponent panel) {
        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5,0));
    }
    
}
