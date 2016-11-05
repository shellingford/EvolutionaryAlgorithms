package evoalg.genotype;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Genotype<T extends Genotype<T>> {

  private final int genotypeId;
  private final String name;

  public Genotype(String name) {
    this.name = name;
    this.genotypeId = 0;
  }

  /**
   * Creates deep copy of genotype object.
   *
   * @return deep copy of genotype object
   */
  public abstract T copy();

  /**
   * Immutable collection of crossover operators that will be used
   * for current genotype representation.
   *
   * @return immutable collection of crossover operators
   */
  public abstract List<CrossoverOp<T>> getCrossoverOp();

  /**
   * Immutable collection of mutation operators that will be used
   * for current genotype representation.
   *
   * @return immutable collection of mutation operators
   */
  public abstract List<MutationOp<T>> getMutationOp();

}
