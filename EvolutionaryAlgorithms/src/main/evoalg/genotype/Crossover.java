package evoalg.genotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import evoalg.Individual;

public class Crossover<T extends Genotype<T>> {

  private List<CrossoverOp<T>> operators;
  private List<Double> opProb;
  private final Random random;

  public Crossover() {
    operators = new ArrayList<>();
    opProb = new ArrayList<>();
    random = new Random();

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
    else if (opProb.get(brojOperatora - 1) != 1) {
      double normal = opProb.get(brojOperatora - 1);
      for (int i = 0; i < opProb.size(); i++) {
        opProb.set(i, opProb.get(i) / normal);
      }
    }
  }

  public Individual<T> mate(Individual<T> ind1, Individual<T> ind2) {
    int randOperatorIndex = random.nextInt(operators.size());
    T croxGenotype = operators.get(randOperatorIndex).mate(ind1.getGenotype(), ind2.getGenotype());
    return new Individual<T>(ind1.getIevaluate(), croxGenotype);
  }

  public void addOperator(CrossoverOp<T> operator) {
    operators.add(operator);
  }
}
