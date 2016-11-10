package evoalg.genotype.bitstring;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;

import com.google.common.collect.ImmutableList;

import evoalg.genotype.Genotype;

/**
 * Bitstring genotype, where data is list of bytes, 0 or 1.
 * For example: [0, 1, 1, 1, 0, 1, 0]
 */
public class BitString extends Genotype<BitString> {

  @Getter
  private final List<Byte> data;

  public BitString() {
    this(0);
  }

  public BitString(int nBits) {
    this.data = ImmutableList.copyOf(initialize(nBits));
  }

  public BitString(List<Byte> data) {
    this.data = ImmutableList.copyOf(data);
  }

  /**
   * Initializes data to random bits.
   *
   * @param nBits number of data bits
   */
  private List<Byte> initialize(int nBits) {
    Random random = new Random();
    return IntStream.range(0, nBits).mapToObj(i -> (byte) random.nextInt(2)).collect(Collectors.toList());
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
   * with newData bits.
   *
   * @param newData new bits
   * @return copy of current instance of BitString with new bits
   */
  public BitString replaceData(List<Byte> newData) {
    return new BitString(newData);
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
