public class S5MinMaxMetrics {

  public static void main(String[] args) throws InterruptedException {

    MinMaxMetrics metrics = new MinMaxMetrics();

    Thread t1 =
        new Thread(
            () -> {
              for (int i = 0; i < 1000; i++) {
                metrics.addSample(i);
              }
            });

    Thread t2 = new Thread(() -> {
      for (int i = 1000; i > 0; i--) {
        metrics.addSample(i);
      }
    });

    Thread reader = new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        System.out.println("Min: " + metrics.getMin());
        System.out.println("Max: " + metrics.getMax());
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });


    t1.start();
    t2.start();
    reader.start();

    t1.join();
    t2.join();
    reader.join();

    System.out.println("\nFinal min: " + metrics.getMin());
    System.out.println("Final max: " + metrics.getMax());
  }
}

class MinMaxMetrics {
  // Add all necessary member variables
  private volatile long minValue;
  private volatile long maxValue;

  /** Initializes all member variables */
  public MinMaxMetrics() {
    // Add code here
    this.minValue = Long.MAX_VALUE;
    this.maxValue = Long.MIN_VALUE;
  }

  /** Adds a new sample to our metrics. */
  public void addSample(long newSample) {
    // Add code here
    synchronized (this) {
      this.minValue = Math.min(newSample, this.minValue);
      this.maxValue = Math.max(newSample, this.maxValue);
    }
  }

  /** Returns the smallest sample we've seen so far. */
  public long getMin() {
    // Add code here
    return this.minValue;
  }

  /** Returns the biggest sample we've seen so far. */
  public long getMax() {
    // Add code here
    return this.maxValue;
  }
}
