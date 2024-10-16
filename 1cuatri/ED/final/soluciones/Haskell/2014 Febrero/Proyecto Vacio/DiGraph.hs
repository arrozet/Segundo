-------------------------------------------------------------------------------
-- DiGraph defined by list of vertices and succesors function
--
-- Data Structures. Grado en Informática. UMA.
-- Pepe Gallardo, 2012
-------------------------------------------------------------------------------

module DiGraph
  ( DiGraph
  , DiEdge((:->))
  , Path
  , empty
  , mkDiGraphEdges
  , mkDiGraphSuc
  , isEmpty
  , vertices
  , diEdges
  , successors
  , predecesors
  , inDegree
  , outDegree
  , addVertex
  , addDiEdge
  , deleteVertex
  , deleteDiEdge
  ) where

import Data.List(nub, intercalate, delete)

import qualified Set as S
import qualified Stack as St
import qualified AVLDictionary as D
import qualified Queue as Q

data DiEdge a  = a :-> a deriving (Eq, Show)

type Path a  = [a] -- Path represented as list of vertices

data DiGraph a  = DG [a] (a -> [a])
-- invariants for DG vs sucs:
-- vs has no repetitions
-- sucs v has no repetitions

empty :: DiGraph a
empty = DG [] (\_ -> [])

isEmpty :: DiGraph a -> Bool
isEmpty (DG vs _) = null vs

mkDiGraphSuc :: (Eq a) => [a] -> (a -> [a]) -> DiGraph a
mkDiGraphSuc vs sucs  = DG (nub vs) sucs'
 where
   sucs' v = nub (sucs v) -- enforces no repetitions in successors

mkDiGraphEdges :: (Eq a) => [a] -> [DiEdge a] -> DiGraph a
mkDiGraphEdges vs es  = DG (nub vs) suc
 where
   suc v = nub [ y | x :-> y <- es, x==v ] -- enforces no repetitions

successors :: (Eq a) => DiGraph a -> a -> [a]
successors (DG vs sucs) v  = sucs' v
 where
   sucs' v
    | elem v vs = sucs v
    | otherwise = []    -- this is consistent with Java definition
                        -- only problem is that if function provided
                        -- to mkGraphSuc is partial, successors may fail

predecesors :: (Eq a) => DiGraph a -> a -> [a]
predecesors (DG vs sucs) v  = [ w | w <- vs, v `elem` sucs w ]

vertices :: DiGraph a -> [a]
vertices (DG vs sucs)  = vs

diEdges :: DiGraph a -> [DiEdge a]
diEdges (DG vs sucs)  = [ v :-> w | v <- vs, w <- sucs v ]

deleteDiEdge :: (Eq a) => DiGraph a -> DiEdge a -> DiGraph a
deleteDiEdge g de = mkDiGraphEdges (vertices g) (delete de (diEdges g))

deleteVertex :: (Eq a) => DiGraph a -> a -> DiGraph a
deleteVertex (DG vs sucs) v = mkDiGraphSuc (delete v vs) sucs'
  where
    sucs' x
      | x == v    = []
      | otherwise = delete v (sucs x)

addVertex :: (Eq a) => DiGraph a -> a -> DiGraph a
addVertex (DG vs sucs) v = mkDiGraphSuc (nub (v:vs)) sucs

addDiEdge :: (Eq a) => DiGraph a -> DiEdge a -> DiGraph a
addDiEdge g de@(v :-> w)
  | any (`notElem` vs) [v,w] = error "addDiEdge: vertices not in directed graph"
  | otherwise                = mkDiGraphEdges vs (de : diEdges g)
  where
    vs = vertices g

outDegree :: (Eq a) => DiGraph a -> a -> Int
outDegree g v  = length (successors g v)

inDegree :: (Eq a) => DiGraph a -> a -> Int
inDegree g v  = length (predecesors g v)

dft :: (Ord a) => DiGraph a -> a -> [a]
dft (DG vs suc) v | or (map (==v) vs) == False = error "dft: can't do dft from a vertex that's not in the diGraph"
                  | otherwise = dft' (DG vs suc) (St.push v St.empty) (S.empty)
    where
        dft' :: (Ord a) => DiGraph a -> St.Stack a -> S.Set a -> [a]
        dft' (DG vs suc) stack visited | St.isEmpty stack = []
                                       | S.isElem (St.top stack) visited = dft' (DG vs suc) (St.pop stack) visited
                                       | otherwise = (St.top stack) : dft' (DG vs suc) (pushAll stack [u | u <- successors (DG vs suc) (St.top stack), S.isElem u visited == False]) (S.insert (St.top stack) visited)
            where
                pushAll stack l = foldr (\x dd -> St.push x dd) stack l

