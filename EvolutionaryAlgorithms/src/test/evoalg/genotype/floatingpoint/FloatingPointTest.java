package evoalg.genotype.floatingpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class FloatingPointTest {

  @Test
  public void correctlyCreatesNonEmptyFloatingPoint() {
    int expectedDataSize = 5;
    FloatingPoint floatingPoint = new FloatingPoint(expectedDataSize, 0d, 10d);
    assertEquals(expectedDataSize, floatingPoint.size());
    assertTrue(floatingPoint.isValid());
  }

  @Test
  public void correctlyCreatesFloatingPointWithData() {
    List<Double> newData = new ArrayList<>();
    newData.add(4.5d);
    newData.add(5.8d);
    newData.add(2.46d);
    newData.add(7d);
    List<Double> newDataCopy = new ArrayList<>(newData);

    FloatingPoint floatingPoint = new FloatingPoint(newData, 0d, 10d);
    //floatingPoint uses newData to create internal copy of it
    assertEquals(newData, floatingPoint.getData());

    //changing newData list should not change floatingPoint's internal list
    newData.add(0d);
    newData.set(0,  0d);
    assertEquals(newDataCopy, floatingPoint.getData());
  }

  @Test
  public void correctlyCreatesCopy() {
    FloatingPoint floatingPoint = new FloatingPoint(5, 0d, 10d);
    FloatingPoint floatingPointCopy = floatingPoint.copy();

    //check if data is correctly copied
    assertTrue(floatingPoint.equals(floatingPointCopy));
  }

  @Test
  public void underalyingListIsImmutable() {
    int expectedDataSize = 5;
    FloatingPoint floatingPoint = new FloatingPoint(expectedDataSize, 0d, 10d);
    assertTrue(floatingPoint.getData() instanceof ImmutableList);
  }

  @Test
  public void correctlyInitializedData() {
    int expectedDataSize = 5;
    FloatingPoint floatingPoint = new FloatingPoint(expectedDataSize, 0d, 10d);
    FloatingPoint initializedFloatingPoint = floatingPoint.initializeData();
    assertEquals(expectedDataSize, initializedFloatingPoint.size());
    assertTrue(initializedFloatingPoint.isValid());
  }

  @Test
  public void correctlyValidatesData() {
    FloatingPoint validFP = new FloatingPoint(Arrays.asList(1d, 2d, 3d, 4d, 5d), 1, 5);
    assertTrue(validFP.isValid());

    FloatingPoint invalidFP = new FloatingPoint(Arrays.asList(1d, 2d, 3d, 4d, 5d), 2, 5);
    assertFalse(invalidFP.isValid());
  }

}
