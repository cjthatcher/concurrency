package com.dentist.other.standard.client;

import com.dentist.other.standard.document.DocumentId;

import java.util.*;

/**
 * Pretends to be a client wrapping some external service.
 */
public class RedClient {

    String[] names = {"Bad Henry", "Old Pete", "The Quiet Man", "Sparky", "Uncle", "Little Louie", "Old Aches & Pains", "The Tilden Flash", "Earl of Snohomish", "Home Run", "Beauty", "Eagle Eye", "Cool Papa", "Little General", "Chief", "Chicken Man", "Sunny Jim", "The Franchise", "Miner", "Ese Hombre", "The Peerless Leader"};
    String[] types = {"Brie", "Gouda", "Cheddar", "Muenster", "Cottage"};
    String[] abilities = {"Defiant", "Clear Body", "Lingering Aroma", "Aura Break", "Propeller Tail", "Hustle", "Guts"};
    private final Random r = new Random();

    public Map<DocumentId, String> getNamesById(List<DocumentId> ids) throws InterruptedException {
        Sleeper.sleep();
        List<String> localNames = Arrays.stream(names).toList();
        Collections.shuffle(localNames);
        Map<DocumentId, String> res = new HashMap<>();
        for (int i = 0; i < names.length && i < ids.size(); i++) {
            res.put(ids.get(i), localNames.get(i));
        }
        return res;
    }

    public Map<String, String> getTypesByName(List<String> names) {
        Map<String, String> res = new HashMap<>();
        names.forEach(name -> res.put(name, pickRandom(types)));
        return res;
    }

    public Map<String, String> getAbilitiesByType(List<String> types) {
        Map<String, String> res = new HashMap<>();
        types.forEach(type -> res.put(type, pickRandom(abilities)));
        return res;
    }

    private String pickRandom(String[] samples) {
        return samples[r.nextInt(samples.length)];
    }
}
