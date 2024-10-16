--By José Manuel Fernández Reyes, 19th December 2023
--7th Data Structure:
--AVL (Adelson-Velskii y Landis)

module AVL (

    AVL,

    empty,
    isEmpty,
    height,
    isAVL,

    node,
    rotR,
    rotL,
    rightLeaning,
    leftLeaning,
    balance,
    insert,
    
    search,
    isElem,

    split,
    combine,
    delete,

    minim,
    maxim,

    mkAVL,
    preOrder,
    inOrder,
    postOrder

) where

import Data.Maybe

data AVL a = Empty | Node a Int (AVL a) (AVL a) deriving Show

--Creates an empty AVL | *Complexity: O(1)*
empty :: AVL a
empty = Empty

--Checks whether the AVL is empty or not | *Complexity: O(1)*
isEmpty :: AVL a -> Bool
isEmpty Empty = True
isEmpty _     = False

--Returns the height of the AVL tree (which is stored as the value of each node) | *Complexity: O(1)*
height :: AVL a -> Int
height Empty            = 0
height (Node x h lt rt) = h

--Checks whether the given tree is AVL or not | *Complexity: O(n)*
isAVL :: (Ord a) => AVL a -> Bool
isAVL Empty = True
isAVL (Node x h lt rt) = forAll (<x) lt && forAll (>x) rt && abs (height lt - height rt) <= 1 && isAVL lt && isAVL rt
    where
        forAll :: (a -> Bool) -> AVL a -> Bool
        forAll p Empty = True
        forAll p (Node x h lt rt) = p x && forAll p lt && forAll p rt

--Creates a new AVL tree merging two of them and with a given value | *Complexity: O(1)*
node :: a -> AVL a -> AVL a -> AVL a
node x lt rt = (Node x h lt rt)
    where h = 1 + max (height lt) (height rt)

--Rotates the AVL tree to the right | *Complexity: O(1)*
rotR :: AVL a -> AVL a
rotR (Node k h (Node lk lh llt lrt) rt) = node lk llt (node k lrt rt)

--Rotates the AVL tree to the left | *Complexity: O(1)*
rotL :: AVL a -> AVL a
rotL (Node k h lt (Node rk rl rlt rrt)) = node rk (node k lt rlt) rrt

--Checks if the tree is balanced to the right | *Complexity: O(1)*
rightLeaning :: AVL a -> Bool 
rightLeaning (Node x h lt rt) =  height lt <= height rt

--Checks if the tree is balanced to the left | *Complexity: O(1)*
leftLeaning :: AVL a -> Bool 
leftLeaning (Node x h lt rt) =  height lt >= height rt

--Balances the tree | *Complexity: O(1)*
balance :: a -> AVL a -> AVL a -> AVL a
balance k lt rt | (lh-rh > 1) && leftLeaning lt = rotR (node k lt rt)
                | (lh-rh > 1)                   = rotR (node k (rotL lt) rt)
                | (rh-lh > 1) && rightLeaning rt = rotL (node k lt rt)
                | (rh-lh > 1)                   = rotL (node k lt (rotR rt))
                | otherwise = node k lt rt
    where
        lh = height lt
        rh = height rt

--Inserts the given element in the AVL tree | *Complexity: O(log n)*
insert :: (Ord a) => a -> AVL a -> AVL a
insert el Empty = (Node el 1 Empty Empty)
insert el (Node x h lt rt) | el == x = Node el h lt rt
                           | el < x  = balance x (insert el lt) rt
                           | otherwise = balance x lt (insert el rt)

--Searchs for the given element in the AVL | *Complexity: O(log n)*
search :: (Ord a) => a -> AVL a -> Maybe a
search el Empty = Nothing
search el (Node x h lt rt) | el < x = search el lt
                           | el > x = search el rt
                           | otherwise = Just x

--Checks whether the given element is in the AVL or not | *Complexity: O(log n)*
isElem :: (Ord a) => a -> AVL a -> Bool
isElem el t = isJust (search el t)

--Returns the smallest element in the AVL and the AVL without that element | *Complexity: O(log n)*
split :: AVL a -> (a, AVL a)
split (Node x h Empty rt) = (x, rt)
split (Node x h lt rt)    = (x', balance x lt' rt)
    where (x', lt') = split lt

--Combines two AVLs giving a new one | *Complexity: O(log n)*
combine :: AVL a -> AVL a -> AVL a
combine lt Empty = lt 
combine Empty rt = rt
combine lt rt    = balance x' lt rt'
    where (x', rt') = split rt

--This is done this way because we suppose that all elements in lt are smaller than
--those in rt. Since AVL are BSTs with balancing property, we must take the smallest element in
--rt, which will be larger than those in lt, and that would be the node. Afterwards, we just have to balance the tree

--Removes the given element from the AVL  | *Complexity: O(log n)*
delete :: (Ord a) => a -> AVL a -> AVL a
delete el Empty = Empty
delete el (Node x h lt rt) | el == x = combine lt rt
                           | el < x  = balance x (delete el lt) rt
                           | otherwise = balance x lt (delete el rt)

--Returns the minimum from the AVL | *Complexity: O(log n)*
minim :: AVL a -> a
minim Empty = error "minim: there is no minim on an empty AVL"
minim (Node x h Empty rt) = x
minim (Node x h lt rt)    = minim lt

--Returns the maximum from the AVL | *Complexity: O(log n)*
maxim :: AVL a -> a 
maxim Empty = error "maxim: there is no maxim on an empty AVL"
maxim (Node x h lt Empty) = x
maxim (Node x h lt rt)    = maxim rt

--Turns the given list into a AVL tree | *Complexity: O(log n)*
mkAVL :: (Ord a) => [a] -> AVL a
mkAVL [] = Empty
mkAVL l  = foldr (\x dd -> insert x dd) Empty l

--Returns the preOrder of the AVL | *Complexity: O(log n)*
preOrder :: AVL a -> [(a, Int)]
preOrder Empty = []
preOrder (Node x h lt rt) = [(x, h)] ++ preOrder lt ++ preOrder rt

--Returns the inOrder of the AVL | *Complexity: O(log n)*
inOrder :: AVL a -> [(a, Int)]
inOrder Empty = []
inOrder (Node x h lt rt) = inOrder lt ++ [(x, h)] ++ inOrder rt

--Returns the postOrder of the AVL | *Complexity: O(log n)*
postOrder :: AVL a -> [(a, Int)]
postOrder Empty = []
postOrder (Node x h lt rt) = postOrder lt ++ postOrder rt ++ [(x, h)]

--Examples

a1 = Node 4 4 (Node 2 2 (Node 1 1 Empty Empty) (Node 3 1 Empty Empty)) (Node 8 3 (Node 6 2 (Node 5 1 Empty Empty) (Node 7 1 Empty Empty)) (Node 9 2 Empty (Node 10 1 Empty Empty)))