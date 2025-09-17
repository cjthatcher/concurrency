# Very Old Direct Thread Manipulation

## Interrupts 
Interrupts! Here's a good link explaining it: https://docs.oracle.com/javase/tutorial/essential/concurrency/interrupt.html

Basically, every thread keeps on chugging forever, until it terminates *or* another thread tells it to stop (an interrupt!). So, interrupts come from someone else telling you to quit doing what you're doing.

Many methods (like join()) are watching for an interrupt message. If it gets that message, it will throw an InterruptedException. That means someone else poked me (the thread that called .join() on someone else...) and told me to quit what I'm doing. Maybe a shutdown? Maybe a timeout from somewhere else? 

Your thread should periodically check if it is interrupted, and if so, throw the exception. If I'm not calling anything that already throws or checks for interrupt, I should occasionally check it myself. See the good example in the docs I linked above. 

So to be very clear:

Me, Thread A, knows about Thread B, and I call `b.join();`.

If someone _else_ knows about me, and they call `a.interrupt()` while I'm there waiting for `b.join()` to come back, then I will throw an `InterruptedException` and have to handle it. So it's not `B` getting interrupted. It's me!

The docs refer to the "current thread" as the one that's executing right now, and the one we're based off of. That's the implicit `thread` behind the current code that's executing. 