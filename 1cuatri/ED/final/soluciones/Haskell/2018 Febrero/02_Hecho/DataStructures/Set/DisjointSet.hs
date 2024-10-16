-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. February 2018. BSc. Computer Science. UMA.
-------------------------------------------------------------------------------

module DataStructures.Set.DisjointSet
                  ( DisjointSet
                  , empty
                  , isEmpty
                  , isElem
                  , numElements
                  , add
                  , areConnected
                  , kind
                  , union
                  , flatten
                  , kinds
                  ) where

import           Data.List                               (intercalate)
import           Data.Maybe                              (fromJust)
import qualified DataStructures.Dictionary.AVLDictionary as D

data DisjointSet a = DS (D.Dictionary a a)

-- | Exercise 1. empty

empty :: DisjointSet a
empty = DS (D.empty)

-- | Exercise 2.a isEmpty

isEmpty :: DisjointSet a -> Bool
isEmpty (DS dic) = (D.isEmpty dic)

-- | Exercise 2.b isElem

isElem :: (Ord a) => a -> DisjointSet a -> Bool
isElem x (DS dic) = elem x (D.keys dic)

-- | Exercise 3. numElements

numElements :: DisjointSet a -> Int
numElements (DS dic) = (D.size dic)

-- | Exercise 4. add

add :: Ord a => a -> DisjointSet a -> DisjointSet a
add x (DS dic)
  | isElem x (DS dic) = (DS dic)
  | otherwise = (DS (D.insert x x dic))

-- | Exercise 5. root

root :: Ord a => a -> DisjointSet a -> Maybe a
root x (DS dic)
  | isElem x (DS dic) = rootRec x (DS dic)
  | otherwise = Nothing

rootRec :: Ord a => a -> DisjointSet a -> Maybe a
rootRec x (DS dic)
  | x == (num(D.valueOf x dic)) = Just x 
  | otherwise = rootRec (num(D.valueOf x dic)) (DS dic)

num :: Maybe a -> a
num (Just x) = x

-- | Exercise 6. isRoot

isRoot :: Ord a => a -> DisjointSet a -> Bool
isRoot x (DS dic)
  | Just x == (root x (DS dic)) = True
  | otherwise = False

-- | Exercise 7. areConnected

areConnected :: Ord a => a -> a -> DisjointSet a -> Bool
areConnected x y (DS dic)
  | (root x (DS dic)) == (root y (DS dic)) = True
  | otherwise = False

-- | Exercise 8. kind

kind :: Ord a => a -> DisjointSet a -> [a]
kind x (DS dic)
  | isElem x (DS dic) = kindRec (num(root x (DS dic))) (DS dic) (D.keys dic) []
  | otherwise = []

kindRec :: Ord a => a -> DisjointSet a -> [a] -> [a] -> [a]
kindRec x (DS dic) [] sol = sol
kindRec x (DS dic) (y:ys) sol
  | areConnected x y (DS dic) = kindRec x (DS dic) ys (sol ++ [y])
  | otherwise = kindRec x (DS dic) ys sol

-- | Exercise 9. union

union :: Ord a => a -> a -> DisjointSet a -> DisjointSet a
union x y (DS dic)
  | isElem x (DS dic) && isElem y (DS dic) && (root x (DS dic)) >= (root y (DS dic)) = (DS (D.insert (num(root x (DS dic))) (num(root y (DS dic))) dic))
  | isElem x (DS dic) && isElem y (DS dic) = (DS (D.insert (num(root y (DS dic))) (num(root x (DS dic))) dic))
  | otherwise = error "union: missing element(s)"

-- |------------------------------------------------------------------------

flatten :: Ord a => DisjointSet a -> DisjointSet a
flatten = undefined

kinds :: Ord a => DisjointSet a -> [[a]]
kinds = undefined

-- |------------------------------------------------------------------------

instance (Ord a, Show a) => Show (DisjointSet a) where
  show (DS d)  = "DictionaryDisjointSet(" ++ intercalate "," (map show (D.keysValues d)) ++ ")"


{-

-- Examples

-- | Exercise 1. empty

>>> empty
DictionaryDisjointSet()

-- | Exercise 2.a isEmpty

>>> isEmpty empty
True

>>> isEmpty (add 1 empty)
False

-- | Exercise 2.b isElem

>>> isElem 1 empty
False

>>> isElem 1 (add 1 empty)
True

>>> isElem 2 (add 1 empty)
False

>>> isElem 1 (add 2 (add 1 empty))
True

-- | Exercise 3. numElements

>>> numElements empty
0

>>> numElements (add 1 empty)
1

>>> numElements (add 2 (add 1 empty))
2

-- | Exercise 4. add

>>> add 1 empty
DictionaryDisjointSet((1,1))

>>> add 2 (add 1 empty)
DictionaryDisjointSet((1,1),(2,2))

>>> add 1 (add 2 (add 1 empty))
DictionaryDisjointSet((1,1),(2,2))

-- | Exercise 5. root

>>> root 1 empty
Nothing

>>> root 1 (add 1 empty)
Just 1

>>> root 2 (add 2 (add 1 empty))
Just 2

>>> root 1 (union 1 2 (add 2 (add 1 empty)))
Just 1

>>> root 2 (union 1 2 (add 2 (add 1 empty)))
Just 1

>>> root 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
Just 1

>>> root 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
Just 2

>>> root 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
Just 1

>>> root 4 (union 1 3 (add 3 (add 2 (add 1 DisjointSet.empty))))
Nothing

-- | Exercise 6. isRoot

>>> isRoot 1 empty
False

>>> isRoot 1 (add 1 empty)
True

>>> isRoot 1 (union 1 2 (add 2 (add 1 empty)))
True

>>> isRoot 2 (union 1 2 (add 2 (add 1 empty)))
False

>>> isRoot 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> isRoot 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> isRoot 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
False

-- | Exercise 7. areConnected

>>> areConnected 1 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> areConnected 3 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> areConnected 1 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
True

>>> areConnected 1 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
False

>>> areConnected 1 2 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
True

>>> areConnected 1 5 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
False

-- | Exercise 8. kind

>>> kind 1 (add 2 (add 1 empty))
[1]

>>> kind 2 (add 2 (add 1 empty))
[2]

>>> kind 3 (add 2 (add 1 empty))
[]

>>> kind 1 (union 1 3 (add 3 (add 2 (add 1 empty))))
[1,3]

>>> kind 3 (union 1 3 (add 3 (add 2 (add 1 empty))))
[1,3]

>>> kind 2 (union 1 3 (add 3 (add 2 (add 1 empty))))
[2]

>>> kind 2 (union 2 3 (union 1 3 (add 3 (add 2 (add 1 empty)))))
[1,2,3]

-- | Exercise 9. union

>>> union 1 2 (add 2 (add 1 empty))
DictionaryDisjointSet((1,1),(2,1))

>>> union 2 1 (add 2 (add 1 empty))
DictionaryDisjointSet((1,1),(2,1))

>>> union 1 1 (add 2 (add 1 empty))
DictionaryDisjointSet((1,1),(2,2))

>>> union 1 3 (add 3 (add 2 (add 1 empty)))
DictionaryDisjointSet((1,1),(2,2),(3,1))

>>> union 1 2 (add 1 empty)
*** Exception: union: missing element(s)

-}
