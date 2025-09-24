package com.dentist.other.standard.document;

public record BlueDocumentData(String name, int ageInDays, boolean believesInJustice,
                        String favoriteAnimal) implements DocumentData {
}
