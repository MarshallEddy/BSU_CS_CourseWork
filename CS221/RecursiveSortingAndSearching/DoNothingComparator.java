import java.util.Comparator;

/**
 * Useless Comparator that always indicates all elements are equivalent.
 *
 * @author mvail
 *
 * @param <T> type of elements being compared
 */
public class DoNothingComparator<T> implements Comparator<T> {
	@Override
	public int compare(T o1, T o2) {
		return 0;
	}
}

