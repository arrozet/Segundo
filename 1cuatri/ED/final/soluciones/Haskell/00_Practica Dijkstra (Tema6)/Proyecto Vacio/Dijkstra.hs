-------------------------------------------------------------------------------
-- Dijkstra's algorithm to compute shortests paths from a source
--
-- Data Structures. Grado en Informática. UMA.
--
-------------------------------------------------------------------------------

module Dijkstra(dijkstra) where

import Data.Function(on)
import Data.List((\\), minimumBy)

import DataStructures.Graph.WeightedGraph
import DataStructures.Dictionary.AVLDictionary


dijkstra :: (Ord v, Ord w, Num w) => WeightedGraph v w -> v -> Dictionary v w
dijkstra g src = undefined


-- Functions for tuples with 3 components
fst3 :: (a,b,c) -> a
fst3 (x,_,_) = x

snd3 :: (a,b,c) -> b
snd3 (_,y,_) = y

thd3 :: (a,b,c) -> c
thd3 (_,_,z) = z

-- Example graph
g1 :: WeightedGraph Char Int
g1 = mkWeightedGraphEdges ['a','b','c','d','e']
                            [ WE 'a' 3 'b', WE 'a' 7 'd'
                            , WE 'b' 4 'c', WE 'b' 2 'd'
                            , WE 'c' 5 'd', WE 'c' 6 'e'
                            , WE 'd' 5 'e'
                            ]

