package evoalg.genotype.floatingpoint;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;

import com.google.common.collect.ImmutableList;

import evoalg.genotype.Genotype;

/**
 * Floating Point genotype where data is list of doubles within specified range
 * by <code>minValue</code> and <code>maxValue</code> (both inclusive).
 * For example: [3.3, 5.6, 7.2, 4.93, 11]
 */
@Getter
public class FloatingPoint extends Genotype<FloatingPoint> {

  private final List<Double> data;
  private final double minValue;
  private final double maxValue;

  private final boolean valid;

  public FloatingPoint(List<Double> data, double minValue, double maxValue) {
    this.data = ImmutableList.copyOf(data);
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.valid = checkIsValid();
  }

  public FloatingPoint(int nBits, double minValue, double maxValue) {
    this.data = ImmutableList.copyOf(initialize(nBits));
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.valid = checkIsValid();
  }

  /**
   * Initializes data to random bits.
   *
   * @param nBits number of data bits
   */
  private List<Double> initialize(int nBits) {
    Random random = new Random();
    return IntStream.range(0, nBits)
                    .mapToObj(i -> minValue + random.nextDouble() * (maxValue - minValue)).collect(Collectors.toList());
  }

  /**
   * All numbers must be within range specified by <code>minValue</code> and <code>maxValue</code> (both inclusive).
   *
   * @return true if data is valid, false otherwise
   */
  private boolean checkIsValid() {
    return data.stream().filter(bit -> Double.compare(bit, minValue) < 0 || Double.compare(bit, maxValue) > 0).count() == 0;
  }

  /**
   * Get bit with specified index.
   *
   * @param index position of the bit
   * @return bit at specified position
   */
  public double get(int index) {
    return data.get(index);
  }

  /**
   * Permutation data size.
   *
   * @return permutation data size
   */
  public int size() {
    return this.data.size();
  }

  /**
   * Creates new instance of FloatingPoint object that is copy of current one but
   * with newData bits.
   *
   * @param newData new bits
   * @return copy of current instance of FloatingPoint with new bits
   */
  public FloatingPoint replaceData(List<Double> newData) {
    return new FloatingPoint(newData, minValue, maxValue);
  }

  @Override
  public FloatingPoint copy() {
    return new FloatingPoint(data, minValue, maxValue);
  }

  @Override
  public FloatingPoint initializeData() {
    return new FloatingPoint(data.size(), minValue, maxValue);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((data == null) ? 0 : data.hashCode());
    long temp;
    temp = Double.doubleToLongBits(maxValue);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(minValue);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    FloatingPoint other = (FloatingPoint) obj;
    if (data == null) {
      if (other.data != null) {
        return false;
      }
    }
    else if (!data.equals(other.data)) {
      return false;
    }
    if (Double.doubleToLongBits(maxValue) != Double.doubleToLongBits(other.maxValue)) {
      return false;
    }
    if (Double.doubleToLongBits(minValue) != Double.doubleToLongBits(other.minValue)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "FloatingPoint [data=" + data + ", minValue=" + minValue + ", maxValue=" + maxValue + "]";
  }

}
