-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.Graph.EulerianCycle(isEulerian, eulerianCycle) where

import DataStructures.Graph.Graph
import Data.List

--H.1)
isEulerian :: Eq a => Graph a -> Bool
isEulerian g
    | isEmpty g = True
    | length (vertices g) == 1 = True
    | otherwise = compSucesores (vertices g) g

compSucesores :: Eq a => [a] -> Graph a -> Bool
compSucesores [] g = True
compSucesores (x:xs) g 
    | mod (length (successors g x)) 2 == 0 = compSucesores xs g
    | otherwise = False


-- H.2)
remove :: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v,u) = comp2
    where
        borrar = deleteEdge g (v,u)
        comp = comprobar borrar v
        comp2 = comprobar comp u

comprobar :: (Eq a) => Graph a -> a -> Graph a
comprobar g v
     | length (successors g v) == 0 = (deleteVertex g v)
     | otherwise = g



-- H.3)
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = extractCycleRec g v0 []


extractCycleRec :: (Eq a) => Graph a -> a -> [a] -> (Graph a, Path a)
extractCycleRec g v0 [] = extractCycleRec listaBorrada u ciclo
        where
            (u:xs) = successors g v0
            ciclo = [v0]
            listaBorrada = remove g (v0,u)
extractCycleRec g v0 (y:ys) 
    | v0 == y = (g, (y:ys)++[v0])
    | otherwise = extractCycleRec listaBorrada u ciclo
        where
            (u:xs) = successors g v0
            ciclo = (y:ys)++[v0]
            listaBorrada = remove g (v0,u)


-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles xs (y:ys)
    | null xs = (y:ys)
    | otherwise = connectCyclesRec xs (y:ys) []

connectCyclesRec :: (Eq a) => Path a -> Path a -> Path a -> Path a
connectCyclesRec [] [] sol = sol
connectCyclesRec xs [] sol = (sol++xs)
connectCyclesRec (x:xs) (y:ys) sol
    | x == y = connectCyclesRec xs [] (sol++(y:ys))
    | otherwise =  connectCyclesRec xs (y:ys) (sol++[x])

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g cycle = vertexInCommonRec vert cycle
    where
        vert = vertices g

vertexInCommonRec :: Eq a => [a] -> Path a -> a 
vertexInCommonRec [] _ = error "No hay vertices en el ciclo"
vertexInCommonRec _ [] = error "No hay vertices en el ciclo"
vertexInCommonRec (x:xs) caminos
    | elem x caminos = x
    | otherwise = vertexInCommonRec xs caminos

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g
    | not(isEulerian g) = error "Grafo no euleriano"
    | otherwise = eulerianCycleRec nuevoG camino
        where
            (x:xs) = vertices g
            (nuevoG, camino) = extractCycle g x

eulerianCycleRec :: Eq a => Graph a -> Path a -> Path a 
eulerianCycleRec g sol
    | isEmpty g = sol
    | otherwise = caminoConectado
        where
            verticeBueno = vertexInCommon g sol
            (nuevoG, camino) = extractCycle g verticeBueno
            caminoConectado = eulerianCycleRec nuevoG (connectCycles sol camino)


data Vertex = A | B | C | D | E | F | G | H | I | J deriving (Show,Eq,Enum,Ord)
g3 :: Graph Vertex -- eulerian
g3 = mkGraphEdges [A .. C]
                  [(A,B), (B,C), (C,A)]

g6 :: Graph Vertex
g6 = mkGraphSuc vertices suc -- eulerian
    where
      vertices = [A .. F]
      suc A = [B,E]
      suc B = [A,C,D,E]
      suc C = [B,D,E,F]
      suc D = [B,C,E,F]
      suc E = [A,B,C,D]
      suc F = [C,D]