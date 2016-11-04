package evoalg.genotype.bitstring;

import java.util.ArrayList;
import java.util.List;

import evoalg.State;
import evoalg.genotype.MutationOp;

/**
 * Simple mutation algorithm, where it randomly takes one bit of the bitstring and
 * flips it.
 *
 * For example:
 *  Bitstring: 0100101
 *  random bit position to be mutated: 2
 *
 *  Mutated:   0110101
 */
public class BitStringMutSimple extends MutationOp<BitString> {

  public BitStringMutSimple(State<BitString> state) {
    super(state);
  }

  @Override
  public BitString mutate(BitString genotype) {
    int position = getRandom().nextInt(genotype.size());

    List<Byte> newData = new ArrayList<>(genotype.getData());
    newData.set(position, (byte) ((newData.get(position) + 1) % 2));

    return genotype.replaceData(newData);
  }

}
