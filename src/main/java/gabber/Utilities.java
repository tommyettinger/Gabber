package gabber;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Tommy Ettinger on 10/9/2016.
 */
public class Utilities {
    /**
     * Rearranges an ArrayList to use the given ordering, returning a copy; random orderings can be produced with
     * {@link gabber.RNG#randomOrdering(int)}. These orderings will never repeat an earlier element,
     * and the returned ArrayList may be shorter than the original if {@code ordering} isn't as long as {@code list}.
     * Using a random ordering is like shuffling, but allows you to repeat the shuffle exactly on other collections of
     * the same size.
     *
     * @param list     an ArrayList that you want to reorder; will not be modified.
     * @param ordering an ordering, typically produced by one of RNG's randomOrdering methods.
     * @param <T>      any generic type
     * @return a modified copy of {@code list} with its ordering changed to match {@code ordering}.
     */
    public static <T> ArrayList<T> reorder(ArrayList<T> list, int... ordering) {
        int ol;
        if (list == null || ordering == null || (ol = Math.min(list.size(), ordering.length)) == 0)
            return list;
        ArrayList<T> alt = new ArrayList<T>(ol);
        for (int i = 0; i < ol; i++) {
            alt.add(list.get((ordering[i] % ol + ol) % ol));
        }
        return alt;
    }

    public static int[] massAdd(int[] data, int toAdd)
    {
        for (int i = 0; i < data.length; i++) data[i] += toAdd;
        return data;
    }

    public static long hash64(final CharSequence data) {
        if (data == null)
            return 0;
        long z = 0x632BE59BD9B4E019L, result = 1L;
        for (int i = 0; i < data.length(); i++) {
            result ^= (z += (data.charAt(i) + 0x9E3779B97F4A7C15L) * 0xD0E89D2D311E289FL) * 0xC6BC279692B5CC83L;
        }
        return result ^ Long.rotateLeft((z * 0xC6BC279692B5CC83L ^ result * 0x9E3779B97F4A7C15L) + 0x632BE59BD9B4E019L, (int) (z >>> 58));
    }

    public static long hash64(final Iterable<String> data) {
        if (data == null)
            return 0;
        long z = 0x632BE59BD9B4E019L, result = 1L;
        for (String datum : data) {
            result ^= (z += (hash64(datum) + 0x9E3779B97F4A7C15L) * 0xD0E89D2D311E289FL) * 0xC6BC279692B5CC83L;
        }
        return result ^ Long.rotateLeft((z * 0xC6BC279692B5CC83L ^ result * 0x9E3779B97F4A7C15L) + 0x632BE59BD9B4E019L, (int) (z >>> 58));
    }


    public static int hash(final CharSequence[] data) {
        if (data == null)
            return 0;
        long z = 0x632BE59BD9B4E019L, result = 1L;
        for (int i = 0; i < data.length; i++) {
            result ^= (z += (hash64(data[i]) + 0x9E3779B97F4A7C15L) * 0xD0E89D2D311E289FL) * 0xC6BC279692B5CC83L;
        }
        return (int) ((result ^= Long.rotateLeft((z * 0xC6BC279692B5CC83L ^ result * 0x9E3779B97F4A7C15L) + 0x632BE59BD9B4E019L, (int) (z >>> 58))) ^ (result >>> 32));
    }

    public static int hash(final Object[] data) {
        if (data == null)
            return 0;
        long z = 0x632BE59BD9B4E019L, result = 1L;
        Object o;
        for (int i = 0; i < data.length; i++) {
            o = data[i];
            result ^= (z += ((o == null ? 0 : o.hashCode()) + 0x9E3779B97F4A7C15L) * 0xD0E89D2D311E289FL) * 0xC6BC279692B5CC83L;
        }
        return (int) ((result ^= Long.rotateLeft((z * 0xC6BC279692B5CC83L ^ result * 0x9E3779B97F4A7C15L) + 0x632BE59BD9B4E019L, (int) (z >>> 58))) ^ (result >>> 32));
    }
    /**
     * Makes a LinkedHashMap (LHM) with key and value types inferred from the types of k0 and v0, and considers all
     * parameters key-value pairs, casting the Objects at positions 0, 2, 4... etc. to K and the objects at positions
     * 1, 3, 5... etc. to V. If rest has an odd-number length, then it discards the last item. If any pair of items in
     * rest cannot be cast to the correct type of K or V, then this inserts nothing for that pair and logs information
     * on the problematic pair to the static Maker.issueLog field.
     * @param k0 the first key; used to infer the types of other keys if generic parameters aren't specified.
     * @param v0 the first value; used to infer the types of other values if generic parameters aren't specified.
     * @param rest an array or vararg of keys and values in pairs; should contain alternating K, V, K, V... elements
     * @param <K> the type of keys in the returned LinkedHashMap; if not specified, will be inferred from k0
     * @param <V> the type of values in the returned LinkedHashMap; if not specified, will be inferred from v0
     * @return a freshly-made LinkedHashMap with K keys and V values, using k0, v0, and the contents of rest to fill it
     */
    @SuppressWarnings("unchecked")
    public static <K, V> LinkedHashMap<K, V> makeLHM(K k0, V v0, Object... rest)
    {
        if(rest == null)
            return makeLHM(k0, v0);
        LinkedHashMap<K, V> lhm = new LinkedHashMap<K, V>(1 + (rest.length / 2));
        lhm.put(k0, v0);

        for (int i = 0; i < rest.length - 1; i+=2) {
            try {
                lhm.put((K) rest[i], (V) rest[i + 1]);
            }catch (ClassCastException cce) {
                cce.printStackTrace();
            }
        }
        return lhm;
    }

}
