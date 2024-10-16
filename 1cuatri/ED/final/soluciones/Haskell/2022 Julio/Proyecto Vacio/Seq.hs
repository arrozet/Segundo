-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
-- Identity number (DNI if Spanish/passport if Erasmus):
--
-- Data Structures. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module Seq (Seq (..),
    addSingleDigit
) where

data Seq a = Empty | Node a (Seq a) deriving (Eq, Show)

-- ESCRIBE TU SOLUCIÓN DEBAJO ----------------------------------------------
-- WRITE YOUR SOLUTION BELOW  ----------------------------------------------
-- EXERCISE 1

addSingleDigit :: (Integral a) => a -> Seq a -> Seq a
addSingleDigit d xs = undefined
