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
        for (int langi = 0; langi < Language.registered.length; langi++) {
            Language flg = Language.registered[langi];
            String name = Language.registeredNames[langi];
            rng.setState(0xf00df00L);
            System.out.println("\nImitating language: \"" + name + "\":\n");
            for (int i = 0; i < 40; i++) {
                System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";"},
                        new String[]{".", ".", "!", "?", "..."}, 0.14));
            }
        }
        Language flg;
        System.out.println("\nImitating language: \"Norse with simplified spelling\":\n");
        rng.setState(0xf00df00L);
        flg = Language.NORSE.addModifiers(Language.Modifier.SIMPLIFY_NORSE);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 9, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.14));
        }

        System.out.println("\n\nLANGUAGE MIXES:\n");

        System.out.println("\nImitating language: \"English 50%, French (no accents) 50%\":\n");
        rng.setState(0xf00df00L);
        flg = Language.ENGLISH.mix(Language.FRENCH.removeAccents(), 0.5);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        System.out.println("\nImitating language: \"Russian Romanized 65%, English 35%\":\n");
        rng.setState(0xf00df00L);
        flg = Language.RUSSIAN_ROMANIZED.mix(Language.ENGLISH, 0.35);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 4, 10, new String[]{",", ",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "..."}, 0.22));
        }
        System.out.println("\nImitating language: \"French 45%, Greek Romanized 55%\":\n");
        rng.setState(0xf00df00L);
        flg = Language.FRENCH.mix(Language.GREEK_ROMANIZED, 0.55);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.22));
        }
        System.out.println("\nImitating language: \"English 75%, Greek Authentic 25%\":\n");
        rng.setState(0xf00df00L);
        flg = Language.ENGLISH.mix(Language.GREEK_AUTHENTIC, 0.25);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 11, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        System.out.println("\nImitating language: \"English with added accents\":\n");
        rng.setState(0xf00df00L);
        flg = Language.ENGLISH.addAccents(0.5, 0.15);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }

        System.out.println("\nImitating language: \"French 35%, Japanese Romanized 65%\":\n");
        rng.setState(0xf00df00L);
        flg = Language.FRENCH.mix(Language.JAPANESE_ROMANIZED, 0.65);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";"},
                    new String[]{".", ".", "!", "?", "...", "..."}, 0.17));
        }

        System.out.println("\nImitating language: \"Russian Romanized 25%, Japanese Romanized 75%\":\n");
        rng.setState(0xf00df00L);
        flg = Language.RUSSIAN_ROMANIZED.mix(Language.JAPANESE_ROMANIZED, 0.75);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 6, 12, new String[]{",", ",", ",", ";", " -"},
                    new String[]{".", ".", ".", "!", "?", "...", "..."}, 0.2));
        }

        System.out.println("\nImitating language: \"English with no repeats of the same letter twice in a row\":\n");
        rng.setState(0xf00df00L);
        flg = Language.ENGLISH.addModifiers(Language.Modifier.NO_DOUBLES);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }
        System.out.println("\nImitating language: \"Japanese Romanized with frequent doubled consonants\":\n");
        rng.setState(0xf00df00L);
        flg = Language.JAPANESE_ROMANIZED.addModifiers(Language.Modifier.DOUBLE_CONSONANTS);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.18));
        }

        System.out.println("\nImitating language: \"Somali 63%, Japanese Romanized 27%, Swahili 10%\":\n");
        rng.setState(0xf00df00L);
        flg = Language.SOMALI.mix(Language.JAPANESE_ROMANIZED, 0.3).mix(Language.SWAHILI, 0.1);
        for (int i = 0; i < 40; i++) {
            System.out.println(flg.sentence(rng, 5, 12, new String[]{",", ",", ";"},
                    new String[]{".", ".", "!", "?", "..."}, 0.15));
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
                Language.randomLanguage(Utilities.hash64("Kittenish")),
                Language.randomLanguage(Utilities.hash64("Puppyspeak")),
                Language.randomLanguage(Utilities.hash64("Rabbitese")),
                Language.randomLanguage(Utilities.hash64("Rabbit Language")),
                Language.randomLanguage(Utilities.hash64("The Roar Of That Slumbering Shadow Which Mankind Wills Itself To Forget")),
                Language.INUKTITUT,
                Language.NORSE,
                Language.NORSE.addModifiers(Language.Modifier.SIMPLIFY_NORSE),
                Language.NAHUATL,
                Language.MONGOLIAN,
                Language.SIMPLISH,
                Language.KOREAN_ROMANIZED,
                Language.SOMALI.addModifiers(Language.modifier("([kd])h", "$1"),
                        Language.modifier("([pfsgkcb])([aeiouy])", "$1l$2", 0.35),
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
                Language.GOBLIN,
                Language.ELF,
                Language.DEMONIC,
                Language.INFERNAL,
                Language.DRAGON,
                Language.KOBOLD,
                Language.ALIEN_A,
                Language.ALIEN_E,
                Language.ALIEN_I,
                Language.ALIEN_O,
                Language.ALIEN_U,
                Language.INSECT,
                Language.MAORI,
                Language.SPANISH
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
