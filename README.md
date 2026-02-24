
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

---

### Data sharing between threads

#### Stack (exclusive)

- Each stack region belongs to a particular thread;
  
- It's a memory region where:
  
  - methods are called;
  - arguments are passed;
  - local variables are stored.

- Stack + Instruction Pointer = state of each thread's execution.
  
- Example of a visible stack: debug a program and observe the variables console, where we can see the stack in action.

#### Heap (shared)

- It's a shared memory region that belongs to the process;
  
- Allocated on the heap:

  - Objects (anything created with *new* operator)
    - String
    - Object
    - Colletion
    - ...
  - Members of classes
  - Static variables

#### Objects x References

- **References**
  
  - Can be allocated on the **stack** (if declared as local variables inside a method);
  - Can be allocated on the **heap** with their parent object (if they are members of a class, eg.: attributes)

- **Objects**
  
  - Always allocated on the heap

![alt text](image-7.png)

---

### Resource Sharing and Critical Regions

##### Resources sharing example:

- database microservice, where each thread processes an HTTP request and, in the end, all threads use the same database connection to perform operations.

#### Atomic Operations

- An operation or a set of operations is considered atomic, if it happens to the rest of the system as if it occured at once.
  
- Single step - "all or nothing".

- No intermediate states

- **Example:**

  - the variable **items++** **is not** an atomic operation
  
    - Get the current value of items
    - Increment the current value by 1
    - Store the result into items
    - Similar to: items = items + 1

#### Synchronization

- Use the **syncrhonized** keyword to protect the critical section in two ways
  
  - Simple way (in front of a method)
  - On a explicit object - more fexible and granular (keeps only the critical section as non concurrent), but also more verbose


#### What needs to be synchronized and what doesn't?

- All reference assignments are atomic
- We can get and set references to objects atomically
  
  - Example: 
    Object a = new Object();
    Object b = new Object();
    a = b; // atomic
<br>
- Getters and setters are atomic and they don't need to be synchronized
<br>
- All assignments to primitive types are safe **except long and double**
- Assignments to long and double if  declared *volatile*
  - Example: 
    volatile double x = 1.0;

    volatile double y = 9.0;

    x = y; // atomic

- https://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.7