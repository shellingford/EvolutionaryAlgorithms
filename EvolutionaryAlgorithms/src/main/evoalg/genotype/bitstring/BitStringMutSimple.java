package evoalg.genotype.bitstring;

import evoalg.State;
import evoalg.genotype.MutationOp;

public class BitStringMutSimple extends MutationOp<BitString> {

  public BitStringMutSimple(State<BitString> state) {
    super(state);
  }

  @Override
  public void mutate(BitString genotip) {
    int pozicija = getRandom().nextInt(genotip.size());
    genotip.set(pozicija, (byte) ((genotip.get(pozicija) + 1) % 2));
  }

}
