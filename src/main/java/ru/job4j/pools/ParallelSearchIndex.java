package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final T t;
    private final int from;
    private final int to;

    public ParallelSearchIndex(T[] array, T t, int from, int to) {
        this.array = array;
        this.t = t;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return findIndexLinear();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftSort = new ParallelSearchIndex<>(array, t, from, mid);
        ParallelSearchIndex<T> rightSort = new ParallelSearchIndex<>(array, t, mid + 1, to);
        leftSort.fork();
        rightSort.fork();
        int left = leftSort.join();
        int right = rightSort.join();
        return Math.max(left, right);
    }

    public static <T> Integer sort(T[] array, T t) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer rsl = forkJoinPool.invoke(new ParallelSearchIndex<>(array, t, 0, array.length - 1));
        return rsl;
    }

    public int findIndexLinear() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(t)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}
