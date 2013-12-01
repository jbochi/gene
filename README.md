# gene

Parallel Genetic Algorithm in Clojure

Work in Progress

## Usage

You only need to define a couple of functions and pass a couple of constants to run your own a genetic algorithm:

* `:score`: The fitness function for a given DNA
* `:random-solution`: A function that returns a random DNA
* `:mutate`: A function that mutates a DNA
* `:crossover`: A function that takes two DNAs and generate a new one
* `:population-size`: The population size for any generation
* `:n-generations`: The number of generations that will run

We provide two examples:

`src/uncap/core.clj` has a solver for the uncapacitated facility location problem.

`src/weasel/core.clj` has a solver for the weasel program.

## Running tests

    $ lein test

## Solving the uncapacitated facility location problem

### Build

    $ lein uberjar

### Running

    $ java -jar ./target/gene-0.1.0-SNAPSHOT-standalone.jar -f test/uncap/data/cap101.txt

## License

Copyright © 2013 Juarez Bochi

Distributed under the Eclipse Public License, the same as Clojure.
