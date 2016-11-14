# EvolutionaryAlgorithms

Evolutionary algorithms framework started when I was still in college with a friend. Now I intend to
modernize it and add more algorithms.

Currently implemented genotypes and crossover/mutation operators for them:
* BitString
 * single point crossover operator
 * uniform crossover operator
 * simple bit flip mutation
 * simple bit mix mutation
 
* Permutation
 * PMX crossover operator
 * OX crossover operator
 * rotation mutation
 * inversion mutation
 * simple toggle mutation
 
There are also two examples, one for each genotype:
* OneMax - bitstring genotype
* N-qeens problem - permutation genotype

NOTE: I'm using Lombok to replace a lot of boilerplate code (getters, constructors...) with annotations so you need to install it for your IDE from: https://projectlombok.org/download.html
