-------------------------------------------------------------------------------
-- Linear implementation of Sets with nodes sorted according to values
-- and non-repeated elements
--
-- Data Structures. Grado en Informática. UMA.
--
-- STUDENT'S NAME: Rubén Oliva Zamora
-------------------------------------------------------------------------------

module DataStructures.Set.SortedLinearSet 
  ( Set
  , empty
  , isEmpty
  , size
  , insert
  , isElem
  , delete

  , fold

  , union
  , intersection
  , difference  
  ) where

import Data.List(intercalate)
import Test.QuickCheck

-- Invariants for this data structure:
--  * (INV1) All Nodes store different elements (no repetitions)
--  * (INV2) Nodes are sorted in ascending order with 
--           respect to values of their elements
--
-- An example of a well constructed set:
--   Node 2 (Node 5 (Node 8 Empty))
--
-- Examples of wrong sets:
--   Node 2 (Node 5 (Node 5 (Node 8 Empty))) -- REPETITION OF ELEMENT 5!
--   Node 7 (Node 1 (Node 8 Empty)) -- ELEMENTS NOT IN ASCENDING ORDER!

data Set a  = Empty | Node a (Set a)

empty :: Set a
empty = Empty

-- esta vacio
isEmpty :: Set a -> Bool
isEmpty Empty = True
isEmpty _     = False

-- elemento pertenece
isElem :: (Ord a) => a -> Set a -> Bool
isElem x Empty = False
isElem x (Node y s) = if y == x then True else isElem x s

-- insertar ordenadamente 
insert :: (Ord a) => a -> Set a -> Set a
insert x Empty = Node x Empty
insert x ls@(Node y s) 
  | x < y   = Node x ls   -- lo meto pq está ordenado de menor a mayor
  | x == y  = Node y s    -- no meto nada porque en un conjunto no hay elementos repetidos
  | x > y   = Node y (insert x s)   -- pongo el y antes

-- borrar y mantener ordenado
delete :: (Ord a) => a -> Set a -> Set a
delete x Empty = Empty
delete x (Node y s) 
  | x < y   = Node y s
  | x == y  = s
  | x > y   = Node y (delete x s)

-- tamaño conjunto
size :: Set a -> Int
size Empty          = 0
size (Node y s)     = 1 + size (s) 

-- foldr en conjunto
fold :: (a -> b -> b) -> b -> Set a -> b
fold f z = fun
 where
  fun Empty       = z
  fun (Node x s)  = f x (fun s)
{-
fold f z Empty = z
fold f z (Node x s) = f x (fold f z s)
-}

union :: (Ord a) => Set a -> Set a -> Set a
{-union s1 Empty = s1
union Empty s2 = s2
union (Node y1 s1) s2 = union s1 (insert y1 s2)-}
--union s1 (Node y2 s2) = union s2 (insert y2 s1)
union s1 s2 = fold insert s1 s2


difference :: (Ord a) => Set a -> Set a -> Set a
difference s1 s2 = fold delete s1 s2

intersection :: (Ord a) => Set a -> Set a -> Set a
intersection Empty s2 = Empty
intersection s1 Empty = Empty
intersection ls1@(Node y1 s1) ls2@(Node y2 s2) 
  | isElem y1 ls2   = Node y1 (intersection s1 ls2)
  | otherwise       = intersection s1 ls2





-- Showing a set
instance (Show a) => Show (Set a) where
  show s  = "SortedLinearSet(" ++ intercalate "," (strings s) ++ ")"
    where
      strings Empty       = []
      strings (Node x s)  = show x : strings s

-- Set equality
instance (Eq a) => Eq (Set a) where
  Empty      == Empty         = True
  (Node x s) == (Node x' s')  = x==x' && s==s'
  _          == _             = False

-- This instance is used by QuickCheck to generate random sets
instance (Ord a, Arbitrary a) => Arbitrary (Set a) where
    arbitrary  = do
      xs <- listOf arbitrary
      return (foldr insert empty xs)

