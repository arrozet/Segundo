-------------------------------------------------------------------------------
-- Estructuras de Datos. 2º Curso. ETSI Informática. UMA
--
--
-- Titulación: Grado en Ingeniería del Software.
-- Alumno: OLIVA ZAMORA, RUBÉN
-- Fecha de entrega: 27 | 09 | 2023
--
-- Relación de Ejercicios 1. Ejercicios resueltos: 1-17
--
-------------------------------------------------------------------------------
import Test.QuickCheck

-- EJERCICIO 1
esTerna :: Integer -> Integer -> Integer -> Bool
esTerna x y z = (x^2+y^2==z^2)

terna :: Integer -> Integer -> (Integer,Integer,Integer)
terna x y = (x^2-y^2, 2*x*y, x^2 + y^2)

p_ternas x y = x>0 && y>0 && x>y ==> esTerna l1 l2 h
    where
        (l1,l2,h) = terna x y


-- EJERCICIO 2
intercambia :: (a,b) -> (b,a)
intercambia (x,y) = (y,x)

-- EJERCICIO 3
-- a)
ordena2 :: Ord a => (a,a) -> (a,a)
ordena2 (x,y) = if (x>y) then (y,x) else (x,y)

p1_ordena2 x y = enOrden (ordena2 (x,y))    -- están ordenados si x<y
    where enOrden (x,y) = x<=y          

p2_ordena2 x y = mismosElementos (x,y) (ordena2 (x,y))      -- para comprobar que has hecho la función bien y en mitad no se cambien los números
    where
        mismosElementos (x,y) (z,v) = (x==z && y==v) || (x==v && y==z)  -- (o, alternativamente, (x,y)==(z,v)||(x,y)==(v,z))

-- b)
ordena3 :: Ord a => (a,a,a) -> (a,a,a)
ordena3 (x,y,z)
    | x>y && x>z    = (a,b,x)
    | y>x && y>z    = (c, d, y)
    | otherwise     = (e, f, z)
        where                   -- hago esto porque si no la terna tendria forma ((elem1, elem2), elem3)
            (a,b) = ordena2(y,z)
            (c,d) = ordena2(x,z)
            (e,f) = ordena2(x,y)

p1_ordena3 x y z = enOrden (ordena3 (x,y,z))
    where enOrden (x,y,z) = x<=y && y<=z  -- no hace falta && x<=z pq si x<=y e y<=z, por transitividad, x<=z   

p2_ordena3 x y z = p2_ordena2 x y && p2_ordena2 x z && p2_ordena2 y z

{- OTRA FORMA SIN USAR p2_ordena2

p2_ordena3 x y z = mismosElementos (x,y,z) (ordena3 (x,y,z))
    where
        mismosElementos (x,y,z) (r,s,t) = (x==r && y==s && z==t) || (x==r && z==s && y==t) || (y==r && x==s && z==t) || (z==r && x==s && y==t) || (y==r && z==s && x==t) || (z==r && y==s && x==t) 
-}

-- EJERCICIO 4 
max2 :: Ord a => a -> a -> a
max2 x y = if x>y then x else y

-- p1_max2: el máximo de dos números x e y coincide o bien con x o bien con y.
p1_max2 x y = m == x || m == y
    where m = max2 x y

-- p2_max2: el máximo de x e y es mayor o igual que x , así como mayor o igual que y.
p2_max2 x y = m >= x && m >= y 
    where m = max2 x y

-- p3_max2: si x es mayor o igual que y, entonces el máximo de x e y es x.
p3_max2 x y = x>=y ==> m == x
    where m = max2 x y

-- p4_max2: si y es mayor o igual que x, entonces el máximo de x e y es y. 
p4_max2 x y = p3_max2 y x       -- lo mismo que p3 pero al revés

-- EJERCICIO 5
entre :: Ord a => a -> (a,a) -> Bool 
entre x (y,z) = x>=y && x<=z

-- EJERCICIO 6 
iguales3 :: Eq a => (a,a,a) -> Bool
iguales3 (x,y,z) = x==y && y==z

-- EJERCICIO 7
-- a)
type TotalSegundos = Integer
type Horas = Integer
type Minutos = Integer
type Segundos = Integer
descomponer :: TotalSegundos -> (Horas,Minutos,Segundos)
descomponer x = (horas, minutos, segundos)
    where
        horas = x `div` 3600
        minutos = (x-horas*3600) `div` 60       -- otra forma: x `div` 60 `mod` 60
        segundos = x `mod` 60

-- b)
p_descomponer x = x>=0 ==> h*3600 + m*60 + s == x
                            && entre m (0,59)
                            && entre s (0,59)
    where (h,m,s) = descomponer x

-- EJERCICIO 8
unEuro :: Double
unEuro = 166.386

pesetasAEuros :: Double -> Double
pesetasAEuros x = x/unEuro

eurosAPesetas :: Double -> Double
eurosAPesetas x = x*unEuro

