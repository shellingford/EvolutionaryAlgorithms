package evoalg.genotype;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import evoalg.State;

@Getter(value = AccessLevel.PUBLIC)
@AllArgsConstructor
public abstract class Genotype<T extends Genotype<T>> {

  private int genotypeId;
  private String name;
  private State<T> state;

  public Genotype(State<T> state, String name) {
    this.state = state;
    this.name = name;
  }

  public abstract void initialize();

  public abstract T copy();

  public abstract List<CrossoverOp<T>> getCrossoverOp();

  public abstract List<MutationOp<T>> getMutationOp();

  public void setGenotypeId(int genotypeId) {
    this.genotypeId = genotypeId;
  }

  public void setName(String name) {
    this.name = name;
  }

}
