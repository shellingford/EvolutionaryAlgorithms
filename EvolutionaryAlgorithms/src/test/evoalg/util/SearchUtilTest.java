package evoalg.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SearchUtilTest {

  @Test
  public void corectlyFindsIndexAtStart() {
    List<Integer> values = Arrays.asList(0, 10, 15, 16, 23, 25);
    int target = 5;

    int index = SearchUtil.findFirstGreater(values, target);
    assertEquals(1, index);
  }

  @Test
  public void corectlyFindsIndexAtEnd() {
    List<Integer> values = Arrays.asList(0, 10, 15, 16, 23, 25);
    int target = 24;

    int index = SearchUtil.findFirstGreater(values, target);
    assertEquals(5, index);
  }

  @Test
  public void corectlyFindsIndexAtMid() {
    List<Integer> values = Arrays.asList(0, 10, 13, 16, 23, 25);
    int target = 14;

    int index = SearchUtil.findFirstGreater(values, target);
    assertEquals(3, index);
  }
}
