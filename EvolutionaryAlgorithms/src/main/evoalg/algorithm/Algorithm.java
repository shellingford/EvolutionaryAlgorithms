package evoalg.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import evoalg.Deme;
import evoalg.Individual;
import evoalg.State;
import evoalg.genotype.Crossover;
import evoalg.genotype.Genotype;
import evoalg.genotype.Mutation;
import evoalg.selection.SelectionOperator;

public abstract class Algorithm<T extends Genotype<T>> {

  private final List<SelectionOperator<T>> selectionOp;

  private final Crossover<T> crossover;

  private final Mutation<T> mutation;

  private final State<T> state;

  private Deme<T> activeDeme;

  private String name;

  public Algorithm(State<T> state, String name, Mutation<T> mutation, Crossover<T> crossover,
      List<SelectionOperator<T>> selectionOp) {
    this.state = state;
    this.name = name;
    this.selectionOp = selectionOp;
    this.mutation = mutation;
    this.crossover = crossover;
  }

  public abstract void initialize();

  public void initializePopulation() {
    for (int iDeme = 0; iDeme < state.getPopulation().size(); iDeme++) {
      Deme<T> deme = state.getPopulation().get(iDeme);
      for (int iInd = 0; iInd < deme.size(); iInd++) {
        evaluate(deme.get(iInd));

      }
    }
  }

  public void advanceGeneration() {
    for (int iDeme = 0; iDeme < state.getPopulation().size(); iDeme++) {
      activeDeme = state.getPopulation().get(iDeme);
      advanceGeneration(activeDeme);
    }
  }

  public abstract void advanceGeneration(Deme<T> deme);

  protected void evaluate(Individual<T> ind) {
    ind.setFitness(state.getFitness().copy());
    ind.evaluate();
  }

  protected int mutate(List<Individual<T>> pool) {
    return mutation.mutation(pool);
  }

  protected int mutate(Individual<T> ind) {
    List<Individual<T>> pool = new ArrayList<>(1);
    pool.add(ind);
    return mutate(pool);
  }

  protected void replaceWith(Individual<T> oldInd, Individual<T> newInd) {
    activeDeme.replace(oldInd.getIndex(), newInd);
  }

  protected void mate(Individual<T> ind1, Individual<T> ind2, Individual<T> child) {
    crossover.mate(ind1, ind2, child);
  }

  protected Individual<T> copy(Individual<T> source) {
    return source.copy();
  }

  protected boolean removeFrom(Individual<T> victim, List<Individual<T>> pool) {
    int index = 0;
    while (index < pool.size() && pool.get(index) != victim) {
      index++;
    }
    if (index == pool.size()) {
      return false;
    }
    pool.remove(index);
    return true;
  }

  protected boolean isMember(Individual<T> single, List<Individual<T>> pool) {
    return pool.contains(single);
  }

  public Crossover<T> getCrossover() {
    return crossover;
  }

  public Mutation<T> getMutation() {
    return mutation;
  }

  public List<SelectionOperator<T>> getSelectionOp() {
    return Collections.unmodifiableList(this.selectionOp);
  }

  public String getName() {
    return name;
  }
}
