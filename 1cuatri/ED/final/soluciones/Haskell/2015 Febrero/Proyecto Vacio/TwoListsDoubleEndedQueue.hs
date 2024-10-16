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
empty = undefined

-- Complexity:
isEmpty :: DEQue a -> Bool
isEmpty _ = undefined

-- Complexity:
addFirst :: a -> DEQue a -> DEQue a
addFirst _ _ = undefined

-- Complexity:
addLast :: a -> DEQue a -> DEQue a
addLast _ _ = undefined

-- Complexity:
first :: DEQue a -> a
first _ = undefined

-- Complexity:
last :: DEQue a -> a
last _ = undefined

-- Complexity:
deleteFirst :: DEQue a -> DEQue a
deleteFirst _ = undefined

-- Complexity:
deleteLast :: DEQue a -> DEQue a
deleteLast _ = undefined



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
