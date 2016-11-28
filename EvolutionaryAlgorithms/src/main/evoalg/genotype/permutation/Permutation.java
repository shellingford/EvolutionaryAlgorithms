package evoalg.genotype.permutation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Getter;

import com.google.common.collect.ImmutableList;

import evoalg.genotype.Genotype;

/**
 * Permutation genotype where data is list of integers from 0 to size of the list - 1.
 * For example: [0, 2, 3, 1, 6, 5, 4]
 */
public class Permutation extends Genotype<Permutation> {

  @Getter
  private final List<Integer> data;

  @Getter
  private final boolean valid;

  public Permutation() {
    this(0);
  }

  public Permutation(int size) {
    this.data = ImmutableList.copyOf(initialize(size));
    this.valid = checkIsValid();
  }

  public Permutation(List<Integer> data) {
    this.data = ImmutableList.copyOf(data);
    this.valid = checkIsValid();
  }

  /**
   * Initializes permutation list where it first generates list of integers from
   * 0 to size - 1 and then shuffles the list.
   *
   * @param size size of the permutation list
   * @return shuffled permutation list
   */
  private List<Integer> initialize(int size) {
    List<Integer> data = IntStream.range(0, size).boxed().collect(Collectors.toList());
    Collections.shuffle(data);
    return data;
  }

  /**
   * In permutation there must not be duplicate numbers, all numbers in the data list must
   * be unique.
   *
   * @return true if data is valid, false otherwise
   */
  private boolean checkIsValid() {
    Set<Integer> set = new HashSet<>(getData());
    return set.size() == size();
  }

  @Override
  public Permutation copy() {
    return new Permutation(this.data);
  }

  @Override
  public Permutation initializeData() {
    return new Permutation(this.data.size());
  }

  /**
   * Get bit with specified index.
   *
   * @param index position of the bit
   * @return bit at specified position
   */
  public int get(int index) {
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
    Permutation other = (Permutation) obj;
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
    return "Permutation [data=" + data + "]";
  }

}
