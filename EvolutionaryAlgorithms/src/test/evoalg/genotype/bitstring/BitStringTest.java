package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class BitStringTest {

  @Test
  public void correctlyCreatesEmptyBitString() {
    BitString bitstring = new BitString();
    assertTrue(bitstring.getData().isEmpty());
  }

  @Test
  public void correctlyCreatesNonEmptyBitString() {
    int expectedDataSize = 5;
    BitString bitstring = new BitString(expectedDataSize);
    assertEquals(expectedDataSize, bitstring.size());
  }

  @Test
  public void correctlyCreatesBitStringWithData() {
    List<Byte> newData = new ArrayList<>();
    newData.add((byte) 1);
    newData.add((byte) 0);
    newData.add((byte) 1);
    newData.add((byte) 1);
    List<Byte> newDataCopy = new ArrayList<>(newData);

    BitString bitstring = new BitString(newData);
    //bitstring uses newData to create internal copy of it
    assertEquals(newData, bitstring.getData());

    //changing newData list should not change bitstring's internal list
    newData.add((byte) 0);
    newData.set(0, (byte) 0);
    assertEquals(newDataCopy, bitstring.getData());
  }

  @Test
  public void correctlyCreatesCopy() {
    BitString bitstring = new BitString(5);
    BitString bitstringCopy = bitstring.copy();

    //check if data is correctly copied
    assertTrue(bitstring.equals(bitstringCopy));
  }

  @Test
  public void underalyingListIsImmutable() {
    int expectedDataSize = 5;
    BitString bitstring = new BitString(expectedDataSize);
    assertTrue(bitstring.getData() instanceof ImmutableList);
  }

  @Test
  public void correctlyInitializedData() {
    int expectedDataSize = 5;
    BitString bitstring = new BitString(expectedDataSize);
    BitString initializedBitstring = bitstring.initializeData();
    assertEquals(expectedDataSize, initializedBitstring.size());
  }

  @Test
  public void correctlyReplacesData() {
    List<Byte> oldData = Arrays.asList((byte) 1, (byte) 0, (byte) 0, (byte) 0);
    List<Byte> newData = new ArrayList<>();
    newData.add((byte) 1);
    newData.add((byte) 0);
    newData.add((byte) 1);
    newData.add((byte) 1);
    List<Byte> newDataCopy = new ArrayList<>(newData);

    BitString bitstring = new BitString(oldData);
    BitString bitstringWithNewData = bitstring.replaceData(newData);

    //bitstring uses newData to create internal copy of it
    assertEquals(newDataCopy, bitstringWithNewData.getData());

    //changing list should not change bitstring's internal list
    newData.add((byte) 0);
    newData.set(0, (byte) 0);
    assertEquals(newDataCopy, bitstringWithNewData.getData());
  }
}
