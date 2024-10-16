-------------------------------------------------------------------------------
-- Maxiphobic Heaps
--
-- see: Fun with Binary Heap Trees
--      & Alternatives to two Classic Data Structures
--      by Chris Okasaki
--
-- Data Structures. Grado en Informática. UMA.
-- Pepe Gallardo, 2012
-------------------------------------------------------------------------------

module DataStructures.Heap.MaxiphobicHeap
  ( Heap
  , empty
  , isEmpty
  , minElem
  , delMin
  , insert
  , merge

  , mkHeap
  , size
  , isHeap

  , drawOnWith
  ) where

import DataStructures.Graphics.DrawTrees
import Test.QuickCheck

data Heap a  = Empty | Node a Int (Heap a) (Heap a) deriving Show

-- number of elements
size :: Heap a -> Int
size Empty            = 0
size (Node _ sz _ _)  = sz

empty :: Heap a
empty  = Empty

isEmpty :: Heap a -> Bool
isEmpty Empty  = True
isEmpty _      = False

-- Partially sorts heaps according to their sizes.
-- Returns largest heap in first position
sort3 :: Heap a -> Heap a -> Heap a -> (Heap a, Heap a, Heap a)
sort3 h1 h2 h3
  | size h1 >= size h2 && size h1 >= size h3 = (h1, h2, h3)
  | size h2 >= size h1 && size h2 >= size h3 = (h2, h1, h3)
  | otherwise = (h3, h1, h2)

-- Recursively merges smallest subheaps. Achieves O(log n) complexity
merge :: (Ord a) => Heap a -> Heap a -> Heap a
merge Empty h2 = h2
merge h1 Empty = h1
merge (Node x1 tam1 izq1 der1) (Node x2 tam2 izq2 der2)
  | x1 < x2 = (Node x1 (tam1+tam2) h1 (merge h2 h3))
  | otherwise = (Node x2 (tam1+tam2) h4 (merge h5 h6))
    where
      (h1, h2, h3) = sort3 izq1 der1 (Node x2 tam2 izq2 der2)
      (h4, h5, h6) = sort3 izq2 der2 (Node x1 tam1 izq1 der1)
  

-- Returns a heap with a single element
singleton :: a -> Heap a
singleton x = (Node x 1 Empty Empty)

-- Inserts an element in a heap
insert :: (Ord a) => a -> Heap a -> Heap a
insert x heap = merge (singleton x) heap

-- Returns minimum element in heap
minElem :: Heap a -> a
minElem (Node x tam izq der ) = x

-- Deletes minimum element from heap
delMin :: (Ord a) => Heap a -> Heap a
delMin (Node x tam izq der) = merge izq der



-- Efficient O(n) bottom-up construction for heaps
mkHeap :: (Ord a) => [a] -> Heap a
mkHeap []  = empty
mkHeap xs  = mergeLoop (map singleton xs)
  where
    mergeLoop [h]  = h
    mergeLoop hs   = mergeLoop (mergePairs hs)

    mergePairs []         = []
    mergePairs [h]        = [h]
    mergePairs (h:h':hs)  = merge h h' : mergePairs hs

-------------------------------------------------------------------------------
-- Generating arbritray Heaps
-------------------------------------------------------------------------------

instance (Ord a, Arbitrary a) => Arbitrary (Heap a) where
  arbitrary  = do
    xs <- arbitrary
    return (mkHeap xs)

-------------------------------------------------------------------------------
-- Invariants
-------------------------------------------------------------------------------

isHeap :: (Ord a) => Heap a -> Bool
isHeap Empty             = True
isHeap (Node x _ lh rh)  = x `lessEq` lh && x `lessEq` rh
                            && isHeap lh && isHeap rh
 where
  x `lessEq` Empty            = True
  x `lessEq` (Node x' _ _ _)  = x <= x'


-------------------------------------------------------------------------------
-- Drawing a Heap
-------------------------------------------------------------------------------

instance Subtrees (Heap a) where
  subtrees Empty             = []
  subtrees (Node _ _ lh rh)  = [lh,rh]

  isEmptyTree  = isEmpty

instance (Show a) => ShowNode (Heap a) where
  showNode (Node x _ _ _) = show x

drawOnWith :: FilePath -> (a -> String) -> Heap a -> IO ()
drawOnWith file toString = _drawOnWith file showHeap
 where
  showHeap (Node x _ _ _) = toString x
