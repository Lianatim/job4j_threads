package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                });
        Thread second = new Thread(
                () -> {
                });
        first.start();
        second.start();

        System.out.println("Thread first: " + first.getName());
        System.out.println("Thread second: " + second.getName());
        if (first.getState() == Thread.State.TERMINATED && second.getState() == Thread.State.TERMINATED) {
            System.out.println("Работа завершена");
        }
    }
}