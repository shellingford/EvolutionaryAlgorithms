package evoalg.genotype.bitstring;

import java.util.Random;

import evoalg.State;
import evoalg.genotype.CrossoverOp;

public class BitStringCrsOnePoint extends CrossoverOp<BitString> {

  public BitStringCrsOnePoint(State<BitString> state) {
    super(state);
  }

  @Override
  public BitString mate(BitString genotip1, BitString genotip2) {
    Random random = new Random();
    int i;

    int pozicija = random.nextInt(genotip1.size());
    BitString novi = new BitString(getState(), genotip1.size());
    switch (random.nextInt(2)) {
    case 0:
      for (i = 0; i < pozicija; i++) {
        novi.set(i, genotip1.get(i));
      }
      for (i = pozicija; i < genotip1.size(); i++) {
        novi.set(i, genotip2.get(i));
      }
      break;
    case 1:
      for (i = 0; i < pozicija; i++) {
        novi.set(i, genotip2.get(i));
      }
      for (i = pozicija; i < genotip1.size(); i++) {
        novi.set(i, genotip1.get(i));
      }
      break;
    }
    return novi;
  }
}
