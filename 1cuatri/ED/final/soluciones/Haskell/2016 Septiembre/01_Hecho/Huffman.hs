-- | Data Structures
-- | September, 2016
-- |
-- | Student's name:
-- | Student's group:

module Huffman where

import qualified DataStructures.Dictionary.AVLDictionary as D
import qualified DataStructures.PriorityQueue.WBLeftistHeapPriorityQueue as PQ
import Data.List (nub)

-- | Exercise 1

weights :: Ord a => [a] -> D.Dictionary a Int
weights [] = D.empty
weights xs = weightRec xs D.empty

weightRec :: Ord a => [a] -> D.Dictionary a Int -> D.Dictionary a Int
weightRec [] dic = dic
weightRec (x:xs) dic
  | elem x (D.keys dic) = weightRec xs ( D.insert x (1+(num(D.valueOf x dic))) dic )
  | otherwise = weightRec xs (D.insert x 1 dic)

num :: Maybe a -> a 
num (Just x) = x
num Nothing = error "No hay valor"


{-

> weights "abracadabra"
AVLDictionary('a'->5,'b'->2,'c'->1,'d'->1,'r'->2)

> weights [1,2,9,2,0,1,6,1,5,5,8]
AVLDictionary(0->1,1->3,2->2,5->2,6->1,8->1,9->1)

> weights ""
AVLDictionary()

-}


-- Implementation of Huffman Trees
data WLeafTree a = WLeaf a Int  -- Stored value (type a) and weight (type Int)
                 | WNode (WLeafTree a) (WLeafTree a) Int -- Left child, right child and weight
                 deriving (Eq, Show)

weight :: WLeafTree a -> Int
weight (WLeaf _ n)   = n
weight (WNode _ _ n) = n

-- Define order on trees according to their weights
instance Eq a => Ord (WLeafTree a) where
  wlt <= wlt' =  weight wlt <= weight wlt'

-- Build a new tree by joining two existing trees
merge :: WLeafTree a -> WLeafTree a -> WLeafTree a
merge wlt1 wlt2 = WNode wlt1 wlt2 (weight wlt1 + weight wlt2)


-- | Exercise 2

-- 2.a
huffmanLeaves :: String -> PQ.PQueue (WLeafTree Char)
huffmanLeaves cad = huffmanLeavesRec cad (weights cad) PQ.empty

huffmanLeavesRec :: String -> D.Dictionary Char Int -> PQ.PQueue (WLeafTree Char) -> PQ.PQueue (WLeafTree Char)
huffmanLeavesRec [] dic pq = pq
huffmanLeavesRec (x:xs) dic pq
  | elem x xs = huffmanLeavesRec xs dic pq
  | otherwise = huffmanLeavesRec xs dic (PQ.enqueue (WLeaf x (num2(D.valueOf x dic)) ) pq)

num2 :: Maybe Int -> Int 
num2 (Just x) = x
num2 Nothing = error "No hay valor"

{-

> huffmanLeaves "abracadabra"
WBLeftistHeapPriorityQueue(WLeaf 'c' 1,WLeaf 'd' 1,WLeaf 'b' 2,WLeaf 'r' 2,WLeaf 'a' 5)

-}

-- 2.b
huffmanTree :: String -> WLeafTree Char
huffmanTree xs
  | length (cadList) < 2 = error "Cadena con menos de dos simbolos"
  | otherwise = huffmanTreeRec cola
    where
      cad = weights xs
      cadList = D.keys cad
      cola = huffmanLeaves(xs)

huffmanTreeRec :: PQ.PQueue (WLeafTree Char) -> WLeafTree Char
huffmanTreeRec cola
  | PQ.isEmpty (PQ.dequeue cola) = elem1
  | otherwise = huffmanTreeRec colaNueva
   where
     elem1 = PQ.first cola
     colaSin1 = PQ.dequeue cola
     elem2 = PQ.first colaSin1
     colaSin2 = PQ.dequeue colaSin1
     merge1 = merge elem1 elem2
     colaNueva = PQ.enqueue merge1 colaSin2


