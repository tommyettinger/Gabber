package gabber;

import regexodus.*;

/**
 * Helps handle formation of messages from a template, using correct pronouns and helping handle various idiosyncrasies
 * in English-language text. You call the static method
 * {@link #transform(CharSequence, String, NounTrait, String, NounTrait)} (or one of its overloads) with a template that
 * has specific placeholder glyphs, along with a user name, optional target name, user NounTrait (an enum in this class)
 * to specify how the user should be addressed, including their gender, optional user NounTrait, and possibly more extra
 * terms that should be inserted. The placeholder glyphs are usually followed by a specific word that is conjugated for
 * the first-person case, and will have that conjugation changed to fit the NounTrait for the user or target. For
 * example, you could use "@Name hit$ ^ for ~ damage!" as a message. You could transform it with user "Heero Supra",
 * userTrait NounTrait.SECOND_PERSON_SINGULAR, target "the beast", targetTrait NounTrait.UNSPECIFIED_GENDER, and extra
 * "10" to get the message "You hit the beast for 10 damage!". You could swap the user and target (along with their
 * traits) to get the message "The beast hits you for 10 damage!" You can handle more complex verbs in some cases, such
 * as "@I hurr$$$ to catch up!" can be transformed to "You hurry to catch up!" or "He hurries to catch up!". The rules
 * are fairly simple; @word conjugates a specific word from a list to the correct kind for the user, while ^word does a
 * similar thing but conjugates for the target. Between 1 and 3 $ chars can be used at the end of verbs to conjugate
 * them, while @s, @ss, ^s, or ^ss can be added at the end of nouns to pluralize them if appropriate. Using one $ or s
 * will add s or nothing, as in the case of hit becoming hits, using two $ or s chars will add es or nothing, as in the
 * case of scratch becoming scratches, and using three will add ies or y, as in the case of carry becoming carries. The
 * words you can put after a @ or ^ are: name, name_s, i, me, my, mine, myself, or any of these with the first char
 * capitalized (meant for words at the start of sentences). You can also use @ or ^ on its own as an equivalent to @name
 * or ^name, can place a ^ before $, $$, or $$$ to conjugate a verb based on the target instead of the user, and can use
 * phrases like face@s, face^s, patch@ss, or patch^ss to change into face/faces or patch/patches based on the correct
 * pluralization for the user or target.
 * Created by Tommy Ettinger on 10/31/2016.
 */
public class Phrasing {

    /**
     * Properties of nouns needed to correctly conjugate those nouns and refer to them with pronouns, such as genders.
     * Includes parts of speech, which only are concerned with whether they refer to a singular noun or a plural noun,
     * and genders for when a gendered pronoun is needed. This provides substantial support for uncommon cases regarding
     * gender and pronoun preferences. That said, gender and pronoun preference can be incredibly hard to handle.
     * The simplest cases are for first- and second-person pronouns; here we have "I/me/my/myself" for
     * {@link #FIRST_PERSON_SINGULAR}, "you/you/your/yourself" for {@link #SECOND_PERSON_SINGULAR},
     * "we/us/our/ourselves" for {@link #FIRST_PERSON_PLURAL}, and "you/you/your/yourselves" for
     * {@link #SECOND_PERSON_PLURAL}; there are more pronouns this can produce, but they aren't listed here.
     * Third-person pronouns are considerably more challenging because English sporadically considers gender as part of
     * conjugation, but doesn't provide a universally-acceptable set of gendered pronouns.
     * <br>
     * This at least tries to provide pronoun handling for the common cases, such as "you" not needing a gendered
     * pronoun at all (it uses {@link #SECOND_PERSON_SINGULAR}), and supports {@link #MALE_GENDER male},
     * {@link #FEMALE_GENDER female}, {@link #NO_GENDER genderless} (using "it" and related forms; preferred especially
     * for things that aren't alive, and in most cases not recommended for people),
     * {@link #UNSPECIFIED_GENDER "unspecified"} (using "they" in place of "he" or "she"; preferred in some cases when
     * describing someone with a non-specific gender or an unknown gender) pronouns, and {@link #GROUP group} for when a
     * group of individuals, regardless of gender or genders, is referred to with a single pronoun. As mentioned, this
     * has support for some uncommon situations, like {@link #ADDITIONAL_GENDER additional gender} (as in, a gender that
     * is in addition to male and female but that is not genderless, which has a clear use case when describing
     * non-human species, and a more delicate use for humans who use non-binary gender pronouns; hopefully "xe" will be
     * acceptable), and finally a {@link #SPECIAL_CASE_GENDER "special case"} pronoun that is unpronounceable and, if
     * given special processing, can be used as a replacement target for customized pronouns. For the additional gender,
     * the non-binary gendered pronouns are modified from the male pronouns by replacing 'h' with 'x' (he becomes xe,
     * his becomes xis). The "special case" pronouns replace the 'h' in the male pronouns with 'qvq', except for in one
     * case. Where, if the female pronoun were used, it would be "hers", but the male pronoun in that case would be "his",
     * changing the male pronoun would lead to a difficult-to-replace case because "his" is also used in the case where
     * the female pronoun is the usefully distinct "her". Here, the "special case" gender diverges from what it usually
     * does, and uses "qvqims" in place of "his" or "hers". The "special case" pronouns should be replaced before being
     * displayed, since they look like gibberish or a glitch and so are probably confusing out of context.

     */
    public enum NounTrait {
        FIRST_PERSON_SINGULAR,
        SECOND_PERSON_SINGULAR,
        FIRST_PERSON_PLURAL,
        SECOND_PERSON_PLURAL,
        NO_GENDER,
        MALE_GENDER,
        FEMALE_GENDER,
        UNSPECIFIED_GENDER,
        ADDITIONAL_GENDER,
        SPECIAL_CASE_GENDER,
        GROUP;

