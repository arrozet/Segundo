data Tree a = Empty | Node a [Tree a] deriving Show

tree1 :: Tree Int
tree1 = Node 1 [ Node 2 [Node 4 []
                        , Node 5 []
                        , Node 6 []
                        ] 
                , Node 3  [ Node 7 [] ]
                ]

sizeT :: Tree a -> Int
sizeT Empty         = 0
sizeT (Node x ts)   = 1 + sum [sizeT t | t <- ts]  -- en lugar de sum -> foldr (+) 0

sumT :: (Num a) => Tree a -> a
sumT Empty      = 0
sum (Node x ts) = x + sum [sumT t | t <- ts]

heightT :: Tree a -> Int
heightT Empty       = 0
heightT Node x ts   = 1
heightT (Node x ts) = 1 + maximum [ heightT t | t <- ts]
--heightT (Node x ts) = if ts == [] then 1 else maximum [ heightT t | t <- ts]   -- maximum con lista vacia da error 
-- no se recomienda esto porque habria que añadir (Eq a) => en el tipo, que es mas restrictivo

containsT :: (Eq a) => a -> Tree a -> Bool
containsT x Empty       = False
contains x (Node y ts)  = if x==y then True else or [containsT t | t <- ts]
                        -- X==Y || or [containsT x t | t <- ts]

atLevelT :: Int -> Tree a -> [a]
atLevelT n Empty = []
atLevelT n (Node x ts)
    | n < 0     = error "nivel negativo"
    | n == 0    = [x]
    | otherwise = concat [atLevelT (n-1) t | t <- ts]

leafsT :: Tree a -> [a]
leafsT Empty        = []
leafsT (Node x [])  = [x]
lealfsT (Node x ts) = concat [leafs t | t <- ts]  


data TreeB a = EmptyB | NodeB a (TreeB a) (TreeB a) deriving Show

tree2 :: TreeB Int
tree2 = NodeB 1 (NodeB 2  (Node 4 EmptyB EmptyB)
                        (NodeB 5 EmptyB EmptyB)
                )
                (NodeB 3 (NodeB 6 EmptyB EmptyB) EmptyB)

sizeB :: TreeB a -> a
sizeB EmptyB            = 0
sizeB (NodeB x lt rt)   = 1 + sizeB lt + sizeB rt

heightB :: TreeB a -> Int
heightB EmptyB  = 0
heightB (Node B x lt rt) = 1 + max (heightB lt) (heightB rt) 

-- hacer el resto de ejercicios (los que hemos hecho para generales)

-- HOJA: NodeB x EmptyB EmptyB