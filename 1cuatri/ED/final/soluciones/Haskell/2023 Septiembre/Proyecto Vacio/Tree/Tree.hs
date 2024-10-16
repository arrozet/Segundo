-------------------------------------------------------------------------------
-- Student's name: ?????????????????????????????????????
-- Identity number (DNI if Spanish/passport if Erasmus): ???????????????????
-- Student's group: ?
-- PC code: ???
--
-- Data Structures. Grado en Informatica. UMA.
-------------------------------------------------------------------------------

module Tree where

data Tree a = Empty
            | Node a (Tree a) (Tree a)
            deriving Show

-- rotates the tree to the right
rotateRight :: Tree a -> Tree a
rotateRight (Node x (Node y a b) c) = Node y a (Node x b c)
rotateRight t                       = t

-- rotates the tree to the left
rotateLeft :: Tree a -> Tree a
rotateLeft (Node x a (Node y b c)) = Node y (Node x a b) c
rotateLeft t                       = t

-- DO NOT MODIFY CODE ABOVE

-- | = Exercise j - makeRoot

makeRoot :: Ord a => a -> Tree a -> Tree a
makeRoot = undefined

-- DO NOT MODIFY CODE BELOW

-- Examples to test your code

tree1 :: Tree Int
tree1 = mkTree [30, 20, 18, 25, 60, 50, 70]

{-

> pretty tree1
    30_
   /   \
 20    60
 / \   / \
18 25 50 70

> pretty (makeRoot 25 tree1)
   25
   / \
 20  30_
 /      \
18      60
        / \
       50 70

> pretty (makeRoot 60 tree1)
       60
      /  \
    30   70
   /  \
 20   50
 / \
18 25

> pretty (makeRoot 18 tree1)
18___
     \
     30_
    /   \
  20    60
    \   / \
    25 50 70

-}

-- code to build a binary search tree

insert :: Ord a => a -> Tree a -> Tree a
insert x Empty = Node x Empty Empty
insert x (Node y l r)
  | x == y = Node x l r
  | x < y = Node y (insert x l) r
  | otherwise = Node y l (insert x r)

mkTree :: Ord a => [a] -> Tree a
mkTree = foldl (flip insert) Empty

-- code to pretty print a binary search tree

-------------------------------------------------------------------------------
-- Pretty Printing a ABST
-- (adapted from http://stackoverflow.com/questions/1733311/pretty-print-a-tree)
-------------------------------------------------------------------------------

pretty :: Show a => Tree a -> IO ()
pretty t = putStrLn (unlines xss)
 where
   (xss,_,_,_) = pprint t

pprint :: Show a => Tree a -> ([String], Int, Int, Int)
pprint Empty                =  ([], 0, 0, 0)
pprint (Node x Empty Empty) =  ([s], ls, 0, ls-1)
  where
    s = show x
    ls = length s
pprint (Node x lt rt)         =  (resultLines, w, lw'-swl, totLW+1+swr)
  where
    nSpaces n = replicate n ' '
    nBars n = replicate n '_'
    -- compute info for string of this node's data
    s = show x
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
