package com.podconnect.app;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JobQueue {

    private final BlockingQueue<Job> queue = new LinkedBlockingQueue<>();
    private final MessageSender sender;

    private volatile boolean running = false;
    private Thread worker;

    public JobQueue(MessageSender sender) {
        this.sender = sender;
    }

    public synchronized void start() {
        if (running) return;

        running = true;

        worker = new Thread(() -> {
            while (running || !queue.isEmpty()) {
                try {
                    Job job = queue.take();
                    sender.send(job.phone, job.message);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;

                } catch (Exception e) {
                    // Prevent worker crash
                    e.printStackTrace();
                }
            }
        }, "JobQueue-Worker");

        worker.start();
    }

    public synchronized void stop() {
        running = false;

        if (worker != null) {
            worker.interrupt();
        }
    }

    public void enqueue(String phone, String message) {
        if (!running) {
            throw new IllegalStateException("JobQueue not started");
        }
        queue.offer(new Job(phone, message));
    }

    private static class Job {
        final String phone;
        final String message;

        Job(String phone, String message) {
            this.phone = phone;
            this.message = message;
        }
    }
}
