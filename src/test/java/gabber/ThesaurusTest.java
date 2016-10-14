package gabber;

import java.util.Arrays;

/**
 * Created by Tommy Ettinger on 5/23/2016.
 */
public class ThesaurusTest {
    public static void main(String[] args) {
        Thesaurus thesaurus = new Thesaurus("Gabber!"), thesaurus2  = new Thesaurus("Gabber!");
        thesaurus.addSynonyms(Arrays.asList("devil", "fiend", "demon", "horror", "abomination", "terror", "hellspawn", "the Adversary"));
        thesaurus.addSynonyms(Arrays.asList("despoiler", "defiler", "blighter", "poisoner", "stainer"));
        thesaurus2.addKnownCategories().addImitationWords();
        for (int i = 0; i < 20; i++) {
            System.out.println(thesaurus.process("You fiend! You demon! You despoiler of creation; devil made flesh!"));
        }
        for (int i = 0; i < 12; i++) {
            //System.out.println(
            //        thesaurus.process("You fiend! You demon! You despoiler of creation; devil made flesh!"));
            //System.out.println(
            //        thesaurus2.process("The small state of Ru`gen` in the Empire`noun` of Fr`gen`, ruled by Duke`noun` So`mod`gen`."));
            System.out.println(
                    thesaurus2.process("Calm`adj` Org`noun`\n"+
                            "Fancy`adj` Fr`gen` Empire`noun`\n"+
                            "Ar`jp`gen` Militia`noun`\n"+
                            "Sinister`noun` Blade`noun`\n"+
                            "Sole`adj` Empire`noun`\n"+
                            "Bandit`nouns`\n"+
                            "Forest`adj` Org`noun` of Sw`gr`gen`\n"+
                            "People's Union`noun` of Ru`so`gen`\n"+
                            "Holy`adj` En`hi`gen` Empire`noun`\n"+
                            "Fancy`adj` Militia`noun`\n"+
                            "Rage`noun` of Gr`gen`\n"+
                            "En`jp`gen` Union`noun`\n"+
                            "Tech`adj` Guard`nouns`\n"+
                            "New Bandit`nouns` of So`mod`gen`\n"+
                            "Light`noun` of Smart`noun`")
            );
        }
        String text;
        Language lang;
        RNG rng = new RNG("GABBER!!!");
        String[] midPunctuation = new String[]{","}, endPunctuation = new String[]{".", ".", ".", "!", "?"};
        for (int i = 0; i < 40; i++) {
            text = thesaurus2.makeNationName();
            System.out.println(text);
            if(thesaurus2.randomLanguages.isEmpty())
                lang = Language.randomLanguage(text);
            else
                lang = thesaurus2.randomLanguages.get(0);
            System.out.println(lang.sentence(rng, 3, 8, midPunctuation, endPunctuation, 0.2));
            System.out.println(lang.sentence(rng, 4, 8, midPunctuation, endPunctuation, 0.2));
        }
    }
}
