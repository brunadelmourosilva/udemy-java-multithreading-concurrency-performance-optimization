import java.util.ArrayList;
import java.util.List;

public class S2MultiExecutor {
  public static void main(String[] args) {

    // anonymous class
    Runnable r1 =
        new Runnable() {
          @Override
          public void run() {
            System.out.println("Runnable 1 running...");
          }
        };

    // lambda version
    Runnable r2 =
        () -> {
          System.out.println("Runnable 2 running...");
        };

    MultiExecutor multiExecutor = new MultiExecutor(List.of(r1, r2));

    multiExecutor.executeAll();
  }
}

class MultiExecutor {

  // Add any necessary member variables here
  private final List<Runnable> tasks;

  /*
   * @param tasks to executed concurrently
   */
  public MultiExecutor(List<Runnable> tasks) {
    // Complete your code here
    this.tasks = tasks;
  }

  /** Starts and executes all the tasks concurrently */
  public void executeAll() {
    // complete your code here
    List<Thread> threads = new ArrayList<>();

    for (Runnable task : tasks) {
      Thread thread = new Thread(task);

      threads.add(thread);
    }

    for (Thread thread : threads) {
      thread.start();
    }
  }
}