bft :: (Ord a) => DiGraph a -> a -> [a]
bft (DG vs suc) v | or (map (==v) vs) == False = error "bft: can't do bft from a vertex that's not in the diGraph"
                  | otherwise = bft' (DG vs suc) (Q.enqueue v Q.empty) (S.empty)
    where
        bft' :: (Ord a) => DiGraph a -> Q.Queue a -> S.Set a -> [a]
        bft' g queue visited | Q.isEmpty queue = []
                             | S.isElem (Q.first queue) visited = bft' g (Q.dequeue queue) visited
                             | otherwise = (Q.first queue) : bft' g (enqueueAll (Q.dequeue queue) [u | u <- successors g (Q.first queue), S.isElem u visited == False]) (S.insert (Q.first queue) visited)
            where
                enqueueAll q l = foldr (\x dd -> Q.enqueue x dd) q l

esCiclico :: (Ord a) => DiGraph a -> Bool
esCiclico g = check $ [dft g v | v <- vertices g,length (dft g v) > 1]
    where
      check dfts = length (foldr (\x dd -> intersect x dd) [] dfts) >= 3
        where
          intersect l1 [] = l1
          intersect l1 l2 = [x | x <- l1, x `elem` l2]

topologicalOrder :: (Ord a) => DiGraph a -> [a]
topologicalOrder g | length (nub (topologicalOrder' g [v | v <- vertices g, inDegree g v == 0] [v | v <- vertices g, outDegree g v == 0])) /= length (vertices g) = error "no puede tener orden topológico; posible grafo cíclico"
                   | otherwise = nub (topologicalOrder' g [v | v <- vertices g, inDegree g v == 0] [v | v <- vertices g, outDegree g v == 0])
    where
      topologicalOrder' g fuente sumidero | length fuente == 0 = []
                                          | otherwise = (head fuente) : topologicalOrder' (deleteVertex g (head fuente)) (nuevaFuente (deleteVertex g (head fuente))) (nuevoSumidero (deleteVertex g (head fuente)))
          where
            nuevaFuente graph = [v | v <- vertices g, inDegree g v == 0]
            nuevoSumidero graph = [v | v <- vertices g, outDegree g v == 0]

--Literalmente resuelto aquí entero del tirón

scc :: (Ord a) => DiGraph a -> a -> [a]
scc g v = scc' (mkDiGraphEdges connected [w :-> u | (u :-> w) <- diEdges g, u `elem` connected && w `elem` connected]) v
    where
      scc' g' v = dft g' v
      connected = (dft g v)

--Los ejercicios que pide:

--(A) Escribe la función que devuelve el grafo inverso de un digrafo:

reverseDiGraph :: Eq a => DiGraph a -> DiGraph a
reverseDiGraph g = undefined

--(B) Escribe la función que toma un grafo g y una lista de vértices vs y devuelve el subgrafo de g con vértices en vs:

restrictDiGraph :: Eq a => DiGraph a -> [a] -> DiGraph a
restrictDiGraph g vs = undefined

--(C) Con ayuda de las funciones anteriores, siguiendo los pasos 1-4 descritos anteriormente, escribe una función para computar la SCC sobre un grafo de un determinado vértice:

type SCC a = [a]

sccOf :: Ord a => DiGraph a -> a -> SCC a
sccOf g v = undefined

--(D) Aplicando reiteradamente la función anterior, podemos obtener todas las componentes del grafo original eliminando en cada paso los vértices de la componente computada. Escribe la función correspondiente a este cómputo:

sccs :: Ord a => DiGraph a -> [SCC a]
sccs g = undefined

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

instance (Eq a, Show a) => Show (DiGraph a) where
  show g@(DG vs sucs)  = "DiGraph("++verts++", "++arcs++")"
   where
    verts = "["++ intercalate "," (map show vs) ++"]"
    arcs  = "[" ++ intercalate ", " (map show (diEdges g)) ++ "]"
