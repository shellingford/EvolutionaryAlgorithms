package evoalg.genotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import evoalg.Individual;
import evoalg.State;

public class Crossover<T extends Genotype<T>> {

  private List<List<CrossoverOp<T>>> operators;
  private List<List<Double>> opProb;
  @Getter
  private State<T> state;
  private String crxGenotypes;
  private Random random;

  public Crossover(State<T> state) {
    this.state = state;
    this.random = new Random();
    operators = new ArrayList<>();
    opProb = new ArrayList<>();
  }

  public void initialize() {
    if (!crxGenotypes.equalsIgnoreCase("random") && !crxGenotypes.equalsIgnoreCase("all")) {
      crxGenotypes = "random";
    }

    for (int genotip = 0; genotip < operators.size(); genotip++) {
      int brojOperatora = operators.get(genotip).size();
      List<Double> probs = new ArrayList<>(brojOperatora);

      probs.add(operators.get(genotip).get(0).getProbability());
      for (int i = 1; i < brojOperatora; i++) {
        probs.add(probs.get(i - 1) + operators.get(genotip).get(i).getProbability());
      }

      if (probs.get(brojOperatora - 1) == 0) {
        probs.set(0, -1.);
      }
      else if (probs.get(brojOperatora - 1) != 1) {
        double normal = probs.get(brojOperatora - 1);
        for (int i = 0; i < probs.size(); i++) {
          probs.set(i, probs.get(i) / normal);
        }
      }
      opProb.add(probs);
    }
  }

  public void mate(Individual<T> ind1, Individual<T> ind2, Individual<T> child) {
    child.getFitness().setValid(false);

    if (ind1 == child) {
      ind1 = ind1.copy();
    }
    if (ind2 == child) {
      ind2 = ind2.copy();
    }

    if (crxGenotypes.equalsIgnoreCase("all")) {
      for (int iGenotype = 0; iGenotype < ind1.size(); iGenotype++) {
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

        T g = operators.get(iGenotype).get(iOperator).mate(ind1.get(iGenotype), ind2.get(iGenotype));

        child.set(iGenotype, g);
      }
    }
    else if (crxGenotypes.equalsIgnoreCase("random")) {
      int iGenotype = random.nextInt(ind1.size());

      // copy unchanged genotypes from parents
      for (int i = 0; i < iGenotype; i++) {
        child.set(i, ind1.get(i).copy());
      }
      for (int i = iGenotype + 1; i < child.size(); i++) {
        child.set(i, ind2.get(i).copy());
      }

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
      T g = operators.get(iGenotype).get(iOperator).mate(ind1.get(iGenotype), ind2.get(iGenotype));
      child.set(iGenotype, g);
    }
  }

  public List<List<CrossoverOp<T>>> getOperators() {
    return operators;
  }

  public void addOperator(CrossoverOp<T> operator, int genotypeID) {
    if (operators.size() == genotypeID) {
      List<CrossoverOp<T>> krizanja = new ArrayList<>();
      krizanja.add(operator);
      operators.add(krizanja);
    }
    else {
      operators.get(genotypeID).add(operator);
    }
  }
}
