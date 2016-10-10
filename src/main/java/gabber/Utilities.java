package gabber;

import java.util.ArrayList;

/**
 * Created by Tommy Ettinger on 10/9/2016.
 */
public class Utilities {
    /**
     * Rearranges an ArrayList to use the given ordering, returning a copy; random orderings can be produced with
     * {@link gabber.RNG#randomOrdering(int)} or
     * {@link gabber.RNG#randomOrdering(int, int[])}. These orderings will never repeat an earlier element,
     * and the returned ArrayList may be shorter than the original if {@code ordering} isn't as long as {@code list}.
     * Using a random ordering is like shuffling, but allows you to repeat the shuffle exactly on other collections of
     * the same size. A reordering can also be inverted with {@link #invertOrdering(int[])} or
     * {@link #invertOrdering(int[], int[])}, getting the change that will undo another ordering.
     * @param list an ArrayList that you want a reordered version of; will not be modified.
     * @param ordering an ordering, typically produced by one of RNG's randomOrdering methods.
     * @param <T> any generic type
     * @return a modified copy of {@code list} with its ordering changed to match {@code ordering}.
     */
    public static <T> ArrayList<T> reorder (ArrayList<T> list, int... ordering) {
        int ol;
        if (list == null || ordering == null || (ol = Math.min(list.size(), ordering.length)) == 0)
            return list;
        ArrayList<T> alt = new ArrayList<T>(ol);
        for (int i = 0; i < ol; i++) {
            alt.add(list.get((ordering[i] % ol + ol) % ol));
        }
        return alt;
    }

    /**
     * Given an ordering such as one produced by {@link gabber.RNG#randomOrdering(int, int[])}, this finds
     * its inverse, able to reverse the reordering and vice versa.
     * @param ordering the ordering to find the inverse for
     * @return the inverse of ordering
     */
    public static int[] invertOrdering(int[] ordering)
    {
        int ol = 0;
        if(ordering == null || (ol = ordering.length) == 0) return ordering;
        int[] next = new int[ol];
        for (int i = 0; i < ol; i++) {
            if(ordering[i] < 0 || ordering[i] >= ol) return next;
            next[ordering[i]] = i;
        }
        return next;
    }

    /**
     * Given an ordering such as one produced by {@link gabber.RNG#randomOrdering(int, int[])}, this finds
     * its inverse, able to reverse the reordering and vice versa. This overload doesn't allocate a new int
     * array, and instead relies on having an int array of the same size as ordering passed to it as an
     * additional argument.
     * @param ordering the ordering to find the inverse for
     * @param dest the int array to put the inverse reordering into; should have the same length as ordering
     * @return the inverse of ordering; will have the same value as dest
     */
    public static int[] invertOrdering(int[] ordering, int[] dest)
    {
        int ol = 0;
        if(ordering == null || dest == null || (ol = Math.min(ordering.length, dest.length)) == 0)
            return ordering;
        for (int i = 0; i < ol; i++) {
            if(ordering[i] < 0 || ordering[i] >= ol) return dest;
            dest[ordering[i]] = i;
        }
        return dest;
    }

    public static long hash64(final CharSequence data) {
        if (data == null)
            return 0;
        long z = 0x632BE59BD9B4E019L, result = 1L;
        for (int i = 0; i < data.length(); i++) {
            result ^= (z += (data.charAt(i) + 0x9E3779B97F4A7C15L) * 0xD0E89D2D311E289FL) * 0xC6BC279692B5CC83L;
        }
        return result ^ Long.rotateLeft((z * 0xC6BC279692B5CC83L ^ result * 0x9E3779B97F4A7C15L) + 0x632BE59BD9B4E019L, (int)(z >>> 58));
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
}
