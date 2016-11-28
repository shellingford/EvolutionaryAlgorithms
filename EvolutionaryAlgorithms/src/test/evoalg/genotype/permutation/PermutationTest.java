package evoalg.genotype.permutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class PermutationTest {

  @Test
  public void correctlyCreatesEmptyPermutation() {
    Permutation permutation = new Permutation();
    assertEquals(0, permutation.size());
  }

  @Test
  public void correctlyCreatesNonEmptyPermutation() {
    int expectedDataSize = 5;
    Permutation permutation = new Permutation(expectedDataSize);
    assertEquals(expectedDataSize, permutation.size());
    assertTrue(permutation.isValid());
  }

  @Test
  public void correctlyCreatesPermutationWithData() {
    List<Integer> newData = new ArrayList<>();
    newData.add(1);
    newData.add(2);
    newData.add(3);
    newData.add(4);
    List<Integer> newDataCopy = new ArrayList<>(newData);

    Permutation permutation = new Permutation(newData);
    //permutation uses newData to create internal copy of it
    assertEquals(newData, permutation.getData());

    //changing newData list should not change permutation's internal list
    newData.add(0);
    newData.set(0,  0);
    assertEquals(newDataCopy, permutation.getData());
  }

  @Test
  public void correctlyCreatesCopy() {
    Permutation permutation = new Permutation(5);
    Permutation permutationCopy = permutation.copy();

    //check if data is correctly copied
    assertTrue(permutation.equals(permutationCopy));
  }

  @Test
  public void underalyingListIsImmutable() {
    int expectedDataSize = 5;
    Permutation permutation = new Permutation(expectedDataSize);
    assertTrue(permutation.getData() instanceof ImmutableList);
  }

  @Test
  public void correctlyInitializedData() {
    int expectedDataSize = 5;
    Permutation permutation = new Permutation(expectedDataSize);
    Permutation initializedPermutation = permutation.initializeData();
    assertEquals(expectedDataSize, initializedPermutation.size());
    assertTrue(initializedPermutation.isValid());
  }

  @Test
  public void correctlyValidatesData() {
    Permutation validPerm = new Permutation(Arrays.asList(1, 2, 3, 4, 5));
    assertTrue(validPerm.isValid());

    Permutation invalidPerm = new Permutation(Arrays.asList(1, 1, 3, 4, 5));
    assertFalse(invalidPerm.isValid());
  }

}
