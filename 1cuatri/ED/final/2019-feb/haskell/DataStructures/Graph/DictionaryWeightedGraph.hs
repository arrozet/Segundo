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
-- Weighted Graph implemented by using a dictionary from
-- sources to another dictionary from destinations to weights
----------------------------------------------

module DataStructures.Graph.DictionaryWeightedGraph
  ( WeightedGraph
  , WeightedEdge(WE)
  , empty
  , isEmpty
  , mkWeightedGraphEdges
  , addVertex
  , addEdge
  , vertices
  , numVertices
  , edges
  , numEdges
  , successors
  ) where

import Data.List(nub, intercalate)

import qualified DataStructures.Dictionary.AVLDictionary as D

data WeightedEdge a w  = WE a w a deriving Show

instance (Eq a, Eq w) => Eq (WeightedEdge a w) where
  WE u w v == WE u' w' v' = (u==u' && v==v' || u==v' && v==u')
                              && w == w'

instance (Eq a, Ord w) => Ord (WeightedEdge a w) where
  compare (WE _ w _) (WE _ w' _) = compare w w'

data WeightedGraph a w  = WG (D.Dictionary a (D.Dictionary a w))

empty :: WeightedGraph a w
empty = WG D.empty

addVertex :: (Ord a) => WeightedGraph a w -> a -> WeightedGraph a w
addVertex g@(WG d1) v
  | D.isDefinedAt v d1  = g
  | otherwise           = (WG (D.insert v (D.empty) d1))

addEdge :: (Ord a, Show a) => WeightedGraph a w -> a -> a -> w -> WeightedGraph a w
addEdge g@(WG d1) src dst w 
  | not ((D.isDefinedAt src d1) && (D.isDefinedAt dst d1))  = error "DictionaryWeightedGraph addEdge: vertex not in graph"
  | otherwise                                               = WG d_sol
    where
      d_src     = parseMaybe (D.valueOf src d1)
      new_d_src = D.insert dst w d_src
      d_sol     = D.insert src new_d_src d1

parseMaybe :: Maybe a -> a
parseMaybe (Just x) = x
parseMaybe Nothing = error "parseMaybe: tried to parse a Nothing"

edges :: (Ord a, Eq a, Eq w) => WeightedGraph a w -> [WeightedEdge a w]
edges g@(WG d1) = aux xs []
  where
    xs = D.keys d1  -- vertex

    -- Selecciona los vertices de los cuales hay que sacar aristas
    aux [] sol     = sol
    aux (v:vs) sol = aux vs sol_v
      where
        dic = parseMaybe (D.valueOf v d1)
        sol_v = aux2 v (ws) sol -- Añade aristas con src = v a la solución
        (ws) = D.keysValues dic

        -- Añade aristas con src = v a la solución
        aux2 v [] sol = sol
        aux2 v ((dst,w):ws) sol = (aux2 v ws sol) ++ [WE v w dst]

    

successors :: (Ord a, Show a) => WeightedGraph a w -> a -> [(a,w)]
successors g@(WG d1) v
  | not (D.isDefinedAt v d1)  = error "DictionaryWeightedGraph successors: vertex not in graph"
  | otherwise                 = aux suc dic
    where
      dic = parseMaybe (D.valueOf v d1)
      suc = D.keys dic

      aux [] dic = []
      aux (s:suc) dic = aux suc dic ++ [(s,w)]
        where
          w = parseMaybe (D.valueOf s dic)


-- NO EDITAR A PARTIR DE AQUÍ    
-- DON'T EDIT ANYTHING BELOW THIS COMMENT

vertices :: WeightedGraph a w -> [a]
vertices (WG d) = D.keys d

isEmpty :: WeightedGraph a w -> Bool
isEmpty (WG d) = D.isEmpty d

mkWeightedGraphEdges :: (Ord a, Show a) => [a] -> [WeightedEdge a w] -> WeightedGraph a w
mkWeightedGraphEdges vs es = wg'
  where
    wg = foldl addVertex empty vs
    wg' = foldr (\(WE u w v) wg -> addEdge wg u v w) wg es

numVertices :: WeightedGraph a w -> Int
numVertices = length . vertices

-- he añadido Ord a aqui
numEdges :: (Ord a, Eq a, Eq w) => WeightedGraph a w -> Int
numEdges = length . edges

-- he añadido Ord a aqui
instance (Ord a, Eq a, Show a, Eq w, Show w) => Show (WeightedGraph a w) where
  show wg  = "DictionaryWeightedGraph("++vs++", "++as++")"
   where
    vs  = "("++ intercalate ", " (map show (vertices wg)) ++")"
    as  = "(" ++ intercalate ", " (map showEdge (edges wg)) ++ ")"
    showEdge (WE x w y)  = intercalate "-" [ show x, show w, show y ]
