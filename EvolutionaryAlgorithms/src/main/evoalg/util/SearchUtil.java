package evoalg.util;

import java.util.List;

/**
 * Utility class with some search algorithms.
 */
public final class SearchUtil {

  private SearchUtil() { }

  /**
   * Finds first number in the list that is greater than <code>target</code> and
   * returns index of it. It assumes that the list <code>values</code> is sorted.
   *
   * @param values list of values
   * @param target target value
   * @return index of first element from the <code>values</code> that has higher
   * value than <code>target</code>
   */
  public static <T extends Number> int findFirstGreater(List<T> values, T target) {
    int start = 0;
    int end = values.size();

    while (start != end) {
      int mid = (start + end) / 2;
      if (Double.compare(values.get(mid).doubleValue(), target.doubleValue()) < 0) {
        start = mid + 1;
      }
      else {
        end = mid;
      }
    }
    return start;
  }
}
