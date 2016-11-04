package evoalg.genotype.bitstring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import evoalg.State;
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

  public BitStringMutMix(State<BitString> state) {
    super(state);
  }

  @Override
  public BitString mutate(BitString genotype) {
    int point1 = getRandom().nextInt(genotype.size() - 1);
    int point2 = point1 + 1 + getRandom().nextInt(genotype.size() - point1 - 1);

    return shuffleValuesBetweenPoints(genotype, point1, point2);
  }

  private BitString shuffleValuesBetweenPoints(BitString genotype, int point1, int point2) {
    List<Byte> dataBetweenPoints = new ArrayList<>(genotype.getData().subList(point1, point2 + 1));
    Collections.shuffle(dataBetweenPoints);

    List<Byte> newData = new ArrayList<>(genotype.getData());
    IntStream.range(point1, point2 + 1).forEach(i -> setNewValues(i, point1, newData, dataBetweenPoints));

    return genotype.replaceData(newData);
  }

  private void setNewValues(int i, int point1, List<Byte> data, List<Byte> dataBetweenPoints) {
    data.set(i, dataBetweenPoints.get(i - point1));
  }
}
