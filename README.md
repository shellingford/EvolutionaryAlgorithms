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
 
* FloatingPoint

Selection algorithms:
* Steady State Tournament
* Roulette Wheel Selection
 
Implemented examples for different genotypes, algorithms and operators:
* OneMax - Bitstring genotype
* N-queens problem - Permutation genotype
* Traveling Salesman Problem - Permutation genotype (with data from http://www.math.uwaterloo.ca/tsp/index.html)



NOTE: I'm using Lombok to replace a lot of boilerplate code (getters, constructors...) with annotations so you need to install it for your IDE from: https://projectlombok.org/download.html
