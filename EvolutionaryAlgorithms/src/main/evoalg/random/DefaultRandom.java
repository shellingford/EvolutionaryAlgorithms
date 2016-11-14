package evoalg.random;

import java.util.Random;

/**
 * Default random implementation that uses {@link java.util.Random} to
 * generate random numbers.
 */
public class DefaultRandom implements IRandomness {

  private final Random random;

  public DefaultRandom() {
    this.random = new Random();
  }

  @Override
  public int nextInt(int bound) {
    return random.nextInt(bound);
  }

  @Override
  public double nextDouble() {
    return random.nextDouble();
  }

  @Override
  public boolean nextBoolean() {
    return random.nextBoolean();
  }

}
