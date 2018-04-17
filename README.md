# boggle-ish
The program's driver is BoggleGame.java, just compile and run this file with Tile.java in the same directory.

The solution-finding aspect of this program is based on the depth-first search algorithm.  I figured that I could treat each Tile object on the board as the root of its own tree, and recursively traverse the possible paths leading out of it, using sum==area as an end case.  Each tree is non-binary, so there are more than two ways to go at each point, which means that a loop is necessary for iterating through each root’s children.  This method significantly increases the efficiency and scalability of my program, because when the program knows a path won’t lead to a solution, it won’t continue on that path and won’t need to calculate any further.
