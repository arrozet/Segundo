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
isEmpty (DEQ fs ls) = null fs && null ls

-- Complexity:
addFirst :: a -> DEQue a -> DEQue a
addFirst x (DEQ fs ls) = (DEQ (x:fs) ls)

-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast x (DEQ fs ls) = (DEQ fs (x:ls))

-- Complexity:
first :: DEQue a -> a
--first empty             = error "first: first of empty"
first (DEQ [] [x])      = x
first dq@(DEQ [] (l:ls)) = first (transformar dq)
first (DEQ (f:fs) _) = head (f:fs)

-- Complexity:
last :: DEQue a -> a
--last empty             = error "last: last of empty"
last (DEQ [x] [])      = x
last dq@(DEQ (f:fs) []) = last (transformar dq)
last (DEQ _ (l:ls)) = head (l:ls)

-- Complexity:
deleteFirst :: DEQue a -> DEQue a
--deleteFirst empty             = error "deleteFirst: deleteFirst of empty"
deleteFirst (DEQ [] [x])      = empty
deleteFirst dq@(DEQ [] (l:ls)) = deleteFirst (transformar dq)
deleteFirst (DEQ (f:fs) ls) = (DEQ fs ls)

transformar :: DEQue a -> DEQue a
--transformar (DEQ [] [])    = (DEQ [] [])
transformar (DEQ [] (l:ls)) = (DEQ new_l1 new_l2)
   where
      mitad_menor = (length (l:ls)) `div` 2
      mitad_mayor = (length (l:ls)) - mitad_menor
      new_l2 = take mitad_mayor (l:ls)  -- elem más grandes pq lista al reves, van al final
      new_l1 = reverse (drop mitad_mayor (l:ls))  -- elem más peques pq lista al reves, van al principio

transformar (DEQ (f:fs) []) = (DEQ new_l1 new_l2)
   where
      mitad_menor = (length (f:fs)) `div` 2
      --mitad_mayor = (length (f:fs)) - mitad_menor
      new_l1 = take mitad_menor (f:fs)  -- elem más grandes pq lista al reves, van al final
      new_l2 = reverse (drop mitad_menor (f:fs))  -- elem más peques pq lista al reves, van al principio
   
--transformar (DEQ fs ls)    = (DEQ fs ls) 

-- Complexity:
deleteLast :: DEQue a -> DEQue a
--deleteLast empty             = error "deleteLast: deleteLast of empty"
deleteLast (DEQ [x] [])      = empty
deleteLast dq@(DEQ fs []) = deleteLast (transformar dq)
deleteLast (DEQ (fs) (l:ls)) = (DEQ (fs) (ls))



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
