package com.dentist.other.thread;

import com.dentist.other.standard.document.Document;
import com.dentist.other.standard.document.DocumentId;
import com.dentist.other.standard.client.IdClient;
import com.dentist.other.standard.document.RedDocumentData;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class DirectThread {

    public List<Document> getAvailableDocuments() {
        try {
            return launchThreadsAndWait();
        } catch (InterruptedException e) {
            System.out.println("Alas, our thread was interrupted. We are probably shutting down. Time to die!");
            throw new RuntimeException(e);
        }
    }

    private List<Document> launchThreadsAndWait() throws InterruptedException {
        // The calling thread (this one) will spawn two additional threads. In parallel, they will query two different sources for a list of IDs.
        AvailableIdsFromTwoSources result = getAvailableIdsFromTwoSources();

        // We have results! Let's do something neat to them (synchronously) in the calling thread, then spam off the three pathways.
        List<DocumentId> deduplicatedIds = deduplicateIds(result.taskOne().getResult(), result.taskTwo().getResult());

        // We can now launch three different paths in parallel.
        // Red documents are going to be fleshed out by the red path. It's three asynchronous calls, each of them depending on the results of the previous call.

        return null;
    }

    private static AvailableIdsFromTwoSources getAvailableIdsFromTwoSources() throws InterruptedException {
        // Step 1, we need to get the list of available IDs + Colors from source A (relational DB) and source B (some remote service)
        // We can do these two queries in parallel. We'll call it phase 1.
        AsyncRequestForList<DocumentId> taskOne = new AsyncRequestForList<>(IdClient::getAvailableIdsFromSourceA);
        Thread one = new Thread(taskOne);
        one.start();

        AsyncRequestForList<DocumentId> taskTwo = new AsyncRequestForList<>(IdClient::getAvailableIdsFromSourceA);
        Thread two = new Thread(taskTwo);
        two.start();

        one.join();
        two.join();

        // At this point we have both results
        // We need to first see if either of them terminated with an exception (and decide what to do about that...)
        // If they were both successful, we can process the results here in the calling thread.

        if (taskOne.hasException() || taskTwo.hasException()) {
            Exception firstException = taskOne.getException() != null ? taskOne.getException() : taskTwo.getException();
            throw new RuntimeException("Oh noes something bad happened somewhere else.", firstException);
        }
        AvailableIdsFromTwoSources result = new AvailableIdsFromTwoSources(taskOne, taskTwo);
        return result;
    }

    private record AvailableIdsFromTwoSources(AsyncRequestForList<DocumentId> taskOne,
                                              AsyncRequestForList<DocumentId> taskTwo) {
    }

    // Some meaningful but quick computation here. NBD.
    private List<DocumentId> deduplicateIds(List<DocumentId> a, List<DocumentId> b) {
        Set<DocumentId> dedup = new HashSet<>();
        dedup.addAll(a);
        dedup.addAll(b);
        return dedup.stream().toList();
    }

}

class AsyncRequestForList<T> implements Runnable {

    private Exception e = null;
    private List<T> result = null;
    private final Supplier<List<T>> functionToRun;

    public AsyncRequestForList(Supplier<List<T>> functionToRun) {
        this.functionToRun = functionToRun;
    }

    public boolean hasException() {
        return e != null;
    }

    public Exception getException() {
        return e;
    }

    public List<T> getResult() {
        return result;
    }

    @Override
    public void run() {
        try {
            result = functionToRun.get();
        } catch (Exception e) {
            this.e = e;
        }
    }
}

// So how do we chain three requests together? First this, then B, then C?
// One option would be to dedicate a whole thread to it, and to just block that thread each time we're waiting.
// That should be fine.

/**
 * Note how we're forced to think about this logic at a slightly coarser level than we want to here.
 * It's ONE thread that's going to do three things.
 * Would be cool if we could define those three things separately...
 */
class RedDocumentThread implements Runnable {

    private List<RedDocumentData> results = null;

    /**
     * Red Documents have a name, a type, and an ability. All Strings.
     */
    @Override
    public void run() {

    }
}