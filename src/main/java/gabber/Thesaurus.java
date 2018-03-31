package gabber;

import regexodus.*;

import java.io.Serializable;
import java.util.*;

/**
 * A text processing class that can swap out occurrences of words and replace them with their synonyms.
 * Created by Tommy Ettinger on 5/23/2016.
 */
public class Thesaurus implements Serializable{
    private static final long serialVersionUID = 3387639905758074640L;
    protected static final Pattern wordMatch = Pattern.compile("([\\pL`]+)"),
            similarFinder = Pattern.compile(".*?\\b(\\w\\w\\w\\w).*?{\\@1}.*$", "ui");
    public HashMap<String, GapShuffler<String>> mappings;
    protected RNG rng;
    public transient ArrayList<Language> randomLanguages = new ArrayList<Language>(2);

    /**
     * Constructs a new Thesaurus with an unseeded RNG used to shuffle word order.
     */
    public Thesaurus()
    {
        mappings = new HashMap<String, GapShuffler<String>>(256);
        rng = new RNG();
    }

    /**
     * Constructs a new Thesaurus, seeding its RNG (used to shuffle word order) with the next long from the given RNG.
     * @param rng an RNG that will only be used to get one long (for seeding this class' RNG)
     */
    public Thesaurus(RNG rng)
    {
        mappings = new HashMap<String, GapShuffler<String>>(256);
        this.rng = new RNG(rng.nextLong());
    }

    /**
     * Constructs a new Thesaurus, seeding its RNG (used to shuffle word order) with shuffleSeed.
     * @param shuffleSeed a long for seeding this class' RNG
     */
    public Thesaurus(long shuffleSeed)
    {
        mappings = new HashMap<String, GapShuffler<String>>(256);
        this.rng = new RNG(shuffleSeed);
    }


    /**
     * Constructs a new Thesaurus, seeding its RNG (used to shuffle word order) with shuffleSeed.
     * @param shuffleSeed a String for seeding this class' RNG
     */
    public Thesaurus(String shuffleSeed)
    {
        mappings = new HashMap<String, GapShuffler<String>>(256);
        this.rng = new RNG(Utilities.hash64(shuffleSeed));
    }

    /**
     * Allows this Thesaurus to find the exact words in synonyms and, when requested, replace each occurrence with a
     * different word from the same List. Each word in synonyms should have the same part of speech, so "demon"
     * and "devils" should not be in the same list of synonyms (singular noun and plural noun), but "demon" and "devil"
     * could be (each is a singular noun). The Strings in synonyms should all be lower-case, since case is picked up
     * from the text as it is being replaced and not from the words themselves. Proper nouns should normally not be used
     * as synonyms, since this could get very confusing if it changed occurrences of "Germany" to "France" at random and
     * a character's name, like "Dorothy", to "Anne", "Emily", "Cynthia", etc. in the middle of a section about Dorothy.
     * The word matching pattern this uses only matches all-letter words, not words that contain hyphens, apostrophes,
     * or other punctuation.
     * @param synonyms a List of lower-case Strings with similar meaning and the same part of speech
     * @return this for chaining
     */
    public Thesaurus addSynonyms(List<String> synonyms)
    {
        if(synonyms.isEmpty())
            return this;
        long prevState = rng.getState();
        rng.setState(Utilities.hash64(synonyms));
        GapShuffler<String> shuffler = new GapShuffler<String>(synonyms, rng);
        for(String syn : synonyms)
        {
            mappings.put(syn, shuffler);
        }
        rng.setState(prevState);
        return this;
    }

    /**
     * Allows this Thesaurus to replace a specific keyword, typically containing multiple backtick characters ('`') so
     * it can't be confused with a "real word," with one of the words in synonyms (chosen in shuffled order). The
     * backtick is the only punctuation character that this class' word matcher considers part of a word, both for this
     * reason and because it is rarely used in English text.
     * @param keyword a word (typically containing backticks, '`') that will be replaced by a word from synonyms
     * @param synonyms a Collection of lower-case Strings with similar meaning and the same part of speech
     * @return this for chaining
     */
    public Thesaurus addCategory(String keyword, List<String> synonyms)
    {
        if(synonyms.isEmpty())
            return this;
        long prevState = rng.getState();
        rng.setState(Utilities.hash64(synonyms));
        GapShuffler<String> shuffler = new GapShuffler<String>(synonyms, rng);
        mappings.put(keyword, shuffler);
        rng.setState(prevState);
        return this;
    }

