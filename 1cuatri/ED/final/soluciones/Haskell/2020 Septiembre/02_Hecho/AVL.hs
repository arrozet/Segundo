{------------------------------------------------------------------------------
 - Student's name:
 -
 - Student's group:
 -----------------------------------------------------------------------------}

module AVL 
  ( 
    Weight
  , Capacity
  , AVL (..)
  , Bin
  , emptyBin
  , remainingCapacity
  , addObject
  , maxRemainingCapacity
  , height
  , nodeWithHeight
  , node
  , rotateLeft
  , addNewBin
  , addFirst
  , addAll
  , toList
  , linearBinPacking
  , seqToList
  , addAllFold
  ) where

type Capacity = Int
type Weight= Int

data Bin = B Capacity [Weight] 

data AVL = Empty | Node Bin Int Capacity AVL AVL deriving Show


emptyBin :: Capacity -> Bin
emptyBin tam = (B tam [])

remainingCapacity :: Bin -> Capacity
remainingCapacity (B tam cap) = tam

addObject :: Weight -> Bin -> Bin
addObject o (B tam cap) 
  | o > tam = error "El cubo no cabe"
  | otherwise = (B (tam-o) (cap++[o]))

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node bin altura cap izq der) = cap

height :: AVL -> Int
height Empty = 0
height (Node bin altura cap izq der) = altura

 
nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight (B cap obj) alt Empty Empty = (Node (B cap obj) alt cap Empty Empty) 
nodeWithHeight (B cap obj) alt Empty (Node bind altd capd izqd derd)
  | capd > cap = (Node (B cap obj) alt capd Empty (Node bind altd capd izqd derd)) 
  | otherwise = (Node (B cap obj) alt cap Empty (Node bind altd capd izqd derd)) 
nodeWithHeight (B cap obj) alt (Node bini alti capi izqi deri) Empty
  | capi > cap = (Node (B cap obj) alt capi (Node bini alti capi izqi deri) Empty) 
  | otherwise = (Node (B cap obj) alt cap (Node bini alti capi izqi deri) Empty) 
nodeWithHeight (B cap obj) alt (Node bini alti capi izqi deri) (Node bind altd capd izqd derd)
  | capi >= capd && capi >= cap = (Node (B cap obj) alt capi (Node bini alti capi izqi deri) (Node bind altd capd izqd derd)) 
  | capd >= capi && capd >= cap = (Node (B cap obj) alt capd (Node bini alti capi izqi deri) (Node bind altd capd izqd derd)) 
  | otherwise = (Node (B cap obj) alt capi (Node bini alti cap izqi deri) (Node bind altd capd izqd derd)) 


node :: Bin -> AVL -> AVL -> AVL
node (B cap obj) Empty Empty = (Node (B cap obj) 1 cap Empty Empty)
node (B cap obj) Empty (Node bind altd capd izqd derd)
  | capd > cap = (Node (B cap obj) (altd+1) capd Empty (Node bind altd capd izqd derd)) 
  | otherwise = (Node (B cap obj) (altd+1) cap Empty (Node bind altd capd izqd derd)) 
node (B cap obj) (Node bini alti capi izqi deri) Empty
  | capi > cap = (Node (B cap obj) (alti+1) capi (Node bini alti capi izqi deri) Empty) 
  | otherwise = (Node (B cap obj) (alti+1) cap (Node bini alti capi izqi deri) Empty) 
node (B cap obj) (Node bini alti capi izqi deri) (Node bind altd capd izqd derd)
  | capi >= capd && capi >= cap = (Node (B cap obj) altura capi (Node bini alti capi izqi deri) (Node bind altd capd izqd derd)) 
  | capd >= capi && capd >= cap = (Node (B cap obj) altura capd (Node bini alti capi izqi deri) (Node bind altd capd izqd derd)) 
  | otherwise = (Node (B cap obj) altura capi (Node bini alti cap izqi deri) (Node bind altd capd izqd derd)) 
    where
      altura = maximaAltura alti altd

maximaAltura :: Int -> Int -> Int
maximaAltura x y
  | x > y = x
  | otherwise = y



rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft (B cap obj) izq (Node bind altd capd izqd derd) = x
  where
    c = node (B cap obj) izq izqd
    x = nodeWithHeight bind altd c derd


addNewBin :: Bin -> AVL -> AVL
addNewBin (B cap obj) Empty = (Node (B cap obj) 1 cap Empty Empty)
addNewBin (B cap obj) (Node (B cap1 obj1) alt capres izq der)
  | (height der) - (height izq)  > 1 = rotateLeft (B cap1 obj1) izq (addNewBin (B cap obj) der)
  | otherwise = (Node (B cap1 obj1) alt capres izq (addNewBin (B cap obj) der))

 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst w x Empty = addNewBin (B (w-x) [x]) Empty
addFirst w x (Node bin alt cap izq der)
  | w < x = addNewBin (B (w-x) [x]) (Node bin alt cap izq der)
  | maxRemainingCapacity izq >= x = (Node bin alt cap (addFirst w x izq) der)
  | cap >= x = (Node (addObject x bin ) alt (cap-x) izq der)
  | otherwise = (Node bin alt cap izq (addFirst w x der))


addAll:: Capacity -> [Weight] -> AVL
addAll w [] = Empty
addAll w xs = addAllRec w xs Empty 

addAllRec :: Capacity -> [Weight] -> AVL -> AVL
addAllRec w [] sol = sol
addAllRec w (x:xs) sol = addAllRec w xs (addFirst w x sol)



toList :: AVL -> [Bin]
toList Empty = []
toList (Node bin alt cap izq der) = toList izq ++ [bin] ++ toList der 

{-
	SOLO PARA ALUMNOS SIN EVALUACION CONTINUA
  ONLY FOR STUDENTS WITHOUT CONTINUOUS ASSESSMENT
 -}

data Sequence = SEmpty | SNode Bin Sequence deriving Show  

linearBinPacking:: Capacity -> [Weight] -> Sequence
linearBinPacking _ _ = undefined

seqToList:: Sequence -> [Bin]
seqToList _ = undefined

addAllFold:: [Weight] -> Capacity -> AVL 
addAllFold _ _ = undefined



{- No modificar. Do not edit -}

objects :: Bin -> [Weight]
objects (B _ os) = reverse os

  
instance Show Bin where
  show b@(B c os) = "Bin("++show c++","++show (objects b)++")"