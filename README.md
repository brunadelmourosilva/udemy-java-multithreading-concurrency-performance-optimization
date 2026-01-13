
# NOTES

### Context switching

All processes may have one or more threads and all threads are competing beteween themselves to be executed on the CPU.

- This is the concept of the context switching:
![alt text](image.png)

- Costs:
![alt text](image-1.png)

- Key Takeaways:
![alt text](image-2.png)

---

### Thread Scheduling

It uses a dynamic priority (by using an epoch) for each thread.

It avoids starvation.

![alt text](image-3.png)

### Multithread x Multi-process

![alt text](image-4.png)

![alt text](image-5.png)

### Daemon threads

Background threads that don't prevent the application from exiting if the main thread terminates.

```
Thread t = new Thread(() -> {});
t.setDaemon(true);
```

- **Scenarios**

  - Background tasks, that should not block our application from terminating. Eg.: file saving thread in a text editor.

---

### Performance in multithreading

- **Latency** - the time completion of a task. Measured in time units.
- **Throughput** - the amount of tasks completed in a given period. Measured in tasks/time unit.

#### Analysis

![alt text](image-6.png)

---

### Thread pooling (throughput)

Create threads once and reusing them for feature tasks instead of recreating the threads each and every time from scratch.

Once the threads are created, they sit in the pool and the tasks are distributed among the threads through a queue.

Each thread takes tasks from that queue whenever that thread is available. If all threads are busy, the tasks are going to stay in the queue and waiting for a thread to become available.

If we keep the threads well, busy and utilized, and feeding tasks into the queue, we can get the maximum throughput and maximum utilization.

This concept provided us a significant performance improvement.

In Java, we can use fixed thread pool executor:

```
int numberOfThreads = 4;
Executor e = Executors.newFixedThreadPool(numberOfThreads);

Runnable task = ...;
e.execute(task);
```