    /**
     * Adds several pre-made categories to this Thesaurus' known categories, but won't cause it to try to replace normal
     * words with synonyms (only categories, which contain backticks in the name). The keywords this currently knows,
     * and the words it will replace those keywords with, are:
     * <br>
     * <ul>
     *     <li>"calm`adj`": harmonious, peaceful, pleasant, serene, placid, tranquil, calm</li>
     *     <li>"calm`noun`": harmony, peace, kindness, serenity, tranquility, calm</li>
     *     <li>"org`noun`": fraternity, brotherhood, order, group, foundation, association, guild, fellowship, partnership</li>
     *     <li>"org`nouns`": fraternities, brotherhoods, orders, groups, foundations, associations, guilds, fellowships, partnerships</li>
     *     <li>"empire`adj`": imperial, prince's, king's, sultan's, regal, dynastic, royal, hegemonic, monarchic, ascendant, emir's, lordly</li>
     *     <li>"empire`noun`": empire, emirate, kingdom, sultanate, dominion, dynasty, imperium, hegemony, triumvirate, ascendancy, monarchy, commonwealth</li>
     *     <li>"empire`nouns`": empires, emirates, kingdoms, sultanates, dominions, dynasties, imperia, hegemonies, triumvirates, ascendancies, monarchies, commonwealths</li>
     *     <li>"union`adj`": united, allied, people's, confederated, federated, congressional, independent, associated, unified, democratic</li>
     *     <li>"union`noun`": union, alliance, coalition, confederation, federation, congress, confederacy, league, faction, republic</li>
     *     <li>"union`nouns`": unions, alliances, coalitions, confederations, federations, congresses, confederacies, leagues, factions, republics</li>
     *     <li>"militia`noun`": rebellion, resistance, militia, liberators, warriors, fighters, militants, front, irregulars</li>
     *     <li>"militia`nouns`": rebellions, resistances, militias, liberators, warriors, fighters, militants, fronts, irregulars</li>
     *     <li>"gang`noun`": gang, syndicate, mob, crew, posse, mafia, cartel</li>
     *     <li>"gang`nouns`": gangs, syndicates, mobs, crews, posses, mafias, cartels</li>
     *     <li>"duke`noun`": duke, earl, baron, fief, lord, shogun</li>
     *     <li>"duke`nouns`": dukes, earls, barons, fiefs, lords, shoguns</li>
     *     <li>"duchy`noun`": duchy, earldom, barony, fiefdom, lordship, shogunate</li>
     *     <li>"duchy`nouns`": duchies, earldoms, baronies, fiefdoms, lordships, shogunates</li>
     *     <li>"magical`adj`": arcane, enchanted, sorcerous, ensorcelled, magical, mystical</li>
     *     <li>"holy`adj`": auspicious, divine, holy, sacred, prophetic, blessed, godly</li>
     *     <li>"unholy`adj`": bewitched, occult, unholy, macabre, accursed, profane, vile</li>
     *     <li>"forest`adj`": natural, primal, verdant, lush, fertile, bountiful</li>
     *     <li>"forest`noun`": nature, forest, greenery, jungle, woodland, grove, copse</li>
     *     <li>"fancy`adj`": grand, glorious, magnificent, magnanimous, majestic, great, powerful</li>
     *     <li>"evil`adj`": heinous, scurrilous, terrible, horrible, debased, wicked, evil, malevolent, nefarious, vile</li>
     *     <li>"good`adj`": righteous, moral, good, pure, compassionate, flawless, perfect</li>
     *     <li>"sinister`adj`": shadowy, silent, lethal, deadly, fatal, venomous, cutthroat, murderous, bloodstained, stalking</li>
     *     <li>"sinister`noun`": shadow, silence, assassin, ninja, venom, poison, snake, murder, blood, razor, tiger</li>
     *     <li>"blade`noun`": blade, knife, sword, axe, stiletto, katana, scimitar, hatchet, spear, glaive, halberd,
     *               hammer, maul, flail, mace, sickle, scythe, whip, lance, nunchaku, saber, cutlass, trident</li>
     *     <li>"bow`noun`": bow, longbow, shortbow, crossbow, sling, atlatl, bolas, javelin, net, shuriken, dagger</li>
     *     <li>"weapon`noun`": blade, knife, sword, axe, stiletto, katana, scimitar, hatchet, spear, glaive, halberd,
     *               hammer, maul, flail, mace, sickle, scythe, whip, lance, nunchaku, saber, cutlass, trident,
     *               bow, longbow, shortbow, crossbow, sling, atlatl, bolas, javelin, net, shuriken, dagger</li>
     *     <li>"musket`noun`": arquebus, blunderbuss, musket, matchlock, flintlock, wheellock, cannon</li>
     *     <li>"grenade`noun`": rocket, grenade, missile, bomb, warhead, explosive, flamethrower</li>
     *     <li>"rifle`noun`": pistol, rifle, handgun, firearm, longarm, shotgun</li>
     *     <li>"blade`nouns`": blades, knives, swords, axes, stilettos, katana, scimitars, hatchets, spears, glaives, halberds,
     *               hammers, mauls, flails, maces, sickles, scythes, whips, lances, nunchaku, sabers, cutlasses, tridents</li>
     *     <li>"bow`nouns`": bows, longbows, shortbows, crossbows, slings, atlatls, bolases, javelins, nets, shuriken, daggers</li>
     *     <li>"weapon`nouns`": blades, knives, swords, axes, stilettos, katana, scimitars, hatchets, spears, glaives, halberds,
     *               hammers, mauls, flails, maces, sickles, scythes, whips, lances, nunchaku, sabers, cutlasses, tridents,
     *               bows, longbows, shortbows, crossbows, slings, atlatls, bolases, javelins, nets, shuriken, daggers</li>
     *     <li>"musket`nouns`": arquebusses, blunderbusses, muskets, matchlocks, flintlocks, wheellocks, cannons</li>
     *     <li>"grenade`nouns`": rockets, grenades, missiles, bombs, warheads, explosives, flamethrowers</li>
     *     <li>"rifle`nouns`": pistols, rifles, handguns, firearms, longarms, shotguns</li>
     *     <li>"tech`adj`": cyber, digital, electronic, techno, hacker, crypto, turbo, mechanical, servo</li>
     *     <li>"sole`adj`": sole, true, singular, total, ultimate, final, last</li>
     *     <li>"light`adj`": bright, glowing, solar, stellar, lunar, radiant, luminous, shimmering</li>
     *     <li>"light`noun`": light, glow, sun, star, moon, radiance, dawn, torch</li>
     *     <li>"light`nouns`": lights, glimmers, suns, stars, moons, torches</li>
     *     <li>"smart`adj`": brilliant, smart, genius, wise, clever, cunning, mindful, aware</li>
     *     <li>"smart`noun`": genius, wisdom, cunning, awareness, mindfulness, acumen, smarts, knowledge</li>
     *     <li>"bandit`noun`": thief, raider, bandit, rogue, brigand, highwayman, pirate</li>
     *     <li>"bandit`nouns`": thieves, raiders, bandits, rogues, brigands, highwaymen, pirates</li>
     *     <li>"guard`noun`": protector, guardian, warden, defender, guard, shield, sentinel, watchman, knight</li>
     *     <li>"guard`nouns`": protectors, guardians, wardens, defenders, guards, shields, sentinels, watchmen, knights</li>
     *     <li>"rage`noun`": rage, fury, anger, wrath, frenzy, vengeance</li>
     * </ul>
     * Capitalizing the first letter in the keyword where it appears in text you call process() on will capitalize the
     * first letter of the produced fake word. Capitalizing the second letter will capitalize the whole produced fake
     * word. This applies only per-instance of each keyword; it won't change the internally-stored list of words.
     * @return this for chaining
     */
    public Thesaurus addKnownCategories()
    {
        for(Map.Entry<String, List<String>> kv : categories.entrySet())
        {
            addCategory(kv.getKey(), kv.getValue());
        }
        return this;
    }

