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

data Index = Idx Int Int deriving (Eq, Ord, Show)

-- | = Exercise a - sparseMatrix
data SparseMatrix = SM Int Int (D.Dictionary Index Int) deriving Show 

sparseMatrix :: Int -> Int -> SparseMatrix
sparseMatrix  = undefined -- to complete

-- | = Exercise b - value
value :: SparseMatrix -> Index -> Int
value  = undefined -- to complete

-- | = Exercise c - update
update :: SparseMatrix -> Index -> Int -> SparseMatrix
update  = undefined -- to complete

-- | = Exercise d - index
index :: SparseMatrix -> Int -> Int -> Index
index  = undefined -- to complete

-- | = Exercise e - set
set :: SparseMatrix -> Int -> Int -> Int -> SparseMatrix
set  = undefined -- to complete

-- | = Exercise f - get
get :: SparseMatrix -> Int -> Int -> Int
get  = undefined -- to complete

-- | = Exercise g - add
add :: SparseMatrix -> SparseMatrix -> SparseMatrix
add  = undefined -- to complete

-- | = Exercise h - transpose
transpose :: SparseMatrix -> SparseMatrix
transpose  = undefined -- to complete

-- | = Exercise i - fromList
-- Complexity of fromList: ???? -- to complete
fromList :: Int -> Int -> [Int] -> SparseMatrix
fromList  = undefined -- to complete




-- Examples

m1 :: SparseMatrix
m1 = fromList 5 5 [0,1,11, 3,0,30, 3,1,31]

m2 :: SparseMatrix
m2 = fromList 5 5 [0,1,-11, 3,0,30, 4,1,41]

m3 :: SparseMatrix 
m3 = add m1 m2

m4 :: SparseMatrix
m4 = transpose m3