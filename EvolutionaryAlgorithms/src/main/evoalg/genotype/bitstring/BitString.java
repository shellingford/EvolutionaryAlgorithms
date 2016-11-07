package evoalg.genotype.bitstring;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import evoalg.genotype.Genotype;

/**
 * Bitstring genotype, where data is list of bytes.
 */
public class BitString extends Genotype<BitString> {

  private final List<Byte> data;

  public BitString() {
    this(0);
  }

  public BitString(int nBits) {
    data = new ArrayList<>(nBits);
    initialize(nBits);
  }

  public BitString(List<Byte> data) {
    this.data = data;
  }

  /**
   * Initializes data to random bits.
   *
   * @param nBits number of data bits
   */
  private void initialize(int nBits) {
    Random random = new Random();
    for (int i = 0; i < nBits; i++) {
      data.add((byte) random.nextInt(2));
    }
  }

  @Override
  public BitString copy() {
    return replaceData(data);
  }

  @Override
  public BitString initializeData() {
    return new BitString(data.size());
  }

  /**
   * Creates new instance of BitString object that is copy of current one but
   * with new bits.
   *
   * @param newData new bits
   * @return copy of current instance of BitString with new bits
   */
  public BitString replaceData(List<Byte> newData) {
    return new BitString(new ArrayList<Byte>(newData));
  }

  /**
   * Get byte with specified index.
   *
   * @param index position of the byte
   * @return byte at specified position
   */
  public byte get(int index) {
    return data.get(index);
  }

  /**
   * Bitstring data size.
   *
   * @return bitstring data size
   */
  public int size() {
    return data.size();
  }

  /**
   * Immutable list of bitstring's data.
   *
   * @return immutable list of data
   */
  public List<Byte> getData() {
    return ImmutableList.copyOf(data);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    BitString other = (BitString) obj;
    if (data == null) {
      if (other.data != null) {
        return false;
      }
    }
    else if (!data.equals(other.data)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "BitString [data=" + data + "]";
  }
}
