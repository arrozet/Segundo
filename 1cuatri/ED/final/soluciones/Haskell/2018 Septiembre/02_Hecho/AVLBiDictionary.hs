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
empty = (Bi D.empty D.empty)

isEmpty :: (Ord a, Ord b) => BiDictionary a b -> Bool
isEmpty (Bi a b)
  | D.isEmpty a = True
  | otherwise = False

size :: (Ord a, Ord b) => BiDictionary a b -> Int
size (Bi a b) 
  | isEmpty (Bi a b) = 0
  | otherwise = D.size a

-- | Exercise b. insert

insert :: (Ord a, Ord b) => a -> b -> BiDictionary a b -> BiDictionary a b
insert k v (Bi a b)
  | elem k (D.values b) = Bi (D.insert k v a) (D.insert v k (D.delete (num(D.valueOf k a)) b))
  | otherwise = Bi (D.insert k v a) (D.insert v k b)


num :: Maybe a -> a 
num (Just x) = x

num2 :: Maybe b -> b
num2 (Just x) = x

-- | Exercise c. valueOf

valueOf :: (Ord a, Ord b) => a -> BiDictionary a b -> Maybe b
valueOf v (Bi a b) = (D.valueOf v a)

-- | Exercise d. keyOf

keyOf :: (Ord a, Ord b) => b -> BiDictionary a b -> Maybe a
keyOf k (Bi a b) = (D.valueOf k b)

-- | Exercise e. deleteByKey

deleteByKey :: (Ord a, Ord b) => a -> BiDictionary a b -> BiDictionary a b
deleteByKey k (Bi a b) = ( Bi (D.delete k a) (D.delete (num(D.valueOf k a)) b) )

-- | Exercise f. deleteByValue

deleteByValue :: (Ord a, Ord b) => b -> BiDictionary a b -> BiDictionary a b
deleteByValue v (Bi a b) = ( Bi (D.delete (num(D.valueOf v b)) a) (D.delete v b) )

-- | Exercise g. toBiDictionary

toBiDictionary :: (Ord a, Ord b) => D.Dictionary a b -> BiDictionary a b
toBiDictionary dic
  | inyectivo dic = toBiDictionaryRec (D.keys dic) dic (Bi D.empty D.empty)
  | otherwise = error "El diccionario no es inyectivo"


toBiDictionaryRec :: (Ord a, Ord b) => [a] -> D.Dictionary a b -> BiDictionary a b -> BiDictionary a b
toBiDictionaryRec [] dic sol = sol
toBiDictionaryRec (x:xs) dic sol = toBiDictionaryRec xs dic (insert x (num(D.valueOf x dic)) sol)



inyectivo :: (Ord a, Ord b) => D.Dictionary a b -> Bool
inyectivo dic = inyectivoRec (D.values dic)

inyectivoRec :: (Ord a) => [a] -> Bool
inyectivoRec [] = True
inyectivoRec (x:xs)
  | not(elem x xs) = inyectivoRec xs
  | otherwise = False


-- | Exercise h. compose

compose :: (Ord a, Ord b, Ord c) => BiDictionary a b -> BiDictionary b c -> BiDictionary a c
compose (Bi a b) (Bi c d) = composeRec (D.values a) (D.keys c) (Bi a b) (Bi c d) (Bi D.empty D.empty)


composeRec :: (Ord a, Ord b, Ord c) => [b] -> [b] -> BiDictionary a b -> BiDictionary b c -> BiDictionary a c -> BiDictionary a c
composeRec [] _ (Bi a b) (Bi c d) sol = sol
composeRec (x:xs) ys (Bi a b) (Bi c d) sol
  | elem x ys = composeRec xs ys (Bi a b) (Bi c d) (insert (num(D.valueOf x b)) (num(D.valueOf x c)) sol )
  | otherwise = composeRec xs ys (Bi a b) (Bi c d) sol

  -- 

-- | Exercise i. isPermutation

isPermutation :: Ord a => BiDictionary a a -> Bool
isPermutation (Bi a b)
  | (inyectivo a) && (inyectivo b) && ( length (D.keys a) == length (D.keys b) )  = isPermutationRec (D.keys a) (D.keys b) (Bi a b)
  | otherwise = False

isPermutationRec :: Ord a => [a] -> [a] -> BiDictionary a a -> Bool
isPermutationRec [] _ dic = True
isPermutationRec (x:xs) ys dic
  | elem x ys = isPermutationRec xs ys dic
  | otherwise = False



-- |------------------------------------------------------------------------


-- | Exercise j. orbitOf

orbitOf :: Ord a => a -> BiDictionary a a -> [a]
orbitOf x (Bi a b)
  | not(elem x (D.keys a)) = error "El elemento no existe en el diccionario"
  | isPermutation (Bi a b) = orbitOfRec x x (Bi a b) []
  | otherwise = error "No es permutacion"


orbitOfRec :: Ord a => a -> a -> BiDictionary a a -> [a] -> [a]
orbitOfRec ini x (Bi a b) [] = orbitOfRec ini (num(D.valueOf x a)) (Bi a b) [x]
orbitOfRec ini x (Bi a b) sol
  | ini == x = sol
  | otherwise = orbitOfRec ini (num(D.valueOf x a)) (Bi a b) (sol ++ [x])


-- | Exercise k. cyclesOf

cyclesOf :: Ord a => BiDictionary a a -> [[a]]
cyclesOf (Bi a b)
  | isPermutation (Bi a b) = cyclesOfRec (D.keys a) (Bi a b) [[]]
  | otherwise = error "No es permutacion"


cyclesOfRec :: Ord a => [a] -> BiDictionary a a -> [[a]] -> [[a]]
cyclesOfRec [] dic sol = sol
cyclesOfRec (x:xs) (Bi a b) sol = otraVuelta
  where
    orbita = orbitOf x (Bi a b)
    dicBorrado = borrarDic orbita (Bi a b)
    otraVuelta 
      | sol == [[]] = cyclesOfRec (borrarKeysLista (D.keys a) orbita []) dicBorrado ([orbita])
      | otherwise = cyclesOfRec (borrarKeysLista (D.keys a) orbita []) dicBorrado (sol ++ [orbita])

borrarDic :: Ord a => [a] -> BiDictionary a a -> BiDictionary a a
borrarDic [] dic = dic
borrarDic (x:xs) dic = borrarDic xs (deleteByKey x dic)

borrarKeysLista :: Ord a =>[a] -> [a] -> [a] -> [a]
borrarKeysLista [] ys sol = sol
borrarKeysLista (x:xs) ys sol
  | elem x ys = borrarKeysLista xs ys sol
  | otherwise = borrarKeysLista xs ys (sol ++ [x])

-- |------------------------------------------------------------------------


instance (Show a, Show b) => Show (BiDictionary a b) where
  show (Bi dk dv)  = "BiDictionary(" ++ intercalate "," (aux (D.keysValues dk)) ++ ")"
                        ++ "(" ++ intercalate "," (aux (D.keysValues dv)) ++ ")"
   where
    aux kvs  = map (\(k,v) -> show k ++ "->" ++ show v) kvs