    /**
     * Adds a large list of words pre-generated by Language and hand-picked for fitness, and makes them
     * accessible with a keyword based on the language and any tweaks made to it. The keywords this currently knows:
     * <br>
     * <ul>
     *     <li>"jp`gen`": Imitation Japanese</li>
     *     <li>"fr`gen`": Imitation French; contains some accented chars</li>
     *     <li>"gr`gen`": Imitation Greek (romanized)</li>
     *     <li>"ru`gen`": Imitation Russian (romanized)</li>
     *     <li>"sw`gen`": Imitation Swahili</li>
     *     <li>"so`gen`": Imitation Somali</li>
     *     <li>"en`gen`": Imitation English (not very good on its own)</li>
     *     <li>"ar`gen`": Imitation Arabic (not very close to actual Arabic; uses a rough romanization technique)</li>
     *     <li>"hi`gen`": Imitation Hindi (romanized but with accents removed)</li>
     *     <li>"fn`gen`": Fantasy Names; styled after the possibly-Europe-like names common in fantasy books</li>
     *     <li>"fn`acc`gen`": Fancy Fantasy Names; the same as "fn`gen`", but with lots of accented chars</li>
     *     <li>"lc`gen`": Lovecraft; styled after the names of creatures from H.P. Lovecraft's Cthulhu Mythos</li>
     *     <li>"ru`so`gen`": Mix of imitation Russian (75%) and Somali (25%)</li>
     *     <li>"gr`hi`gen`": Mix of imitation Greek (50%) and Hindi (50%)</li>
     *     <li>"sw`fr`gen`": Mix of imitation Swahili (70%) and French (30%)</li>
     *     <li>"ar`jp`gen`": Mix of imitation Arabic (60%) and Japanese (40%)</li>
     *     <li>"sw`gr`gen`": Mix of imitation Swahili (60%) and Greek (40%)</li>
     *     <li>"gr`so`gen`": Mix of imitation Greek (60%) and Somali (40%)</li>
     *     <li>"en`hi`gen`": Mix of imitation English (60%) and Hindi (40%)</li>
     *     <li>"en`jp`gen`": Mix of imitation English (60%) and Japanese (40%)</li>
     *     <li>"so`hi`gen`": Mix of imitation Somali (60%) and Hindi (40%)</li>
     *     <li>"ru`gr`gen`": Mix of imitation Russian (60%) and Greek (40%)</li>
     *     <li>"lc`gr`gen`": Mix of Lovecraft-styled names (60%) and imitation Russian (40%)</li>
     *     <li>"fr`mod`gen`": Imitation French; modified to replace doubled consonants like "gg" with "gsh" or similar</li>
     *     <li>"jp`mod`gen`": Imitation Japanese; modified to sometimes double vowels from "a" to "aa" or similar</li>
     *     <li>"so`mod`gen`": Imitation Somali (not really); modified beyond recognition and contains accents</li>
     * </ul>
     * Capitalizing the first letter in the keyword where it appears in text you call process() on will capitalize the
     * first letter of the produced fake word, which is often desirable for things like place names. Capitalizing the
     * second letter will capitalize the whole produced fake word. This applies only per-instance of each keyword; it
     * won't change the internally-stored list of words.
     * @return this for chaining
     */
    public Thesaurus addImitationWords()
    {
        for(Map.Entry<String, List<String>> kv : languages.entrySet())
        {
            addCategory(kv.getKey(), kv.getValue());
        }
        return this;
    }

