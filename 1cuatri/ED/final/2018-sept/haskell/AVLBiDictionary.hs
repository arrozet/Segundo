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
isEmpty (Bi dk dv) = D.isEmpty dk && D.isEmpty dv

size :: (Ord a, Ord b) => BiDictionary a b -> Int
size (Bi dk dv) = if (D.size dk == D.size dv) then D.size dk else error "size: both dictionaries doesn't have the same size"

-- | Exercise b. insert

insert :: (Ord a, Ord b) => a -> b -> BiDictionary a b -> BiDictionary a b
insert k v (Bi dk dv) 
   | elem k (D.keys dk)                         = Bi (D.insert k v dk_delete) (D.insert v k dv_delete)
   | elem v (D.keys dv)                         = Bi (D.insert k v dk_delete2) (D.insert v k dv_delete2)
   | otherwise                                  = Bi (D.insert k v dk) (D.insert v k dv)
      where
        -- tengo key duplicada
        dv_delete = D.delete v' dv  -- para asegurar que es inyectivo
        v' = fromJust(D.valueOf k dk)
        dk_delete = D.delete k dk

        -- tengo valor duplicado
        dk_delete2 = D.delete k' dk  -- para asegurar que es inyectivo
        k' = fromJust(D.valueOf v dv)
        dv_delete2 = D.delete v dv
        
        

-- | Exercise c. valueOf

valueOf :: (Ord a, Ord b) => a -> BiDictionary a b -> Maybe b
valueOf k (Bi dk dv) = D.valueOf k dk

-- | Exercise d. keyOf

keyOf :: (Ord a, Ord b) => b -> BiDictionary a b -> Maybe a
keyOf v (Bi dk dv) = D.valueOf v dv

-- | Exercise e. deleteByKey

deleteByKey :: (Ord a, Ord b) => a -> BiDictionary a b -> BiDictionary a b
deleteByKey k bi@(Bi dk dv) = Bi (D.delete k dk) (D.delete v' dv) 
  where
    v' = fromJust(valueOf k bi)

-- | Exercise f. deleteByValue

deleteByValue :: (Ord a, Ord b) => b -> BiDictionary a b -> BiDictionary a b
deleteByValue v bi@(Bi dk dv)  = Bi (D.delete k' dk) (D.delete v dv) 
  where
    k' = fromJust(keyOf v bi)

-- | Exercise g. toBiDictionary

toBiDictionary :: (Ord a, Ord b) => D.Dictionary a b -> BiDictionary a b
toBiDictionary d = aux (D.keysValues d) empty
  where
    aux [] bi = bi
    aux ((k,v):xs) bi@(Bi dk dv)
      | D.isDefinedAt k dk || D.isDefinedAt v dv      = error "toBidictionary: no injective dictionary to start with"
      | otherwise                                     = aux xs (Bi (new_dk) (new_dv))
        where
          new_dk = D.insert k v dk
          new_dv = D.insert v k dv

-- | Exercise h. compose

compose :: (Ord a, Ord b, Ord c) => BiDictionary a b -> BiDictionary b c -> BiDictionary a c
compose bi1@(Bi dk1 dv1) bi2@(Bi dk2 dv2) = aux (D.keys dk1) bi1 bi2 empty
  where
    aux [] _ _ bi = bi
    aux (k1:xs) bi1@(Bi dk1 dv1) bi2@(Bi dk2 dv2) bi
      | v2 == Nothing   = aux xs bi1 bi2 bi
      | otherwise       = aux xs bi1 bi2 (insert k1 (fromJust v2) bi)
        where
          v1 = fromJust(valueOf k1 bi1)
          v2 = valueOf v1 bi2


-- | Exercise i. isPermutation

isPermutation :: Ord a => BiDictionary a a -> Bool
isPermutation bi1@(Bi dk1 dv1) = aux (D.keys dk1) bi1
  where
    aux [] bi1      = True
    aux (k1:xs) bi1@(Bi dk1 dv1)
      | D.isDefinedAt k1 dv1  = aux xs bi1
      | otherwise             = False



-- |------------------------------------------------------------------------


-- | Exercise j. orbitOf

orbitOf :: Ord a => a -> BiDictionary a a -> [a]
orbitOf = undefined

-- | Exercise k. cyclesOf

cyclesOf :: Ord a => BiDictionary a a -> [[a]]
cyclesOf = undefined

-- |------------------------------------------------------------------------


instance (Show a, Show b) => Show (BiDictionary a b) where
  show (Bi dk dv)  = "BiDictionary(" ++ intercalate "," (aux (D.keysValues dk)) ++ ")"
                        ++ "(" ++ intercalate "," (aux (D.keysValues dv)) ++ ")"
   where
    aux kvs  = map (\(k,v) -> show k ++ "->" ++ show v) kvs
