package UrlShortenerApp.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class KeyService {

    private static HashMap<Integer, Character> encryptionMap;

    static {
        initializeEncryptionMap();
    }

    private static void initializeEncryptionMap() {
        encryptionMap = new HashMap<>();
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            encryptionMap.put(i, c);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i - 26);
            encryptionMap.put(i, c);
        }
    }


    public static String generateEncryptedUrlId(Long id) {
        List<Integer> sequence = new LinkedList<>();
        while (id > 0) {
            int rem = (int) (id % 52);
            ((LinkedList<Integer>) sequence).addFirst(rem);
            id = id / 52;
        }
        StringBuilder finalId = new StringBuilder();
        for (int digit : sequence) {
            finalId.append(encryptionMap.get(digit));
        }
        return finalId.toString();
    }



}
