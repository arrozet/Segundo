import DataStructures.Graph.DictionaryWeightedGraph

main :: IO ()
main = do
  let graph = empty :: WeightedGraph Int Int
      graphWithVertices = foldl addVertex graph [1, 2, 3, 4]
      graphWithEdges = foldr (\(u, v, w) g -> addEdge g u v w) graphWithVertices [(1, 2, 10), (1, 3, 5), (2, 4, 8)]

  putStrLn "Graph with Vertices:"
  print graphWithVertices

  putStrLn "\nGraph with Edges:"
  print graphWithEdges

  putStrLn "\nEdges in the Graph:"
  print $ edges graphWithEdges

  putStrLn "\nSuccessors for Vertex 1:"
  print $ successors graphWithEdges 1

  putStrLn "\nSuccessors for Vertex 3:"
  print $ successors graphWithEdges 3