-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module BinaryTree
  ( BinaryTree
  , empty
  , isEmpty
  , insert
  , mkBST
  -- | todo
  , subTreesInRange -- EXERCISE 2
  ) where

data BinaryTree a = Empty
                  | Node a (BinaryTree a) (BinaryTree a)
                  deriving Show


-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 2

subTreesInRange :: Ord a => BinaryTree a -> a -> a -> Integer
subTreesInRange Empty min max = 0
subTreesInRange (Node x izq der) min max = sol
  where
    lista = recorrerArbol (Node x izq der)
    solmala = length (intervalo min max lista [])
    sol = intInteger solmala

intInteger :: Int -> Integer
intInteger x = toInteger x

recorrerArbol :: Ord a => BinaryTree a -> [a]
recorrerArbol Empty = []
recorrerArbol (Node x izq der) = [x] ++ recorrerArbol izq ++ recorrerArbol der

intervalo :: Ord a => a -> a -> [a] -> [a] -> [a]
intervalo min max [] sol = sol
intervalo min max (x:xs) sol
  | x >= min && x <= max = intervalo min max xs ([x]++sol)
  | otherwise = intervalo min max xs sol

-- | NO MODIFICAR A PARTIR DE AQUÍ --------------------------------------------
-- | DO NOT MODIFY CODE BELOW      --------------------------------------------

empty :: BinaryTree a
empty  = Empty

isEmpty :: BinaryTree a -> Bool
isEmpty Empty = True
isEmpty _     = False

insert :: Ord a => a -> BinaryTree a -> BinaryTree a
insert x' Empty  =  Node x' Empty Empty
insert x' (Node x lt rt)
    | x'<x       = Node x (insert x' lt) rt
    | x'>x       = Node x lt (insert x' rt)
    | otherwise  = Node x' lt rt

mkBST :: Ord a => [a] -> BinaryTree a
mkBST xs  = foldl (flip insert) empty xs
