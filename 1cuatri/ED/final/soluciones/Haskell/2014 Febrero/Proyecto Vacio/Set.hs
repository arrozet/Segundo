--By José Manuel Fernández Reyes, 21st December 2023
--11th Data Structure:
--SortedLinearSet

module Set (
    Set,
    empty,
    isEmpty,
    size,
    insert,
    delete,
    isElem
) where

data Set a = Empty | Node a (Set a) deriving Show

empty :: Set a
empty = Empty

isEmpty :: Set a -> Bool
isEmpty Empty = True
isEmpty _     = False

size :: Set a -> Int
size Empty = 0
size (Node x s) = 1 + size s

insert :: (Ord a) => a -> Set a -> Set a
insert x Empty = (Node x Empty)
insert x (Node y s) | x == y = (Node y s)
                    | x > y  = (Node y (insert x s))
                    | otherwise = (Node x (Node y s))

delete :: (Eq a) => a -> Set a -> Set a
delete x Empty = Empty
delete x (Node y s) | x == y = s
                    | otherwise = (Node y (delete x s))

isElem :: (Eq a) => a -> Set a -> Bool
isElem x Empty = False
isElem x (Node y s) | x == y = True
                    | otherwise = isElem x s

--Examples

s1 :: Set Int
s1 = insert 7 (insert 3 (insert 1 (insert 3 empty)))