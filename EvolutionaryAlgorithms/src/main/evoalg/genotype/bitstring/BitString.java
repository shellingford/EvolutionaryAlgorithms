package evoalg.genotype.bitstring;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

import com.google.common.collect.ImmutableList;

import evoalg.genotype.CrossoverOp;
import evoalg.genotype.Genotype;
import evoalg.genotype.MutationOp;

public class BitString extends Genotype<BitString> {

  private final List<Byte> data;
  @Getter
  private final int nBits;

  public BitString() {
    this(0);
    initialize();
  }

  public BitString(int nBits) {
    super("BitString");
    this.nBits = nBits;
    data = new ArrayList<>(nBits);
    initialize();
  }

  public BitString(String name, int genotypeId, List<Byte> data) {
    super(genotypeId, name);
    this.data = data;
    this.nBits = data.size();
  }

  public BitString(List<Byte> data) {
    super(0, "");
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
    return new BitString(getName(), getGenotypeId(), new ArrayList<Byte>(newData));
  }

  @Override
  public List<CrossoverOp<BitString>> getCrossoverOp() {
    return ImmutableList.of(new BitStringCrsOnePoint());
  }

  @Override
  public List<MutationOp<BitString>> getMutationOp() {
    return ImmutableList.of(new BitStringMutSimple());
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
