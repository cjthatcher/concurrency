package com.dentist.other.thread;

import com.dentist.other.standard.Document;
import com.dentist.other.standard.DocumentId;
import com.dentist.other.standard.StandardRequests;

import java.util.List;
import java.util.function.Supplier;

class DirectThread {

    public List<Document> getAvailableDocuments() {
        try {
            return launchThreadsAndWait();
        } catch (InterruptedException e) {
            System.out.println("Alas, our thread was interrupted. We are probably shutting down. Time to die!");
            throw new RuntimeException(e);
        }
    }

    private List<Document> launchThreadsAndWait() throws InterruptedException {
        // Step 1, we need to get the list of available IDs + Colors from source A (relational DB) and source B (some remote service)
        // We can do these two queries in parallel. We'll call it phase 1.
        QueryStandardRequests<DocumentId> taskOne = new QueryStandardRequests<>(StandardRequests::getAvailableIdsFromSourceA);
        Thread one = new Thread(taskOne);
        one.start();

        QueryStandardRequests<DocumentId> taskTwo = new QueryStandardRequests<>(StandardRequests::getAvailableIdsFromSourceA);
        Thread two = new Thread(taskTwo);
        two.start();

        one.join();
        two.join();

        return null;
    }

}

class QueryStandardRequests<T> implements Runnable {

    private Throwable t = null;
    private List<T> availableIds = null;
    private final Supplier<List<T>> functionToRun;

    public QueryStandardRequests(Supplier<List<T>> functionToRun) {
        this.functionToRun = functionToRun;
    }

    @Override
    public void run() {
        try {
           availableIds = functionToRun.get();
        } catch (Exception e) {
            t = e;
        }
    }
}