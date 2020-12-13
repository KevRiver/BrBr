package com.brbr.brick;

import java.util.PriorityQueue;

// 특정 시간 후에 action을 실행하기 위한 scheduler
public class Scheduler {

    private final PriorityQueue<Entry> queue = new PriorityQueue<>();

    public void postDelayed(long delay, Runnable runnable) {
        queue.add(new Entry(System.currentTimeMillis() + delay, runnable));
    }

    public void update() {
        synchronized (queue) {
            while (true) {
                Entry firstEntry = queue.peek();
                if (firstEntry == null || firstEntry.executeTime > System.currentTimeMillis()) break;

                firstEntry.runnable.run();
                queue.poll();
            }
        }
    }

    private static class Entry implements Comparable<Entry> {

        public long executeTime;
        public Runnable runnable;

        public Entry(long executeTime, Runnable runnable) {
            this.executeTime = executeTime;
            this.runnable = runnable;
        }

        @Override
        public int compareTo(Entry other) {
            return Long.compare(executeTime, other.executeTime);
        }
    }

}
