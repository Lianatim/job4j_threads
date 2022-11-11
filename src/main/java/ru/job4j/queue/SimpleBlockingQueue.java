package ru.job4j.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int max;

    public SimpleBlockingQueue(int max) {
        this.max = max;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == max) {
                wait();
            }
            queue.offer(value);
            notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                wait();
            }
            T rsl = queue.poll();
            notifyAll();
            return rsl;
        }
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
