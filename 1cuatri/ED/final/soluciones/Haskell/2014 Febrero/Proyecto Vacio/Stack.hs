--By José Manuel Fernández Reyes, 21st December 2023
--10th Data Structure:
--Stack

module Stack (
    Stack,
    empty,
    isEmpty,
    push,
    pop,
    top,
    size,
    stackToList
) where

data Stack a = Empty | Node a (Stack a) deriving Show

empty :: Stack a
empty = Empty

isEmpty :: Stack a -> Bool
isEmpty Empty = True
isEmpty _     = False

push :: a -> Stack a -> Stack a
push x Empty = Node x Empty
push x (Node y s) = Node x (Node y s)

pop :: Stack a -> Stack a
pop Empty = error "pop: can't do pop on an empty stack"
pop (Node x s) = s

top :: Stack a -> a
top Empty = error "top: can't do top on an empty stack"
top (Node x s) = x

size :: Stack a -> Int
size Empty = 0
size (Node x s) = 1 + size s

stackToList :: Stack a -> [a]
stackToList Empty = []
stackToList (Node x s) = x : stackToList s

--Examples

s1 :: Stack Int
s1 = Node 2 (Node 5 (Node 9 (Node 1 (Node 3 Empty))))





