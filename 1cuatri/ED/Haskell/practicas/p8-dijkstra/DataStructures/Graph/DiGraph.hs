-------------------------------------------------------------------------------
-- DiGraph defined by list of vertices and succesors function
--
-- Data Structures. Grado en Informática. UMA.
-- Pepe Gallardo, 2012
-------------------------------------------------------------------------------

module DataStructures.Graph.DiGraph
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

instance (Eq a, Show a) => Show (DiGraph a) where
  show g@(DG vs sucs)  = "DiGraph("++verts++", "++arcs++")"
   where
    verts = "["++ intercalate "," (map show vs) ++"]"
    arcs  = "[" ++ intercalate ", " (map show (diEdges g)) ++ "]"
