--By José Manuel Fernández Reyes, 19th December 2023
--8th Data Structure:
--Dictionary

module AVLDictionary (
    Dictionary,
    empty,
    isEmpty,
    
    insert,
    delete,
    get,

    keys,
    values,
    keysValues,

    mapValues

) where

import qualified AVL as A
import Data.Function(on)
import Data.List(intercalate)
import Test.QuickCheck

data Rel a b = a :-> b 

key :: Rel a b -> a 
key (k :-> v) = k

value :: Rel a b -> b
value (k :-> v) = v

withKey :: a -> Rel a b
withKey k = k :-> undefined

instance (Eq a) => Eq (Rel a b) where
  (==)  = (==) `on` key

instance (Ord a) => Ord (Rel a b) where
  compare  = compare `on` key


data Dictionary a b = D (A.AVL (Rel a b)) 

empty :: Dictionary a b
empty = D (A.empty)

isEmpty :: Dictionary a b -> Bool
isEmpty (D avl) = A.isEmpty avl

insert :: (Ord a) => a -> b -> Dictionary a b -> Dictionary a b
insert k v (D avl) = D (A.insert (k :-> v) avl)

delete ::  (Ord a) => a -> Dictionary a b -> Dictionary a b
delete k (D avl) = D (A.delete (withKey k) avl)

get ::  (Ord a) => a -> Dictionary a b -> Maybe b
get k (D avl) = get' (A.search (withKey k) avl)
    where
        get' Nothing =  Nothing
        get' (Just (k :-> v)) = Just v

keys :: Dictionary a b -> [a]
keys (D avl) = foldr (\x dd -> [key (fst x)] ++ dd) [] (A.inOrder avl)

values :: Dictionary a b -> [b]
values (D avl) = foldr (\x dd -> [value (fst x)] ++ dd) [] (A.inOrder avl)

keysValues :: Dictionary a b -> [(a,b)]
keysValues (D avl) = foldr (\x dd -> [(key (fst x), value (fst x))] ++ dd) [] (A.inOrder avl)

mapValues :: (Ord a) => (b -> c) -> Dictionary a b -> Dictionary a c
mapValues f (D avl) = D (foldr (\x dd -> A.insert ((key (fst x)) :-> (f (value (fst x)))) dd) A.empty (A.inOrder avl))

auxiliar :: Dictionary a b -> [Rel a b]
auxiliar (D avl) = [fst l | l <- A.inOrder avl]

instance (Show a, Show b) => Show (Dictionary a b) where
  show (D avl)  = "AVLDictionary(" ++ intercalate "," (aux (auxiliar (D avl))) ++ ")"
   where
    aux []             = []
    aux (x:->y : xys)  = (show x++"->"++show y) : aux xys

instance (Ord a, Arbitrary a, Arbitrary b) => Arbitrary (Dictionary a b) where
    arbitrary  = do
      kvs <- listOf arbitrary
      return (foldr (\(k,v) -> insert k v) empty kvs)

instance (Eq a, Eq b) => Eq (Dictionary a b) where
  d == d' = keysValues d == keysValues d'

--Examples

d = insert 2 "two" (insert 3 "three" (insert 4 "four" (insert 1 "one" (insert 5 "five" empty))))