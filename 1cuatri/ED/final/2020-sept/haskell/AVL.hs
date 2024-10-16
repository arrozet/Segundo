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
emptyBin c = B c []

remainingCapacity :: Bin -> Capacity
remainingCapacity b@(B c w) = c

addObject :: Weight -> Bin -> Bin
addObject o b@(B c w)
  | o > remainingCapacity b   = error "addObject: cannot add object because weight exceeds remaining capacity"
  | otherwise                 = B (c-o) (w++[o])

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty                  = 0 --error "maxRemainingCapacity: cannot get capacity of Empty"
maxRemainingCapacity (Node b h c_max t1 t2) = c_max

height :: AVL -> Int
height Empty                                = 0 --error "height: cannot get height of Empty"
height (Node b h c_max t1 t2)               = h


 
nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight b h t1 t2 
  | c1 >= c2 && c1 >= c3  = Node b h c1 t1 t2
  | c2 >= c1 && c2 >= c3  = Node b h c2 t1 t2
  | otherwise             = Node b h c3 t1 t2
    where 
      c1 = remainingCapacity b
      c2 = maxRemainingCapacity t1
      c3 = maxRemainingCapacity t2


node :: Bin -> AVL -> AVL -> AVL
node b t1 t2
  | height t1 >= height t2 = nodeWithHeight b (1 + height t1) t1 t2
  | otherwise             = nodeWithHeight b (1 + height t2) t1 t2

rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft b t1 t2@(Node bd hd c_maxd t1d t2d) = node bd (node b t1 t1d) t2d

addNewBin :: Bin -> AVL -> AVL
addNewBin b Empty                     = node b Empty Empty -- llego al final de la espina derecha
addNewBin b t@(Node b1 h c_max t1 t2)
  | (height t2 - height t1) > 1 = rotateLeft b1 t1 (addNewBin b t2)
  | otherwise                   = node b1 t1 (addNewBin b t2)
 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst c w Empty                = addNewBin (addObject w (B c [])) Empty
addFirst c w t@(Node b1 h c_max t1 t2)
  | maxRemainingCapacity t < w    = addNewBin (addObject w (B c [])) t
  | maxRemainingCapacity t1 >= w  = node b1 (addFirst c w t1) t2
  | remainingCapacity b1 >= w     = node (addObject w b1) t1 t2
  | otherwise                     = node b1 t1 (addFirst c w t2)

addAll:: Capacity -> [Weight] -> AVL
addAll c []     = Empty
addAll c (w:ws) = aux c (w:ws) Empty
  where
    aux c [] t = t
    aux c (w:ws) t = aux c ws (addFirst c w t)

toList :: AVL -> [Bin]
toList Empty                      = []
toList t@(Node b1 h c_max t1 t2)  = toList t1 ++ [b1] ++ toList t2

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