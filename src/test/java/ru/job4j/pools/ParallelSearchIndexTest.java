package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelSearchIndexTest {

    @Test
    void whenSortArray() {
        Integer[] array = new Integer[]{
                5, 4, 3, 2, 1
        };
        assertThat(ParallelSearchIndex.sort(array, 2)).isEqualTo(3);
    }

    @Test
    void whenSortArrayMore10() {
        Integer[] array = new Integer[]{
                5, 4, 3, 2, 1, 6, 3, 9, 10, 100, 2, 7, 3, 44, 8, 2465
        };
        assertThat(ParallelSearchIndex.sort(array, 100)).isEqualTo(9);
    }

    @Test
    void whenSortObject() {
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();
        Object object4 = new Object();
        Object[] array = new Object[]{
                object4, object1, object3, object2
        };
        assertThat(ParallelSearchIndex.sort(array, object4)).isEqualTo(0);
    }

    @Test
    void whenSortDiffObject() {
        Object object1 = new Object();
        Integer object2 = 3;
        Object object3 = new Object();
        Double object4 = 5.3;
        Object[] array = new Object[]{
                object4, object1, object3, object2
        };
        assertThat(ParallelSearchIndex.sort(array, object2)).isEqualTo(3);
    }

    @Test
    void whenSortWithMinus1() {
        Object object1 = new Object();
        Integer object2 = 3;
        Object object3 = new Object();
        Double object4 = 5.3;
        Object notExist = new Object();
        Object[] array = new Object[]{
                object4, object1, object3, object2
        };
        assertThat(ParallelSearchIndex.sort(array, notExist)).isEqualTo(-1);
    }
}