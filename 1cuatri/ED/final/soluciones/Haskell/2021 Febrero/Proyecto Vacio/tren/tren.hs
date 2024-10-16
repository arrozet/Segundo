---------------------------------------------------------------------------------------
-- Data Structures. Febrero 2021
--Grado en Informática. UMA.
--
--Student's name: Juan Díaz-Flores Merino
---------------------------------------------------------------------------------------
module Tren where
import Data.List


data Tren = Maquina | Vagon Int [Int] Tren


tope :: Int
tope = 10

--Ejemplos de prueba

ejemplo1 :: Tren
ejemplo1 = Vagon 2 [5,3] (Vagon 3 [3,2,2] (Vagon 5 [5] Maquina))

ejemplo2 :: Tren
ejemplo2 = Vagon 1 [2,1,1,3,2] (Vagon 4 [4,2] Maquina)


-- /
-- >> del 5 ejemplo1
-- (7,[3])-(3,[3,2,2])-(5,[5])-XxIx>
--
-- >> del 5 (del 5 ejemplo1)
-- (7,[3])-(3,[3,2,2])-XxIx>
--
-- >> foldr del ejemplo1 [5,2,5,3,3]
-- (8,[2])-XxIx>
--
-- >> del 1 ejemplo2
-- (2,[2,1,3,2])-(4,[4,2])-XxIx>
--
-- >> del 5 ejemplo2
-- (1,[2,1,1,3,2])-(4,[4,2])-*** Exception : No se ha encontrado ese peso en el tren
-- del: borra un objeto de un peso dado del tren. error si ese peso no está en el tren
-- Encuentra el primer peso que coincida y lo quita del vagón.
-- Si como resultado de eliminar un peso, un vagón queda vacío de objetos se desconecta del  tren.

del :: Int -> Tren -> Tren
del = undefined

---------------------------------------------------------------------------------------

instance Show Tren where
    show Maquina = "XxIx>"
    show (Vagon c xs rt) = concat ["(", show c, ",", show xs, ")", "-", show rt]