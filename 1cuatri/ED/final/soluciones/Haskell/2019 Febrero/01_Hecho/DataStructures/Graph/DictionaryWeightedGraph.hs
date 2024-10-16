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
addVertex (WG dic) x
  | elem x (D.keys dic) = (WG dic)
  | otherwise = WG (D.insert x (D.empty) dic)

addEdge :: (Ord a, Show a) => WeightedGraph a w -> a -> a -> w -> WeightedGraph a w
addEdge (WG dic) v1 v2 peso
  | elem v1 (D.keys dic) = WG ( D.insert v1 ( D.insert v2 peso (num(D.valueOf v1 dic)) ) dic )
  | otherwise = error "El vertice no existe"

num :: Maybe a -> a 
num (Just x) = x

edges :: (Eq a, Eq w, Ord a) => WeightedGraph a w -> [WeightedEdge a w]
edges (WG dic) = edgesRec keysVertices dic []
  where
    keysVertices = D.keys dic


edgesRec :: (Eq a, Eq w, Ord a) => [a] -> D.Dictionary a (D.Dictionary a w) -> [WeightedEdge a w] -> [WeightedEdge a w]
edgesRec [] dic sol = sol
edgesRec (x:xs) dic [] = edgesRec xs dic (verticeRec x keysConexiones conexionesDic [])
  where
    conexionesDic = num(D.valueOf x dic)
    keysConexiones = D.keys (conexionesDic) 
edgesRec (x:xs) dic sol = edgesRec xs dic (verticeRec x keysConexiones conexionesDic sol)
  where
    conexionesDic = num(D.valueOf x dic)
    keysConexiones = D.keys (conexionesDic) 


verticeRec :: (Ord a) => a -> [a] -> D.Dictionary a w -> [WeightedEdge a w] -> [WeightedEdge a w]
verticeRec x [] dic sol = sol
verticeRec x (y:ys) dic [] = verticeRec x ys dic [WE x (num(D.valueOf y dic)) y]
verticeRec x (y:ys) dic sol = verticeRec x ys dic (sol++[WE x (num(D.valueOf y dic)) y])


successors :: (Ord a, Show a) => WeightedGraph a w -> a -> [(a,w)]
successors (WG dic) x
  | elem x (D.keys dic) = succRec keysConexiones conexionesDic []
  | otherwise = error "El vertice no esta en el grafo"
    where
      conexionesDic = num(D.valueOf x dic)
      keysConexiones = D.keys (conexionesDic) 


succRec :: (Ord a) => [a] -> D.Dictionary a w -> [(a,w)] -> [(a,w)]
succRec [] dic sol = sol
succRec (y:ys) dic [] = succRec ys dic [(y, (num(D.valueOf y dic)) )]
succRec (y:ys) dic sol = succRec ys dic (sol++[(y, (num(D.valueOf y dic)) )])



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

numEdges :: (Eq a, Eq w, Ord a) => WeightedGraph a w -> Int
numEdges = length . edges

instance (Eq a, Show a, Eq w, Show w, Ord a) => Show (WeightedGraph a w) where
  show wg  = "DictionaryWeightedGraph("++vs++", "++as++")"
   where
    vs  = "("++ intercalate ", " (map show (vertices wg)) ++")"
    as  = "(" ++ intercalate ", " (map showEdge (edges wg)) ++ ")"
    showEdge (WE x w y)  = intercalate "-" [ show x, show w, show y ]