{-

> printWLeafTree $ huffmanTree "abracadabra"
      11_______
      /        \
('a',5)        6_______________
              /                \
        ('r',2)          _______4
                        /       \
                       2        ('b',2)
                      / \
                ('c',1) ('d',1)

> printWLeafTree $ huffmanTree "abracadabra pata de cabra"
                         ______________________25_____________
                        /                                     \
         ______________10______                          ______15
        /                      \                        /       \
       4_______                6_______                6        ('a',9)
      /        \              /        \              / \
('d',2)        2        (' ',3)        3        ('b',3) ('r',3)
              / \                     / \
        ('e',1) ('p',1)         ('t',1) ('c',2)

> printWLeafTree $ huffmanTree "aaa"
*** Exception: huffmanTree: the string must have at least two different symbols

-}


-- | Exercise 3

-- 3.a
joinDics :: Ord a => D.Dictionary a b -> D.Dictionary a b -> D.Dictionary a b
joinDics dic1 dic2 
  | D.isEmpty dic1 = dic2
  | D.isEmpty dic2 = dic1
  | otherwise = joinDicsRec (D.keys dic1) (D.keys dic2) dic1 dic2 D.empty

joinDicsRec ::Ord a => [a] -> [a] -> D.Dictionary a b -> D.Dictionary a b -> D.Dictionary a b -> D.Dictionary a b
joinDicsRec [] [] dic1 dic2 sol = sol
joinDicsRec (x:xs) [] dic1 dic2 sol = joinDicsRec xs [] dic1 dic2 (D.insert x (num(D.valueOf x dic1)) sol)
joinDicsRec [] (y:ys) dic1 dic2 sol = joinDicsRec [] ys dic1 dic2 (D.insert y (num(D.valueOf y dic2)) sol)
joinDicsRec (x:xs) (y:ys) dic1 dic2 sol = joinDicsRec xs (y:ys) dic1 dic2 (D.insert x (num(D.valueOf x dic1)) sol)

{-

> joinDics (D.insert 'a' 1 $ D.insert 'c' 3 $ D.empty) D.empty
AVLDictionary('a'->1,'c'->3)

> joinDics (D.insert 'a' 1 $ D.insert 'c' 3 $ D.empty) (D.insert 'b' 2 $ D.insert 'd' 4 $ D.insert 'e' 5 $ D.empty)
AVLDictionary('a'->1,'b'->2,'c'->3,'d'->4,'e'->5)

-}

-- 3.b
prefixWith :: Ord a => b -> D.Dictionary a [b] -> D.Dictionary a [b]
prefixWith x dic = prefixWithRec x (D.keys dic) dic D.empty

prefixWithRec :: Ord a => b -> [a] -> D.Dictionary a [b] -> D.Dictionary a [b] -> D.Dictionary a [b]
prefixWithRec x [] dic sol = sol
prefixWithRec x (y:ys) dic sol = prefixWithRec x ys dic (D.insert y (x:(num(D.valueOf y dic))) sol) 

{-

> prefixWith 0 (D.insert 'a' [0,0,1] $ D.insert 'b' [1,0,0] $ D.empty)
AVLDictionary('a'->[0,0,0,1],'b'->[0,1,0,0])

> prefixWith 'h' (D.insert 1 "asta" $ D.insert 2 "echo" $ D.empty)
AVLDictionary(1->"hasta",2->"hecho")

-}

-- 3.c



huffmanCode :: WLeafTree Char -> D.Dictionary Char [Integer]
huffmanCode wleaf = huffmanCodeRec wleaf D.empty []


huffmanCodeRec :: WLeafTree Char -> D.Dictionary Char [Integer] -> [Integer] -> D.Dictionary Char [Integer]
huffmanCodeRec (WLeaf x num) sol xs = (D.insert x xs sol)
huffmanCodeRec (WNode izq der num) sol [] = joinDics (huffmanCodeRec izq sol [0]) (huffmanCodeRec der sol [1])
huffmanCodeRec (WNode izq der num) sol xs = joinDics (huffmanCodeRec izq sol (xs ++ [0])) (huffmanCodeRec der sol (xs ++ [1]))


