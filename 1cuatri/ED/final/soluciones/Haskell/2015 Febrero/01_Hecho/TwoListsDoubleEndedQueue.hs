-------------------------------------------------------------------------------
-- Estructuras de Datos. Grado en Informática, IS e IC. UMA.
-- Examen de Febrero 2015.
--
-- Implementación del TAD Deque
--
-- Apellidos:
-- Nombre:
-- Grado en Ingeniería ...
-- Grupo:
-- Número de PC:
-------------------------------------------------------------------------------

module TwoListsDoubleEndedQueue
   ( DEQue
   , empty
   , isEmpty
   , first
   , last
   , addFirst
   , addLast
   , deleteFirst
   , deleteLast
   ) where

import Prelude hiding (last)
import Data.List(intercalate)
import Test.QuickCheck

data DEQue a = DEQ [a] [a]

-- Complexity:
empty :: DEQue a
empty = DEQ [] []

-- Complexity:
isEmpty :: DEQue a -> Bool
isEmpty (DEQ [] []) = True
isEmpty _ = False

-- Complexity:
addFirst :: a -> DEQue a -> DEQue a
addFirst x (DEQ [] []) = (DEQ [x] [])
addFirst x (DEQ (y:ys) []) = (DEQ (x:(y:ys)) [])
addFirst x (DEQ [] (y:ys) ) = (DEQ [x] (y:ys))
addFirst x (DEQ (y:ys) (z:zs)) = (DEQ (x:(y:ys)) (z:zs))


-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast x (DEQ [] []) = (DEQ [] [x])
addLast x (DEQ (y:ys) []) = (DEQ ((y:ys)) [x])
addLast x (DEQ [] (y:ys) ) = (DEQ [] (x:(y:ys)))
addLast x (DEQ (y:ys) (z:zs)) = (DEQ ((y:ys)) (x:(z:zs)))

-- Complexity:
first :: DEQue a -> a
first (DEQ [] []) = error "La lista esta vacia"
first (DEQ [] (y:ys)) = first (DEQ prim sec)
   where
      (prim, sec) = mezclarListas [] (y:ys) ((div (length (y:ys)) 2)+1)
first (DEQ (x:xs) _) = x




-- Complexity:
last :: DEQue a -> a
last (DEQ [] []) = error "La lista esta vacia"
last (DEQ (x:xs) []) = last (DEQ prim sec)
   where
      (prim, sec) = mezclarListas (x:xs) [] ((div (length (x:xs)) 2)+1)
last (DEQ _ (y:ys)) = y


-- Complexity:
deleteFirst :: DEQue a -> DEQue a
deleteFirst (DEQ [] []) = error "La lista esta vacia"
deleteFirst (DEQ [] (y:ys)) = deleteFirst (DEQ prim sec)
   where
      (prim, sec) = mezclarListas [] (y:ys) ((div (length (y:ys)) 2)+1)
deleteFirst (DEQ (x:xs) []) = (DEQ (xs) [])
deleteFirst (DEQ (x:xs) (y:ys)) = (DEQ (xs) (y:ys))


-- Complexity:
deleteLast :: DEQue a -> DEQue a
deleteLast (DEQ [] []) = error "La lista esta vacia"
deleteLast (DEQ [] (y:ys)) = (DEQ [] (ys))
deleteLast (DEQ (x:xs) []) = deleteLast (DEQ prim sec)
   where
      (prim, sec) = mezclarListas (x:xs) [] ((div (length (x:xs)) 2)+1)
deleteLast (DEQ (x:xs) (y:ys)) = (DEQ (x:xs) (ys))

mezclarListas :: [a] -> [a] -> Int -> ([a],[a])
mezclarListas [] (x:xs) tam = mezclarListasDer [] (x:xs) tam
mezclarListas (x:xs) [] tam = mezclarListasIzq (x:xs) [] tam

mezclarListasIzq :: [a] -> [a] -> Int -> ([a],[a])
mezclarListasIzq xs [] 0 = ([],xs)
mezclarListasIzq (x:xs) ys 0 = (ys,reverse(x:xs))
mezclarListasIzq (x:xs) [] tam = mezclarListasIzq (xs) [x] (tam-1)
mezclarListasIzq (x:xs) (y:ys) tam = mezclarListasIzq (xs) ((y:ys)++[x]) (tam-1)


mezclarListasDer :: [a] -> [a] -> Int -> ([a],[a])
mezclarListasDer [] ys 0 = (ys,[])
mezclarListasDer xs (y:ys) 0 = (xs,reverse(y:ys))
mezclarListasDer [] (y:ys) tam = mezclarListasDer [y] (ys) (tam-1)
mezclarListasDer (x:xs) (y:ys) tam = mezclarListasDer ((x:xs)++[y]) (ys) (tam-1)

kaka = mezclarListas [1,2,3,4,5] [] 2



instance (Show a) => Show (DEQue a) where
   show q = "TwoListsDoubleEndedQueue(" ++ intercalate "," [show x | x <- toList q] ++ ")"

toList :: DEQue a -> [a]
toList (DEQ xs ys) =  xs ++ reverse ys

instance (Eq a) => Eq (DEQue a) where
   q == q' =  toList q == toList q'

instance (Arbitrary a) => Arbitrary (DEQue a) where
   arbitrary =  do
      xs <- listOf arbitrary
      ops <- listOf (oneof [return addFirst, return addLast])
      return (foldr id empty (zipWith ($) ops xs))
