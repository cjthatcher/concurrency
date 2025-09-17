package com.dentist.other.standard;

import java.util.stream.Collectors;
import java.util.List;

class StandardRequests {

    // The standard flow is going to look something like a simplified Frap dashboard flow.

    // Start with two async requests to gather a bunch of {id, documentType}s we need to augment.
    // Deduplicate that list once we have them both.

    // Launch three async paths
    // For doc type 1, do a three-step chain "getDoc1part1, getDoc1part2(part1), getDoc1part3(part2)"
    // for doc type 2, for each id, do a one-step single request "getDoc2forId-n"
    // for doc type 3, do a one-step with a return type, then two parallels that depend on part 1, then a third part that depends on part (1, 2a, 2b)

    // Combine all the doc types into a big old list of "Document" for returns.


    public List<DocumentId> getAvailableIdsFromSourceA() {
        // sleep the configured time
        // fail if appropriate

        return java.util.Arrays.stream(names).map(name -> new DocumentId(name, DocumentColor.random())).collect(Collectors.toList());
    }

    private static final String[] names = {"Tiger","Crab","Ant-Eater","Skeptic","Cryptid","Frightening","Carbohydrate"};
}