-- p_inversas x = eurosAPesetas (pesetasAEuros x) == x      -- comentamos para cambiarla por aproximadamente igual, tal y como nos dice el ejercicio 9
-- Falla por errores de redondeo debidos a la limitación de precisión de los números en coma flotante. Por ej, con 0.41, devuelve 0.41000000000000003

-- EJERCICIO 9
infix 4 ~= 
(~=) :: Double -> Double -> Bool
x ~= y = abs (x-y) < epsilon
    where epsilon = 1/1000 

p_inversas x = eurosAPesetas (pesetasAEuros x) ~= x

-- EJERCICIO 10
-- a)
raíces :: Double -> Double -> Double -> (Double,Double)     -- por qué si lo hago con Rational da error?    pq sqrt no está definido para rational
raíces a b c = if discriminante a b c < 0 then error "Raíces no reales" else (ec_segundo_grado_mas, ec_segundo_grado_menos)
    where 
        discriminante a b c = b^2-4*a*c
        ec_segundo_grado_mas = (-b+sqrt(b^2-4*a*c))/(2*a)
        ec_segundo_grado_menos = (-b-sqrt(b^2-4*a*c))/(2*a)

-- b)
p1_raíces a b c = esRaíz r1 && esRaíz r2        -- esta falla, hay que hacer p2 que arregle esa
    where
        (r1,r2) = raíces a b c
        esRaíz r = a*r^2 + b*r + c ~= 0

p2_raíces a b c = a /= 0 && b^2-4*a*c >= 0 ==> esRaíz r1 && esRaíz r2   -- esta funciona, porque ya no se puede dividir por 0 ni salen raíces complejas
    where
        (r1,r2) = raíces a b c
        esRaíz r = a*r^2 + b*r + c ~= 0


-- EJERCICIO 11
esMúltiplo :: (Integral a) => a -> a -> Bool
{-esMúltiplo x y = (x `div` y) * y == x -}  -- Funciona, pero es más elegante
esMúltiplo x y = x `mod` y == 0             -- no añado y mod x pq por ej, 9 es multiplo de 3 pero 3 no es multiplo de 0

-- EJERCICIO 12
infixl 1 ==>>       -- precedencia conjuncion (&&): 3   precedencia disyuncion (||): 2

(==>>) :: Bool -> Bool -> Bool
x ==>> y 
    | x==False                  = True      -- si x=0, y=0 -> True, si x=0, y=1 -> True
    | x==True && y==True        = True
    | otherwise                 = False     -- el unico caso que queda es x==True y y==False

{- Otra forma:
False ==>> y = True
True ==>> False = False
True ==>> True = True
-}

-- EJERCICIO 13
esBisiesto :: Int -> Bool               -- por qué si no pongo paréntesis, 1985 sale bisiesto?  pq && tiene mas procedencia que ==>>
esBisiesto n = n `mod` 4 == 0 && (n `mod` 100 == 0 ==>> n `mod` 400 == 0)

-- EJERCICIO 14
-- a)
potencia :: Integer -> Integer -> Integer
potencia b n 
    | n == 0    = 1
    | n > 0     = b * potencia b (n-1)      -- por qué si no pongo el paréntesis no va?
    | otherwise = error "Potencia inválida"

-- b)
potencia' :: Integer -> Integer -> Integer
potencia' b n
    | n == 0                        = 1
    | n == 2                        = b*b                   -- hay que definir este caso base, pq si metes 2 se ejecuta infinitamente b^2, pues b^(2/2)^2=b^2
    | n > 0  && n `mod` 2 == 0      = (potencia' b (n `div` 2)) `potencia'` 2           -- diferencia / y div?
    | n > 0  && n `mod` 2 == 1      = b * ((potencia' b ((n-1) `div` 2)) `potencia'` 2)
    | otherwise                     = error "Potencia no válida"

-- c)
p_pot b n = n>=0 ==> potencia b n == sol && potencia' b n == sol
    where sol = b^n

-- d) 
-- ESTO ES DE ADA

-- EJERCICIO 15
factorial :: Integer -> Integer
factorial n
    | n == 0    = 1
    | n > 0     = n*factorial (n-1)
    | otherwise = error "factorial no válido"


-- EJERCICIO 16
-- a)
divideA :: Integer -> Integer -> Bool
divideA x y = mod y x == 0

-- b)
p1_divideA x y = y/=0 && y `divideA` x ==> div x y * y == x
-- y no es 0 (para evitar indeterminacion), entonces (x div y) * y = x; considerando que div es la division entera y que por ej 7 div 4 = 1 
                                                                                                            -- pero 4 no divide a 7 pues 1*4 != 7

-- c) 
p2_divideA x y z = x/=0 && x `divideA` y && x `divideA` z ==> x `divideA` (y+z)  

-- EJERCICIO 17
mediana :: Ord a => (a,a,a,a,a) -> a
mediana (x,y,z,t,u) 
    | x > z         = mediana (z,y,x,t,u)       -- básicamente esto pone z en medio, convirtiéndose en la mediana
    | y > z         = mediana (x,z,y,t,u)
    | z > t         = mediana (x,y,t,z,u)
    | z > u         = mediana (x,y,u,t,z)
    | otherwise     = z