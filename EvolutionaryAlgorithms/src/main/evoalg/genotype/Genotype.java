package evoalg.genotype;

public abstract class Genotype<T extends Genotype<T>> {

  /**
   * Creates deep copy of genotype object.
   *
   * @return deep copy of genotype object
   */
  public abstract T copy();

  /**
   * First initialization of data upon creation of the individual.
   * Creates random data for specified genotype.
   *
   * @return genotype instance with random data
   */
  public abstract T initializeData();

  @Override
  public abstract boolean equals(Object other);
  @Override
  public abstract int hashCode();

}
