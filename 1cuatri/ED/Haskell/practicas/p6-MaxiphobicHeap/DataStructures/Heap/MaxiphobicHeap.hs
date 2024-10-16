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
sort3 a b c 
  | size a >= size b && size a >= size c  = (a,b,c)
  | size b >= size a && size b >= size c  = (b,a,c)
  | otherwise                             = (c,a,b) -- es el c

-- Recursively merges smallest subheaps. Achieves O(log n) complexity
merge :: (Ord a) => Heap a -> Heap a -> Heap a
merge Empty Empty = Empty
merge Empty h2@(Node y ty yl yr) = h2
merge h1@(Node x tx xl xr) Empty = h1

merge h1@(Node v1 t1 l1 r1) h2@(Node v2 t2 l2 r2) 
{-
  | y <= x        = (Node y (tx+ty) t1y (merge t2y t3y)) -- raiz mas peque, a la izq + grande
  | otherwise     = (Node x (tx+ty) t1x (merge t2x t3x)) -- a la derecha lo que salga de merge
     where   
      (t1y, t2y, t3y)  = sort3 a yl yr
      (t1x, t2x, t3x)  = sort3 b xl xr                   
-}
  | v2 <= v1        = (Node v2 (t1+t2) grande (merge s2 s3)) -- raiz mas peque, a la izq + grande
  | otherwise       = merge h2 h1       -- cambio de orden para evitar poner 2 where
     where   
      (grande, s2, s3)  = sort3 h1 l2 r2  

-- Returns a heap with a single element
singleton :: a -> Heap a
singleton v = Node v 1 Empty Empty

-- Inserts an element in a heap
insert :: (Ord a) => a -> Heap a -> Heap a
insert v h = merge (singleton v) h 

-- Returns minimum element in heap
minElem :: Heap a -> a
minElem Empty                 = error "No hay elemento minimo"
minElem h@(Node v t l r)  = v

-- Deletes minimum element from heap
delMin :: (Ord a) => Heap a -> Heap a
delMin Empty                 = error "No hay elemento minimo"
delMin h@(Node v t l r)      = merge l r



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
