package evoalg;

/**
 * Wrapper around {@link System#currentTimeMillis() System.currentTimeMillis} that will
 * be used to measure algorithm duration and help with testing.
 */
public class SystemTime {

  /**
   * Returns the current time in milliseconds.
   *
   * @return the difference, measured in milliseconds, between the current time and midnight, January 1, 1970 UTC.
   */
  public long currentTimeMillis() {
    return System.currentTimeMillis();
  }

}
