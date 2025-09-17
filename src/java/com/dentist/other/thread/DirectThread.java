package com.dentist.other.thread;

import java.util.List;
import com.dentist.other.standard.Document;

class DirectThread {

    public List<Document> getAvailableDocuments() {
        // Step 1, we need to get the list of available IDs + Colors from source A (relational DB) and source B (some remote service)
        // We can do these two queries in parallel. We'll call it phase 1.

        return null;
    }
}