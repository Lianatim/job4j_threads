package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void whenExecute2ThreadThen2() {
        CASCount casCount = new CASCount();
        for (int i = 0; i < 100; i++) {
            new Thread(casCount::increment).start();
        }
        assertThat(casCount.get()).isEqualTo(99);
    }

}