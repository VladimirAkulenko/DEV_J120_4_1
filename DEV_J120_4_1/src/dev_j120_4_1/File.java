/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dev_j120_4_1;

/**
 *
 * @author USER
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class File {
    private static final String URL = "books.dat";

    public void saveBooksFile(List list) throws IOException{
        Path path = Paths.get(URL);
        if (!path.isAbsolute())
            path = path.toAbsolutePath();
        Path dir = path.getParent();
        if (!Files.isDirectory(dir))
            Files.createDirectories(dir);
        if (!Files.exists(path))
            Files.createFile(path);
        List<String> stringList = new ArrayList<>();
        list.forEach((s) -> {
            stringList.add(s.toString());
        });
        Files.write(path, stringList);
    }

    public List<String> extractBooksFromFile() throws IOException{
        Path path = Paths.get(URL);
        if (!path.isAbsolute())
            path = path.toAbsolutePath();
        if (!Files.exists(path))
            throw new IOException("Исходный файл данных не найден. База книг пуста.");
        ArrayList<String> sourceList = new ArrayList<>(Files.readAllLines(path));
        return sourceList;
    }
}
