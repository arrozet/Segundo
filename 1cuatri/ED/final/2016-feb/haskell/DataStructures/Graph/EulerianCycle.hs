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
--isEulerian empty = False
isEulerian g = and [even (degree g x) | x <- vertices g]    -- mira si todos los vertices tienen grado par

-- H.2)

remove :: (Eq a) => Graph a -> (a,a) -> Graph a
remove g (v,u) = g'
    where
        g2 = deleteEdge g (v,u)
        vertices_a_borrar = [x | x <- vertices g2, (degree g2 x) == 0]  -- los aislados, grado 0
        g' = borrarListaVertices g2 vertices_a_borrar 
            where   -- para borrar todos los vertices de la lista
                borrarListaVertices g2 []       = g2
                borrarListaVertices g2 (x:xs)   = borrarListaVertices (deleteVertex g2 x) xs


-- H.3)
{-
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = aux (g', [v0,x])
    where
        x = head (successors g v0)
        g' = remove g (v0, x)

        aux (grafo, camino)
            | primero == v_ultimo   = (grafo, camino)
            | otherwise             = aux (grafo', camino2)
                where
                    primero = head camino
                    v_ultimo = last camino
                    u = head (successors grafo v_ultimo)
                    camino2 = camino ++ [u]
                    grafo' = remove grafo (v_ultimo, u)
-}

extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = c
    where
        c = ciclo new_g [v0,u]    
            where
                u = head (successors g v0)
                new_g = remove g (v0,u)          
        --g' = removeAll g c

                

ciclo :: (Eq a) => Graph a -> [a] -> (Graph a, Path a)
ciclo g cycle 
    | primero == v_ultimo                  = (g,cycle)     -- si he llegado a un ciclo
    | otherwise                                                 = ciclo (new_g) (cycle')
        where 
            primero = head cycle
            v_ultimo = last cycle
            u = head (successors g v_ultimo)
            cycle' = cycle ++ [u]
            new_g = remove g (v_ultimo,u)
{-
removeAll :: (Eq a) => Graph a -> [a] -> Graph a
removeAll g []          = g     -- si me quedan 0 o 1 aristas, terminé
removeAll g [x]         = g
removeAll g (x:y:xs)    = removeAll (remove g (x,y)) (y:xs) -- borro 1 arista y sigo mirando las demas
-}
{-
extractCycle :: (Eq a) => Graph a -> a -> (Graph a, Path a)
extractCycle g v0 = extractCycleRec g v0 []


extractCycleRec :: (Eq a) => Graph a -> a -> [a] -> (Graph a, Path a)

extractCycleRec g v0 (ys) 
    | ys == []      = extractCycleRec (remove g (v0,head (successors g v0))) (head (successors g v0)) [v0]
    | v0 == head ys = (g, (ys)++[v0])
    | otherwise = extractCycleRec listaBorrada u ciclo
        where
            (u:xs) = successors g v0   -- cabeza sucesores
            ciclo = (ys)++[v0] 
            listaBorrada = remove g (v0,u)
-}
-- H.4)
connectCycles :: (Eq a) => Path a -> Path a -> Path a
connectCycles [] (y:ys)  = (y:ys)
connectCycles (x:xs) (y:ys)
    | x==y               = [x] ++ ys ++ xs 
    | otherwise          = x : connectCycles xs (y:ys)

-- H.5)
vertexInCommon :: Eq a => Graph a -> Path a -> a
vertexInCommon g cycle = head (vertices g `intersect` cycle)    -- intersect es de Data.List

-- H.6) 
eulerianCycle :: Eq a => Graph a -> Path a
eulerianCycle g
    | isEulerian g == False     = error "eulerianCycle: graph is not eulerian"
    | otherwise                 = auxEulerian g' c 
        where
            (g', c) = (extractCycle g (head (vertices g)))
            auxEulerian g xs       
                | isEmpty g     = xs
                | otherwise     = auxEulerian g2 conexion
                    where
                        v = vertexInCommon g xs 
                        (g2, ciclo) = extractCycle g v
                        conexion = connectCycles xs ciclo
