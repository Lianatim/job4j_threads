package ru.job4j.queue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void whenAddThenDelete() throws InterruptedException {
        SimpleBlockingQueue sbq = new SimpleBlockingQueue<>(2);
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
}