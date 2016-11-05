package evoalg.genotype;

import java.util.Random;

import lombok.Getter;

@Getter
public abstract class MutationOp<T extends Genotype<T>> {

  private double probability;
  private T myGenotype;
  private Random random;

  public MutationOp() {
    this.random = new Random();
    this.probability = 0;
  }

  public abstract T mutate(T ind);

  public void setMyGenotype(T myGenotype) {
    this.myGenotype = myGenotype;
  }
}
