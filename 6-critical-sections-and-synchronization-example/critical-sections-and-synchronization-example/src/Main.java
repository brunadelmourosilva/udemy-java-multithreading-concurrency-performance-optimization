/*
 * Copyright (c) 2019-2023. Michael Pogrebinsky - Top Developer Academy
 * https://topdeveloperacademy.com
 * All rights reserved
 */

/**
 * Critical Section & Synchronization
 * https://www.udemy.com/java-multithreading-concurrency-performance-optimization
 */
public class Main {
  public static void main(String[] args) throws InterruptedException {
    InventoryCounter inventoryCounter = new InventoryCounter();
    IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
    DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

    incrementingThread.start();
    decrementingThread.start();

    incrementingThread.join();
    decrementingThread.join();

    System.out.println("We currently have " + inventoryCounter.getItems() + " items");
  }

  public static class DecrementingThread extends Thread {

    private final InventoryCounter inventoryCounter;

    public DecrementingThread(InventoryCounter inventoryCounter) {
      this.inventoryCounter = inventoryCounter;
    }

    @Override
    public void run() {
      for (int i = 0; i < 10000; i++) {
        inventoryCounter.decrement();
      }
    }
  }

  public static class IncrementingThread extends Thread {

    private final InventoryCounter inventoryCounter;

    public IncrementingThread(InventoryCounter inventoryCounter) {
      this.inventoryCounter = inventoryCounter;
    }

    @Override
    public void run() {
      for (int i = 0; i < 10000; i++) {
        inventoryCounter.increment();
      }
    }
  }

  private static class InventoryCounter {
    Object lock = new Object();
    private int items = 0;

    //    // todo synchronizing the entire method
    //    public synchronized void increment() {
    //        items++;
    //    }

    public void increment() {
      // todo synchronizing only the critical section
      synchronized (this.lock) {
        items++;
      }
    }

    public void decrement() {
      // todo synchronizing only the critical section
      synchronized (this.lock) {
        items--;
      }
    }

    // it doesn't necessary to sync. getters and setters
    // and for primitive types EXCEPT for long and double (64 bits)
    public int getItems() {
        return items;
    }
  }
}
