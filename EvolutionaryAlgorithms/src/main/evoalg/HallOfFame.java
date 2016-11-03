package evoalg;

import java.util.ArrayList;
import java.util.List;

import evoalg.genotype.Genotype;
import evoalg.selection.SelBestOp;

public class HallOfFame<T extends Genotype<T>> {

  private boolean empty;
  private State<T> state;
  private int size;
  private int lastChangeGen;
  private List<Individual<T>> bestIndividuals;
  private List<Integer> bestGenerations;
  private SelBestOp<T> selectBest;

  public HallOfFame() {
    selectBest = new SelBestOp<T>();
    size = 0;
    bestIndividuals = new ArrayList<>();
    bestGenerations = new ArrayList<>();
    empty = true;
    lastChangeGen = 0;
  }

  public void initialize(State<T> state) {
    this.state = state;
  }

  public void operate(State<T> state) {
    Population<T> pop = state.getPopulation();
    for (int i = 0; i < pop.size(); i++) {
      operate(pop.get(i));
    }
  }

  public void operate(List<Individual<T>> individuals) {
    Individual<T> best = selectBest.select(individuals);

    if (empty) {
      bestIndividuals.add(best.copy());
      bestGenerations.add(state.getGenerationNo());
      empty = false;
      lastChangeGen = state.getGenerationNo();
      size++;
      return;
    }

    for (int i = 0; i < size; i++) {
      if (best.getFitness().compareTo(bestIndividuals.get(i).getFitness()) == -1) {
        bestIndividuals.add(i, best.copy());
        bestGenerations.add(i, state.getGenerationNo());
        lastChangeGen = state.getGenerationNo();
      }
    }
  }

  public List<Individual<T>> getBest() {
    return bestIndividuals;
  }

  public int getLastChange() {
    return lastChangeGen;
  }
}
