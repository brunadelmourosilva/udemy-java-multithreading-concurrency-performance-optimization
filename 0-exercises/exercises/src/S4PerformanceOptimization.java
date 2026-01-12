import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class S4PerformanceOptimization {

  // todo how to know how many threads could I use to get the best performance (given my number of
  // todo computer cores)?
  // https://chatgpt.com/c/695e7852-1c3c-832c-a8c6-6f3869b75460
  public static void main(String[] args) throws InterruptedException {

    int cores = Runtime.getRuntime().availableProcessors();
    System.out.println("Number of computer cores: " + cores);

    System.out.println(
        "Result: "
            + new ComplexCalculationPerformance()
                .calculateResult(
                    // 100
                    BigInteger.valueOf(10),
                    BigInteger.valueOf(2),
                    // 125
                    BigInteger.valueOf(5),
                    BigInteger.valueOf(3)));
  }
}

class ComplexCalculationPerformance {

  public BigInteger calculateResult(
      BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2)
      throws InterruptedException {

    BigInteger result = BigInteger.ZERO;
    /*
        Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
        Where each calculation in (...) is calculated on a different thread
    */
    List<PowerCalculatingThread> threads = new ArrayList<>();
    threads.add(new PowerCalculatingThread(base1, power1));
    threads.add(new PowerCalculatingThread(base2, power2));
    threads.add(new PowerCalculatingThread(base1, power1));
    threads.add(new PowerCalculatingThread(base2, power2));
    threads.add(new PowerCalculatingThread(base1, power1));
    threads.add(new PowerCalculatingThread(base2, power2));
    threads.add(new PowerCalculatingThread(base1, power1));
    threads.add(new PowerCalculatingThread(base2, power2));

    // gets lower performance
    threads.add(new PowerCalculatingThread(base1, power1));
    threads.add(new PowerCalculatingThread(base2, power2));
    threads.add(new PowerCalculatingThread(base1, power1));
    threads.add(new PowerCalculatingThread(base2, power2));

    /* key point here: this solution runs sequentially, not concurrently. */
    //    for (PowerCalculatingThread thread : threads) {
    //      thread.start();
    //      thread.join();
    //      result = result.add(thread.getResult());
    //    }

    var startTime = System.currentTimeMillis();

    /* concurrent version */
    for (PowerCalculatingThread thread : threads) {
      thread.start();
    }
    for (PowerCalculatingThread thread : threads) {
      thread.join();
    }
    for (PowerCalculatingThread thread : threads) {
      result = result.add(thread.getResult());
    }

    var endTime = System.currentTimeMillis();
    var duration = endTime - startTime;
    System.out.println("Duration for running " + threads.size() + " threads: " + duration);

    return result;
  }

  private static class PowerCalculatingThread extends Thread {
    private final BigInteger base;
    private final BigInteger power;
    private BigInteger result = BigInteger.ONE;

    public PowerCalculatingThread(BigInteger base, BigInteger power) {
      this.base = base;
      this.power = power;
    }

    @Override
    public void run() {
      /*
      Implement the calculation of result = base ^ power
      */
      this.result = this.base.pow(this.power.intValue());
    }

    public BigInteger getResult() {
      return result;
    }
  }
}
