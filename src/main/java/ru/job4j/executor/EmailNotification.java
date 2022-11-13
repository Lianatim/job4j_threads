package ru.job4j.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private static final ExecutorService POOL = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    private void emailTo(User user) {
        String subject = String.format("Notification %s to email %s.", user.getUsername(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getEmail());
        POOL.submit(() -> send(subject, body, user.getEmail()));
        close();
    }

    private void close() {
        POOL.shutdown();
        while (!POOL.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {

    }
}
