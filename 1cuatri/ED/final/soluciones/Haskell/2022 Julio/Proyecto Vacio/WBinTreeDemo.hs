module WBinTreeDemo where

import           WBinTree

-- |
-- >>> wtree
-- Node 5 'K' (Node 2 'N' (Node 1 'U' Empty Empty) Empty) (Node 2 'T' (Node 1 'H' Empty Empty) Empty)

wtree :: WBinTree Char
wtree = mkWBinTree "HUTNK"

iwb :: Bool
iwb = isWeightBalanced wtree