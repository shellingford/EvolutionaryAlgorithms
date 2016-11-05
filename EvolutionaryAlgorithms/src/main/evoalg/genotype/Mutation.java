package evoalg.genotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import evoalg.Individual;
import evoalg.State;

public class Mutation<T extends Genotype<T>> {

  private List<List<MutationOp<T>>> operators;
  private List<List<Double>> opProb;
  @Getter
  private State<T> state;
  private String mutGenotypes;
  private Random random;
  private double indMutProb;
  private double geneMutProb;

  public Mutation(State<T> state) {
    this.state = state;
    this.random = new Random();
    operators = new ArrayList<>();
    opProb = new ArrayList<>();
  }

  public void initialize() {
    for (int genotip = 0; genotip < operators.size(); genotip++) {
      int brojOperatora = operators.get(genotip).size();

      List<Double> probs = new ArrayList<>(brojOperatora);
      probs.add(operators.get(genotip).get(0).getProbability());

      for (int i = 1; i < brojOperatora; i++) {
        // probs.set(i, probs.get(i-1) +
        // operators.get(genotip).get(i).getProbability());
        probs.add(probs.get(i - 1) + operators.get(genotip).get(i).getProbability());
      }

      if (probs.get(brojOperatora - 1) == 0) {
        probs.set(0, -1.);
      }
      else {
        if (probs.get(brojOperatora - 1) != 1) {
          double normal = probs.get(brojOperatora - 1);
          for (int i = 0; i < probs.size(); i++) {
            probs.set(i, probs.get(i) / normal);
          }
        }
        opProb.add(probs);
      }
    }
  }

  public int mutation(List<Individual<T>> pool) {
    int mutated = 0;
    for (int i = 0; i < pool.size(); i++) {
      if (random.nextDouble() <= indMutProb) {
        mutated++;
        mutate(pool.get(i));
      }
    }

    return mutated;
  }

  public void mutate(Individual<T> ind) {
    if (mutGenotypes.equalsIgnoreCase("random")) {
      int iGenotype = random.nextInt(ind.size());
      int iOperator;

      if (opProb.get(iGenotype).get(0) < 0) {
        iOperator = random.nextInt(operators.get(iGenotype).size());
      }
      else {
        double slucajni = random.nextDouble();
        iOperator = 0;
        while (opProb.get(iGenotype).get(iOperator) < slucajni) {
          iOperator++;
        }
      }
      operators.get(iGenotype).get(iOperator).mutate(ind.get(iGenotype));
    }
    else if (mutGenotypes.equalsIgnoreCase("all")) {
      for (int iGenotype = 0; iGenotype < ind.size(); iGenotype++) {
        // choose operator
        int iOperator;

        if (opProb.get(iGenotype).get(0) < 0) {
          iOperator = random.nextInt(operators.get(iGenotype).size());
        }
        else {
          double slucajni = random.nextDouble();
          iOperator = 0;
          while (opProb.get(iGenotype).get(iOperator) < slucajni) {
            iOperator++;
          }
        }
        operators.get(iGenotype).get(0).mutate(ind.get(iGenotype));
      }
    }
  }

  public List<List<MutationOp<T>>> getOperators() {
    return operators;
  }

  public void addOperator(MutationOp<T> operator, int genotypeID) {
    if (operators.size() == genotypeID) {
      List<MutationOp<T>> mutacije = new ArrayList<>();
      mutacije.add(operator);
      operators.add(mutacije);
    }
    else {
      operators.get(genotypeID).add(operator);
    }
  }

  public double getGeneMutProb() {
    return geneMutProb;
  }
}
