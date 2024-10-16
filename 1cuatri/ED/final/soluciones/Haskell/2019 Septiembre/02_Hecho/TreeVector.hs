-------------------------------------------------------------------------------
-- Student's name:
-- Student's group:
--
-- Data Structures. September 2019. Grado en Informática. UMA.
-------------------------------------------------------------------------------

module TreeVector( Vector
                 , vector
                 , size
                 , get
                 , set
                 , mapVector
                 , toList
                 , fromList
                 , pretty
                 ) where

import           Test.QuickCheck hiding (vector)

data Vector a = V Int (Tree a) -- size, binary tree
             deriving Show

data Tree a = Leaf a
              | Node (Tree a) (Tree a)
               deriving Show

-- | Exercise a. vector

{- |

>>> pretty $ vector 2 'a'
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'a' 'a'

>>> pretty $ vector 0 7
7

>>> pretty $ vector (-1) 5
*** Exception: vector: negative exponent
...

-}

vector :: Int -> a -> Vector a
vector n x
  | n < 0 = error "El tamaño no puede ser negativo"
  | otherwise = V (2^n) (crearArbol n x)

crearArbol :: Int -> a -> Tree a
crearArbol 0 x = Leaf x
crearArbol n x = Node (crearArbol (n-1) x) (crearArbol (n-1) x)

-- | Exercise b. size

{- |

>>> size $ vector 3 'a'
8

>>> size $ vector 0 5
1

-}

size :: Vector a -> Int
size (V n arbol) = n

-- | Exercise c. get

{- |

>>> get 0 (vector 3 'a')
'a'

>>> get 5 (vector 3 'a')
'a'

>>> get 7 (vector 3 'a')
'a'

>>> get 8 (vector 3 'a')
*** Exception: get: index out of bounds
...

-}

get :: Int -> Vector a -> a
get i (V n arbol)
  | i > (n-1) || i < 0= error "index out of bounds"
  | otherwise = getRec i arbol

getRec :: Int -> Tree a -> a 
getRec i (Leaf x) = x
getRec i (Node izq der)
  | mod i 2 == 0 = getRec (div i 2) izq
  | otherwise = getRec (div i 2) der

-- | Exercise d. set

