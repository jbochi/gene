# gene

Distributed Genetic Algorithm in Clojure

## Usage

You only need to define a couple of functions and pass a couple of constants to run your own a genetic algorithm:

* `:score`: The fitness function for a given DNA
* `:random-solution`: A function that returns a random DNA
* `:mutate`: A function that mutates a DNA
* `:crossover`: A function that takes two DNAs and generate a new one
* `:population-size`: The population size for any generation
* `:n-generations`: The number of generations that will run

We provide two examples:

`src/uncap/core.clj` has a solver for the [uncapacitated facility location problem](http://en.wikipedia.org/wiki/Facility_location).

`src/weasel/core.clj` has a solver for the [Weasel Program](http://en.wikipedia.org/wiki/Weasel_program).

## Going distributed

We provide two additional optional arguments to run the simulation on a cluster:

* `:listen-addr`: The address to listen/receive new solutions
* `:send-addr`: The address to send/export new solutions

For example, you can run a master node with `:listen-addr` set to `"tcp://*:9999"` and any number of
workers with `:send-addr` set to `"tcp://master-node-ip:9999"`. Other topologies are possible.

This follows the "Island model", where each process runs it's own simulation and some individuals
migrate from island to island.

We use [ØMQ](http://zeromq.org/) for all communication.

## Running tests

    $ lein test

## Solving the uncapacitated facility location problem

### Build

    $ lein uberjar

### Running

    $ java -jar ./target/gene-0.1.0-SNAPSHOT-standalone.jar -f test/uncap/data/cap101.txt

To see all available options, run it with no arguments:

    $ java -jar ./target/gene-0.1.0-SNAPSHOT-standalone.jar

## License

Copyright © 2013 Juarez Bochi

Distributed under the Eclipse Public License, the same as Clojure.
