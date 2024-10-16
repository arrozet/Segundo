-------------------------------------------------------------------------------
-- Linear implementation of Sets with nodes sorted according to values
-- and non-repeated elements
--
-- Data Structures. Grado en Informática. UMA.
--
-- STUDENT'S NAME:
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

n1 = Node 2 (Node 5 (Node 8 Empty))
n2 = Node 1 (Node 2 (Node 9 Empty))

empty :: Set a
empty = Empty

isEmpty :: Set a -> Bool
isEmpty Empty = True
isEmpty _ = False

isElem :: (Ord a) => a -> Set a -> Bool
isElem x Empty = False
isElem x (Node y ys)
  | x == y = True
  | otherwise = isElem x ys

insert :: (Ord a) => a -> Set a -> Set a
insert x Empty = (Node x Empty)
insert x (Node y ys)
  | x == y = (Node y ys)
  | x < y = Node x (Node y ys)
  | otherwise = Node y (insert x ys)

delete :: (Ord a) => a -> Set a -> Set a
delete x Empty = Empty
delete x (Node y ys)
  | x == y = ys
  | x > y = Node y (delete x ys)
  | otherwise = (Node y ys)

size :: Set a -> Int
size Empty = 0
size (Node y ys) = 1+size ys

fold :: (a -> b -> b) -> b -> Set a -> b
fold f z = fun
 where
  fun Empty       = z
  fun (Node x s)  = f x (fun s)

union :: (Ord a) => Set a -> Set a -> Set a
union Empty Empty = Empty
union Empty (Node y ys) = (Node y ys)
union (Node x xs) Empty = (Node x xs)
union (Node x xs) (Node y ys)
  | x == y = Node x (union xs ys)
  | x > y = Node y (union (Node x xs) ys)
  | otherwise = Node x (union xs (Node y ys))

difference :: (Ord a) => Set a -> Set a -> Set a
difference Empty Empty = Empty
difference (Node x xs) Empty = (Node x xs)
difference Empty (Node x xs) = Empty
difference (Node x xs) (Node y ys)
  | x == y = difference xs ys
  | x > y = difference (Node x xs) ys
  | otherwise = Node x (difference xs (Node y ys)) 

intersection :: (Ord a) => Set a -> Set a -> Set a
intersection Empty Empty = Empty
intersection xs Empty = Empty
intersection Empty xs = Empty
intersection (Node x xs) (Node y ys)
  | x == y = Node x (intersection xs ys)
  | x > y = intersection (Node x xs) ys
  | otherwise = intersection xs (Node y ys)





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

