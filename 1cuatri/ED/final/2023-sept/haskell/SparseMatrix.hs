-------------------------------------------------------------------------------
-- Student's name: ?????????????????????????????????????
-- Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
-- Student's group: ?
-- PC code: ???
--
-- Data Structures. Grado en Informatica. UMA.
-------------------------------------------------------------------------------

module SparseMatrix(SparseMatrix, set, get, add, transpose, fromList) where

import qualified DataStructures.Dictionary.AVLDictionary as D 
import Data.Maybe

data Index = Idx Int Int deriving (Eq, Ord, Show)

-- | = Exercise a - sparseMatrix
data SparseMatrix = SM Int Int (D.Dictionary Index Int) deriving Show 

sparseMatrix :: Int -> Int -> SparseMatrix
sparseMatrix f c = SM f c D.empty -- to complete

-- | = Exercise b - value
value :: SparseMatrix -> Index -> Int
value sm@(SM f1 c1 d) i@(Idx f2 c2)
    | D.isDefinedAt i d     = fromJust(D.valueOf i d)
    | otherwise             = 0

-- | = Exercise c - update
update :: SparseMatrix -> Index -> Int -> SparseMatrix
update sm@(SM f1 c1 d) i@(Idx f2 c2) v = SM f1 c1 (D.insert i v d)

-- | = Exercise d - index
index :: SparseMatrix -> Int -> Int -> Index
index sm@(SM f1 c1 d) f c
    | f >= f1 || f < 0 || c >= c1 || c < 0      = error "index: index is not valid"
    | otherwise                                 = Idx f c

-- | = Exercise e - set
set :: SparseMatrix -> Int -> Int -> Int -> SparseMatrix
set sm f c v = update sm (index sm f c) v

-- | = Exercise f - get
get :: SparseMatrix -> Int -> Int -> Int
get sm f c = value sm (index sm f c)

-- | = Exercise g - add
add :: SparseMatrix -> SparseMatrix -> SparseMatrix
add sm1 sm2@(SM f2 c2 d2) = aux sm1 sm2 (D.keys d2)
    where
        aux sm1 sm2 []          = sm1
        aux sm1 sm2 (k2:ks)     = aux sm1' sm2 (ks)
            where
                sm1' = set sm1 f' c' ((value sm1 k2) + (value sm2 k2))
                (Idx f' c') = k2

-- | = Exercise h - transpose
transpose :: SparseMatrix -> SparseMatrix
transpose sm@(SM f c d) = aux sm (D.keys d)
    where
        aux sm []       = sm
        aux sm (k:ks)   = aux sm'' ks
            where
                (SM f2 c2 d2) = set sm c1 f1 v
                sm'' = (SM f2 c2 (D.delete k d2))

                v = value sm k
                (Idx f1 c1) = k

-- | = Exercise i - fromList
-- Complexity of fromList: O(n*logn) -- to complete
fromList :: Int -> Int -> [Int] -> SparseMatrix
fromList f c list
    | (length list) `mod` 3 == 0    = aux (sparseMatrix f c) list
    | otherwise                     = error "fromList: list is not multiple of 3"
        where
            aux sm []               = sm
            aux sm (f1:c1:v1:xs)    = aux (set sm f1 c1 v1) xs




-- Examples

m1 :: SparseMatrix
m1 = fromList 5 5 [0,1,11, 3,0,30, 3,1,31]

m2 :: SparseMatrix
m2 = fromList 5 5 [0,1,-11, 3,0,30, 4,1,41]

m3 :: SparseMatrix 
m3 = add m1 m2

m4 :: SparseMatrix
m4 = transpose m3