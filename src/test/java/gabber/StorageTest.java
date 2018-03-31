package gabber;

/**
 * Created by Tommy Ettinger on 3/30/2018.
 */
public class StorageTest {
    public static void main(String[] args)
    {
        Language rl = Language.randomLanguage(200L);
        System.out.println(rl.word(100L, true));
        System.out.println(rl.serializeToString());
        Language rl2 = Language.deserializeFromString(rl.serializeToString());
        System.out.println(rl2.word(100L, true));
        System.out.println(rl.equals(rl2));
    }
}
