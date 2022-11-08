package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        try {
            progress.start();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            String[] process = new String[]{"\\", "|", "/"};
            for (String s : process) {
                System.out.print("\r load: " + s);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
