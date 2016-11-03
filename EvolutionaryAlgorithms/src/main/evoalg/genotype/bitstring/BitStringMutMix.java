package evoalg.genotype.bitstring;

import evoalg.State;
import evoalg.genotype.MutationOp;

public class BitStringMutMix extends MutationOp<BitString> {

  public BitStringMutMix(State<BitString> state) {
    super(state);
  }

  @Override
  public void mutate(BitString bitstring) {
    int bitIndexSmaller = getRandom().nextInt(bitstring.size());
    int bitIndexBigger;

    do {
      bitIndexBigger = getRandom().nextInt(bitstring.size());
    } while (bitIndexSmaller == bitIndexBigger);

    int tmp = bitIndexSmaller;
    if (bitIndexSmaller > bitIndexBigger) {
      bitIndexSmaller = bitIndexBigger;
      bitIndexBigger = tmp;
    }

    int counter0 = 0;
    int counter1 = 0;

    for (int i = bitIndexSmaller; i <= bitIndexBigger; i++) {
      if (bitstring.get(i) == 1) {
        counter1++;
      }
      else {
        counter0++;
      }
    }

    int fairness0 = counter0;
    int fairness1 = counter1;

    for (int i = bitIndexSmaller; i <= bitIndexBigger; i++) {
      int rnd = getRandom().nextInt(fairness0 + fairness1) + 1;
      if (rnd <= fairness1) {
        if (counter1 > 0) {
          bitstring.set(i, (byte) 1);
          counter1--;
        }
        else {
          bitstring.set(i, (byte) 0);
          counter0--;
        }
      }
      else {
        if (counter0 > 0) {
          bitstring.set(i, (byte) 0);
          counter0--;
        }
        else {
          bitstring.set(i, (byte) 1);
          counter1--;
        }
      }
    }
  }
}
