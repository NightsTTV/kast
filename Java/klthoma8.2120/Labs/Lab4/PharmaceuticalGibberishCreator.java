import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class PharmaceuticalGibberishCreator {

    public static void main(String[] args) {
        // Variables/arrays
        String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
        String vowels = "AEIOU";

        // VC Pairs
        String[] vcPairs = {
            "ab", "ac", "ad", "af", "ag", "al", "am", "an", "ap", "ar", "as", "at", "av",
            "eb", "ec", "ed", "ef", "eg", "el", "em", "en", "ep", "er", "es", "et", "ev",
            "ib", "ic", "id", "if", "ig", "il", "im", "in", "ip", "ir", "is", "it", "iv",
            "ob", "oc", "od", "of", "og", "ol", "om", "on", "op", "or", "os", "ot", "ov",
            "ub", "uc", "ud", "uf", "ug", "ul", "um", "un", "up", "ur", "us", "ut", "uv"
        };

        // CV Pairs
        String[] cvPairs = {
            "ba", "be", "bi", "bo", "bu",
            "ca", "ce", "ci", "co", "cu",
            "da", "de", "di", "do", "du",
            "fa", "fe", "fi", "fo", "fu",
            "ga", "ge", "gi", "go", "gu",
            "la", "le", "li", "lo", "lu",
            "ma", "me", "mi", "mo", "mu",
            "na", "ne", "ni", "no", "nu",
            "pa", "pe", "pi", "po", "pu",
            "ra", "re", "ri", "ro", "ru",
            "sa", "se", "si", "so", "su",
            "ta", "te", "ti", "to", "tu",
            "va", "ve", "vi", "vo", "vu"
        };
        // Initialize random variable
        Random random = new Random();
        // Word length set randomly between 4 and 7
        int wordLength = random.nextInt(4) + 4;
        // Initialize first version 
        StringBuilder gibWord = new StringBuilder();
        // Generate gibberish word 
        for (int i = 0; i < wordLength; i++) {
            if (i % 2 == 0) {
                gibWord.append(consonants.charAt(random.nextInt(consonants.length())));
            } else {
                gibWord.append(vowels.charAt(random.nextInt(vowels.length())));
            }
        }
        // Apply transformations
        String finalWord = applyWordTransformations(gibWord.toString(), cvPairs, vcPairs, random);

        System.out.println("Gibberish Pharmaceutical Word: " + finalWord);
    }
    public static String applyWordTransformations(String word, String[] cvPairs, String[] vcPairs, Random random) {
        StringBuilder transformedWord = new StringBuilder(word);

        for (int i = 0; i < word.length() - 1; i += 2) {
            String pair = word.substring(i, i + 2);

            if (isVowel(pair.charAt(0))) {
                transformedWord.replace(i, i + 2, randomPair(vcPairs, pair.charAt(0), random));
            } else {
                transformedWord.replace(i, i + 2, randomPair(cvPairs, pair.charAt(0), random));
            }
        }
        return transformedWord.toString();
    }
    public static String randomPair(String[] pairs, char startLetter, Random random) {
        List<String> filteredPairs = new ArrayList<>();

        for (String pair : pairs) {
            if (pair.charAt(0) == startLetter) {
                filteredPairs.add(pair);
            }
        }

        if (!filteredPairs.isEmpty()) {
            return filteredPairs.get(random.nextInt(filteredPairs.size()));
        } else {
            return String.valueOf(startLetter);
        }
    }

    public static boolean isVowel(char c) {
        return "AEIOU".indexOf(c) != -1;
    }
}
