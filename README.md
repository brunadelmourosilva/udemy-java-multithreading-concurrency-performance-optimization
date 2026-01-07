
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

- **scenarios**

  - Background tasks, that should not block our application from terminating. Eg.: file saving thread in a text editor.