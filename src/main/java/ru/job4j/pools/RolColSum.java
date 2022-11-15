package ru.job4j.pools;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            rsl[i] = getSum(matrix, i);
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        CompletableFuture.runAsync(
                () -> {
                    for (int i = 0; i < matrix.length; i++) {
                        rsl[i] = getSum(matrix, i);
                    }
                }
        );
        return rsl;
    }

    public static Sums getSum(int[][] matrix, int index) {
        int rowSum = 0;
        int columnSum = 0;
        for (int j = 0; j < matrix[index].length; j++) {
            rowSum += matrix[index][j];
            columnSum += matrix[j][index];
        }
        return new Sums(rowSum, columnSum);
    }

}
