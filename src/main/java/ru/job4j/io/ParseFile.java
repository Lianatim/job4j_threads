package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {

    private final static int CONDITION_UN = 0x80;
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) {
        String output = "";
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    output += (char) data;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public synchronized String getContentWithoutUnicode() {
        return getContent(data -> data < CONDITION_UN);
    }

    public synchronized String getContentSimple() {
        return getContent(data -> true);
    }
}