    /**
     * Given a String, StringBuilder, or other CharSequence that should contain words this knows synonyms for, this
     * replaces each occurrence of such a known word with one of its synonyms, leaving unknown words untouched. Words
     * that were learned together as synonyms with addSynonyms() will be replaced in such a way that an individual
     * replacement word should not occur too close to a previous occurrence of the same word; that is, replacing the
     * text "You fiend! You demon! You despoiler of creation; devil made flesh!", where "fiend", "demon", and "devil"
     * are all synonyms, would never produce a string that contained "fiend" as the replacement for all three of those.
     * @param text a CharSequence, such as a String, that contains words in the source language
     * @return a String of the translated text.
     */
    public String process(CharSequence text)
    {
        Replacer rep = wordMatch.replacer(new SynonymSubstitution());
        return rep.replace(text);
    }

    public String lookup(String word)
    {
        if(word.isEmpty())
            return word;
        String word2 = word.toLowerCase();
        if(mappings.containsKey(word2))
        {
            String nx = mappings.get(word2).next();
            if(nx.isEmpty())
                return nx;
            if(word.length() > 1 && Category.Lu.contains(word.charAt(1)))
                return nx.toUpperCase();
            if(Category.Lu.contains(word.charAt(0)))
            {
                return Character.toUpperCase(nx.charAt(0)) + nx.substring(1, nx.length());
            }
            return nx;
        }
        return word;
    }

    private class SynonymSubstitution implements Substitution
    {
        @Override
        public void appendSubstitution(MatchResult match, TextBuffer dest) {
            dest.append(lookup(match.group(0)));
        }
    }

