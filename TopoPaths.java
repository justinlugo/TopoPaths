// Justin Lugo
// COP 3503, Fall 2021

import java.io.*;
import java.util.*;

public class TopoPaths
{
  public static int countTopoPaths(String filename)
  {
    // The purpose of this function is to count the number of TopoPaths within a graph written
    // in a text file and return said value (there can only be one TopoPath, so we return either 1
    // or 0).
    try
    {
      // We start by setting up the variables that we plan on utilizing and initialize the ones
      // that need to begin at 0 before we get into the topological processes.
      int count = 0, node, numEdges, numVertices, vertex;
      int [] incomingEdge;
      boolean [][] adjMatrix;
      File file = new File(filename);
      Scanner scan = new Scanner(file);

      // The first number in the text file is the number of vertices that we have within our graph.
      numVertices = scan.nextInt();

      // Set the adjacency matrix to have dimensions accordingly to the number of vertices passed
      // in to the program.
      adjMatrix = new boolean[numVertices][numVertices];

      // Set our adjacency matrix as occupied at the corresponding edge and vertex values. We start
      // this by iterating through the amount of vertices we have in our graph.
      for (int i = 0; i < numVertices; i++)
      {
        // The next number is the first number of each line of the text file, and that integer
        // represents the number of edges that we have from each vertex.
        numEdges = scan.nextInt();

        // Then we finally iterate through the amount of edges there are...
        for (int j = 0; j < numEdges; j++)
        {
          // ... And scan in our connected vertices, and set the matrix to true at its proper 
          // location.
          vertex = scan.nextInt();
          adjMatrix[i][vertex - 1] = true;
        }
      }

      // Now we topologically sort our graph, in which case I adapted code from Prof. Szumlanski's
      // toposort.java code from class to match my coding style while also applying it to return
      // an integer value instead of a boolean one.
      incomingEdge = new int[adjMatrix.length];

      // First we count the number of incoming edges of each respective vertex based off our
      // adjacency matrix.
      for (int i = 0; i < adjMatrix.length; i++)
      {
        for (int j = 0; j < adjMatrix.length; j++)
        {
          // If we have a true value at the matrix indices, we increment our number of incoming
          // edges.
          if (adjMatrix[i][j] == true)
          {
            incomingEdge[j] += 1;
          }
          // Otherwise, we don't have any incoming edges at that location, so we do not increment.
          else
          {
            incomingEdge[j] += 0;
          }
        }
      }

      Queue<Integer> q = new ArrayDeque<Integer>();

      // If our vertex has zero incoming edges, we add it to the queue so that we can visit it and
      // continue our process.
      for (int i = 0; i < adjMatrix.length; i++)
      {
        if (incomingEdge[i] == 0)
        {
          q.add(i);
        }
      }

      // While the queue is not empty, we're going to pull out a vertex to add to our topological
      // sort.
      while (!q.isEmpty())
      {
        node = q.remove();

        // If our queue is not empty (meaning it has a value being held at the head), AND the matrix
        // value at the head and our current node is false, we return 0 as that means that there is
        // no valid topopath.
        if ((!q.isEmpty()) && (adjMatrix[q.peek()][node] == false))
        {
          return 0;
        }

        // Count the number of unique vertices we see.
        ++count;

        // Now we decrement the incoming edge count and if it equals zero, we add it to our queue
        // to topologically sort.
        for (int i = 0; i < adjMatrix.length; i++)
        {
          if (adjMatrix[node][i] == true && --incomingEdge[i] == 0)
          {
            q.add(i);
          }
        }
      }

      // If we pass out of the loop without including each vertex in our
      // topological sort, we must have a cycle in the graph, meaning a topopath does not exist.
      if (count != adjMatrix.length)
      {
        return 0;
      }

      // Should we have gotten this far, that means that we have our TopoPath! As a result, we
      // return 1, signifying that a proper TopoPath exists within the graph that's been inputted.
      return 1;
    }

    // If we encounter an error anywhere in the process we reach this catch phase, where we'll
    // return 0.
    catch(IOException e)
    {
      return 0;
    }
  }

  public static double difficultyRating()
  {
    // This function returns an value representative of my feelings regarding the difficulty of the
    // assignment.
    return 3.0;
  }

  public static double hoursSpent()
  {
    // This function returns an approximate value of how long this assignment took me.
    return 3.0;
  }
}
