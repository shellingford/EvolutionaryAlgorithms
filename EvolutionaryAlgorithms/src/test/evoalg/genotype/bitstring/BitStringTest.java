package evoalg.genotype.bitstring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
    assertEquals(expectedDataSize, bitstring.getData().size());
  }

  @Test
  public void correctlyCreatesCopy() {
    BitString bitstring = new BitString(5);
    BitString bitstringCopy = bitstring.copy();

    //check if data is correctly copied
    assertTrue(bitstring.equals(bitstringCopy));
  }
}
