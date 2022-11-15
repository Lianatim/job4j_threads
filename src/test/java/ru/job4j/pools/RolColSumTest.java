package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    void whenSum() {
        int[][] matrix = new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
        RolColSum.Sums[] rsl = new RolColSum.Sums[]{new RolColSum.Sums(3, 6), new RolColSum.Sums(6, 6), new RolColSum.Sums(9, 6)};
        assertThat(RolColSum.sum(matrix)).isEqualTo(rsl);
    }

    @Test
    void whenAsyncSum() {
        int[][] matrix = new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};
        RolColSum.Sums[] rsl = new RolColSum.Sums[]{new RolColSum.Sums(3, 6), new RolColSum.Sums(6, 6), new RolColSum.Sums(9, 6)};
        assertThat(RolColSum.asyncSum(matrix)).isEqualTo(rsl);
    }
}