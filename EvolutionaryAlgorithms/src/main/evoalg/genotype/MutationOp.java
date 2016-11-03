package evoalg.genotype;

import java.util.Random;

import lombok.Getter;
import evoalg.State;

@Getter
public abstract class MutationOp<T extends Genotype<T>> {

  private State<T> state;
  private double probability;
  private T myGenotype;
  private Random random;

  public MutationOp(State<T> state) {
    this.state = state;
    this.random = new Random();
    this.probability = 0;
  }

  public abstract void mutate(T ind);

  public void setMyGenotype(T myGenotype) {
    this.myGenotype = myGenotype;
  }
}
