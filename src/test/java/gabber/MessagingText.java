package gabber;

import static gabber.Messaging.NounTrait.*;

/**
 * Created by Tommy Ettinger on 11/2/2016.
 */
public class MessagingText {
    public static void main(String[] args)
    {
        String message = "@Name hit$ ^ for ~ damage!";
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblin", MALE_GENDER, "10"));
        System.out.println(Messaging.transform(message, "the goblin", MALE_GENDER, "Heero Supra", SECOND_PERSON_SINGULAR, "10"));

        message = "@Name spit$ in ^name_s face^s!";
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblin", MALE_GENDER));
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblins", GROUP));
        System.out.println(Messaging.transform(message, "the goblin", MALE_GENDER, "Heero Supra", SECOND_PERSON_SINGULAR));
        System.out.println(Messaging.transform(message, "the goblins", GROUP, "Heero Supra", SECOND_PERSON_SINGULAR));

        message = "@Name @don_t care what ^name think^$, @i will keep partying!";
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblin", MALE_GENDER));
        System.out.println(Messaging.transform(message, "Heero Supra", SECOND_PERSON_SINGULAR, "the goblins", GROUP));
        System.out.println(Messaging.transform(message, "the goblin", MALE_GENDER, "Heero Supra", SECOND_PERSON_SINGULAR));
        System.out.println(Messaging.transform(message, "the goblins", GROUP, "Heero Supra", SECOND_PERSON_SINGULAR));
    }
}
