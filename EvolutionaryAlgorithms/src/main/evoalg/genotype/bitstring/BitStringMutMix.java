package evoalg.genotype.bitstring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import evoalg.genotype.MutationOp;

/**
 * Mix bitstring mutation operator.
 * Algorithm: first select two points in the bitstring and then between those points (inclusive both)
 * randomly replace 1s and 0s, but the count of 1s and 0s needs to stay the same.
 *
 * For example:
 *  Bitstring: 01 | 001 | 11
 *
 *  Mutated:   01 | 010 | 11
 */
public class BitStringMutMix extends MutationOp<BitString> {

  @Override
  public BitString mutate(BitString genotype) {
    int point1 = getRandom().nextInt(genotype.size() - 1);
    int point2 = point1 + 1 + getRandom().nextInt(genotype.size() - point1 - 1);

    return shuffleValuesBetweenPoints(genotype, point1, point2);
  }

  /**
   * Extracts sublist that is from point1 to point2 within genotype data, then shuffle that sublist
   * and replace original bits within two points with shuffled ones.
   *
   * @param genotype individual's genotype
   * @param point1 first random point
   * @param point2 second random point
   * @return new bitstring with shuffled data
   */
  private BitString shuffleValuesBetweenPoints(BitString genotype, int point1, int point2) {
    List<Byte> dataBetweenPoints = new ArrayList<>(genotype.getData().subList(point1, point2 + 1));
    Collections.shuffle(dataBetweenPoints);

    List<Byte> newData = new ArrayList<>(genotype.getData());
    IntStream.range(point1, point2 + 1).forEach(i -> setNewValues(i, point1, newData, dataBetweenPoints));

    return genotype.replaceData(newData);
  }

  /**
   * Sets new bitstring value of bit i with a value from dataBetweenPoints.
   *
   * @param i bit index which value will be changed
   * @param point1 first random point from where we change bits
   * @param data original bitstring data
   * @param dataBetweenPoints shuffled data between two points
   */
  private void setNewValues(int i, int point1, List<Byte> data, List<Byte> dataBetweenPoints) {
    data.set(i, dataBetweenPoints.get(i - point1));
  }
}
