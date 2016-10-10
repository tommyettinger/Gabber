package gabber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tommy Ettinger on 10/9/2016.
 */
public class RNG implements Serializable {
    private static final long serialVersionUID = 4378460257281186370L;

    /**
     * 2 raised to the 53, - 1.
     */
    private static final long DOUBLE_MASK = (1L << 53) - 1;
    /**
     * 2 raised to the -53.
     */
    private static final double NORM_53 = 1. / (1L << 53);

    public long state;

    public RNG() {
        state = Double.doubleToLongBits(Math.random());
    }

    public RNG(long seed) {
        state = seed;
    }

    /**
     * Can return any long, positive or negative, of any size permissible in a 64-bit signed integer.
     *
     * @return any long, all 64 bits are random
     */
    public long nextLong() {
        long z = (state += 0x9E3779B97F4A7C15L);
        z = (z ^ (z >>> 30)) * 0xBF58476D1CE4E5B9L;
        z = (z ^ (z >>> 27)) * 0x94D049BB133111EBL;
        return z ^ (z >>> 31);
    }

    /**
     * Can return any int, positive or negative, of any size permissible in a 32-bit signed integer.
     *
     * @return any int, all 32 bits are random
     */
    public int nextInt() {
        return (int) nextLong();
    }

    /**
     * Exclusive on the upper bound n.  The lower bound is 0.
     * Credit for this particular method of faster pseudo-random bounded int generation goes to Daniel Lemire,
     * http://lemire.me/blog/2016/06/27/a-fast-alternative-to-the-modulo-reduction/
     * @param bound the upper bound; should be positive
     * @return a random int less than n and at least equal to 0
     */
    public int nextInt(final int bound) {
        return (int)((bound * (nextLong() & 0x7FFFFFFFL)) >> 31);
    }

    /**
     * Inclusive lower, exclusive upper.
     *
     * @param lower the lower bound, inclusive, can be positive or negative
     * @param upper the upper bound, exclusive, should be positive, must be greater than lower
     * @return a random int at least equal to lower and less than upper
     */
    public int between(final int lower, final int upper) {
        if (upper - lower <= 0) throw new IllegalArgumentException();
        return lower + nextInt(upper - lower);
    }

    /**
     * Exclusive on the upper bound n. The lower bound is 0.
     *
     * @param n the upper bound; should be positive
     * @return a random long less than n
     */
    public long nextLong(final long n) {
        if (n <= 0) throw new IllegalArgumentException();
        //for(;;) {
        final long bits = nextLong() >>> 1;
        return bits % n;
        //long value = bits % n;
        //value = (value < 0) ? -value : value;
        //if ( bits - value + ( n - 1 ) >= 0 ) return value;
        //}
    }

    /**
     * Inclusive lower, exclusive upper.
     *
     * @param lower the lower bound, inclusive, can be positive or negative
     * @param upper the upper bound, exclusive, should be positive, must be greater than lower
     * @return a random long at least equal to lower and less than upper
     */
    public long between(final long lower, final long upper) {
        if (upper - lower <= 0) throw new IllegalArgumentException();
        return lower + nextLong(upper - lower);
    }

    /**
     * Gets a uniform random double in the range [0.0,1.0)
     *
     * @return a random double at least equal to 0.0 and less than 1.0
     */
    public double nextDouble() {
        return (nextLong() & DOUBLE_MASK) * NORM_53;
    }

    /**
     * Gets a uniform random double in the range [0.0,outer) given a positive parameter outer. If outer
     * is negative, it will be the (exclusive) lower bound and 0.0 will be the (inclusive) upper bound.
     *
     * @param outer the exclusive outer bound, can be negative
     * @return a random double between 0.0 (inclusive) and outer (exclusive)
     */
    public double nextDouble(final double outer) {
        return nextDouble() * outer;
    }

    /**
     * Returns a value from an even distribution from inner (inclusive) to outer
     * (exclusive). Both inner and outer can be positive or negative, and unlike with
     * {@link #nextInt(int, int)}, they don't have any requirement to be less than or
     * greater than the other (though if they are equal, this will always return inner).
     *
     * @param inner the inner (smaller absolute value) bound on the return value (inclusive)
     * @param outer the outer (larger absolute value) bound on the return value (exclusive)
     * @return the found value
     */
    public double between(double inner, double outer) {
        return inner + (outer - inner) * nextDouble();
    }

    public <T> T getRandomElement(T[] array) {
        if (array.length < 1) {
            return null;
        }
        return array[nextInt(array.length)];
    }

    /**
     * Shuffle an array using the Fisher-Yates algorithm.
     *
     * @param elements an array of T; will not be modified
     * @param <T>      can be any non-primitive type.
     * @return a shuffled copy of elements
     */
    public <T> T[] shuffle(T[] elements) {
        T[] array = elements.clone();
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int r = i + nextInt(n - i);
            T t = array[r];
            array[r] = array[i];
            array[i] = t;
        }
        return array;
    }
    /**
     * Shuffle a {@link List} using the Fisher-Yates algorithm.
     * @param elements a List of T; will not be modified
     * @param <T> can be any non-primitive type.
     * @return a shuffled ArrayList containing the whole of elements in pseudo-random order.
     */
    public <T> ArrayList<T> shuffle(List<T> elements)
    {
        ArrayList<T> al = new ArrayList<T>(elements);
        int n = al.size();
        for (int i = 0; i < n; i++)
        {
            Collections.swap(al, i + nextInt(n - i), i);
        }
        return al;
    }

    /**
     * Gets a random portion of a List and returns it as a new List. Will only use a given position in the given
     * List at most once; does this by shuffling a copy of the List and getting a section of it.
     * @param data a List of T; will not be modified.
     * @param count the non-negative number of elements to randomly take from data
     * @param <T> can be any non-primitive type
     * @return a List of T that has length equal to the smaller of count or data.length
     */
    public <T> List<T> randomPortion(List<T> data, int count)
    {
        return shuffle(data).subList(0, Math.min(count, data.size()));
    }
    /**
     * Generates a random permutation of the range from 0 (inclusive) to length (exclusive).
     * Useful for passing to OrderedMap or OrderedSet's reorder() methods.
     * @param length the size of the ordering to produce
     * @return a random ordering containing all ints from 0 to length (exclusive)
     */
    public int[] randomOrdering(int length)
    {
        if(length <= 0)
            return new int[0];
        int[] dest = new int[length];
        for (int i = 0; i < length; i++)
        {
            int r = nextInt(i + 1);
            if(r != i)
                dest[i] = dest[r];
            dest[r] = i;
        }
        return dest;
    }

    /**
     * Generates a random permutation of the range from 0 (inclusive) to length (exclusive) and stores it in
     * the dest parameter, avoiding allocations.
     * Useful for passing to OrderedMap or OrderedSet's reorder() methods.
     * @param length the size of the ordering to produce
     * @param dest the destination array; will be modified
     * @return dest, filled with a random ordering containing all ints from 0 to length (exclusive)
     */
    public int[] randomOrdering(int length, int[] dest)
    {
        if(dest == null) return null;
        for (int i = 0; i < length && i < dest.length; i++)
        {
            int r = nextInt(i + 1);
            if(r != i)
                dest[i] = dest[r];
            dest[r] = i;
        }
        return dest;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RNG rng = (RNG) o;
        return state == rng.state;
    }

    @Override
    public int hashCode() {
        return (int) (state ^ (state >>> 32));
    }

    @Override
    public String toString() {
        return "RNG{" +
                "state=" + state +
                '}';
    }
}