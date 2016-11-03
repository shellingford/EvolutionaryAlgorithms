package evoalg.genotype.bitstring;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import evoalg.State;
import evoalg.genotype.CrossoverOp;
import evoalg.genotype.Genotype;
import evoalg.genotype.MutationOp;

@Getter
public class BitString extends Genotype<BitString> {

  private List<Byte> data;
  private int nBits;

  public BitString(State<BitString> state) {
    this(state, 0);
  }

  public BitString(State<BitString> state, int nBits) {
    super(state, "BitString");
    data = new ArrayList<>(nBits);
    this.nBits = nBits;
  }

  public BitString(State<BitString> state, List<Byte> data) {
    super(state, "BitString");
    this.data = data;
  }

  @Override
  public void initialize() {
    Random random = new Random();
    data = new ArrayList<>(nBits);
    for (int i = 0; i < data.size(); i++) {
      data.set(i, (byte) random.nextInt(2));
    }
  }

  @Override
  public BitString copy() {
    BitString copy = new BitString(getState(), data.size());

    for (int i = 0; i < copy.size(); i++) {
      copy.set(i, data.get(i));
    }
    copy.setGenotypeId(getGenotypeId());
    copy.setName(getName());
    return copy;
  }

  @Override
  public List<CrossoverOp<BitString>> getCrossoverOp() {
    List<CrossoverOp<BitString>> krizanja = new ArrayList<>(1);
    krizanja.add(new BitStringCrsOnePoint(getState()));
    return krizanja;
  }

  @Override
  public List<MutationOp<BitString>> getMutationOp() {
    List<MutationOp<BitString>> mutacije = new ArrayList<>(1);
    mutacije.add(new BitStringMutSimple(getState()));
    return mutacije;
  }

  public byte get(int index) {
    return data.get(index);
  }

  public void set(int index, byte element) {
    data.set(index, element);
  }

  public int size() {
    return data.size();
  }

  public void add(byte element) {
    data.add(element);
  }

  public void setData(List<Byte> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    String str = "";

    for (byte bit : data) {
      str += bit;
    }
    return str;
  }
}
