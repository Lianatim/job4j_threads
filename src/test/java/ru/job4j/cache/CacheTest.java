package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {

    @Test
    void whenAdd() throws InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(2, 2);
        Thread thread1 = new Thread(() -> cache.add(base));
        Thread thread2 = new Thread(() -> cache.add(base2));
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        assertThat(cache.get(1)).isEqualTo(base);
    }

    @Test
    void whenAddThenUpdate() throws InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(2, 2);
        Base base3 = new Base(2, 2);
        Thread thread1 = new Thread(() -> cache.add(base));
        Thread thread2 = new Thread(() -> cache.add(base2));
        Thread thread3 = new Thread(() -> cache.update(base3));
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();
        assertThat(cache.get(2)).isEqualTo(new Base(2, 3));
    }

    @Test
    void whenDelete() throws InterruptedException {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Thread thread1 = new Thread(() -> cache.add(base));
        Thread thread2 = new Thread(() -> cache.delete(base));
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        assertThat(cache.get(1)).isNull();
    }

    @Test
    void whenAddThenUpdateWithException() throws InterruptedException {
        Cache cache = new Cache();
        Base base2 = new Base(1, 1);
        Base base3 = new Base(1, 2);
        cache.add(base2);
        assertThatThrownBy(() -> cache.update(base3))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Versions are not equal");
    }
}