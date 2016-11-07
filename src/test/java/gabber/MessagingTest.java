package gabber;

import static gabber.Messaging.NounTrait.*;

/**
 * Created by Tommy Ettinger on 11/2/2016.
 */
public class MessagingTest {
    public static void main(String[] args)
    {
        String message = "@Name hit$ ^ for ~ ~ damage and ~ ~ damage!";
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblin", MALE_GENDER, "10", "bludgeoning", "4", "lightning"));
        System.out.println(Messaging.transform(message, "the goblin", MALE_GENDER, "Heero Supra", SECOND_PERSON_SINGULAR, "10", "poison", "3", "piercing"));

        message = "@Name spit$ in ^name_s face^s!";
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblin", MALE_GENDER));
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblins", GROUP));
        System.out.println(Messaging.transform(message, "the goblin", MALE_GENDER, "Heero Supra", SECOND_PERSON_SINGULAR));
        System.out.println(Messaging.transform(message, "the goblins", GROUP, "Heero Supra", SECOND_PERSON_SINGULAR));

        message = "@Name @don_t care what ^name think^$, @i'll get @myself onto the dancefloor!";
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblin", MALE_GENDER));
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblins", GROUP));
        System.out.println(Messaging.transform(message, "the goblin", MALE_GENDER, "Heero Supra", SECOND_PERSON_SINGULAR));
        System.out.println(Messaging.transform(message, "the goblins", GROUP, "Heero Supra", SECOND_PERSON_SINGULAR));

        message = "@Name@m gonna try out the escapes! \\@, \\^ \\$\\~!";
        System.out.println(Messaging.transform(message, "Heero Supra", FIRST_PERSON_SINGULAR));
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR));
        System.out.println(Messaging.transform(message, "the goblin", MALE_GENDER, "Heero Supra", SECOND_PERSON_SINGULAR));
        System.out.println(Messaging.transform(message, "the goblins", GROUP, "Heero Supra", SECOND_PERSON_SINGULAR));


    }
}
