package evoalg.genotype;

import java.util.Random;

import lombok.Getter;

@Getter
public abstract class CrossoverOp<T extends Genotype<T>> {

  private double probability;
  private T myGenotype;
  private Random random;

  public CrossoverOp() {
    this.random = new Random();
    this.probability = 0;
  }

  public abstract T mate(T ind1, T ind2);

  public void setMyGenotype(T myGenotype) {
    this.myGenotype = myGenotype;
  }
}
