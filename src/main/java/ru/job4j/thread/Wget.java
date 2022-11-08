package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        long start;
        long end;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            while (true) {
                start = System.currentTimeMillis();
                bytesRead = in.read(dataBuffer, 0, 1024);
                end = System.currentTimeMillis();
                if (bytesRead == -1) {
                    break;
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long time = end - start;
                if (time < speed) {
                    Thread.sleep(1000 - time);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("The number of arguments must be equal to two");
        }
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static void validate(String[] args) {
        if (args[0].isEmpty() || args[1].isEmpty()) {
            throw new IllegalArgumentException("One of the args is empty");
        }

        if (Integer.parseInt(args[1]) < 0) {
            throw new IllegalArgumentException("The speed must be greater than zero");
        }
    }
}