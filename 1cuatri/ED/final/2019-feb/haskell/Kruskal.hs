----------------------------------------------
-- Estructuras de Datos.  2018/19
-- 2º Curso del Grado en Ingeniería [Informática | del Software | de Computadores].
-- Escuela Técnica Superior de Ingeniería en Informática. UMA
--
-- Examen 4 de febrero de 2019
--
-- ALUMNO/NAME:
-- GRADO/STUDIES:
-- NÚM. MÁQUINA/MACHINE NUMBER:
--
----------------------------------------------

module Kruskal(kruskal, kruskals) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.PriorityQueue.LinearPriorityQueue as Q
import DataStructures.Graph.DictionaryWeightedGraph

kruskal :: (Ord a, Ord w, Eq a) => WeightedGraph a w -> [WeightedEdge a w]
kruskal g = aux pq dict sol
    where
        sol = []
        pq = llenarPQ (Q.empty) (edges g)
        dict = llenarDict (D.empty) (vertices g)

        aux pq dict sol
            | Q.isEmpty pq          = sol
            | rep_src /= rep_dst    = aux pq' new_dic ([(WE src wght dst)]++sol)
            | otherwise             = aux pq' dict sol
                where 
                    (WE src wght dst) = Q.first pq
                    pq' = Q.dequeue pq
                    rep_src = representante src dict
                    rep_dst = representante dst dict
                    new_dic = D.insert rep_dst src dict



llenarPQ :: (Eq a, Ord w) => Q.PQueue (WeightedEdge a w) -> [WeightedEdge a w] -> Q.PQueue (WeightedEdge a w)
llenarPQ pq []      = pq
llenarPQ pq (e:es)  = llenarPQ (Q.enqueue e pq) es

llenarDict :: Ord a => D.Dictionary a a -> [a] -> D.Dictionary a a
llenarDict d []     = d
llenarDict d (v:vs) = llenarDict (D.insert v v d) vs

representante :: (Ord a, Eq a) => a -> D.Dictionary a a -> a
representante v dict
    | v == v'   = v
    | otherwise = representante v' dict
        where
            v' = parseMaybe (D.valueOf v dict)

parseMaybe :: Maybe a -> a
parseMaybe (Just x) = x
parseMaybe Nothing = error "parseMaybe: tried to parse a Nothing"

-- Solo para evaluación continua / only for part time students
kruskals :: (Ord a, Ord w) => WeightedGraph a w -> [[WeightedEdge a w]]
kruskals = undefined
