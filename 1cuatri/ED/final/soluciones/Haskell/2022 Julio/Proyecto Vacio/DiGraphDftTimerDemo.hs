{-
  Student's name: ??????????
  Identity number (DNI if Spanish/passport if Erasmus): ???????????
-}

module DiGraphDftDemo where

import           DataStructures.Dictionary.AVLDictionary as D
import           DataStructures.Graph.DiGraph
import           DataStructures.Set.BSTSet               as S
import           DiGraphDftTimer

diGraph :: DiGraph Int
diGraph = mkDiGraphSuc [0..7] sucs
  where
    sucs 0 = [1, 2]
    sucs 2 = [3, 4]
    sucs 3 = [1, 5]
    sucs 4 = [5]
    sucs 6 = [7]
    sucs _ = []

main :: IO ()
main = test diGraph

{-

Arrival time for 0 is 0
Departure time for 0 is 11
Arrival time for 1 is 1
Departure time for 1 is 2
Arrival time for 2 is 3
Departure time for 2 is 10
Arrival time for 3 is 4
Departure time for 3 is 7
Arrival time for 4 is 8
Departure time for 4 is 9
Arrival time for 5 is 5
Departure time for 5 is 6
Arrival time for 6 is 12
Departure time for 6 is 15
Arrival time for 7 is 13
Departure time for 7 is 14

-}

test :: (Show v, Ord v) => DiGraph v -> IO ()
test diGraph = mapM_ output (vertices diGraph)
  where
    (arrivalD, departureD) = diGraphDftTimer diGraph
    output v = do
      putStrLn $ concat [ "Arrival time for ", show v, " is ", fromD v arrivalD ]
      putStrLn $ concat [ "Departure time for ", show v, " is ", fromD v departureD ]
    fromD k dict = aux (D.valueOf k dict)
      where
        aux Nothing  = "Non-visited"
        aux (Just v) = show v
