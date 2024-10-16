-------------------------------------------------------------------------------
-- Apellidos, Nombre: 
-- Titulacion, Grupo: 
--
-- Estructuras de Datos. Grados en Informatica. UMA.
-------------------------------------------------------------------------------

module AVLBiDictionary( BiDictionary
                      , empty
                      , isEmpty
                      , size
                      , insert
                      , valueOf
                      , keyOf
                      , deleteByKey
                      , deleteByValue
                      , toBiDictionary
                      , compose
                      , isPermutation
                      , orbitOf
                      , cyclesOf
                      ) where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.Set.BSTSet               as S

import           Data.List                               (intercalate, nub,
                                                          (\\))
import           Data.Maybe                              (fromJust, fromMaybe,
                                                          isJust)
import           Test.QuickCheck


data BiDictionary a b = Bi (D.Dictionary a b) (D.Dictionary b a)

-- | Exercise a. empty, isEmpty, size

empty :: (Ord a, Ord b) => BiDictionary a b
empty = Bi (D.empty) (D.empty)

isEmpty :: (Ord a, Ord b) => BiDictionary a b -> Bool
isEmpty (Bi a b) = D.isEmpty a

size :: (Ord a, Ord b) => BiDictionary a b -> Int
size (Bi a b) 
  | isEmpty (Bi a b) = 0
  | otherwise = D.size a

-- | Exercise b. insert

insert :: (Ord a, Ord b) => a -> b -> BiDictionary a b -> BiDictionary a b
insert k v (Bi a b)
  | elem k (D.values b) = Bi ( D.insert k v ( D.delete k a ) ) ( D.insert v k (D.delete ( fromJust( D.valueOf k a) ) b ) )
  | otherwise = Bi (D.insert k v a) (D.insert v k b)

-- | Exercise c. valueOf

valueOf :: (Ord a, Ord b) => a -> BiDictionary a b -> Maybe b
valueOf x ( Bi a b ) = (D.valueOf x a)

-- | Exercise d. keyOf

keyOf :: (Ord a, Ord b) => b -> BiDictionary a b -> Maybe a
keyOf x ( Bi a b ) = (D.valueOf x b)

-- | Exercise e. deleteByKey

deleteByKey :: (Ord a, Ord b) => a -> BiDictionary a b -> BiDictionary a b
deleteByKey k (Bi a b)
  | elem k (D.keys a) = Bi (D.delete k a) (D.delete (fromJust (D.valueOf k a) ) b)
  | otherwise = (Bi a b)

-- | Exercise f. deleteByValue

deleteByValue :: (Ord a, Ord b) => b -> BiDictionary a b -> BiDictionary a b
deleteByValue v (Bi a b)
  | elem v (D.keys b) = Bi (D.delete (fromJust (D.valueOf v b)) a) (D.delete v b)
  | otherwise = (Bi a b)

-- | Exercise g. toBiDictionary

toBiDictionary :: (Ord a, Ord b) => D.Dictionary a b -> BiDictionary a b
toBiDictionary a
  | not (inyectivo (D.values a)) = error "No es inyectivo"
  | otherwise = insertRec (D.keys a) (D.values a) empty

insertRec :: (Ord a, Ord b) => [a] -> [b] -> BiDictionary a b -> BiDictionary a b
insertRec [] [] dic = dic
insertRec (x:xs) (y:ys) dic = insert x y (insertRec xs ys dic)


inyectivo :: (Ord a) => [a] -> Bool
inyectivo [] = True
inyectivo (x:xs)
  | elem x xs = False
  | otherwise = inyectivo xs

-- | Exercise h. compose

compose :: (Ord a, Ord b, Ord c) => BiDictionary a b -> BiDictionary b c -> BiDictionary a c
compose (Bi a b) (Bi d c) = composeRec (D.keys a) (Bi a b) (Bi d c) empty


composeRec :: (Ord a, Ord b, Ord c) => [a] -> BiDictionary a b -> BiDictionary b c -> BiDictionary a c -> BiDictionary a c
composeRec [] _ _ sol = sol
composeRec (x:xs) (Bi a b) (Bi d c) sol
  | elem (fromJust (D.valueOf x a)) (D.keys d) = composeRec  xs (Bi a b) (Bi d c) ( insert x ( fromJust ( D.valueOf ( fromJust ( D.valueOf x a ) ) d)  )   sol  )
  | otherwise = composeRec xs (Bi a b) (Bi d c) sol

-- | Exercise i. isPermutation

isPermutation :: Ord a => BiDictionary a a -> Bool
isPermutation (Bi a b)
  | not(inyectivo(D.values a)) && (D.size a /= D.size b) = False
  | otherwise = isPermutationRec (D.keys a) (D.values a)


isPermutationRec :: Ord a => [a] -> [a] -> Bool
isPermutationRec [] [] = True
isPermutationRec [] ys = True
isPermutationRec xs [] = True
isPermutationRec (x:xs) ys
  | elem x ys = isPermutationRec xs ys
  | otherwise = False


-- |------------------------------------------------------------------------


-- | Exercise j. orbitOf

orbitOf :: Ord a => a -> BiDictionary a a -> [a]
orbitOf x (Bi a b)
  | isPermutation (Bi a b) && (elem x (D.keys a)) = orbitOfLista x (Bi a b) []
  | isPermutation (Bi a b) = []
  | otherwise = error "No es permutacion"

orbitOfLista :: Ord a => a -> BiDictionary a a -> [a] -> [a]
orbitOfLista x (Bi a b) xs
  | elem x xs = []
  | otherwise = x : ( orbitOfLista (fromJust (D.valueOf x a)) (Bi a b) (x:xs) )

-- | Exercise k. cyclesOf

cyclesOf :: Ord a => BiDictionary a a -> [[a]]
cyclesOf (Bi a b)
  | isPermutation (Bi a b) = cyclesOfLista (head (D.keys a)) (Bi a b) [[]]
  | otherwise = error "No es permutacion"

cyclesOfLista :: Ord a => a -> BiDictionary a a -> [[a]] -> [[a]]
cyclesOfLista x (Bi a b) lista
  | isEmpty (Bi a b) = [[]]
  | otherwise = orbita : (cyclesOfLista nx (Bi c d) lista)
   where
     orbita = orbitOf x (Bi a b)
     (Bi c d) = borrarListas (orbita) (Bi a b)
     nx = (head (D.keys c))

borrarListas :: Ord a => [a] -> BiDictionary a a -> BiDictionary a a
borrarListas [] dic = dic
borrarListas (x:xs) dic = deleteByKey x (borrarListas xs dic)


-- |------------------------------------------------------------------------


instance (Show a, Show b) => Show (BiDictionary a b) where
  show (Bi dk dv)  = "BiDictionary(" ++ intercalate "," (aux (D.keysValues dk)) ++ ")"
                        ++ "(" ++ intercalate "," (aux (D.keysValues dv)) ++ ")"
   where
    aux kvs  = map (\(k,v) -> show k ++ "->" ++ show v) kvs