        public String nameText(String term) {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                    return "I";
                case FIRST_PERSON_PLURAL:
                    return "we";
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "you";
                default:
                    return term;
            }
        }
        public String name_sText(String term) {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                    return "my";
                case FIRST_PERSON_PLURAL:
                    return "our";
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "your";
                default:
                    if(term.isEmpty()) return "";
                    else if(term.charAt(term.length()-1) == 's') return term + '\'';
                    else return term + "'s";
            }
        }

        public String iText() {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                    return "I";
                case FIRST_PERSON_PLURAL:
                    return "we";
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "you";
                case NO_GENDER:
                    return "it";
                case MALE_GENDER:
                    return "he";
                case FEMALE_GENDER:
                    return "she";
                //case UNSPECIFIED_GENDER: return "they";
                case ADDITIONAL_GENDER:
                    return "xe";
                case SPECIAL_CASE_GENDER:
                    return "qvqe";
                default:
                    return "they";
            }
        }
        public String meText() {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                    return "me";
                case FIRST_PERSON_PLURAL:
                    return "us";
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "you";
                case NO_GENDER:
                    return "it";
                case MALE_GENDER:
                    return "him";
                case FEMALE_GENDER:
                    return "her";
                //case UNSPECIFIED_GENDER: return "them";
                case ADDITIONAL_GENDER:
                    return "xim";
                case SPECIAL_CASE_GENDER:
                    return "qvqim";
                default:
                    return "them";
            }
        }

        public String myText() {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                    return "my";
                case FIRST_PERSON_PLURAL:
                    return "our";
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "your";
                case NO_GENDER:
                    return "its";
                case MALE_GENDER:
                    return "his";
                case FEMALE_GENDER:
                    return "her";
                //case UNSPECIFIED_GENDER: return "their";
                case ADDITIONAL_GENDER:
                    return "xis";
                case SPECIAL_CASE_GENDER:
                    return "qvqis";
                default:
                    return "their";
            }
        }
        public String mineText() {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                    return "mine";
                case FIRST_PERSON_PLURAL:
                    return "ours";
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "yours";
                case NO_GENDER:
                    return "its";
                case MALE_GENDER:
                    return "his";
                case FEMALE_GENDER:
                    return "hers";
                //case UNSPECIFIED_GENDER: return "theirs";
                case ADDITIONAL_GENDER:
                    return "xis";
                case SPECIAL_CASE_GENDER:
                    return "qvqims";
                default:
                    return "theirs";
            }
        }

        public String myselfText() {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                    return "myself";
                case FIRST_PERSON_PLURAL:
                    return "ourselves";
                case SECOND_PERSON_SINGULAR:
                    return "yourself";
                case SECOND_PERSON_PLURAL:
                    return "yourselves";
                case NO_GENDER:
                    return "itself";
                case MALE_GENDER:
                    return "himself";
                case FEMALE_GENDER:
                    return "herself";
                case UNSPECIFIED_GENDER:
                    return "themself";
                case ADDITIONAL_GENDER:
                    return "ximself";
                case SPECIAL_CASE_GENDER:
                    return "qvqimself";
                default:
                    return "themselves";
            }
        }

        public String $Text() {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                case FIRST_PERSON_PLURAL:
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "";
                default:
                    return "s";
            }
        }
        public String sText() {
            switch (this) {
                case FIRST_PERSON_PLURAL:
                case SECOND_PERSON_PLURAL:
                case GROUP:
                    return "s";
                default:
                    return "";
            }
        }public String ssText() {
            switch (this) {
                case FIRST_PERSON_PLURAL:
                case SECOND_PERSON_PLURAL:
                case GROUP:
                    return "es";
                default:
                    return "";
            }
        }
        public String $$Text() {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                case FIRST_PERSON_PLURAL:
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "";
                default:
                    return "es";
            }
        }
        public String $$$Text() {
            switch (this) {
                case FIRST_PERSON_SINGULAR:
                case FIRST_PERSON_PLURAL:
                case SECOND_PERSON_SINGULAR:
                case SECOND_PERSON_PLURAL:
                    return "y";
                default:
                    return "ies";
            }
        }
    }
    public static String transform(CharSequence message, String user, NounTrait userTrait)
    {
        Replacer ur = new Replacer(userPattern, new BeingSubstitution(user, userTrait));
        return ur.replace(message);
    }

    public static String transform(CharSequence message, String user, NounTrait userTrait, String target, NounTrait targetTrait)
    {
        Replacer tr = new Replacer(targetPattern, new BeingSubstitution(target, targetTrait)),
                ur = new Replacer(userPattern, new BeingSubstitution(user, userTrait));
        return ur.replace(tr.replace(message));
    }
    public static String transform(CharSequence message, String user, NounTrait userTrait, String target, NounTrait targetTrait, String... extra)
    {
        Replacer tr = new Replacer(targetPattern, new BeingSubstitution(target, targetTrait)),
                ur = new Replacer(userPattern, new BeingSubstitution(user, userTrait));
        String text = ur.replace(tr.replace(message));
        if(extra != null && extra.length > 0)
        {
            for (int i = 0; i < extra.length; i++) {
                text = text.replace("~", extra[i]);
            }
        }
        return text;
    }
    protected static final Pattern userPattern = Pattern.compile("({$$$}\\$\\$\\$)|({$$}\\$\\$)|({$}\\$)|({ss}@ss)|({s}@s)|" +
            "({name}@name)|({Name}@Name)|({name_s}@name_s)|({Name_s}@Name_s)|({i}@i)|({I}@I)|({me}@me)|({Me}@Me)|" +
            "({my}@my)|({My}@My)|({mine}@mine)|({Mine}@Mine)|({myself}@myself)|({Myself}@Myself)|({=name}@))"),
            targetPattern = Pattern.compile("({$$$}\\^\\$\\$\\$)|({$$}\\^\\$\\$)|({$}\\^\\$)|({ss}\\^ss)|({s}\\^s)|" +
                    "({name}\\^name)|({Name}\\^Name)|({name_s}\\^name_s)|({Name_s}\\^Name_s)|({i}\\^i)|({I}\\^I)|({me}\\^me)|({Me}\\^Me)|" +
                    "({my}\\^my)|({My}\\^My)|({mine}\\^mine)|({Mine}\\^Mine)|({myself}\\^myself)|({Myself}\\^Myself)|({=name}\\^))");
    protected static class BeingSubstitution implements Substitution {

        public String term;
        public NounTrait trait;

        public BeingSubstitution()
        {
            term = "Joe";
            trait = NounTrait.MALE_GENDER;
        }

        public BeingSubstitution(String term, NounTrait trait)
        {
            this.term = (term == null) ? "" : term;
            this.trait = (trait == null) ? NounTrait.UNSPECIFIED_GENDER : trait;
        }
        public static void appendCapitalized(String s, TextBuffer dest)
        {
            dest.append(Character.toUpperCase(s.charAt(0)));
            if(s.length() > 1)
                dest.append(s.substring(1, s.length()));
        }
        @Override
        public void appendSubstitution(MatchResult match, TextBuffer dest) {
            if(match.isCaptured("name"))
            {
                dest.append(trait.nameText(term));
            }
            else if(match.isCaptured("Name"))
            {
                appendCapitalized(trait.nameText(term), dest);
            }
            else if(match.isCaptured("name_s"))
            {
                dest.append(trait.name_sText(term));
            }
            else if(match.isCaptured("Name_s"))
            {
                appendCapitalized(trait.name_sText(term), dest);
            }
            else if(match.isCaptured("i"))
            {
                dest.append(trait.iText());
            }
            else if(match.isCaptured("I"))
            {
                appendCapitalized(trait.iText(), dest);
            }
            else if(match.isCaptured("me"))
            {
                dest.append(trait.meText());
            }
            else if(match.isCaptured("Me"))
            {
                appendCapitalized(trait.meText(), dest);
            }
            else if(match.isCaptured("my"))
            {
                dest.append(trait.myText());
            }
            else if(match.isCaptured("My"))
            {
                appendCapitalized(trait.myText(), dest);
            }
            else if(match.isCaptured("mine"))
            {
                dest.append(trait.mineText());
            }
            else if(match.isCaptured("Mine"))
            {
                appendCapitalized(trait.mineText(), dest);
            }
            else if(match.isCaptured("myself"))
            {
                dest.append(trait.myselfText());
            }
            else if(match.isCaptured("Myself"))
            {
                appendCapitalized(trait.myselfText(), dest);
            }
            else if(match.isCaptured("s"))
            {
                dest.append(trait.sText());
            }
            else if(match.isCaptured("ss"))
            {
                dest.append(trait.ssText());
            }
            else if(match.isCaptured("$"))
            {
                dest.append(trait.$Text());
            }
            else if(match.isCaptured("$$"))
            {
                dest.append(trait.$$Text());
            }
            else if(match.isCaptured("$$$"))
            {
                dest.append(trait.$$$Text());
            }
            else
                match.getGroup(0, dest);
        }
    }
}