{- |

>>> pretty (set 0 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'b' 'a' 'a' 'a'

>>> pretty (set 1 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'b' 'a'

>>> pretty (set 3 'b' (vector 2 'a'))
    _______
   /       \
   _       _
  / \     / \
'a' 'a' 'a' 'b'

> pretty $ foldr (\ (i, x) v -> set i x v) (vector 3 '*') (zip [0..] "function")
        _______________
       /               \
    _______         _______
   /       \       /       \
   _       _       _       _
  / \     / \     / \     / \
'f' 't' 'n' 'o' 'u' 'i' 'c' 'n'

>>> pretty (set 4 'b' (vector 2 'a'))
*** Exception: set: index out of bounds
...

-}

set :: Int -> a -> Vector a -> Vector a
set i x (V tam arbol)
  | i < 0 || i > (tam-1) = error "index out of bounds"
  | otherwise = (V tam (setArbol i x arbol))

setArbol :: Int -> a -> Tree a -> Tree a
setArbol i x (Leaf y) = Leaf x
setArbol i x (Node izq der)
  | mod i 2 == 0 = Node (setArbol (div i 2) x izq) (der)
  | otherwise = Node (izq) (setArbol (div i 2) x der)

-- | Exercise e. mapVector

{- |

>>> pretty $ mapVector (*2) (vector 2 3)
  ___
 /   \
 _   _
/ \ / \
6 6 6 6

>>> pretty $ mapVector (\ x -> 2*x + 5) (foldr (\ (i, x) v -> set i x v) (vector 3 0) (zip [0..] [4, 0, 9, 2, 0, 1, 7, -3]))
     _________
    /         \
   ____      ___
  /    \    /   \
  _    _    _   _
 / \  / \  / \ / \
13 5 23 19 5 7 9 -1

-}

mapVector :: (a -> a) -> Vector a -> Vector a
mapVector f (V tam arbol) = (V tam (mapVectorRec f tam arbol))


mapVectorRec :: (a -> a) -> Int -> Tree a -> Tree a
mapVectorRec f 1 (Leaf x) = Leaf (f x)
mapVectorRec f tam (Node izq der) = Node (mapVectorRec f (div tam 2) izq) (mapVectorRec f (div tam 2) der)



-- | Exercise f. intercalate

{- |

>>> intercalate [0,1,2,3] [4,5,6,7]
[0,4,1,5,2,6,3,7]

>>> intercalate "haskell" "java"
"hjaasvka"

>>> intercalate [] "java"
""

-}

intercalate :: [a] -> [a] -> [a]
intercalate [] [] = []
intercalate xs [] = []
intercalate [] ys = []
intercalate (x:xs) (y:ys) = x:y:(intercalate xs ys)


-- | Exercise g. toList

{- |

>>> toList (vector 3 'a')
"aaaaaaaa"

>>> toList $ foldr (\ (i, x) v -> set i x v) (vector 3 '*') (zip [0..] "function")
"function"

-}

toList :: Vector a -> [a]
toList (V tam arbol) = toListRec arbol

toListRec :: Tree a -> [a]
toListRec (Leaf x) = [x]
toListRec (Node izq der) = intercalate (toListRec izq) (toListRec der)


-- | Exercise h. Complexity

-- Fill the table below:
--
--    operation        complexity class
--    ---------------------------------
--    vector                      1
--    ---------------------------------
--    size                        1
--    ---------------------------------
--    get                         log n
--    ---------------------------------
--    set                         log n
--    ---------------------------------
--    mapVector                   log n
--    ---------------------------------
--    intercalate                 n
--    ---------------------------------
--    toList                      n log n
--    ---------------------------------


-- | Solo para alumnos sin evaluación continua
----------------------------------------------------------------------

-- | Exercise i. isPowerOfTwo

{- |

>>> isPowerOfTwo 16
True

>>> isPowerOfTwo 17
False

-}

isPowerOfTwo :: Int -> Bool
isPowerOfTwo x
  | x < 2 = False
  | x == 2 = True
  | mod x 2 == 0 = isPowerOfTwo (div x 2)
  | otherwise = False

-- | Exercise j. fromList

{- |

>>> pretty $ fromList [0..7]
    _______
   /       \
  ___     ___
 /   \   /   \
 _   _   _   _
/ \ / \ / \ / \
0 4 2 6 1 5 3 7

>>> pretty $ fromList "property"
        _______________
       /               \
    _______         _______
   /       \       /       \
   _       _       _       _
  / \     / \     / \     / \
'p' 'e' 'o' 't' 'r' 'r' 'p' 'y'

-}

fromList :: [a] -> Vector a
fromList xs
  | isPowerOfTwo (length xs) = V ((length xs)^2) (fromListRec (internum xs))
  | otherwise = error "La lista no es potencia de 2"

internum :: [a] -> [a]
internum xs = intercalate izq der
  where
    (izq,der) = splitAt (div (length xs) 2) xs


fromListRec :: [a] -> Tree a 
fromListRec [x] = (Leaf x)
fromListRec xs = Node (fromListRec izq) (fromListRec der)
  where
    (izq,der) = splitAt (div (length xs) 2) xs


-------------------------------------------------------------------------------
-- QuickCheck Properties to check your implementation
-------------------------------------------------------------------------------

{-

> checkAll
+++ OK, passed 100 tests.
+++ OK, passed 100 tests.

> checkAll
*** Gave up! Passed only 99 tests.
+++ OK, passed 100 tests.

-}

-- This instace is used by QuickCheck to generate random arrays
instance (Arbitrary a) => Arbitrary (Vector a) where
    arbitrary  = do
      exp <- arbitrary
      v <- arbitrary
      return ((vector :: Int -> a -> Vector a) (abs exp) v)

prop_vector_OK :: Eq a => Int -> a -> Property
prop_vector_OK n v =
  n >= 0 && n < 10 ==> size a == 2^n &&
                       and [ get i a == v | i <- [0..size a-1] ]
  where a = vector n v

prop_set_OK :: Eq a => Int -> a -> Vector a -> Property
prop_set_OK i v a =
  i >= 0 && i < size a ==> get i (set i v a) == v

type T = Char

checkAll :: IO ()
checkAll =
  do
    quickCheck (prop_vector_OK :: Int -> T -> Property)
    quickCheck (prop_set_OK :: Int -> T -> Vector T -> Property)

-------------------------------------------------------------------------------
-- Pretty Printing a Vector
-- (adapted from http://stackoverflow.com/questions/1733311/pretty-print-a-tree)
-------------------------------------------------------------------------------

pretty :: (Show a) => Vector a -> IO ()
pretty (V _ t)  = putStr (unlines xss)
 where
   (xss,_,_,_) = pprint t

pprint :: Show t => Tree t -> ([String], Int, Int, Int)
pprint (Leaf x)              = ([s], ls, 0, ls-1)
  where
    s = show x
    ls = length s
pprint (Node lt rt)         =  (resultLines, w, lw'-swl, totLW+1+swr)
  where
    nSpaces n = replicate n ' '
    nBars n = replicate n '_'
    -- compute info for string of this node's data
    s = ""
    sw = length s
    swl = div sw 2
    swr = div (sw-1) 2
    (lp,lw,_,lc) = pprint lt
    (rp,rw,rc,_) = pprint rt
    -- recurse
    (lw',lb) = if lw==0 then (1," ") else (lw,"/")
    (rw',rb) = if rw==0 then (1," ") else (rw,"\\")
    -- compute full width of this tree
    totLW = maximum [lw', swl,  1]
    totRW = maximum [rw', swr, 1]
    w = totLW + 1 + totRW
{-
A suggestive example:
     dddd | d | dddd__
        / |   |       \
      lll |   |       rr
          |   |      ...
          |   | rrrrrrrrrrr
     ----       ----           swl, swr (left/right string width (of this node) before any padding)
      ---       -----------    lw, rw   (left/right width (of subtree) before any padding)
     ----                      totLW
                -----------    totRW
     ----   -   -----------    w (total width)
-}
    -- get right column info that accounts for left side
    rc2 = totLW + 1 + rc
    -- make left and right tree same height
    llp = length lp
    lrp = length rp
    lp' = if llp < lrp then lp ++ replicate (lrp - llp) "" else lp
    rp' = if lrp < llp then rp ++ replicate (llp - lrp) "" else rp
    -- widen left and right trees if necessary (in case parent node is wider, and also to fix the 'added height')
    lp'' = map (\s -> if length s < totLW then nSpaces (totLW - length s) ++ s else s) lp'
    rp'' = map (\s -> if length s < totRW then s ++ nSpaces (totRW - length s) else s) rp'
    -- first part of line1
    line1 = if swl < lw' - lc - 1 then
                nSpaces (lc + 1) ++ nBars (lw' - lc - swl) ++ s
            else
                nSpaces (totLW - swl) ++ s
    -- line1 right bars
    lline1 = length line1
    line1' = if rc2 > lline1 then
                line1 ++ nBars (rc2 - lline1)
             else
                line1
    -- line1 right padding
    line1'' = line1' ++ nSpaces (w - length line1')
    -- first part of line2
    line2 = nSpaces (totLW - lw' + lc) ++ lb
    -- pad rest of left half
    line2' = line2 ++ nSpaces (totLW - length line2)
    -- add right content
    line2'' = line2' ++ " " ++ nSpaces rc ++ rb
    -- add right padding
    line2''' = line2'' ++ nSpaces (w - length line2'')
    resultLines = line1'' : line2''' : zipWith (\lt rt -> lt ++ " " ++ rt) lp'' rp''
