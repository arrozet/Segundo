-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
--
-- Titulación: Grado en Ingeniería del Software.
-- Alumno: OLIVA ZAMORA, RUBÉN
-- Fecha de entrega: DIA | MES | 2023
--
-- Relación de Ejercicios 2. Ejercicios resueltos: 1-40
--
-------------------------------------------------------------------------------
import Test.QuickCheck
import Data.Char

-- EJERCICIO 1
data Direction = North | South | East | West deriving (Eq,Enum,Show)
-- a)
(<<) :: Direction -> Direction -> Bool
x << y = fromEnum x < fromEnum y

p_menor x y = (x<y) == (x<<y)
instance Arbitrary Direction where  -- quickCheck llama a arbitrary para generar los casos de prueba
    arbitrary = do  -- do le dice a la funcion arbitrary que es lo que tiene que hacer
        n <- choose (0,3)   -- genera numero aleatorio entre 0 y 3
        return $ toEnum n       -- $ se usa para evitar poner paréntesis, es un operador de
                                -- precedencia muy baja por lo que permite evaluar expresiones a su derecha antes

--b)
instance Ord Direction where    
    x <= y = fromEnum x <= fromEnum y



-- EJERCICIO 2
-- a)
máximoYresto :: (Ord a) => [a] -> (a,[a])                       -- no sé cuál es el método para que no salga ordenada originalmente
máximoYresto [] = error "Lista vacía"
máximoYresto (x:xs) = (max(x:xs), filter (/= max(x:xs)) (x:xs))
    where
        max [x] = x
        max (x:xs)  |   x > max(xs)     = x
                    |   otherwise       = max(xs)

-- b)
máximoYresto' :: (Ord a) => [a] -> (a,[a])
máximoYresto' [] = error "Lista vacía"
máximoYresto' (x:xs) = (maximum (x:xs), restantes)
    where
        restantes = [ y | y <- (x:xs), y/= (maximum (x:xs))]


-- EJERCICIO 3 - X
reparte :: [a] -> ([a],[a])
reparte [] = ([],[])
reparte[x] = ([x],[])
reparte(x:y:[]) = ([x],[y])
--reparte (x:y:zs) = (x:(reparte zs ), y:(reparte zs))
reparte (x:y:zs) = (x:xs, y:ys)
    where
        (xs,ys)= reparte zs

        -- por qué si se pone como arriba (comentado) no funciona?

-- EJERCICIO 4 - X
distintos :: Eq a => [a] -> Bool
distintos [] = True
distintos [x] = True
distintos (x:xs) = not (elem x xs) && distintos xs

-- EJERCICIO 5
replicate' :: Int -> a -> [a]
-- a)
replicate' n x = [x | y <- [1..n]]  -- el _ ó y es para indicar que me da igual el elemento que haya en esa posicion
-- b)
p_replicate' n x = n >= 0 && n<=1000 ==>   -- se acota entre 0 y 1000 el numero de elemntos de la lista para que no salga una enorme
                            length (filter (==x) xs) == n   -- todos los elementos son x (lista de cardinal n --> tiene n veces el elemento x)
                            && length (filter (/=x) xs) == 0    -- no hay ningún elemnto que no sea x
                                where xs = replicate' n x

-- c) : +++ OK, passed 100 tests; 98 discarded.

-- EJERCICIO 6
divideA :: Int -> Int -> Bool
divideA x y = y `mod` x == 0 

divisores :: Int -> [Int]   -- este es para numeros naturales
divisores n = [x | x <- [1..n], x `divideA` n]

divisores' :: Int -> [Int]      -- este es para numeros enteros
divisores' n  
    | n >= 0    = [x | x <- [-n..(-1)]++[1..n], x `divideA` n] -- he excluido el 0 para que no de error al dividir por 0
    | otherwise = [x | x <- [n..(-1)]++[1..(-n)], x `divideA` n]



-- EJERCICIO 7

-- a)
mcd :: Int -> Int -> Int
mcd x y = maximum(lista_comun)
    where 
        lista_comun = [ a | a <- divisores' x, a `elem` divisores' y]

-- b)
p_mcd x y z = x /= 0 && y /=0 && z /= 0 ==> mcd (z*x) (z*y) == abs z * mcd x y

-- c) 
mcm :: Int -> Int -> Int
mcm x y = (x * y) `div` (mcd x y)

-- el algoritmo de Euclides para el cálculo del mcd es más eficiente que el que has desarrollado 
-- en este ejercicio

-- EJERCICIO 8
-- a)
esPrimo :: Int -> Bool
esPrimo x = x `elem` lista_divisores && 1 `elem` lista_divisores && length lista_divisores == 2
    where 
        lista_divisores = divisores x

-- b)
primosHasta :: Int -> [Int]
primosHasta n = [x | x <- [1..n], esPrimo x]

-- c)
primosHasta' :: Int -> [Int]
primosHasta' n = filter esPrimo [1..n]

