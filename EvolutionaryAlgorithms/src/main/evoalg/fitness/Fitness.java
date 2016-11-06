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

}
