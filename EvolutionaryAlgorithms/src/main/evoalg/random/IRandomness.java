package evoalg.random;

/**
 * Main interface for using random numbers in the framework.
 */
public interface IRandomness {

  /**
   * Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and the
   * specified value (exclusive).
   *
   * @param bound the upper bound (exclusive). Must be positive.
   * @return the next pseudorandom, uniformly distributed int value between zero (inclusive)
   *         and bound (exclusive) from this random number generator's sequence
   */
  int nextInt(int bound);

  /**
   * Returns the next pseudorandom, uniformly distributed double value between 0.0 and 1.0.
   *
   * @return the next pseudorandom, uniformly distributed double value between 0.0 and 1.0
   */
  double nextDouble();

  /**
   * Returns the next pseudorandom, uniformly distributed boolean value.
   *
   * @return the next pseudorandom, uniformly distributed boolean value
   */
  boolean nextBoolean();
}
