package evoalg.genotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

import com.google.common.collect.ImmutableList;

import evoalg.Individual;

/**
 * Main mutation class that uses mutation operators to mutate and individual
 * or a list of individuals.
 *
 * @param <T> genotype
 */
public class Mutation<T extends Genotype<T>> {

  private final List<MutationOp<T>> operators;
  private final List<Double> opProb;
  @Getter
  private final Random random;
  private double indMutProb;

  public Mutation(List<MutationOp<T>> operators) {
    this.operators = operators;
    this.random = new Random();
    opProb = new ArrayList<>();

    initialize();
  }

  private void initialize() {
    int brojOperatora = operators.size();

    opProb.add(operators.get(0).getProbability());

    for (int i = 1; i < brojOperatora; i++) {
      opProb.add(opProb.get(i - 1) + operators.get(i).getProbability());
    }

    if (opProb.get(brojOperatora - 1) == 0) {
      opProb.set(0, -1.);
    }
    else {
      if (opProb.get(brojOperatora - 1) != 1) {
        double normal = opProb.get(brojOperatora - 1);
        for (int i = 0; i < opProb.size(); i++) {
          opProb.set(i, opProb.get(i) / normal);
        }
      }
    }
  }

  public List<Individual<T>> mutation(List<Individual<T>> pool) {
    List<Individual<T>> poolWithMutations = new ArrayList<>();
    for (int i = 0; i < pool.size(); i++) {
      Individual<T> currentIndividual = pool.get(i);
      if (random.nextDouble() <= indMutProb) {
        currentIndividual = mutate(pool.get(i));
      }
      poolWithMutations.add(currentIndividual);
    }
    return poolWithMutations;
  }

  public Individual<T> mutate(Individual<T> ind) {
    int randOperatorIndex = random.nextInt(operators.size());
    if (random.nextDouble() <= indMutProb) {
      T mutatedGenotype = operators.get(randOperatorIndex).mutate(ind.getGenotype());
      return new Individual<T>(ind.getIevaluate(), mutatedGenotype);
    }
    else {
      return ind;
    }
  }

  /**
   * Immutable collection of mutation operators that will be used
   * for current genotype representation.
   *
   * @return immutable collection of mutation operators
   */
  public List<MutationOp<T>> getMutationOp() {
    return ImmutableList.copyOf(operators);
  }

}
