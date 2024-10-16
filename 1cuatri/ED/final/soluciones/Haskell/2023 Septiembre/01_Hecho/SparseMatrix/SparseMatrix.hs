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
sparseMatrix i j = SM i j (D.empty) 

-- | = Exercise b - value
value :: SparseMatrix -> Index -> Int
value (SM tami tamj dict) indice
    | elem indice (D.keys dict) = (num(D.valueOf indice dict))
    | otherwise = 0

num :: Maybe Int -> Int
num (Just x) = x

-- | = Exercise c - update
update :: SparseMatrix -> Index -> Int -> SparseMatrix
update (SM tami tamj dict) indice x = SM tami tamj (D.insert indice x dict)

-- | = Exercise d - index
index :: SparseMatrix -> Int -> Int -> Index
index (SM tami tamj dict) i j
    | tami < i || tamj < j || i < 0 || j < 0= error "indices fuera de rango"
    | otherwise = (Idx i j)

-- | = Exercise e - set
set :: SparseMatrix -> Int -> Int -> Int -> SparseMatrix
set (SM tami tamj dict) i j x
    | tami < i || tamj < j || i < 0 || j < 0 = error "indices fuera de rango"
    | otherwise = update (SM tami tamj dict) (Idx i j) x

-- | = Exercise f - get
get :: SparseMatrix -> Int -> Int -> Int
get (SM tami tamj dict) i j 
    | tami < i || tamj < j || i < 0 || j < 0 = error "indices fuera de rango"
    | otherwise = value (SM tami tamj dict) (Idx i j)

-- | = Exercise g - add
add :: SparseMatrix -> SparseMatrix -> SparseMatrix
add (SM tami1 tamj1 dict1) (SM tami2 tamj2 dict2)
    | tami1 >= tami2 && tamj1 >= tamj2 = (SM tami1 tamj1 (addRec dict1 dict2 (D.keys dict1) (D.keys dict2) (D.empty)))
    | tami2 >= tami1 && tamj2 >= tamj1 = (SM tami2 tamj2 (addRec dict1 dict2 (D.keys dict1) (D.keys dict2) (D.empty)))
    | tami1 >= tami2 && tamj2 >= tamj1 = (SM tami1 tamj2 (addRec dict1 dict2 (D.keys dict1) (D.keys dict2) (D.empty)))
    | otherwise = (SM tami2 tamj1 (addRec dict1 dict2 (D.keys dict1) (D.keys dict2) (D.empty)))

addRec :: D.Dictionary Index Int -> D.Dictionary Index Int -> [Index] -> [Index] -> D.Dictionary Index Int -> D.Dictionary Index Int
addRec dict1 dict2 [] [] sol = sol
addRec dict1 dict2 (x:xs) [] sol = D.insert x (num(D.valueOf x dict1)) (addRec dict1 dict2 xs [] sol)
addRec dict1 dict2 [] (y:ys) sol = D.insert y (num(D.valueOf y dict2)) (addRec dict1 dict2 [] ys sol)
addRec dict1 dict2 (x:xs) (y:ys) sol = D.insert x (num(D.valueOf x dict1)) (addRec dict1 dict2 xs (y:ys) sol)


-- | = Exercise h - transpose
transpose :: SparseMatrix -> SparseMatrix
transpose (SM tami tamj dict) = (SM tamj tami (transposeRec dict (D.keys dict) (D.empty) ) )

transposeRec :: D.Dictionary Index Int -> [Index] -> D.Dictionary Index Int -> D.Dictionary Index Int
transposeRec dict [] sol = sol
transposeRec dict ((Idx i j):xs) sol = D.insert (Idx j i) (num(D.valueOf (Idx i j) dict)) (transposeRec (D.delete (Idx i j) dict) xs (D.delete (Idx i j) dict))


-- | = Exercise i - fromList
-- Complexity of fromList: ???? -- to complete
fromList :: Int -> Int -> [Int] -> SparseMatrix
fromList i j xs
    | (mod (length xs) 3) == 0 = (SM i j (fromListRec xs (D.empty)))
    | otherwise = error "La lista no es multiplo de 3"

fromListRec :: [Int] -> D.Dictionary Index Int -> D.Dictionary Index Int
fromListRec [] sol = sol
fromListRec (x:y:z:xs) sol = D.insert (Idx x y) z (fromListRec xs sol)




-- Examples

m1 :: SparseMatrix
m1 = fromList 5 5 [0,1,11, 3,0,30, 3,1,31]

m2 :: SparseMatrix
m2 = fromList 5 5 [0,1,-11, 3,0,30, 4,1,41]

m3 :: SparseMatrix 
m3 = add m1 m2

m4 :: SparseMatrix
m4 = transpose m3

i1 = (Idx 0 2 )
i2 = (Idx 1 0 )
i3 = (Idx 5 3 )
i4 = (Idx 0 0 )

d1 = D.insert i1 10 D.empty
d2 = D.insert i2 20 d1

d3 = D.insert i3 5 D.empty
d4 = D.insert i4 30 d3

s = (SM 2 3 d2) 
s2 = (SM 5 5 d4) 

