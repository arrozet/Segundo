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
emptyBin c = (B c [])

remainingCapacity :: Bin -> Capacity
remainingCapacity (B c xs) = c

addObject :: Weight -> Bin -> Bin
addObject w (B c xs)
  | w <= c = (B (c-w) (w:xs))
  | otherwise = error "No cabe el objeto en el cubo"

maxRemainingCapacity :: AVL -> Capacity
maxRemainingCapacity Empty = 0
maxRemainingCapacity (Node (B c xs) altura capacidad izq der) = capacidad

height :: AVL -> Int
height Empty = 0
height (Node (B c xs) altura capacidad izq der) = altura


 
nodeWithHeight :: Bin -> Int -> AVL -> AVL -> AVL
nodeWithHeight (B c xs) altura Empty Empty = ( Node (B c xs) altura c Empty Empty )

nodeWithHeight (B c xs) altura Empty (Node (B cd xsd) alturad capacidadd izqd derd)
  | c >= capacidadd = ( Node (B c xs) altura c Empty (Node (B cd xsd) alturad capacidadd izqd derd) )
  | otherwise = ( Node (B c xs) altura capacidadd Empty (Node (B cd xsd) alturad capacidadd izqd derd) )

nodeWithHeight (B c xs) altura (Node (B ci xsi) alturai capacidadi izqi deri) Empty
  | c >= capacidadi = ( Node (B c xs) altura c (Node (B ci xsi) alturai capacidadi izqi deri) Empty )
  | otherwise = ( Node (B c xs) altura capacidadi (Node (B ci xsi) alturai capacidadi izqi deri) Empty )

nodeWithHeight (B c xs) altura (Node (B ci xsi) alturai capacidadi izqi deri) (Node (B cd xsd) alturad capacidadd izqd derd)
  | (c >= capacidadi) && (c >= capacidadd) = ( Node (B c xs) altura c (Node (B ci xsi) alturai capacidadi izqi deri) (Node (B cd xsd) alturad capacidadd izqd derd) )
  | (capacidadi >= c) && (capacidadi >= capacidadd) = ( Node (B c xs) altura capacidadi (Node (B ci xsi) alturai capacidadi izqi deri) (Node (B cd xsd) alturad capacidadd izqd derd) )
  | otherwise = (Node (B c xs) altura capacidadi (Node (B ci xsi) alturai capacidadd izqi deri) (Node (B cd xsd) alturad capacidadd izqd derd) )


node :: Bin -> AVL -> AVL -> AVL
node (B c xs) Empty Empty = nodeWithHeight (B c xs) 1 Empty Empty
node (B c xs) Empty (Node (B cd xsd) alturad capacidadd izqd derd) = nodeWithHeight (B c xs) (alturad+1)  Empty (Node (B cd xsd) alturad capacidadd izqd derd)
node (B c xs) (Node (B ci xsi) alturai capacidadi izqi deri) Empty = nodeWithHeight (B c xs) (alturai+1) (Node (B ci xsi) alturai capacidadi izqi deri) Empty
node (B c xs) (Node (B ci xsi) alturai capacidadi izqi deri) (Node (B cd xsd) alturad capacidadd izqd derd)
  | alturai >= alturad = nodeWithHeight (B c xs) (alturai+1) (Node (B ci xsi) alturai capacidadi izqi deri) (Node (B cd xsd) alturad capacidadd izqd derd)
  | otherwise = nodeWithHeight (B c xs) (alturad+1) (Node (B ci xsi) alturai capacidadi izqi deri) (Node (B cd xsd) alturad capacidadd izqd derd)


rotateLeft :: Bin -> AVL -> AVL -> AVL
rotateLeft (B c xs) izq (Node (B cd xsd) alturad capacidadd izqd derd) = root
  where
    nizq = node (B c xs) izq izqd
    root = node (B cd xsd) nizq derd 


addNewBin :: Bin -> AVL -> AVL
addNewBin (B c xs) Empty = (Node (B c xs) 1 c Empty Empty)
addNewBin (B c xs) (Node (B cn xsn) altura capacidad izq der) 
  | (height der) - (height izq) > 1 = rotateLeft (B c xs) izq (addNewBin (B c xs) der)
  | otherwise = (Node (B cn xsn) altura capacidad izq (addNewBin (B c xs) der))

 
addFirst :: Capacity -> Weight -> AVL -> AVL
addFirst capacidadrest pesoagregar Empty = addNewBin (B (capacidadrest-pesoagregar) [pesoagregar]) Empty
addFirst capacidadrest pesoagregar (Node (B c xs) altura capacidad izq der)
  | capacidadrest < pesoagregar = addNewBin (B (capacidadrest-pesoagregar) [pesoagregar]) (Node (B c xs) altura capacidad izq der)
  | maxRemainingCapacity izq >= pesoagregar =  (Node (B c xs) altura capacidad (addFirst capacidadrest pesoagregar izq) der)
  | maxRemainingCapacity (Node (B c xs) altura capacidad izq der) >= pesoagregar = (Node ( addObject (pesoagregar) (B c xs) ) altura (capacidad-pesoagregar) izq der)
  | otherwise = (Node (B c xs) altura capacidad izq (addFirst capacidadrest pesoagregar der) )

addAll:: Capacity -> [Weight] -> AVL
addAll cap (x:xs) = addAllRec cap (x:xs) Empty

addAllRec :: Capacity -> [Weight] -> AVL -> AVL
addAllRec cap [] avl = avl
addAllRec cap (x:xs) avl = addAllRec cap xs (addFirst cap x avl)  

toList :: AVL -> [Bin]
toList Empty = []
toList (Node bin a c izq der) = toList izq ++ [bin] ++ toList der

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