    private class RandomLanguageSubstitution implements Substitution
    {
        @Override
        public void appendSubstitution(MatchResult match, TextBuffer dest) {
            Language lang = Language.randomLanguage(rng.nextLong());
            randomLanguages.add(lang);
            dest.append(lang.word(rng, true));
        }
    }

    /**
     * Generates a random possible name for a nation, such as "Iond-Gouccief Alliance" or "The Last Drayo Commonwealth".
     * Needs {@link #addKnownCategories()} to be called on this Thesaurus first. May use accented characters, as in
     * "Thùdshù-Hyóttiálb Hegemony" or "The Glorious Chô Empire"; if you want to strip these out and replace accented
     * chars with their un-accented counterparts, you can use {@link Language#removeAccents(CharSequence)}, which
     * returns a CharSequence that can be converted to String if needed. Shortly after calling this method, but before
     * calling it again, you can retrieve the generated random languages, if any were used while making nation names, by
     * getting the Language elements of this class' {@link #randomLanguages} field. Using one of these
     * Language objects, you can produce many more words with a similar style to the nation name, like "Drayo" in
     * "The Last Drayo Commonwealth". If more than one language was used in the nation name, as in "Thùdshù-Hyóttiálb
     * Hegemony", you will have two languages in randomLanguages, so here "Thùdshù" would be generated by the first
     * language, and "Hyóttiálb" by the second language. Calling this method replaces the current contents of
     * randomLanguages, so if you want to use those languages, get them while you can.
     *
     * @return a random name for a nation or a loose equivalent to a nation, as a String
     */
    public String makeNationName()
    {
        String working = process(rng.getRandomElement(nationTerms));
        int frustration = 0;
        while (frustration++ < 8 && similarFinder.matches(working))
            working = process(rng.getRandomElement(nationTerms));
        randomLanguages.clear();
        RandomLanguageSubstitution sub = new RandomLanguageSubstitution();
        Replacer replacer = Pattern.compile("@").replacer(sub);
        return replacer.replace(working);
    }

    private static final String[] nationTerms = new String[]{
            "Union`adj` Union`noun` of @", "Union`adj` @ Union`noun`", "@ Union`noun`", "@ Union`noun`", "@-@ Union`noun`", "Union`adj` Union`noun` of @",
            "Union`adj` Duchy`nouns` of @",  "The @ Duchy`noun`", "The Fancy`adj` @ Duchy`noun`", "The Sole`adj` @ Empire`noun`",
            "@ Empire`noun`", "@ Empire`noun`", "@ Empire`noun`", "@-@ Empire`noun`", "The Fancy`adj` @ Empire`noun`", "The Fancy`adj` @ Empire`noun`", "The Holy`adj` @ Empire`noun`",};

