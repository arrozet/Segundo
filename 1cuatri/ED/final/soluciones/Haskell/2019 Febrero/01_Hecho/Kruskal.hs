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

kruskal :: (Ord a, Ord w) => WeightedGraph a w -> [WeightedEdge a w]
kruskal grafo = kruskalRec dict cola grafo sol
    where
        dict = crearDict grafo
        cola = colaPrioridad grafo
        sol = []

kruskalRec :: (Ord a, Ord w) => D.Dictionary a a -> Q.PQueue (WeightedEdge a w) -> WeightedGraph a w -> [WeightedEdge a w] -> [WeightedEdge a w]
kruskalRec dict cola grafo sol
    | Q.isEmpty cola = sol
    | rep1 /= rep2 = kruskalRec asocRep nuevaCola grafo (sol++[(WE v1 peso v2)])
    | otherwise = kruskalRec dict nuevaCola grafo sol
        where
            (WE v1 peso v2) = Q.first cola
            nuevaCola = Q.dequeue cola
            rep1 = representante v1 dict
            rep2 = representante v2 dict
            asocRep = D.insert rep2 v1 dict



-- Esta funcion crea un diccionario con los vertices conectados a si mismo
crearDict :: (Ord a) => WeightedGraph a w -> D.Dictionary a a
crearDict grafo = dictRec keys (D.empty)
    where
        keys = vertices grafo

dictRec :: (Ord a) => [a] -> D.Dictionary a a-> D.Dictionary a a
dictRec [] sol = sol
dictRec (x:xs) sol = dictRec xs (D.insert x x sol)


-- Crea la cola de prioridad con las aristas ordenadas
colaPrioridad :: (Eq a, Eq w, Ord a, Ord w) => WeightedGraph a w -> Q.PQueue (WeightedEdge a w)
colaPrioridad grafo = colaPrioridadRec lista Q.empty
    where
        lista = edges grafo

colaPrioridadRec :: (Ord a) => [a] -> Q.PQueue a -> Q.PQueue a
colaPrioridadRec [] sol = sol
colaPrioridadRec (x:xs) sol = colaPrioridadRec xs (Q.enqueue x sol)

-- Funcion representante
representante :: (Ord a) => a -> D.Dictionary a a -> a
representante x dic
    | x == (num(D.valueOf x dic)) = x
    | otherwise = representante (num(D.valueOf x dic)) dic

num :: (Ord a) => Maybe a -> a 
num (Just x) = x


-- Solo para evaluación continua / only for part time students
kruskals :: (Ord a, Ord w) => WeightedGraph a w -> [[WeightedEdge a w]]
kruskals = undefined
