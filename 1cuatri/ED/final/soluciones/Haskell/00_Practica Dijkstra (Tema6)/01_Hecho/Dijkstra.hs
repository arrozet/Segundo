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
dijkstra g src = dijkstraRec v listaAllenar dict g
        where
            listaAllenar = [src]
            vtemp = vertices g
            dict = insert src 0 empty
            v = borrarVertice vtemp src []

dijkstraRec :: (Ord v, Ord w, Num w) => [v] -> [v] -> Dictionary v w -> WeightedGraph v w -> Dictionary v w
dijkstraRec [] listaV dict g = dict
dijkstraRec (x:xs) listaV dict g = dijkstraRec (borrarVertice (x:xs) v2 []) (listaV++[v2]) (insert v2 w dict) g
    where
        sucesores = crearSucesores g listaV [] (x:xs) dict
        (v1,v2,w) = minimo sucesores (head sucesores)

crearSucesores :: (Ord v, Ord w, Num w) => WeightedGraph v w -> [v] -> [(v,v,w)] -> [v] -> Dictionary v w-> [(v,v,w)]
crearSucesores g [] sol lista dict = sol
crearSucesores g (x:xs) sol lista dict = crearSucesores g xs (sol++(crearSucesoresRec x suc [] lista dict)) lista dict
    where
        suc = successors g x

crearSucesoresRec :: (Ord v, Ord w, Num w) => v -> [(v,w)] -> [(v,v,w)] -> [v] -> Dictionary v w -> [(v,v,w)]
crearSucesoresRec x [] sol lista dict = sol
crearSucesoresRec x ((b,c):ys) sol lista dict
    | elem b lista = crearSucesoresRec x ys ( sol++[(x,b,( c + (num(valueOf x dict)) ))] ) lista dict
    | otherwise = crearSucesoresRec x ys ( sol ) lista dict


borrarVertice :: (Ord v ) => [v] -> v -> [v] -> [v]
borrarVertice (y:ys) x sol
    | x == y = (sol++ys)
    | otherwise = borrarVertice (ys) x (sol++[y])


minimo :: (Ord w) => [(v,v,w)] -> (v,v,w) -> (v,v,w)
minimo [] sol = sol
minimo ((a,b,c): ys) (a1,b2,c1)
    | c > c1 = minimo ys (a1,b2,c1)
    | otherwise = minimo ys (a,b,c)

num :: Maybe w -> w
num (Just x) = x


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
                            , WE 'd' 4 'e'
                            ]

