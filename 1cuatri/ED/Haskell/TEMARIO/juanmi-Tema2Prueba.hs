import Test.QuickCheck

{- 

Listas: Son como las arrays, conjuntos de elementos y todos del mismo tipo se escribe entre [] se pueden mezclar listas con tuplas, ejemplos
[1, 2, 5] o [('a', 5) ('z', 6)]
Funciones de las listas:
head:: [a] -> a // devuelve el primer elemento de una lista
tail :: [a] -> [a] // devuelve el ultimo elemento de la lista
null :: [a] -> Bool // devuelve True si la lista esta vacia y Falase si tiene algun dato

-}

-- Ejemplos:
len :: [a] -> Int
len xs = if null xs then 0 else 1 + len(tail xs)

p1 xs ys = len xs + len ys == len (xs ++ ys)

-- Operador predefinido, es la concatenacion ejemplo:
-- [1,2,3] ++ [10,20] esto dara [1, 2, 3, 10, 20]


{- 

Poeradpr (:)
Añade un elemento al principio de una lista, como e add de los arraylist
Ejemplo:

10 : [1, 5, 8] me devolvera [10, 1, 5, 8]
Esta operacion es igual que la concatenacion con ++, pero solo permite añadir un unico valor al principio de la lista con la ventaja que el algoritmo es mas optimizado es O(1) mientras ++ es O(n)
Esta propiedad es asociativa a la derecha ejemplo 1 : 2 : 3 : [] dara como resultaddo [1, 2, 3]


-}

{-

Type String = [Char]
los String son considerados lista concatenando caracteres, se escribe igual esto:
['p', 'e`, 'p', 'e'] == "pepe"
Por tato las operaciones de las listas como head, tail o null se pueen usar en los Strings ya que no dejan de ser listas
Se puede usar por ejemplo el operacion ++ para unir dos Strings
pepe ++ juan == pepejuan

-}


{-
Patrones:(Sirven para definir que ocurre cuando le pasas a una lista el numero de parametros)
Por eje olo si pasas una lista vacia, con un elemento, con dos, etc... y para cada caso se crea una funcion diferente segun nos convenga

Otro tipo de patron es (x:xs), este patron delitima patrones que al menos tengan 1 elemento
Otro tipo seria (x:y:xs) este patron delimita patrones que al menos tengan 2 elementos

-}

f :: [Int] -> Int
f [] = 0
f [x] = 2*x
f [x,y] = x + y


len2 :: [a] -> Int
len2 [] = 0
len2 (_:xs) = 1 + len2 xs



sorted :: (Ord a) => [a] -> Bool -- Elementos que dado una lista devuelve True si estan ordenados en casos ascendentes o si se mantiene
sorted [] = True
sorted [_] = True
sorted (x : y : zs) = x <= y && sorted (y : zs)

-- cuando pomes _ donde iria una variable significa que es un comodin y vale cualquier variable
-- Funcion predefinida para calcular la longitud de una lista length

-- sumatorio n => 1+ 2 + 3.... n
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

-- Fucnion take n xs oernite extraer los elementos de una lista empezando por el principio
-- Devuelve el sufijo de xs despues de los primeros n elementos

toma :: Int -> [a] -> [a]
toma 0 _ = []
toma _ [] = []
toma n (x:xs)
    | n > 0 = x : toma (n-1) xs
    | otherwise = []

-- Implementar el drop en casa

cuadrado :: Integer -> Integer
cuadrado x = x * x

doble :: Integer -> Integer
doble x = 2x

-- Funcion map aplica una funcion como primer argumento y en el segundo una lista que le aplica a toda lista
-- Ejemplo
-- map cuadrado [0. 20. 30] tiene un tipo como map :: (a->b) -> [a] -> [b] 
-- map f [] = []
-- map f (x:xs) = fx : map f xs

-- Funcion filrwe dada una funcion que devuelve verdadero o falso, devuelve solo la sublista para los cuales la funcion es True
-- filter :: (a->Bool)-> [a] -> [a]
-- Notcion lambda: Sirve para crear una funcion dentro de un map, como map (\x -> x * x) [10, 20, 30]
-- Otro ejemplo (\x y -> 10*x + y) 10 5 sin usar map ni nada
-- Otra funcion de order superior es zipWith, el primer argumento tiene que ser una funcion e 2 argumentos, el segundo argumento debe ser una lista y el terero tambien
-- EJ: zipWith (\x y -> x*10 + y) [10, 20, 30] [5, 6, 7]