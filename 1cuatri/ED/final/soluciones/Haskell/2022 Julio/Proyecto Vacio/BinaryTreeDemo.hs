module SubTreeDemo where

import BinaryTree

t1 = mkBST [11,2,1,7,29,15,40,35]

demo1 = subTreesInRange t1 0 10
--demo1 = 3

demo2 = subTreesInRange t1 10 20
--demo2 = 1

demo3 = subTreesInRange t1 1 50
--demo3 = 8
