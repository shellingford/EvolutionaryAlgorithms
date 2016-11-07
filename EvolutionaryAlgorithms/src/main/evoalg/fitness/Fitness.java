package evoalg.fitness;

import lombok.AllArgsConstructor;
import lombok.Getter;
import evoalg.genotype.Genotype;

/**
 *  Fitness comparator class used to correctly sort individuals by their
 *  fitness value.
 *
 *  It is needed as sometimes our evaluation fitness function will produce
 *  higher score for better and sometimes for worse individuals.
 */
@Getter
@AllArgsConstructor
public abstract class Fitness<T extends Genotype<T>> implements Comparable<Fitness<T>> {

  private double value;

  /**
   * Makes deep copy of current fitness instance.
   *
   * @return deep copy of current instance
   */
  public abstract Fitness<T> copy();

  /**
   * Makes deep copy of current fitness instance with new
   * fitness value.
   *
   * @param value new fitness value
   * @return deep copy of current instance with new value
   */
  public abstract Fitness<T> copy(double value);

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(value);
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
    @SuppressWarnings("unchecked")
    Fitness<T> other = (Fitness<T>) obj;
    if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
      return false;
    }
    return true;
  }

}
