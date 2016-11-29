package evoalg.genotype.floatingpoint;

import java.util.ArrayList;
import java.util.List;

import evoalg.genotype.MutationOp;
import evoalg.random.IRandomness;

/**
 * Simple mutation algorithm, where it randomly takes one bit of the floatingpoint and
 * replace it with a new random value.
 *
 * For example:
 *  FloatingPoint: 5.5 1.02 4 7.8 9.2
 *  random bit position to be mutated: 2
 *
 *  Mutated:       5.5 1.02 7.3 7.8 9.2
 */
public class FloatingPointMutSimple extends MutationOp<FloatingPoint> {

  public FloatingPointMutSimple(IRandomness random) {
    super(random);
  }

  @Override
  public FloatingPoint mutate(FloatingPoint genotype) {
    int position = getRandom().nextInt(genotype.size());

    List<Double> newData = new ArrayList<>(genotype.getData());
    newData.set(position,
        genotype.getMinValue() + getRandom().nextDouble() * (genotype.getMaxValue() - genotype.getMinValue()));

    return genotype.replaceData(newData);
  }

}
