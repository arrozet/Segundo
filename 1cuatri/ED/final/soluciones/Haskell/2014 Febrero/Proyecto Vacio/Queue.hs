--By José Manuel Fernández Reyes, 18th December 2023
--6th Data Structure:
--Queue

module Queue (
    Queue,
    empty,
    isEmpty,
    first,
    enqueue,
    dequeue,
    isElem
) where

data Queue a = Empty | Node a (Queue a) deriving Show

empty :: Queue a
empty = Empty

isEmpty :: Queue a -> Bool
isEmpty Empty = True
isEmpty _     = False

first :: Queue a -> a
first Empty = error "first: you can't do first on an empty queue"
first (Node x q) = x

enqueue :: a -> Queue a -> Queue a
enqueue x Empty = Node x Empty
enqueue x (Node y q) = (Node y (enqueue x q))

dequeue :: Queue a -> Queue a
dequeue Empty = Empty
dequeue (Node x q) = q

isElem :: (Eq a) => a -> Queue a -> Bool
isElem x Empty = False
isElem x (Node y q) | x == y = True
                    | otherwise = isElem x q