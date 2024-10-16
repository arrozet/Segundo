-------------------------------------------------------------------------------
{-
ESTO LO HE HECHO YO PORQUE NO EXISTÍA. LOS EJEMPLOS LOS SAQUÉ DE WUOLAH. 
DESCARGA EL DataStructures DEL TEMA 6.

Alumno: OLIVA ZAMORA, RUBÉN
-}
-------------------------------------------------------------------------------

module DiGraphSCC
  ( reverseDiGraph
  , restrictDiGraph
  , sccOf
  , sccs
  ) where

--import Data.List(nub, intercalate, delete)

import DataStructures.Graph.DiGraph
import DataStructures.Graph.DiGraphDFT as DFT


--Literalmente resuelto aquí entero del tirón
{-
scc :: (Ord a) => DiGraph a -> a -> [a]
scc g v = scc' (mkDiGraphEdges connected [w :-> u | (u :-> w) <- diEdges g, u `elem` connected && w `elem` connected]) v
    where
      scc' g' v = dft g' v
      connected = (dft g v)
-}

--Los ejercicios que pide:

--(A) Escribe la función que devuelve el grafo inverso de un digrafo:

reverseDiGraph :: Eq a => DiGraph a -> DiGraph a
reverseDiGraph g = mkDiGraphEdges (vertices g) new_suc
  where
    new_suc = [ (y :-> x) | (x :-> y) <- diEdges g]   -- cojo los edges y le doy la vuelta a las flechas

--(B) Escribe la función que toma un grafo g y una lista de vértices vs y devuelve el subgrafo de g con vértices en vs:

restrictDiGraph :: Eq a => DiGraph a -> [a] -> DiGraph a
restrictDiGraph g vs = mkDiGraphEdges vs new_suc
  where
    new_suc = [ (x :-> y) | (x :-> y) <- diEdges g, x `elem` vs && y `elem` vs]  -- lo mismo que antes, pero solo con los elementos que estan en vs

--(C) Con ayuda de las funciones anteriores, siguiendo los pasos 1-4 descritos anteriormente, escribe una función para computar 
-- la SCC sobre un grafo de un determinado vértice:
type SCC a = [a]
sccOf :: Ord a => DiGraph a -> a -> SCC a
sccOf g v = DFT.dft g' v
  where
    vs = DFT.dft g v
    gr = restrictDiGraph g vs
    g' = reverseDiGraph gr
-- simplemente he seguido los pasos del algoritmo

--(D) Aplicando reiteradamente la función anterior, podemos obtener todas las componentes del grafo original 
-- eliminando en cada paso los vértices de la componente computada. Escribe la función correspondiente a este cómputo:

sccs :: Ord a => DiGraph a -> [SCC a]
sccs g = aux g (vertices g)
  where
    aux g []            = []   -- si no quedan vertices restantes es que hemos terminado
    aux g restantes = new_FC : (aux g' restantes')  -- añadimos el scc del grafo con el primer vertice que quede
      where
        v:vs = vertices g                 -- para sacar 1er vertice
        g' = restrictDiGraph g restantes' -- para restringir vertices a los que no esten en el newFC
        new_FC = sccOf g v                -- el del primer vertice de mi grafo
        restantes' = [x | x <- v:vs, not (x `elem` new_FC)] -- si no está en el new_FC, entonces es que me queda por mirar

--EL GRAFO DEL ENUNCIADO ES EL G7

--Examples

g1 :: DiGraph Int
g1 = mkDiGraphSuc [1,2,3,4] suc
    where
        suc 1 = [2]
        suc 2 = [3]
        suc 3 = [1,4]
        suc 4 = []

g1' :: DiGraph Int 
g1' = mkDiGraphEdges [1,2,3,4] [1 :-> 2, 2 :-> 3, 3 :-> 1, 3 :-> 4]

g2 :: DiGraph Char
g2 = mkDiGraphEdges  ['a', 'b', 'c', 'd'] ['a' :-> 'b', 'a' :-> 'd', 'd' :-> 'b', 'b':-> 'c']

g3 :: DiGraph Int
g3 = mkDiGraphEdges [0,1,2,3,4] [0 :-> 1, 1 :-> 2, 2 :-> 3, 3 :-> 4, 4 :-> 2]

g4 :: DiGraph Int
g4 = mkDiGraphEdges [1,2,3,4,5] [1 :-> 2, 2 :-> 3, 3 :-> 4, 4:-> 5]

cyclicGraph :: DiGraph Int
cyclicGraph = mkDiGraphEdges [1, 2, 3, 4, 5] [1 :-> 2, 2 :-> 3, 3 :-> 4, 4 :-> 5, 5 :-> 1]

acyclicGraph :: DiGraph Int
acyclicGraph = mkDiGraphEdges [1, 2, 3, 4, 5] [1 :-> 2, 2 :-> 3, 3 :-> 4, 4 :-> 5]

g5 :: DiGraph Int
g5 = mkDiGraphEdges [0,1,2,3,4,5,6,7,8,9,10,11,12] [0 :-> 1, 0 :-> 2, 0 :-> 3, 0 :-> 5, 0 :-> 6, 2 :-> 3, 3 :-> 4, 3 :-> 5, 4 :-> 9, 6 :-> 4, 6 :-> 9, 7 :-> 6, 8 :-> 7, 9 :-> 10, 9 :-> 11, 9 :-> 12, 11 :-> 12]

g6 :: DiGraph Int
g6 = mkDiGraphSuc [0,1,2,3,4,5,6] suc
    where
        suc 0 = [1,2,5]
        suc 1 = [4]
        suc 2 = []
        suc 3 = [2,4,5,6]
        suc 4 = []
        suc 5 = [2]
        suc 6 = [0,4]

g7 :: DiGraph Char
g7 = mkDiGraphSuc ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'] suc
    where
      suc 'A' = ['B']
      suc 'B' = ['E', 'F']
      suc 'C' = ['D', 'G']
      suc 'D' = ['C', 'H']
      suc 'E' = ['A', 'F']
      suc 'F' = ['G']
      suc 'G' = ['F']
      suc 'H' = ['D', 'G']

