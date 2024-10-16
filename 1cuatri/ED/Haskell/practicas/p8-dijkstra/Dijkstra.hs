-------------------------------------------------------------------------------
-- Dijkstra's algorithm to compute shortests paths from a source
--
-- Data Structures. Grado en Informática. UMA.
--
-------------------------------------------------------------------------------

module Dijkstra(dijkstra) where

import Data.Function(on)
-- el on se usa en minimumBy para sacar el mínimo de una lista ordenada según un criterio "compare `on` criterio"
import Data.List((\\), minimumBy)
-- \\ es el operador de diferencia, elimina los elementos de l2 en l1 -> l1 \\ l2

import DataStructures.Graph.WeightedGraph
import DataStructures.Dictionary.AVLDictionary


dijkstra :: (Ord v, Ord w, Num w) => WeightedGraph v w -> v -> Dictionary v w

--                  vOpt            vRest                      dictionary
dijkstra g src = aux [src] (vertices g \\ [src]) (insert src 0 empty) g
{-    where 
        --aux :: (Ord v, Ord w, Num w) => [a] -> [a] -> Dictionary v w -> Dictionary v w
        aux vOpt [] d = d   -- vRest, vértices no explorados, es vacío
        --                 añado min    lo quito de lista de restantes   inserto en diccionario
        aux vOpt vRest d = aux (vSucMin:vOpt) (vRest \\ [vSucMin]) (insert vSucMin cMin d)
            where
                --                    tengo que sumar lo que ya llevo de coste, pero cuidado, es maybe
                caminos = [ (v, vSuc, c + fromMaybe (valueOf v d)) | v <- vOpt, (vSuc, c) <- successors g v, elem vSuc vRest] -- solo considero sucesores restantes; los ya explorados fuera
                (vMin, vSucMin, cMin) = minimumBy (compare `on` thd3) caminos
-}

aux :: (Ord v, Ord w, Num w) => [v] -> [v] -> Dictionary v w -> WeightedGraph v w -> Dictionary v w
aux vOpt [] d g = d   -- vRest, vértices no explorados, es vacío
--                 añado min    lo quito de lista de restantes   inserto en diccionario
aux vOpt vRest d g = aux (vSucMin:vOpt) (vRest \\ [vSucMin]) (insert vSucMin cMin d) g
    where
        --                    tengo que sumar lo que ya llevo de coste, pero cuidado, es maybe
        caminos = [ (v, vSuc, c + fromMaybe (valueOf v d)) | v <- vOpt, (vSuc, c) <- successors g v, elem vSuc vRest] -- solo considero sucesores restantes; los ya explorados fuera
        (vMin, vSucMin, cMin) = minimumBy (compare `on` thd3) caminos

-- valueOf la key, para sumar en c, es un Maybe. tengo que parsearlo
fromMaybe :: Maybe a -> a
fromMaybe (Just x) = x

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
                            , WE 'd' 4 'e'  -- en el grafo del enunciado es 4, no 5
                            ]

