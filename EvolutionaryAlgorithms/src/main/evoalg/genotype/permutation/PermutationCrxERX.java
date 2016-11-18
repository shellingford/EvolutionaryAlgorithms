package evoalg.genotype.permutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import evoalg.genotype.CrossoverOp;
import evoalg.random.IRandomness;

/**
 * Edge recombination crossover (ERX) implementation for permutation genotype.
 *
 * It is an operator that creates a path that is similar to a set of existing
 * paths (parents) by looking at the edges rather than the vertices. Operator
 * is based on an adjacency matrix, which lists the neighbours of each node in
 * any parent.
 *
 * Algorithm:
 * CHILD = Empty Chromosome
 * 1. X = the first node from a random parent.
 * 2. While the CHILD chromo isn't full, Loop:
 *    - Append X to CHILD
 *    - Remove X from Neighbour Lists
 *
 *    if X's neighbour list is empty:
 *       - Z = random node not already in CHILD
 *    else
 *       - Determine neighbour of X that has fewest neighbours
 *       - If there is a tie, randomly choose 1
 *       - Z = chosen node
 *    X = Z
 *
 * Parent 1: 0154362
 * Parent 2: 6501234
 * Generate Neighbour List:
 *  0: 1 2 5
 *  1: 0 5 2
 *  2: 0 6 1 3
 *  3: 4 6 2 4
 *  4: 5 3 6
 *  5: 1 4 6 0
 *  6: 3 2 4 5
 *
 * Possible child: 0154623
 */
public class PermutationCrxERX extends CrossoverOp<Permutation> {

  public PermutationCrxERX(IRandomness random) {
    super(random);
  }

  @Override
  public Permutation mate(Permutation parent1, Permutation parent2) {
    Map<Integer, Set<Integer>> neighbours = createNeighboursMap(parent1, parent2);
    List<Integer> newData = new ArrayList<>();

    IntStream.range(0, parent1.size()).forEach(i -> getNewEdge(newData, neighbours));

    return new Permutation(newData);
  }

  /**
   * Creates a map where key is an element (value, not index) from a parent, and value is set with all
   * the neighbours from both parents for that element.
   *
   * @param parent1 parent
   * @param parent2 parent
   * @return map with neighbour data
   */
  private Map<Integer, Set<Integer>> createNeighboursMap(Permutation parent1, Permutation parent2) {
    Map<Integer, Set<Integer>> neighbours = new HashMap<>();

    for (int i = 0; i < parent1.size(); i++) {
      //parents have the same size so this calculation will be valid for both parents
      int before = i - 1 >= 0 ? i - 1 : parent1.size() - 1;
      int after = i + 1 < parent1.size() ? i + 1 : 0;

      addToMap(neighbours, parent1.get(i), parent1.get(before), parent1.get(after));
      addToMap(neighbours, parent2.get(i), parent2.get(before), parent2.get(after));
    }

    return neighbours;
  }

  /**
   * If key already exists in the map then it appends its set with neighbour1 and neighbour2,
   * if not then it creates a new set with values neighbour1 and neighbour2 and puts it in the
   * map with specified key.
   *
   * @param neighbours map
   * @param key map's key
   * @param neighbour1 neighbour from parent1
   * @param neighbour2 neighbour from parent2
   */
  private void addToMap(Map<Integer, Set<Integer>> neighbours, int key, int neighbour1, int neighbour2) {
    Set<Integer> keyNeighbours = new HashSet<>();
    if (neighbours.containsKey(key)) {
      keyNeighbours = neighbours.get(key);
    }
    else {
      neighbours.put(key, keyNeighbours);
    }
    keyNeighbours.add(neighbour1);
    keyNeighbours.add(neighbour2);
  }

  /**
   * If newData is empty then it randomly adds new element in it, otherwise add new element
   * that has least number of neighbours from the neighbours of last added element in newData.
   * If that element doesn't have any neighbours then add a random element.
   *
   * @param newData child's chromosome data list
   * @param neighbours map with neighbours data
   */
  private void getNewEdge(List<Integer> newData, Map<Integer, Set<Integer>> neighbours) {
    if (newData.isEmpty()) {
      addRandomElement(newData, neighbours);
    }
    else {
      int lastElement = newData.get(newData.size() - 1);
      Set<Integer> keyNeighbours = neighbours.get(lastElement);
      if (keyNeighbours == null || keyNeighbours.isEmpty()) {
        addRandomElement(newData, neighbours);
      }
      else {
        int neighbour = findNeighbour(keyNeighbours, neighbours);
        newData.add(neighbour);
        removeNeighbour(neighbours, neighbour);
      }
    }
  }

  /**
   * Adds randomly new key from neighbours map in the newData that is not already in it.
   * After that we remove all neighbours that have value of random key from neighbour map.
   *
   * @param newData child's chromosome data list
   * @param neighbours map with neighbours data
   */
  private void addRandomElement(List<Integer> newData, Map<Integer, Set<Integer>> neighbours) {
    List<Integer> keys = new ArrayList<>(neighbours.keySet());
    //we need only elements that are not already in the newData list
    keys.removeAll(newData);

    int randomElem = getRandom().nextInt(keys.size());
    int key = keys.get(randomElem);
    newData.add(key);
    removeNeighbour(neighbours, key);
  }

  /**
   * Removes neighbour value from all values in the map. Key with neighbour value remains
   * in the map, only sets are changed.
   *
   * @param neighbours map from where we must remove neighbour value
   * @param neighbour neighbour value that will be removed
   */
  private void removeNeighbour(Map<Integer, Set<Integer>> neighbours, Integer neighbour) {
    neighbours.forEach((k, v) -> {
      if (v.contains(neighbour)) {
        v.remove(neighbour);
      }
    });
  }

  /**
   * Finds all neighbours in keyNeighbours that have fewest neighbours. If there are multiple,
   * then randomly select one.
   *
   * @param keyNeighbours set of neighbours from which we choose one
   * @param neighbours map with neighbours data
   * @return neighbour with fewest neighbours
   */
  private int findNeighbour(Set<Integer> keyNeighbours, Map<Integer, Set<Integer>> neighbours) {
    List<Integer> possibleNeighbours = new ArrayList<>();
    int minNeighbourCount = Integer.MAX_VALUE;

    for (int keyNeighbour : keyNeighbours) {
      int currentNeighbourCount = neighbours.get(keyNeighbour).size();

      //if current neighbour has fewer neighbours then reset list and add it in it
      if (currentNeighbourCount < minNeighbourCount) {
        minNeighbourCount = currentNeighbourCount;
        possibleNeighbours.clear();
        possibleNeighbours.add(keyNeighbour);
      }
      //if there is another neighbour with fewest neighbours, then add it to the list
      else if (currentNeighbourCount == minNeighbourCount) {
        possibleNeighbours.add(keyNeighbour);
      }
    }

    int randomIndex = getRandom().nextInt(possibleNeighbours.size());
    return possibleNeighbours.get(randomIndex);
  }

}
