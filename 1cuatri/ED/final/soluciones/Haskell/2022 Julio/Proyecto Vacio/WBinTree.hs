-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module WBinTree( WBinTree
               , empty
               , insert
               , isWeightBalanced
               , mkWBinTree
               ) where

data WBinTree a = Empty
                | Node Int a (WBinTree a) (WBinTree a)
                deriving Show

-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 4

isWeightBalanced :: WBinTree a -> Bool
isWeightBalanced = undefined

insert :: a -> WBinTree a -> WBinTree a
insert = undefined


-- | NO MODIFICAR A PARTIR DE AQUÍ --------------------------------------------
-- | DO NOT MODIFY CODE BELOW      --------------------------------------------

empty :: WBinTree a
empty = Empty

mkWBinTree :: [a] -> WBinTree a
mkWBinTree = foldr insert empty