-- d)
p1_primos x = primosHasta x == primosHasta' x

-- Existen métodos más eficientes para calcular listas de primos
-- como la CRIBA DE ARISTÓTENES

-- EJERCICIO 9
-- a)
pares :: Int -> [(Int,Int)]
pares n = [ (x, n-x) | x <- primosHasta (n `div` 2), esPrimo x && esPrimo (n-x)] 
 -- n `div` 2 asegura que los pares sean únicos (sin duplicados)
 -- por ej, con n=10 -> [2,3,5] -> 10-2 = 8 (NO primo)
 --                             -> 10-3 = 7 (SÍ primo)
 --                             -> 10-5 = 5 (SÍ primo)

 -- b)
goldbach :: Int -> Bool
goldbach n = n > 2 && not (null (pares n))
-- null toma una lista y devuelve True si está vacía

-- c) 
goldbachHasta :: Int -> Bool
goldbachHasta n = and [goldbach x | x <- [3..n], even x]    -- 3 pues deben ser nºs mayores que 2
-- and toma una lista de booleanos y devuelve True si todos son ciertos

-- d)
goldbachDébilHasta :: Int -> Bool
goldbachDébilHasta n = and [goldbach (x-3) | x <- [6..n], odd x] -- 6 pues deben ser nºs mayores que 5

-- EJERCICIO 10
-- a)
esPerfecto :: Int -> Bool
esPerfecto n = n == (foldr (+) 0 (divisores n)) - n -- le resto n pq divisores n arroja 
--                                                     los divisores 1..n, incluyendo n (que no quiero incluirlo)
-- b)
perfectosMenoresQue :: Int -> [Int]
perfectosMenoresQue n = [x | x <- [1..n], esPerfecto x]


-- EJERCICIO 11 - X
-- a)
take' :: Int -> [a] -> [a]
take' n xs = [ x | (p,x) <- zip [0..(n-1)] xs]  -- cojo los n primeros pares y le saco los valores asociados a la lista (n primeros elementos de la lista)
-- b)
drop' :: Int -> [a] -> [a]
drop' n xs = [ x | (p,x) <- zip [1..length xs] xs, p>n]   -- cojo todas las posiciones menos las n primeras
-- c)
p_takedrop n xs = n>=0 ==> (take' n xs) ++ (drop' n xs) == xs 

-- EJERCICIO 12 - X
-- a)
concat' :: [[a]] -> [a]
concat' xs = foldr (++) [] xs

-- b)
concat'' :: [[a]] -> [a]
concat'' xs = [y | x <- xs, y <- x] -- por la sintaxis, el primer generador es lo primero que se hace

-- EJERCICIO 13 - X
desconocida :: (Ord a) => [a] -> Bool
desconocida xs = and [x <= y | (x,y) <- zip xs (tail xs)]   -- mira si la lista esta ordenada de forma NO estrictamente creciente . devuelve false si no lo está
-- solo para que se vea como funciona el zip xs (tail xs)
desconocida2 :: [a] -> [(a,a)]
desconocida2 xs =  zip xs (tail xs)
-- 

-- EJERCICIO 14 - X
-- a)
-- la lista que se le pasa por parámetro está ordenada ascendentemente
inserta :: (Ord a) => a -> [a] -> [a]
inserta x xs = (takeWhile (<x) xs) ++ [x] ++ (dropWhile (<x) xs)    -- cojo los de debajo del num, meto el num y cojo los de encima del num

-- b) 
inserta' :: (Ord a) => a -> [a] -> [a]
inserta' x [] = [x]
inserta' x (y:ys)
    | x >= y =  y : inserta' x ys
    | x < y =   x : y : ys

-- c) 
p1_inserta x xs = desconocida xs ==> desconocida (inserta x xs)     -- si la lista está ordenada crecientemente, inserta también está ordenado crecientemente

-- d)
-- funciona porque inserta siempre mete el elemento en su posición, considerando el orden relativo como orden ascendente

-- e) 
ordena :: (Ord a) => [a] -> [a]
ordena xs = foldr inserta [] xs

-- f)
p_ordena xs = True ==> desconocida (ordena xs) == True  -- desconocida devuelve True si la lista está ordenada crecientemente. Como ordena debería ordenar la lista, la condición debería ser verdadeaa

-- EJERCICIO 22 - X
-- a)
binarios :: Int -> [[Char]]
binarios 0 = [ [] ]
binarios n = (map ('0':) (binarios (n-1))) ++ (map ('1':) (binarios (n-1)))

-- b) NO SE PUEDE HACER PQ NO TENGO DEFINIDOS todosEn, que es del EJ 21
p_binarios n = n>=0 && n<=10 ==> 
                    long xss == 2^n
                    && distintos xss
                    && all (`todosEn` "01") xss
            where xss = binarios n

long :: [a] -> Integer
long xs = fromIntegral (length xs)
