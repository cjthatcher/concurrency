package com.dentist.other.standard.document;

import java.util.List;

public record GreenDocumentData(String alternateId, List<Integer> favoriteIntegerSequence) implements DocumentData {
}