{-

> huffmanCode (huffmanTree "abracadabra")
AVLDictionary('a'->[0],'b'->[1,1,1],'c'->[1,1,0,0],'d'->[1,1,0,1],'r'->[1,0])

-}

-- ONLY for students not taking continuous assessment

-- | Exercise 4

encode :: String -> D.Dictionary Char [Integer] -> [Integer]
encode [] dic = []
encode ys dic = encodeRec ys [] dic


encodeRec :: String -> [Integer] -> D.Dictionary Char [Integer] -> [Integer]
encodeRec [] xs dic = xs
encodeRec (y:ys) xs dic = xs ++ (encodeRec ys (num(D.valueOf y dic)) dic)

{-

> encode "abracadabra" (huffmanCode (huffmanTree "abracadabra"))
[0,1,1,1,1,0,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1,0,0]

-}

-- | Exercise 5



--data WLeafTree a = WLeaf a Int  -- Stored value (type a) and weight (type Int)
--                 | WNode (WLeafTree a) (WLeafTree a) Int -- Left child, right child and weight
--                 deriving (Eq, Show)


-- 5.a
takeSymbol :: [Integer] -> WLeafTree Char -> (Char, [Integer])
takeSymbol xs arbol = takeSymbolRec xs [] codigo keys
  where
    codigo = huffmanCode arbol
    keys = D.keys codigo


takeSymbolRec :: [Integer] -> [Integer] -> D.Dictionary Char [Integer] -> [Char] -> (Char, [Integer])
takeSymbolRec [] listaC dic (k:ks) = error "codigo no encontrado"
takeSymbolRec (x:xs) [] dic keys = takeSymbolRec (xs) [x] dic keys
takeSymbolRec (x:xs) listaC dic (k:ks)
  | elem listaC (D.values dic) = ( buscarLetra listaC dic (k:ks) , listaC )
  | otherwise = takeSymbolRec (xs) (x:listaC) dic (k:ks)


buscarLetra :: [Integer] -> D.Dictionary Char [Integer] -> [Char] -> Char
buscarLetra lista dic (x:xs)
  | lista == (num3(D.valueOf x dic)) = x
  | otherwise = buscarLetra lista dic xs


num3 :: Maybe [Integer] -> [Integer]
num3 (Just x) = x
num3 Nothing = error "No hay valor"

{-

> takeSymbol [0,1,1,1,1,0,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1,0,0] (huffmanTree "abracadabra")
('a',[1,1,1,1,0,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1,0,0])

> takeSymbol [1,1,1,1,0,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1,0,0] (huffmanTree "abracadabra")
('b',[1,0,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1,0,0])

-}

-- 5.b
decode :: [Integer] -> WLeafTree Char -> String
decode [] arbol = []
decode lista arbol = letra : (decode resto arbol)
  where 
    (letra, resto) = takeSymbol lista arbol

{-

> decode [0,1,1,1,1,0,0,1,1,0,0,0,1,1,0,1,0,1,1,1,1,0,0] (huffmanTree "abracadabra")
"abracadabra"

-}

-------------------------------------------------------------------------------
-- Pretty Printing a WLeafTree
-- (adapted from http://stackoverflow.com/questions/1733311/pretty-print-a-tree)
-------------------------------------------------------------------------------

printWLeafTree :: (Show a) => WLeafTree a -> IO ()
printWLeafTree t = putStrLn (unlines xss)
 where
   (xss,_,_,_) = pprint t

pprint :: Show a => WLeafTree a -> ([String], Int, Int, Int)
pprint (WLeaf x we)             =  ([s], ls, 0, ls-1)
  where
    s = show (x,we)
    ls = length s
pprint (WNode lt rt we)         =  (resultLines, w, lw'-swl, totLW+1+swr)
  where
    nSpaces n = replicate n ' '
    nBars n = replicate n '_'
    -- compute info for string of this node's data
    s = show we
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
