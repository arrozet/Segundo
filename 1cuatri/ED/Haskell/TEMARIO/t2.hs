import Test.QuickCheck
import Data.Char

len :: [a] -> Int   -- se le llama xs en lugar de x pq es un argumento complejo (convenio - no es obligatorio)
-- len xs = if null xs then 0 else 1+len(tail xs)
len [] = 0
len (x:xs) = 1 + len xs     -- x:xs significa una lista de al menos 1 elemento (x)

p1 xs ys = len xs + len ys == len (xs ++ ys)

-- ++ es la concatenación de listas
-- : es para añadir un elemento al principio de la lista

f :: [Int] -> Int       -- definición de una función mediante patrones
f [] = 0
f [x] = 2*x
f [x,y] = x+y


sorted :: (Ord a) => [a] -> Bool   -- devuelve true si sus elementos están orndeados en orden ascendente
sorted [] = True
sorted [_] = True       -- _ significa que hay un elemento, sin importarme que elemento es  
sorted (x:y:zs) = x <= y && sorted (y:zs)   -- a debe ser ordenable para usar <=
                            -- se deja el segundo para hacer la comparación del segundo con el tercero


-- sumatorio n => 1 + 2+ 3 + ... + n

sumatorio :: Integer -> Integer
sumatorio n = aux 0 n
    where 
        aux ac 0 = ac
        aux ac n | n > 0 = aux (ac + n) (n-1)

long :: [a] -> Int
long xs = aux 0 xs
    where
        aux ac [] = ac
        aux ac (x:xs) = aux (ac+1) xs

toma :: Int -> [a] -> [a]
toma 0 _ = []
toma _ [] = []
toma n (x:xs)
    | n>0          = x : toma (n-1) xs
    | otherwise    = []


-- hacer el drop


cuadrado :: Int -> Int
cuadrado x = x^2

-- la notación (\x y -> 10*x +y) sirve para definir una función "on the go"
-- una funcion lambda o funcion anonima se llama, por ejemplo, (\x -> x * x)
-- zipWith (\fun) [l1] [l2] sirve para que cada elemento i de l1 se efectue la funcion con el elemento i de l2
-- si la fun usa un signo (+,^,*...) simplemente se interpreta lo que se debería hacer
-- map (^3) [1,2,3,4] -> [1,8,27,64]
-- map (3^) [1,2,3,4] -> [3,9,27,81] 

-- caso de uso de notacion de listas por secuencia aritmetica
fact :: Int -> Int
fact x = product [1..x]


-- fold right
-- foldr f z [lista] retorna el valor resultante de realizarf la funcion f a cada elemento de la lista, ademas de z. Devuelve un solo valor

-- foldr (\X XS -> XS ++ [X]) [] [1,2,3,4]          -- con esto, podemos invertir una lista


-- CURRYING
f2 :: Int -> Int -> Int -> Int
f2 x y z = x + 2*y +3*z

-- f2 = \x -> (\y -> (\z -> x + 2^y + 3+z))

-- f2 10 = (\y -> (\z -> x + 2^y + 3+z))

-- f2 10 20 = (\z -> x + 2^y + 3+z)

-- F2 10 20 30 = 10 + 2*20 + 3+30

isMultipleOf :: Integer -> Integer -> Bool
isMultipleOf y x = mod x y == 0

-- :t isMultipleOf 3, por el currying, tiene tipo Integer -> Bool

suma10 :: Integer -> Integer
suma10 x = x+10

-- para manipular listas de listas, se usan dos maps anidados


-- DATA
data Direction = North | South | East | West        -- esto es un nuevo tipo
-- hay una forma de que el interprete haga el Show, Eq y Ord directamente_
-- data Direction = North | South | East | West deriving (Show, Eq, Ord)
-- lo que genera el deriving es igualdad estructural (North == North, South == South), 
-- orden estructural (el primero -North- es el más pequño, el segundo -South- el segundo más pequeño)
-- el show simplemente coge el nombre del valor y lo convierte en cadena

directionToInt :: Direction -> Int
directionToInt North = 0
directionToInt South = 1
directionToInt East = 2
directionToInt West = 3


class MyClass a where           -- esto es como una interfaz de Java
    myFunction :: a -> Bool

instance MyClass Direction where
    myFunction North    = True
    myFunction _        = False

instance MyClass Char where
    myFunction c        = even (ord c)

instance Eq Direction where
    North == North  = True
    South == South  = True
    East == East    = True
    West == West    = True
    _ == _          = False     -- hay que ponerla al final, si no da siempre false

{-instance Ord Direction where        -- tan solo definiiendo <=, el resto de metodos predefinidos se encargan de que esto funcione
    North <= _      = True
    
    South <= North  = False
    South <= _      = True

    East <= North   = False
    East <= South   = False
    East <= _       = True

    West <= North   = False
    West <= South   = False
    West <= East    = False
    West <= _       = True
-}

instance Ord Direction where    -- forma corta de hacerlo
    x <= y = directionToInt x <= directionToInt y

instance Show Direction where   -- es el toString
    show North = "North"
    show South = "South"
    show East  = "East"
    show West  = "West"


data Degrees = Celsius Double | Fahrenheit Double deriving (Show)

list :: [Degrees]
list = [ Celsius 1.5, Fahrenheit 55.2]

frozen :: Degrees -> Bool
frozen (Celsius c)      =   c <= 0
frozen (Fahrenheit f)   =   f <= 32

data TU  = Number Int | Character Char | Boolean Bool deriving (Show)

list2 :: [TU]
list2 = [Number 10, Number 100, Character 'a', Boolean True]

-- ENTENDER TIPO MAYBE


-- TIPO RECURSIVO
{-
data SecuenciaEnteros = Vacia | Nodo Int SecuenciaEnteros deriving (Show)

sec1 :: SecuenciaEnteros
sec1 = Vacia

sec2 :: SecuenciaEnteros
sec2 = Nodo 10 Vacía

sec3 :: SecuenciaEnteros
sec3 = Nodo 10 (Nodo 20 Vacia)

longSec :: SecuenciaEnteros
longSec Vacia = 0
longSec (Nodo x ys) = 1+ longSec ys
-}

data Sec a = Vacia | Nodo a (Sec a) deriving (Show) -- tipo enlazado

sec2 :: Sec Int
sec2 = Nodo 10 (Nodo 20 (Nodo 30 Vacia))

sec3 :: Sec Char
sec3 = Nodo 'a' (Nodo 'b' (Nodo 'c' (Nodo 'd' Vacia)))

longSec :: Sec a -> Int
longSec Vacia = 0
longSec (Nodo _ ys) = 1 + longSec ys

sumaSec :: (Num a) => Sec a -> a
sumaSec Vacia = 0
sumaSec (Nodo x ys) = x + sumaSec ys

concatSeq :: Sec a -> Sec a -> Sec a
concatSeq Vacia ys = ys
concatSeq (Nodo x xs) ys = Nodo x (concatSeq xs ys) 