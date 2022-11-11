package ru.job4j.queue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenAddThenDelete() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(() -> {
            try {
                sbq.offer(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread producer1 = new Thread(() -> {
            try {
                sbq.offer(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                sbq.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        producer1.start();
        consumer.start();
        producer.join();
        producer1.join();
        consumer.join();
        assertThat(sbq.poll()).isEqualTo(2);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            value -> {
                                try {
                                    queue.offer(value);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo((Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void whenFetchAllThenGet() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            value -> {
                                try {
                                    queue.offer(value);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    IntStream.range(0, 3).forEach(
                            value -> {
                                try {
                                    buffer.add(queue.poll());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    Thread.currentThread().interrupt();
                                }
                            }
                    );
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo((Arrays.asList(0, 1, 2)));
    }
}