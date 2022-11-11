package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void whenIncrementAndGet() {
        CASCount casCount = new CASCount();
        for (int i = 0; i < 150; i++) {
            new Thread(casCount::increment).start();
        }
        assertThat(casCount.get()).isEqualTo(149);
    }

    @Test
    public void whenIncrementAndGetTwoThread() {
        CASCount casCount = new CASCount();
        Thread thread = new Thread(() -> {
                for (int j = 1; j <= 150; j++) {
                    casCount.increment();
                }
            });
        Thread thread2 = new Thread(() -> {
            for (int j = 1; j <= 150; j++) {
                casCount.increment();
            }
        });
        thread.start();
        thread2.start();
        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(casCount.get()).isEqualTo(300);
    }

}