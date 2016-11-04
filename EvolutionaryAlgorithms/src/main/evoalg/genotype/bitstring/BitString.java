package evoalg.genotype.bitstring;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

import com.google.common.collect.ImmutableList;

import evoalg.State;
import evoalg.genotype.CrossoverOp;
import evoalg.genotype.Genotype;
import evoalg.genotype.MutationOp;

public class BitString extends Genotype<BitString> {

  private final List<Byte> data;
  @Getter
  private final int nBits;

  public BitString(State<BitString> state) {
    this(state, 0);
    initialize();
  }

  public BitString(State<BitString> state, int nBits) {
    super(state, "BitString");
    this.nBits = nBits;
    data = new ArrayList<>(nBits);
    initialize();
  }

  public BitString(State<BitString> state, String name, int genotypeId, List<Byte> data) {
    super(genotypeId, name, state);
    this.data = data;
    this.nBits = data.size();
  }

  public BitString(State<BitString> state, List<Byte> data) {
    super(0, "", state);
    this.data = data;
    this.nBits = data.size();
  }

  private void initialize() {
    Random random = new Random();
    for (int i = 0; i < data.size(); i++) {
      data.set(i, (byte) random.nextInt(2));
    }
  }

  @Override
  public BitString copy() {
    return replaceData(data);
  }

  /**
   * Creates new instance of BitString object that is copy of current one but
   * with new bits.
   *
   * @param newData new bits
   * @return copy of current instance of BitString with new bits
   */
  public BitString replaceData(List<Byte> newData) {
    return new BitString(getState(), getName(), getGenotypeId(), new ArrayList<Byte>(newData));
  }

  @Override
  public List<CrossoverOp<BitString>> getCrossoverOp() {
    return ImmutableList.of(new BitStringCrsOnePoint(getState()));
  }

  @Override
  public List<MutationOp<BitString>> getMutationOp() {
    return ImmutableList.of(new BitStringMutSimple(getState()));
  }

  public byte get(int index) {
    return data.get(index);
  }

  public int size() {
    return data.size();
  }

  public List<Byte> getData() {
    return ImmutableList.copyOf(data);
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
