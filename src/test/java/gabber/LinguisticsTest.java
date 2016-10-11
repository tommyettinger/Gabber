package gabber;

import java.util.HashMap;

/**
 * Created by Tommy Ettinger on 11/29/2015.
 */
public class LinguisticsTest {
    public static void main(String[] args)
    {
        RNG rng = new RNG(0xf00df00L);
        Language flg = Language.ENGLISH;

        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 10, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.17));
        }
        rng.setState(0xf00df00L);
        flg = Language.LOVECRAFT;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 3, 9, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "!", "?", "...", "..."}, 0.15));
        }
        rng.setState(0xf00df00L);
        flg = Language.GREEK_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.2));
        }
        rng.setState(0xf00df00L);
        flg = Language.GREEK_AUTHENTIC;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.2));
        }
        rng.setState(0xf00df00L);
        flg = Language.FRENCH;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 12, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.17));
        }
        rng.setState(0xf00df00L);
        flg = Language.RUSSIAN_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 13, new String[]{",", ",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.25));
        }
        rng.setState(0xf00df00L);
        flg = Language.RUSSIAN_AUTHENTIC;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 13, new String[]{",", ",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.25));
        }
        rng.setState(0xf00df00L);
        flg = Language.JAPANESE_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 13, new String[]{",", ",", ",", ",", ";"},
                    new String[]{".", ".", ".", "!", "?", "...", "..."}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = Language.SWAHILI;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = Language.SOMALI;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }


        rng.setState(0xf00df00L);
        flg = Language.ENGLISH.mix(Language.FRENCH.removeAccents(), 0.5);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        rng.setState(0xf00df00L);
        flg = Language.RUSSIAN_ROMANIZED.mix(Language.ENGLISH, 0.35);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 10, new String[]{",", ",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.22));
        }
        rng.setState(0xf00df00L);
        flg = Language.FRENCH.mix(Language.GREEK_ROMANIZED, 0.55);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.22));
        }
        rng.setState(0xf00df00L);
        flg = Language.ENGLISH.mix(Language.GREEK_AUTHENTIC, 0.25);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        rng.setState(0xf00df00L);
        flg = Language.ENGLISH.addAccents(0.5, 0.15);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }

        rng.setState(0xf00df00L);
        flg = Language.FRENCH.mix(Language.JAPANESE_ROMANIZED, 0.65);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", "!", "?", "...", "..."}, 0.17));
        }

        rng.setState(0xf00df00L);
        flg = Language.RUSSIAN_ROMANIZED.mix(Language.JAPANESE_ROMANIZED, 0.75);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "...", "..."}, 0.2));
        }

        rng.setState(0xf00df00L);
        flg = Language.ENGLISH.addModifiers(Language.Modifier.NO_DOUBLES);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }

        rng.setState(0xf00df00L);
        flg = Language.JAPANESE_ROMANIZED.addModifiers(Language.Modifier.DOUBLE_CONSONANTS);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        rng.setState(0xf00df00L);
        flg = Language.SOMALI.mix(Language.JAPANESE_ROMANIZED, 0.3).mix(Language.SWAHILI, 0.1);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.15));
        }

        rng.setState(0xf00df00L);
        flg = Language.FANTASY_NAME;
        System.out.print(flg.word(rng, true, rng.between(2, 4)));
        for (int i = 1; i < 10; i++) {
            System.out.print(", " + flg.word(rng, true, rng.between(2, 4)));
        }
        System.out.println("...");

        rng.setState(0xf00df00L);
        flg = Language.FANCY_FANTASY_NAME;
        System.out.print(flg.word(rng, true, rng.between(2, 4)));
        for (int i = 1; i < 10; i++) {
            System.out.print(", " + flg.word(rng, true, rng.between(2, 4)));
        }
        System.out.println("...");
        System.out.println('"' + Language.ENGLISH.sentence(rng, 4, 7, new String[]{" -", ",", ",", ";"}, new String[]{"!", "!", "...", "...", ".", "?"}, 0.2) + "\",");
        System.out.println('"' + Language.JAPANESE_ROMANIZED.sentence(rng, 4, 7, new String[]{" -", ",", ",", ";"}, new String[]{"!", "!", "...", "...", ".", "?"}, 0.2) + "\",");
        System.out.println('"' + Language.FRENCH.sentence(rng, 5, 8, new String[]{" -", ",", ",", ";"}, new String[]{"!", "?", ".", "...", ".", "?"}, 0.1) + "\",");
        System.out.println('"' + Language.GREEK_ROMANIZED.sentence(rng, 5, 8, new String[]{",", ",", ";"}, new String[]{"!", "?", ".", "...", ".", "?"}, 0.15) + "\",");
        System.out.println('"' + Language.GREEK_AUTHENTIC.sentence(rng, 5, 8, new String[]{",", ",", ";"}, new String[]{"!", "?", ".", "...", ".", "?"}, 0.15) + "\",");
        System.out.println('"' + Language.RUSSIAN_ROMANIZED.sentence(rng, 4, 7, new String[]{" -", ",", ",", ",", ";"}, new String[]{"!", "!", ".", "...", ".", "?"}, 0.22) + "\",");
        System.out.println('"' + Language.RUSSIAN_AUTHENTIC.sentence(rng, 4, 7, new String[]{" -", ",", ",", ",", ";"}, new String[]{"!", "!", ".", "...", ".", "?"}, 0.22) + "\",");
        System.out.println('"' + Language.LOVECRAFT.sentence(rng, 4, 7, new String[]{" -", ",", ",", ";"}, new String[]{"!", "!", "...", "...", ".", "?"}, 0.2) + "\",");
        System.out.println('"' + Language.SWAHILI.sentence(rng, 4, 7, new String[]{",", ",", ";"}, new String[]{"!", "?", ".", ".", "."}, 0.12) + "\",");
        flg = Language.FRENCH.mix(Language.JAPANESE_ROMANIZED, 0.65);
        System.out.println('"' + flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                new String[]{".", ".", "!", "?", "...", "..."}, 0.17) + "\",");
        flg = Language.ENGLISH.addAccents(0.5, 0.15);
        System.out.println('"' + flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                new String[]{".", ".", "!", "?", "...", "..."}, 0.17) + "\",");
        flg = Language.RUSSIAN_AUTHENTIC.mix(Language.GREEK_AUTHENTIC, 0.5).mix(Language.FRENCH, 0.35);
        System.out.println('"' + flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";", " -"},
                new String[]{".", ".", "!", "?", "...", "..."}, 0.2) + "\",");
        flg = Language.FANCY_FANTASY_NAME;
        System.out.println('"' + flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";", " -"},
                new String[]{".", ".", "!", "?", "...", "..."}, 0.2) + "\",");
        flg = Language.SWAHILI.mix(Language.JAPANESE_ROMANIZED, 0.35); //.mix(Language.FRENCH, 0.35)
        System.out.println('"' + flg.sentence(rng, 4, 7, new String[]{",", ",", ";"},
                new String[]{"!", "?", ".", ".", "."}, 0.12) + "\",");
        flg = Language.SWAHILI.mix(Language.JAPANESE_ROMANIZED, 0.32).mix(Language.FANCY_FANTASY_NAME, 0.25);
        System.out.println('"' + flg.sentence(rng, 4, 7, new String[]{",", ",", ";"},
                new String[]{"!", "?", ".", ".", "."}, 0.12) + "\",");
        flg = Language.SOMALI.mix(Language.JAPANESE_ROMANIZED, 0.3).mix(Language.SWAHILI, 0.15);
        System.out.println('"' + flg.sentence(rng, 4, 7, new String[]{",", ",", ";"},
                new String[]{"!", "?", ".", ".", "."}, 0.15) + "\",");

        rng.setState(0xf00df00L);
        flg = Language.HINDI_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.12));
        }
        rng.setState(0xf00df00L);
        flg = Language.ARABIC_ROMANIZED;
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 9, new String[]{",", ",", ",", ";", ";"},
                    new String[]{".", ".", ".", "!", "?"}, 0.18));
        }
        System.out.println("\n-----------------------------------------------------------------------------");
        System.out.println();
        Language[] languages = new Language[]{

                Language.ENGLISH,
                Language.LOVECRAFT,
                Language.JAPANESE_ROMANIZED,
                Language.FRENCH,
                Language.GREEK_ROMANIZED,
                Language.GREEK_AUTHENTIC,
                Language.RUSSIAN_ROMANIZED,
                Language.RUSSIAN_AUTHENTIC,
                Language.SWAHILI,
                Language.SOMALI,
                Language.FANTASY_NAME,
                Language.FANCY_FANTASY_NAME,
                Language.ARABIC_ROMANIZED,
                Language.HINDI_ROMANIZED.removeAccents(),
                Language.RUSSIAN_ROMANIZED.mix(Language.SOMALI, 0.25),
                Language.GREEK_ROMANIZED.mix(Language.HINDI_ROMANIZED.removeAccents(), 0.5),
                Language.SWAHILI.mix(Language.FRENCH, 0.3),
                Language.ARABIC_ROMANIZED.mix(Language.JAPANESE_ROMANIZED, 0.4),
                Language.SWAHILI.mix(Language.GREEK_ROMANIZED, 0.4),
                Language.GREEK_ROMANIZED.mix(Language.SOMALI, 0.4),
                Language.ENGLISH.mix(Language.HINDI_ROMANIZED.removeAccents(), 0.4),
                Language.ENGLISH.mix(Language.JAPANESE_ROMANIZED, 0.4),
                Language.SOMALI.mix(Language.HINDI_ROMANIZED.removeAccents(), 0.4),
                Language.FRENCH.addModifiers(Language.modifier("([^aeiou])\\1", "$1ph", 0.3),
                        Language.modifier("([^aeiou])\\1", "$1ch", 0.4),
                        Language.modifier("([^aeiou])\\1", "$1sh", 0.5),
                        Language.modifier("([^aeiou])\\1", "$1", 0.9)),
                Language.JAPANESE_ROMANIZED.addModifiers(Language.Modifier.DOUBLE_VOWELS),
                Language.SOMALI.addModifiers(Language.modifier("([kd])h", "$1"),
                        Language.modifier("([pfsgkcb])([aeiouy])", "$1l$2", 0.35),
                        /*Language.modifier("a+", "á", 0.18),
                        Language.modifier("e+", "é", 0.18),
                        Language.modifier("i+", "í", 0.18),
                        Language.modifier("o+", "ó", 0.18),
                        Language.modifier("u+", "ú", 0.18),*/
                        //Language.modifier("aa", "au"),
                        Language.modifier("ii", "ai"),
                        Language.modifier("uu", "ia"),
                        Language.modifier("([aeo])\\1", "$1"),
                        Language.modifier("^x", "v"),
                        Language.modifier("([^aeiou]|^)u([^aeiou]|$)", "$1a$2", 0.6),
                        Language.modifier("([aeiou])[^aeiou]([aeiou])", "$1v$2", 0.06),
                        Language.modifier("([aeiou])[^aeiou]([aeiou])", "$1l$2", 0.07),
                        Language.modifier("([aeiou])[^aeiou]([aeiou])", "$1n$2", 0.07),
                        Language.modifier("([aeiou])[^aeiou]([aeiou])", "$1z$2", 0.08),
                        Language.modifier("([^aeiou])[aeiou]+$", "$1ia", 0.35),
                        Language.modifier("([^aeiou])[bpdtkgj]", "$1"),
                        Language.modifier("[jg]$", "th"),
                        Language.modifier("g", "c", 0.92),
                        Language.modifier("([aeiou])[wy]$", "$1l", 0.6),
                        Language.modifier("([aeiou])[wy]$", "$1n"),
                        Language.modifier("[qf]$", "l", 0.4),
                        Language.modifier("[qf]$", "n", 0.65),
                        Language.modifier("[qf]$", "s"),
                        Language.modifier("cy", "sp"),
                        Language.modifier("kl", "sk"),
                        Language.modifier("qu+", "qui"),
                        Language.modifier("q([^u])", "qu$1"),
                        Language.modifier("cc", "ch"),
                        Language.modifier("[^aeiou]([^aeiou][^aeiou])", "$1"),
                        Language.Modifier.NO_DOUBLES
                ),
                Language.randomLanguage("Kittenish"),
                Language.randomLanguage("Puppyspeak"),
                Language.randomLanguage("Rabbitese"),
                Language.randomLanguage("Rabbit Language"),
                Language.randomLanguage("The Roar Of That Slumbering Shadow Which Mankind Wills Itself To Forget"),
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
