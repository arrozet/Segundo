import Data.Char        -- para funcion ordç
import Test.QuickCheck

-- comentario
--twice :: Integer -> Integer
twice :: (Num a) => a -> a      -- ahora twice esta sobrecargada, pq (+) esta sobrecargado y en twice se usa (+)
twice x = x + x

-- inferencia de tipos
-- twice2 :: (Num a) => a -> a
twice2 x = x + x
-- el interprete deduce el tipo. SE DEBE ESCRIBIR EN EL CÓDIGO (puedes verlo preguntandole a haskell con :t twice2)


square :: Integer -> Integer
square x = x*x

p1 x y = True ==> square(x+y) == square x + square y + 2*x*y
{-
para probar: quickCheck (p1 :: Integer -> Integer -> Property)  

lo suyo es hacerlo asi, indicando explícitamente su tipo (en lugar de quickCheck p1) porque si la función es polimórfica, 
va a probar con (), la tupla vacía, en todos sus casos.

si no hay una implicación (no hay precondición) el tipo de la propiedad es p :: Integer -> Integer -> Bool; es decir, 
no devuelve Property, devuelve Bool.

con verboseCheck te sale cada valor que ha comprobado
-}

-- La evaluación perezosa de Haskell funciona con referencias y punteros



pythagoras :: Integer -> Integer -> Integer
pythagoras x y = square x + square y

{- varias
    lineas
-}


next :: Char -> Char
next x = chr (ord x + 1)


maxi :: Integer -> Integer -> Integer
maxi x y = if x >= y then x else y

fact :: Integer -> Integer
fact x = if x == 0 then 1 else x * fact (x-1)

factorial :: Integer -> Integer     -- notación de guarda
factorial x
    | x == 0    = 1                        -- casos (|) y condiciones (lo que hay detrás de |)
    | x > 0     = x * factorial (x - 1)
    | otherwise = error "factorial de negativo"

fst3 :: (a,b,c) -> a      -- tipo polimórfico, puede coger cualquier valor
fst3(x,y,z) = x

primero :: (a,a) -> a       -- deben ser iguales
primero(x,y) = x

{- 
La suma es polimorfico de 3, con los 3 tipos iguales.   
(+) :: (Num a) => a -> a -> a    [hace eso siempre que a sea un número] 
Eso es un tipo sobrecargado
-}

data Dia = Lunes | Martes | Miercoles | Jueves | Viernes | Sabado | Domingo deriving Show         -- esto es una definición de tipo enumerado
                                                                            -- si pongo deriving Show, el interprete te crea el Show (como el toString) automaticamente
prueba_data :: Char -> Dia
prueba_data x = if x == 'a' then Lunes else Martes      -- te dice que no puede pintar el resultado, pq el print no lo tiene predefinido