-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module DataStructures.Graph.EulerianCycle where

import DataStructures.Graph.Graph
import Data.List

--H.1)
isEulerian :: Eq a => Graph a -> Bool
isEulerian g =  and[ mod(degree g v) 2==0|v<-vertices g]

-- H.2)

remove:: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v, u)
    |aisladoV && aisladoU = deleteVertex (deleteVertex g' v)u 
    |aisladoV = deleteVertex g' v
    |aisladoU = deleteVertex g' u
    |otherwise = g'
         where 
            g' = deleteEdge g (v, u)
            aisladoU =degree g' u ==0
            aisladoV = degree g' v == 0
{-
remove g (v,u) = comprobarVertices (deleteEdge g (v, u)) (v, u) (vertices g)
     where 
        comprobarVertices g (v, u) [] = g
        comprobarVertices g (v, u) (x:xs) = if x==v && (degree g v) == 0 then comprobarVertices (deleteVertex g v) (v, u) xs else if  x==u && (degree g u) == 0 then comprobarVertices (deleteVertex g u) (v, u) xs else comprobarVertices g (v, u) xs
-}

-- H.3)
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = extractCycleRec g v0 []


extractCycleRec :: (Eq a) => Graph a -> a -> [a] -> (Graph a, Path a)
extractCycleRec g v0 [] = extractCycleRec listaBorrada u ciclo
        where
            (u:xs) = successors g v0   -- cabeza sucesores
            ciclo = [v0]   -- ciclo
            listaBorrada = remove g (v0,u) -- grafo actualizado

extractCycleRec g v0 (ys) 
    | v0 == y = (g, (ys)++[v0])
    | otherwise = extractCycleRec listaBorrada u ciclo
        where
            (u:xs) = successors g v0   -- cabeza sucesores
            ciclo = (ys)++[v0] 
            listaBorrada = remove g (v0,u)

-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] y = y
connectCycles (x:xs) (y:ys)
    |x == y = (y:ys) ++ xs
    |otherwise =   [x]++ (connectCycles xs (y:ys))
   
-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g cycle = encontrarVertice (vertices g) (vertices g) cycle
   where
       encontrarVertice v [] (y:ys) = encontrarVertice v v ys
       encontrarVertice v(x:xs)(y:ys)
          |x == y = x
          |otherwise = encontrarVertice v xs (y:ys)

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g = if isEulerian g then cicloComp g (vertices g)  else error "Grafo no Euleriano"
   where 
         cicloComp g []  =  []
         cicloComp g (v:vs)  =  snd(extractCycle g v) ++ cicloComp grafoActualizado verts 
            where 
               grafoActualizado = fst (extractCycle g v)
               verts = vertices grafoActualizado
          