    public static final HashMap<String, List<String>> categories = new HashMap<String, List<String>>(64),
            languages = new HashMap<String, List<String>>(32);
    static {
        categories.put("calm`adj`", Arrays.asList("harmonious", "peaceful", "pleasant", "serene", "placid", "tranquil", "calm"));
        categories.put("calm`noun`", Arrays.asList("harmony", "peace", "kindness", "serenity", "tranquility", "calm"));
        categories.put("org`noun`", Arrays.asList("fraternity", "brotherhood", "order", "group", "foundation", "association", "guild", "fellowship", "partnership"));
        categories.put("org`nouns`", Arrays.asList("fraternities", "brotherhoods", "orders", "groups", "foundations", "associations", "guilds", "fellowships", "partnerships"));
        categories.put("empire`adj`", Arrays.asList("imperial", "prince's", "king's", "sultan's", "regal", "dynastic", "royal", "hegemonic", "monarchic", "ascendant", "emir's", "lordly"));
        categories.put("empire`noun`", Arrays.asList("empire", "emirate", "kingdom", "sultanate", "dominion", "dynasty", "imperium", "hegemony", "triumvirate", "ascendancy", "monarchy", "commonwealth"));
        categories.put("empire`nouns`", Arrays.asList("empires", "emirates", "kingdoms", "sultanates", "dominions", "dynasties", "imperia", "hegemonies", "triumvirates", "ascendancies", "monarchies", "commonwealths"));
        categories.put("union`adj`", Arrays.asList("united", "allied", "people's", "confederated", "federated", "congressional", "independent", "associated", "unified", "democratic"));
        categories.put("union`noun`", Arrays.asList("union", "alliance", "coalition", "confederation", "federation", "congress", "confederacy", "league", "faction", "republic"));
        categories.put("union`nouns`", Arrays.asList("unions", "alliances", "coalitions", "confederations", "federations", "congresses", "confederacies", "leagues", "factions", "republics"));
        categories.put("militia`noun`", Arrays.asList("rebellion", "resistance", "militia", "liberators", "warriors", "fighters", "militants", "front", "irregulars"));
        categories.put("militia`nouns`", Arrays.asList("rebellions", "resistances", "militias", "liberators", "warriors", "fighters", "militants", "fronts", "irregulars"));
        categories.put("gang`noun`", Arrays.asList("gang", "syndicate", "mob", "crew", "posse", "mafia", "cartel"));
        categories.put("gang`nouns`", Arrays.asList("gangs", "syndicates", "mobs", "crews", "posses", "mafias", "cartels"));
        categories.put("duke`noun`", Arrays.asList("duke", "earl", "baron", "fief", "lord", "shogun"));
        categories.put("duke`nouns`", Arrays.asList("dukes", "earls", "barons", "fiefs", "lords", "shoguns"));
        categories.put("duchy`noun`", Arrays.asList("duchy", "earldom", "barony", "fiefdom", "lordship", "shogunate"));
        categories.put("duchy`nouns`", Arrays.asList("duchies", "earldoms", "baronies", "fiefdoms", "lordships", "shogunates"));
        categories.put("magical`adj`", Arrays.asList("arcane", "enchanted", "sorcerous", "ensorcelled", "magical", "mystical"));
        categories.put("holy`adj`", Arrays.asList("auspicious", "divine", "holy", "sacred", "prophetic", "blessed", "godly"));
        categories.put("unholy`adj`", Arrays.asList("bewitched", "occult", "unholy", "macabre", "accursed", "profane", "vile"));
        categories.put("forest`adj`", Arrays.asList("natural", "primal", "verdant", "lush", "fertile", "bountiful"));
        categories.put("forest`noun`", Arrays.asList("nature", "forest", "greenery", "jungle", "woodland", "grove", "copse"));
        categories.put("fancy`adj`", Arrays.asList("grand", "glorious", "magnificent", "magnanimous", "majestic", "great", "powerful"));
        categories.put("evil`adj`", Arrays.asList("heinous", "scurrilous", "terrible", "horrible", "debased", "wicked", "evil", "malevolent", "nefarious", "vile"));
        categories.put("good`adj`", Arrays.asList("righteous", "moral", "good", "pure", "compassionate", "flawless", "perfect"));
        categories.put("sinister`adj`", Arrays.asList("shadowy", "silent", "lethal", "deadly", "fatal", "venomous", "cutthroat", "murderous", "bloodstained", "stalking"));
        categories.put("sinister`noun`", Arrays.asList("shadow", "silence", "assassin", "ninja", "venom", "poison", "snake", "murder", "blood", "razor", "tiger"));
        categories.put("blade`noun`", Arrays.asList("blade", "knife", "sword", "axe", "stiletto", "katana", "scimitar", "hatchet", "spear", "glaive", "halberd",
                "hammer", "maul", "flail", "mace", "sickle", "scythe", "whip", "lance", "nunchaku", "saber", "cutlass", "trident"));
        categories.put("bow`noun`", Arrays.asList("bow", "longbow", "shortbow", "crossbow", "sling", "atlatl", "bolas", "javelin", "net", "shuriken", "dagger"));
        categories.put("weapon`noun`", Arrays.asList("blade", "knife", "sword", "axe", "stiletto", "katana", "scimitar", "hatchet", "spear", "glaive", "halberd",
                "hammer", "maul", "flail", "mace", "sickle", "scythe", "whip", "lance", "nunchaku", "saber", "cutlass", "trident",
                "bow", "longbow", "shortbow", "crossbow", "sling", "atlatl", "bolas", "javelin", "net", "shuriken", "dagger"));
        categories.put("musket`noun`", Arrays.asList("arquebus", "blunderbuss", "musket", "matchlock", "flintlock", "wheellock", "cannon"));
        categories.put("grenade`noun`", Arrays.asList("rocket", "grenade", "missile", "bomb", "warhead", "explosive", "flamethrower"));
        categories.put("rifle`noun`", Arrays.asList("pistol", "rifle", "handgun", "firearm", "longarm", "shotgun"));
        categories.put("blade`nouns`", Arrays.asList("blades", "knives", "swords", "axes", "stilettos", "katana", "scimitars", "hatchets", "spears", "glaives", "halberds",
                "hammers", "mauls", "flails", "maces", "sickles", "scythes", "whips", "lances", "nunchaku", "sabers", "cutlasses", "tridents"));
        categories.put("bow`nouns`", Arrays.asList("bows", "longbows", "shortbows", "crossbows", "slings", "atlatls", "bolases", "javelins", "nets", "shuriken", "daggers"));
        categories.put("weapon`nouns`", Arrays.asList("blades", "knives", "swords", "axes", "stilettos", "katana", "scimitars", "hatchets", "spears", "glaives", "halberds",
                "hammers", "mauls", "flails", "maces", "sickles", "scythes", "whips", "lances", "nunchaku", "sabers", "cutlasses", "tridents",
                "bows", "longbows", "shortbows", "crossbows", "slings", "atlatls", "bolases", "javelins", "nets", "shuriken", "daggers"));
        categories.put("musket`nouns`", Arrays.asList("arquebusses", "blunderbusses", "muskets", "matchlocks", "flintlocks", "wheellocks", "cannons"));
        categories.put("grenade`nouns`", Arrays.asList("rockets", "grenades", "missiles", "bombs", "warheads", "explosives", "flamethrowers"));
        categories.put("rifle`nouns`", Arrays.asList("pistols", "rifles", "handguns", "firearms", "longarms", "shotguns"));
        categories.put("tech`adj`", Arrays.asList("cyber", "digital", "electronic", "techno", "hacker", "crypto", "turbo", "mechanical", "servo"));
        categories.put("sole`adj`", Arrays.asList("sole", "true", "singular", "total", "ultimate", "final", "last"));
        categories.put("light`adj`", Arrays.asList("bright", "glowing", "solar", "stellar", "lunar", "radiant", "luminous", "shimmering"));
        categories.put("light`noun`", Arrays.asList("light", "glow", "sun", "star", "moon", "radiance", "dawn", "torch"));
        categories.put("light`nouns`", Arrays.asList("lights", "glimmers", "suns", "stars", "moons", "torches"));
        categories.put("smart`adj`", Arrays.asList("brilliant", "smart", "genius", "wise", "clever", "cunning", "mindful", "aware"));
        categories.put("smart`noun`", Arrays.asList("genius", "wisdom", "cunning", "awareness", "mindfulness", "acumen", "smarts", "knowledge"));
        categories.put("bandit`noun`", Arrays.asList("thief", "raider", "bandit", "rogue", "brigand", "highwayman", "pirate"));
        categories.put("bandit`nouns`", Arrays.asList("thieves", "raiders", "bandits", "rogues", "brigands", "highwaymen", "pirates"));
        categories.put("guard`noun`", Arrays.asList("protector", "guardian", "warden", "defender", "guard", "shield", "sentinel", "watchman", "knight"));
        categories.put("guard`nouns`", Arrays.asList("protectors", "guardians", "wardens", "defenders", "guards", "shields", "sentinels", "watchmen", "knights"));
        categories.put("rage`noun`", Arrays.asList("rage", "fury", "anger", "wrath", "frenzy", "vengeance"));

        languages.put("lc`gen`", Arrays.asList("lahatos", "iatsiltak", "hmimrekaarl", "yixaltaishk", "cthupaxa", "zvroggamraa", "ixaakran"));
        languages.put("jp`gen`", Arrays.asList("naimoken", "kishigu", "houdaibo", "souchaya", "aijake", "hyazuran", "pajokke", "sokkimou"));
        languages.put("fr`gen`", Arrays.asList("devive", "esiggoi", "afaddouille", "roiquide", "obaploui", "baîmefi", "vêggrôste", "blaçeglè", "bamissecois"));
        languages.put("gr`gen`", Arrays.asList("lemabus", "ithonxeum", "etoneoch", "eirkuirstes", "taunonkos", "krailozes", "amarstei", "psorsteomium"));
        languages.put("ru`gen`", Arrays.asList("belyvia", "tiuzhiskit", "dazyved", "dabrisazetsky", "shaskianyr", "goskabad", "deblieskib", "neskagre"));
        languages.put("sw`gen`", Arrays.asList("mzabandu", "nzaloi", "tamzamda", "anzibo", "jamsala", "latazi", "faazaza", "uzoge", "mbomuta", "nbasonga"));
        languages.put("so`gen`", Arrays.asList("daggidda", "xabuumaq", "naadhana", "goquusad", "baxiltuu", "qooddaddut", "mosumyuuc", "uggular", "jaabacyut"));
        languages.put("en`gen`", Arrays.asList("thabbackion", "joongipper", "urbigsus", "otsaffet", "pittesely", "ramesist", "elgimmac", "genosont", "bessented"));
        languages.put("fn`gen`", Arrays.asList("kemosso", "venzendo", "tybangue", "evendi", "ringamye", "drayusta", "acleutos", "nenizo", "ifelle", "rytoudo"));
        languages.put("fn`acc`gen`", Arrays.asList("tánzeku", "nìāfőshi", "ñoffêfès", "áfŏmu", "drĕstishű", "pyeryĕquı", "bėdĕbǽ", "nęìjônne", "mainűthî"));
        languages.put("ar`gen`", Arrays.asList("iibaatuu", "wiilnalza", "ulanzha", "jaliifa", "iqaddiz", "waatufaa", "lizhuqa", "qinzaamju", "zuzuri"));
        languages.put("hi`gen`", Arrays.asList("maghubadhit", "bhunasu", "ipruja", "dhuevasam", "nubudho", "ghasaibi", "virjorghu", "khlindairai", "irsinam"));
        languages.put("ru`so`gen`", Arrays.asList("tserokyb", "zhieziufoj", "bisaskug", "nuriesyv", "gybared", "bableqa", "pybadis", "wiuskoglif", "zakalieb"));
        languages.put("gr`hi`gen`", Arrays.asList("takhada", "bepsegos", "ovukhrim", "sinupiam", "nabogon", "umianum", "dhainukotron", "muisaithi", "aerpraidha"));
        languages.put("sw`fr`gen`", Arrays.asList("nchaleûja", "soëhusi", "nsavarço", "fambofai", "namyàmse", "mfonsapa", "zalasha", "hiplaîpu", "hœumyemza"));
        languages.put("ar`jp`gen`", Arrays.asList("jukkaizhi", "hibiikkiiz", "shomela", "qhabohuz", "isiikya", "akkirzuh", "jalukhmih", "uujajon", "ryaataibna"));
        languages.put("sw`gr`gen`", Arrays.asList("ozuxii", "muguino", "nauteicha", "mjixazi", "yataya", "pomboirki", "achuiga", "maleibe", "psizeso", "njameichim"));
        languages.put("gr`so`gen`", Arrays.asList("xaaxoteum", "basaalii", "azaibe", "oupeddom", "pseiqigioh", "garkame", "uddoulis", "jobegos", "eqisol"));
        languages.put("en`hi`gen`", Arrays.asList("promolchi", "dhontriso", "gobhamblom", "hombangot", "sutsidalm", "dhindhinaur", "megsesa", "skaghinma", "thacebha"));
        languages.put("en`jp`gen`", Arrays.asList("nyintazu", "haxinsen", "kedezorp", "angapec", "donesalk", "ranepurgy", "laldimyi", "ipprijain", "bizinni"));
        languages.put("so`hi`gen`", Arrays.asList("yiteevadh", "omithid", "qugadhit", "nujagi", "nidogish", "danurbha", "sarojik", "cigafo", "tavodduu", "huqoyint"));
        languages.put("fr`mod`gen`", Arrays.asList("egleidô", "glaiemegchragne", "veçebun", "aubudaî", "peirquembrut", "eglecque", "marçoimeaux", "jêmbrégshre"));
        languages.put("jp`mod`gen`", Arrays.asList("dotobyu", "nikinaan", "gimoummee", "aanzaro", "ryasheeso", "aizaizo", "nyaikkashaa", "kitaani", "maabyopai"));
        languages.put("so`mod`gen`", Arrays.asList("sanata", "ájisha", "soreeggár", "quágeleu", "abaxé", "tedora", "bloxajac", "tiblarxo", "oodagí", "jélebi"));
        languages.put("ru`gr`gen`", Arrays.asList("zydievov", "pyplerta", "gaupythian", "kaustybre", "larkygagda", "metuskiev", "vuvidzhian", "ykadzhodna", "paziutso"));
        languages.put("lc`gr`gen`", Arrays.asList("fesiagroigor", "gledzhiggiakh", "saghiathask", "sheglerliv", "hmepobor", "riagarosk", "kramrufot", "glonuskiub"));
    }
}
