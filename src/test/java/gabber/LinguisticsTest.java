package gabber;

import java.util.HashMap;
import static gabber.Language.*;

/**
 * Created by Tommy Ettinger on 11/29/2015.
 */
public class LinguisticsTest {
    public static void main(String[] args)
    {
        RNG rng = new RNG(0xf00df00L);
        Language flg = ENGLISH;

        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 10, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.17));
        }
        rng.setState(0xf00df00L);
        flg = LOVECRAFT;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 3, 9, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "!", "?", "...", "..."}, 0.15));
        }
        rng.setState(0xf00df00L);
        flg = GREEK_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.2));
        }
        rng.setState(0xf00df00L);
        flg = GREEK_AUTHENTIC;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.2));
        }
        rng.setState(0xf00df00L);
        flg = FRENCH;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 12, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.17));
        }
        rng.setState(0xf00df00L);
        flg = RUSSIAN_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 13, new String[]{",", ",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.25));
        }
        rng.setState(0xf00df00L);
        flg = RUSSIAN_AUTHENTIC;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 13, new String[]{",", ",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.25));
        }
        rng.setState(0xf00df00L);
        flg = JAPANESE_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 13, new String[]{",", ",", ",", ",", ";"},
                    new String[]{".", ".", ".", "!", "?", "...", "..."}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = SWAHILI;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = SOMALI;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = ARABIC_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = HINDI_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = INUKTITUT;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = NORSE;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = NAHUATL;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }


        rng.setState(0xf00df00L);
        flg = ENGLISH.mix(FRENCH.removeAccents(), 0.5);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        rng.setState(0xf00df00L);
        flg = RUSSIAN_ROMANIZED.mix(ENGLISH, 0.35);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 10, new String[]{",", ",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.22));
        }
        rng.setState(0xf00df00L);
        flg = FRENCH.mix(GREEK_ROMANIZED, 0.55);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.22));
        }
        rng.setState(0xf00df00L);
        flg = ENGLISH.mix(GREEK_AUTHENTIC, 0.25);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        rng.setState(0xf00df00L);
        flg = ENGLISH.addAccents(0.5, 0.15);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }

        rng.setState(0xf00df00L);
        flg = FRENCH.mix(JAPANESE_ROMANIZED, 0.65);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", "!", "?", "...", "..."}, 0.17));
        }

        rng.setState(0xf00df00L);
        flg = RUSSIAN_ROMANIZED.mix(JAPANESE_ROMANIZED, 0.75);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "...", "..."}, 0.2));
        }

        rng.setState(0xf00df00L);
        flg = ENGLISH.addModifiers(Modifier.NO_DOUBLES);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }

        rng.setState(0xf00df00L);
        flg = JAPANESE_ROMANIZED.addModifiers(Modifier.DOUBLE_CONSONANTS);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        rng.setState(0xf00df00L);
        flg = mixAll(SOMALI, 0.6, JAPANESE_ROMANIZED, 0.3, SWAHILI, 0.1);// SOMALI.mix(JAPANESE_ROMANIZED, 0.3).mix(SWAHILI, 0.1);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.15));
        }
        rng.setState(0xf00df00L);
        flg = NORSE.addModifiers(Modifier.SIMPLIFY_NORSE);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = mixAll(NAHUATL, 5, INUKTITUT, 3, ARABIC_ROMANIZED, 2);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }

        rng.setState(0xf00df00L);
        flg = FANTASY_NAME;
        System.out.print(flg.word(rng, true, rng.between(2, 4)));
        for (int i = 1; i < 10; i++) {
            System.out.print(", " + flg.word(rng, true, rng.between(2, 4)));
        }
        System.out.println("...");

        rng.setState(0xf00df00L);
        flg = FANCY_FANTASY_NAME;
        System.out.print(flg.word(rng, true, rng.between(2, 4)));
        for (int i = 1; i < 10; i++) {
            System.out.print(", " + flg.word(rng, true, rng.between(2, 4)));
        }
        System.out.println("...");
        System.out.println('"' + ENGLISH.sentence(rng, 4, 7, new String[]{" -", ",", ",", ";"}, new String[]{"!", "!", "...", "...", ".", "?"}, 0.2) + "\",");
        System.out.println('"' + JAPANESE_ROMANIZED.sentence(rng, 4, 7, new String[]{" -", ",", ",", ";"}, new String[]{"!", "!", "...", "...", ".", "?"}, 0.2) + "\",");
        System.out.println('"' + FRENCH.sentence(rng, 5, 8, new String[]{" -", ",", ",", ";"}, new String[]{"!", "?", ".", "...", ".", "?"}, 0.1) + "\",");
        System.out.println('"' + GREEK_ROMANIZED.sentence(rng, 5, 8, new String[]{",", ",", ";"}, new String[]{"!", "?", ".", "...", ".", "?"}, 0.15) + "\",");
        System.out.println('"' + GREEK_AUTHENTIC.sentence(rng, 5, 8, new String[]{",", ",", ";"}, new String[]{"!", "?", ".", "...", ".", "?"}, 0.15) + "\",");
        System.out.println('"' + RUSSIAN_ROMANIZED.sentence(rng, 4, 7, new String[]{" -", ",", ",", ",", ";"}, new String[]{"!", "!", ".", "...", ".", "?"}, 0.22) + "\",");
        System.out.println('"' + RUSSIAN_AUTHENTIC.sentence(rng, 4, 7, new String[]{" -", ",", ",", ",", ";"}, new String[]{"!", "!", ".", "...", ".", "?"}, 0.22) + "\",");
        System.out.println('"' + LOVECRAFT.sentence(rng, 4, 7, new String[]{" -", ",", ",", ";"}, new String[]{"!", "!", "...", "...", ".", "?"}, 0.2) + "\",");
        System.out.println('"' + SWAHILI.sentence(rng, 4, 7, new String[]{",", ",", ";"}, new String[]{"!", "?", ".", ".", "."}, 0.12) + "\",");
        flg = FRENCH.mix(JAPANESE_ROMANIZED, 0.65);
        System.out.println('"' + flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                new String[]{".", ".", "!", "?", "...", "..."}, 0.17) + "\",");
        flg = ENGLISH.addAccents(0.5, 0.15);
        System.out.println('"' + flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                new String[]{".", ".", "!", "?", "...", "..."}, 0.17) + "\",");
        flg = mixAll(RUSSIAN_AUTHENTIC, 1, GREEK_AUTHENTIC, 1, FRENCH, 1);// RUSSIAN_AUTHENTIC.mix(GREEK_AUTHENTIC, 0.5).mix(FRENCH, 0.35);
        System.out.println('"' + flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";", " -"},
                new String[]{".", ".", "!", "?", "...", "..."}, 0.2) + "\",");
        flg = FANCY_FANTASY_NAME;
        System.out.println('"' + flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";", " -"},
                new String[]{".", ".", "!", "?", "...", "..."}, 0.2) + "\",");
        flg = SWAHILI.mix(JAPANESE_ROMANIZED, 0.35);
        System.out.println('"' + flg.sentence(rng, 4, 7, new String[]{",", ",", ";"},
                new String[]{"!", "?", ".", ".", "."}, 0.12) + "\",");
        flg = SWAHILI.mix(JAPANESE_ROMANIZED, 0.32).mix(FANCY_FANTASY_NAME, 0.25);
        System.out.println('"' + flg.sentence(rng, 4, 7, new String[]{",", ",", ";"},
                new String[]{"!", "?", ".", ".", "."}, 0.12) + "\",");
        flg = SOMALI.mix(JAPANESE_ROMANIZED, 0.3).mix(SWAHILI, 0.15);
        System.out.println('"' + flg.sentence(rng, 4, 7, new String[]{",", ",", ";"},
                new String[]{"!", "?", ".", ".", "."}, 0.15) + "\",");
        flg = mixAll(NAHUATL, 5, INUKTITUT, 3, ARABIC_ROMANIZED, 2);
        System.out.println('"' + flg.sentence(rng, 4, 6, new String[]{",", ",", ";"},
                new String[]{"!", "?", ".", ".", "."}, 0.1) + "\",");

        System.out.println("\n-----------------------------------------------------------------------------");
        System.out.println();
        Language[] languages = new Language[]{

//                ENGLISH,
//                LOVECRAFT,
//                JAPANESE_ROMANIZED,
//                FRENCH,
//                GREEK_ROMANIZED,
//                GREEK_AUTHENTIC,
//                RUSSIAN_ROMANIZED,
//                RUSSIAN_AUTHENTIC,
//                SWAHILI,
//                SOMALI,
//                ARABIC_ROMANIZED,
//                HINDI_ROMANIZED,
//                INUKTITUT,
//                NORSE,
//                NAHUATL,
//                FANTASY_NAME,
//                FANCY_FANTASY_NAME,
//                mixAll(RUSSIAN_ROMANIZED, 3, SOMALI, 1),
//                mixAll(GREEK_ROMANIZED, 1, HINDI_ROMANIZED.removeAccents(), 1),
//                mixAll(SWAHILI, 7, FRENCH, 3),
//                mixAll(ARABIC_ROMANIZED, 3, JAPANESE_ROMANIZED, 2),
//                mixAll(SWAHILI, 3, GREEK_ROMANIZED, 2),
//                mixAll(GREEK_ROMANIZED, 3, SOMALI, 2),
//                mixAll(ENGLISH, 3, HINDI_ROMANIZED.removeAccents(), 2),
//                mixAll(ENGLISH, 3, JAPANESE_ROMANIZED, 2),
//                mixAll(SOMALI, 3, HINDI_ROMANIZED.removeAccents(), 2),
//                FRENCH.addModifiers(modifier("([^aeiou])\\1", "$1ph", 0.3),
//                        modifier("([^aeiou])\\1", "$1ch", 0.4),
//                        modifier("([^aeiou])\\1", "$1sh", 0.5),
//                        modifier("([^aeiou])\\1", "$1", 0.9)),
//                JAPANESE_ROMANIZED.addModifiers(Modifier.DOUBLE_VOWELS),
//                SOMALI.addModifiers(modifier("([kd])h", "$1"),
//                        modifier("([pfsgkcb])([aeiouy])", "$1l$2", 0.35),
//                        /*Language.modifier("a+", "á", 0.18),
//                        Language.modifier("e+", "é", 0.18),
//                        Language.modifier("i+", "í", 0.18),
//                        Language.modifier("o+", "ó", 0.18),
//                        Language.modifier("u+", "ú", 0.18),*/
//                        //Language.modifier("aa", "au"),
//                        modifier("ii", "ai"),
//                        modifier("uu", "ia"),
//                        modifier("([aeo])\\1", "$1"),
//                        modifier("^x", "v"),
//                        modifier("([^aeiou]|^)u([^aeiou]|$)", "$1a$2", 0.6),
//                        modifier("([aeiou])[^aeiou]([aeiou])", "$1v$2", 0.06),
//                        modifier("([aeiou])[^aeiou]([aeiou])", "$1l$2", 0.07),
//                        modifier("([aeiou])[^aeiou]([aeiou])", "$1n$2", 0.07),
//                        modifier("([aeiou])[^aeiou]([aeiou])", "$1z$2", 0.08),
//                        modifier("([^aeiou])[aeiou]+$", "$1ia", 0.35),
//                        modifier("([^aeiou])[bpdtkgj]", "$1"),
//                        modifier("[jg]$", "th"),
//                        modifier("g", "c", 0.92),
//                        modifier("([aeiou])[wy]$", "$1l", 0.6),
//                        modifier("([aeiou])[wy]$", "$1n"),
//                        modifier("[qf]$", "l", 0.4),
//                        modifier("[qf]$", "n", 0.65),
//                        modifier("[qf]$", "s"),
//                        modifier("cy", "sp"),
//                        modifier("kl", "sk"),
//                        modifier("qu+", "qui"),
//                        modifier("q([^u])", "qu$1"),
//                        modifier("cc", "ch"),
//                        modifier("[^aeiou]([^aeiou][^aeiou])", "$1"),
//                        Modifier.NO_DOUBLES
//                ),
//                mixAll(INUKTITUT, 3, ARABIC_ROMANIZED, 1, RUSSIAN_ROMANIZED, 1),
//                mixAll(NORSE, 5, ENGLISH, 3, RUSSIAN_ROMANIZED, 1),
                mixAll(NORSE.addModifiers(Modifier.SIMPLIFY_NORSE), 2, INUKTITUT, 2, SOMALI, 2),
                mixAll(NAHUATL, 5, INUKTITUT, 3, ARABIC_ROMANIZED, 2),
                mixAll(NAHUATL, 5, JAPANESE_ROMANIZED, 2, SOMALI, 1, LOVECRAFT, 1),
                NORSE.addModifiers(Modifier.SIMPLIFY_NORSE),
                randomLanguage("Kittenish"),
                randomLanguage("Puppyspeak"),
                randomLanguage("Rabbitese"),
                randomLanguage("Rabbit Language"),
                randomLanguage("The Roar Of That Slumbering Shadow Which Mankind Wills Itself To Forget"),
                INUKTITUT,
                NORSE,
                NORSE.addModifiers(Modifier.SIMPLIFY_NORSE),
                NAHUATL

                //Language.RUSSIAN_ROMANIZED.mix(Language.GREEK_ROMANIZED, 0.4),
                //Language.LOVECRAFT.mix(Language.RUSSIAN_ROMANIZED, 0.4),
                //Language.randomLanguage(new RNG(2252637788195L)),
        };
        String[] oz = new String[]{
                "Uncle Uncles Carbuncle Carbuncles Live Lives Lived Living Liver Livers Livery Liveries",
                "Dorothy lived in the midst of the great Kansas prairies, with Uncle Henry, who was a ",
                "farmer, and Aunt Em, who was the farmer's wife. Their house was small, for the ",
                "lumber to build it had to be carried by wagon many miles. There were four walls, ",
                "a floor and a roof, which made one room; and this room contained a rusty looking ",
                "cookstove, a cupboard for the dishes, a table, three or four chairs, and the beds. ",
                "Uncle Henry and Aunt Em had a big bed in one corner, and Dorothy a little bed in ",
                "another corner. There was no garret at all, and no cellar-except a small hole dug ",
                "in the ground, called a cyclone cellar, where the family could go in case one of ",
                "those great whirlwinds arose, mighty enough to crush any building in its path. It ",
                "was reached by a trap door in the middle of the floor, from which a ladder led ",
                "down into the small, dark hole.",

        }, oz2 = new String[oz.length];
        System.out.println("ORIGINAL:");
        for(String o : oz)
        {
            System.out.println(o);
        }
        System.out.println("\n\nGENERATED:\n");
        RNG sr = new RNG(2252637788195L);
        for(Language lang : languages) {
            Translator cipher = new Translator(lang, 41041041L);
            int ctr = 0;
            for (String s : oz) {
                oz2[ctr] = cipher.translate(s);
                System.out.println(oz2[ctr++]);
            }

            HashMap<String, String> vocabulary = new HashMap<String, String>(16);
            cipher.learnTranslations(vocabulary, "Dorothy", "farmer", "the", "room", "one", "uncle", "aunt");
            for (String s : oz2) {
                System.out.println(cipher.reverseTranslate(s, vocabulary));
            }
            System.out.println();
            for (String s : oz2) {
                System.out.println(cipher.reverseTranslate(s, cipher.reverseDictionary));
            }
            System.out.println();
        }
    }
}
