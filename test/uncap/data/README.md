Uncapacitated warehouse location
================================

Problems taken from http://people.brunel.ac.uk/~mastjjb/jeb/orlib/uncapinfo.html

There are currently 15 data files.

These data files are the test problem sets VII, X, XIII and
A to C in Table 2 of J.E.Beasley; Lagrangean heuristics for
location problems; European Journal of Operational
Research, vol.65, 1993, pp383-399.

The following table gives the relationship between test
problem set and the appropriate files:

Problem set        Files
VII                cap71, ..., cap74
X                  cap101, ..., cap104
XIII               cap131, ..., cap134
A                  capa
B                  capb
C                  capc

The format of these data files is:
number of potential warehouse locations (m), number of
customers (n)
for each potential warehouse location i (i=1,...,m):
capacity, fixed cost
for each customer j (j=1,...,n): demand, cost of allocating
all of the demand of j to warehouse i (i=1,...,m)

As these test problems are derived from test problems for
the capacitated warehouse location problem they include
capacity figures for each potential warehouse location
and demand figures for each customer. These figures should
(obviously) be ignored when solving these problems
as uncapacitated warehouse location problems.

The value of the optimal solution for each of these data
files is given in the file uncapopt.

The largest file is capc of size 1300Kb (approximately).
The entire set of files is of size 4500Kb (approximately).
