package evoalg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import evoalg.fitness.IEvaluate;
import evoalg.genotype.bitstring.BitString;

public class PopulationTest {

  @SuppressWarnings("unchecked")
  private final IEvaluate<BitString> ievaluate = Mockito.mock(IEvaluate.class);

  @Test
  public void correctlyCreatesPopulation() {
    BitString genotype = new BitString(2);
    int demeSize = 5;
    int populationSize = 10;

    Population<BitString> population = new Population<>(ievaluate, genotype, populationSize, demeSize);

    assertEquals(populationSize, population.size());
    assertEquals(demeSize, population.getDemes().get(0).size());
  }

  @Test
  public void correctlyCreatesPopulationWithSetDemes() {
    BitString genotype = new BitString(2);
    int demeSize = 5;
    List<Deme<BitString>> demes = Arrays.asList(new Deme<BitString>(ievaluate, demeSize, genotype),
        new Deme<BitString>(ievaluate, demeSize, genotype));

    Population<BitString> population = new Population<>(demeSize, demes);
    assertEquals(demes.size(), population.size());
    assertTrue(population.getDemes().containsAll(demes));
  }

  @Test
  public void correctlyResetsPopulation() {
    BitString genotype = new BitString(2);
    int demeSize = 5;
    List<Deme<BitString>> demes = Arrays.asList(new Deme<BitString>(ievaluate, demeSize, genotype),
        new Deme<BitString>(ievaluate, demeSize, genotype));

    Population<BitString> population = new Population<>(demeSize, demes);
    Population<BitString> resetPopulation = population.reset(genotype, ievaluate);

    //the size stays the same, but demes are initialized to random ones
    assertEquals(population.size(), resetPopulation.size());
    assertFalse(resetPopulation.getDemes().containsAll(demes));
  }

}
