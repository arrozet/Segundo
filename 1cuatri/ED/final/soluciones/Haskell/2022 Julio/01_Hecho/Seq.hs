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
addSingleDigit d xs = sol
    where
        cadena = crearCadena xs []
        cadenaSum = sumNumero d (reverse cadena)
        cadenaOk = reverse cadenaSum
        sol = crearSeq cadenaOk Empty

crearCadena :: (Integral a) => Seq a -> [a] -> [a]
crearCadena Empty sol = sol
crearCadena (Node a xs) sol = [a] ++ (crearCadena xs sol)

sumNumero :: (Integral a) => a -> [a] -> [a]
sumNumero d [] = [d]
sumNumero d (x:xs)
    | x + d < 10 = ((x+d):xs)
    | otherwise = (d+x-10):(sumNumero (1) xs)

crearSeq :: (Integral a) => [a] -> Seq a -> Seq a
crearSeq [] sol = sol
crearSeq (x:xs) sol = Node x (crearSeq xs sol)

seq1 = Node 9 (Node 9 (Node 9 (Node 3 Empty)))
cadena1 = reverse (crearCadena seq1 [])