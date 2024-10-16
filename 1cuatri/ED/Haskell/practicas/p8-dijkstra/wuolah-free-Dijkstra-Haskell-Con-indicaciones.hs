-------------------------------------------------------------------------------
-- Dijkstra's algorithm to compute shortests paths from a source
--
-- Data Structures. Grado en Informática. UMA.
--
-- Jose Antonio Casado Molina
--
-------------------------------------------------------------------------------

module Dijkstra(dijkstra) where

import Data.Function(on)
import Data.List((\\), minimumBy)

import DataStructures.Graph.WeightedGraph
import DataStructures.Dictionary.AVLDictionary


dijkstra :: (Ord v, Ord w, Num w) => WeightedGraph v w -> v -> Dictionary v w
dijkstra g src = aux [src] (vertices g \\ [src]) (insert src 0 empty)   -- Inicialización del algoritmo (funcion auxiliar) -> vertice optimo, vertices no optimos, diccionario : 
                                                                        -- Haremos recursión para añadir los vértices y construir así el camino óptimo (algoritmo de Dijkstra)
    where
        aux vs [] d = d     -- Última iteración (la lista de vertices no optimos se queda vacía)
        aux vs ws d = aux (uMin : vs) (ws \\ [uMin]) (insert uMin costMin d)    -- añadir vértice al conj. de v.opt. (vs) , quitar vértice del conj. de v.no opt. (ws)  y añadir al diccionario la entrada (v --> coste)
            where
                cs = [ (v, u, c + unJust (valueOf v d)) | v <- vs, (u, c) <- successors g v, elem u ws]     -- Extender vertices desde v a sus sucesores : Devuelve (vertice, sucesor, coste)
                                                                                                            -- Para calcular el coste : c + unJust (valueOf) --> coste que llevamos + coste del vértice (aplicando la función unJust)
                (vMin, uMin, costMin) = minim cs

unJust :: Maybe a -> a                 -- FUNCIÓN para conseguir el coste de pasar de un vértice a otro, buscando en el diccionario (devuelve un Just x o un Nothing x ya que es un Maybe) y pasando de Just x a (sólo) x
unJust (Just x) = x


minim :: Ord v => [(a,b,c)] => (a,b,c) --FUNCIÓN para obtener el valor mínimo de una tupla de vértices -> vértice  mínimo, sucesor mínimo, coste mínimo
minim [(k,y,z)]      = (x,y,z)
minim ((x,y,z) : ts) =                 --comparar la 3 componente de la tupla con la 1 componente de la cola (ts) --> Elegir la mínima
    | z < z'    = (x,y,z)
    | otherwise = (x',y',z')
    where
        (x',y',z') = minim ts          --tupla mínima de la cola


-- Functions for tuples with 3 components
fst3 :: (a,b,c) -> a
fst3 (x,_,_) = x

snd3 :: (a,b,c) -> b
snd3 (_,y,_) = y

thd3 :: (a,b,c) -> c
thd3 (_,_,z) = z

-- Example graph
g1 :: WeightedGraph Char Int
g1 = mkWeightedGraphEdges ['a','b','c','d','e']
                            [ WE 'a' 3 'b', WE 'a' 7 'd'
                            , WE 'b' 4 'c', WE 'b' 2 'd'
                            , WE 'c' 5 'd', WE 'c' 6 'e'
                            , WE 'd' 5 'e'
                            ]

