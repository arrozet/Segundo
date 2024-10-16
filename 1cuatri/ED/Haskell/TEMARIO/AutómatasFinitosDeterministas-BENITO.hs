-- © José Manuel Fernández Reyes 19/10/2023 | ETS de Ingenieria Informática (UMA)
-- Este programa (o como se llame) tiene todos los derechos reservados. No es posible el uso de esta obra sin autorización. Número de registro: 2310205638648. Safe Creative.
--PROGRAMA PARA AUTÓMATAS FINITOS DETERMINISTAS

import Data.List
import Data.Char
import Data.Foldable
import Control.Monad

--ALFABETO

data Alfabeto = Alfabeto [Char]

instance Show Alfabeto where
    show (Alfabeto x) | length x == 0 = error "Un alfabeto no puede estar vacío"
                      | otherwise = "{" ++ [head x]  ++ ", " ++ showAlfabeto (Alfabeto (drop 1 x))
            where
                showAlfabeto (Alfabeto x) | length x == 0 = "}"
                                          | length x == 1 = [head x] ++ showAlfabeto (Alfabeto (drop 1 x))
                                          | otherwise = [head x] ++ ", " ++ showAlfabeto (Alfabeto (drop 1 x))

instance Eq Alfabeto where
    (Alfabeto a1) == (Alfabeto a2) = a1 == a2

cadenaSobreAlfabeto :: Alfabeto -> String -> Bool
cadenaSobreAlfabeto (Alfabeto alf) cadena | length cadena == 0 = True
                                          | otherwise = and [símboloContenido (Alfabeto alf) (cadena !! n) | n <- [0..(length cadena)-1]]
    where
        símboloContenido (Alfabeto alf) cad | length alf == 0 = False
                                            | cad == (head alf) = True
                                            | otherwise = símboloContenido (Alfabeto (drop 1 alf)) cad

infix 5 ^*
(^*) :: Alfabeto -> [String]
(^*) (Alfabeto alf)  = cadenasSobreSigmaEstrella (Alfabeto alf)
    where 
       cadenasDeLongitudN :: Int -> Alfabeto -> [String]
       cadenasDeLongitudN n (Alfabeto alf) = replicateM n alf
       cadenasSobreSigmaEstrella :: Alfabeto -> [String]
       cadenasSobreSigmaEstrella (Alfabeto alf) = concatMap (\n -> cadenasDeLongitudN n (Alfabeto alf)) [0..] 

infix 5 ^+
(^+) :: Alfabeto -> [String]
(^+) (Alfabeto alf) = (drop 1 ((^*) (Alfabeto alf)))
 where 
       cadenasDeLongitudN :: Int -> Alfabeto -> [String]
       cadenasDeLongitudN n (Alfabeto alf) = replicateM n alf
       cadenasSobreSigmaEstrella :: Alfabeto -> [String]
       cadenasSobreSigmaEstrella (Alfabeto alf) = concatMap (\n -> cadenasDeLongitudN n (Alfabeto alf)) [0..] 

cadenaVacía :: String
cadenaVacía = ""


--LENGUAJES

data Lenguaje = L Alfabeto [String]

instance Show Lenguaje where 
    show (L (Alfabeto sigma) s) | length s <= 50 && and [cadenaSobreAlfabeto (Alfabeto sigma) (s !! n) | n <- [0..(length s-1)]] == False = "Este lenguaje no es sobre el alfabeto " ++ show (Alfabeto sigma)
                                | length s == 0 = "L = {epsilon}"
                                | length s == 1 = "L = {" ++ head s ++ "}"
                                | head s == cadenaVacía = "L = {epsilon, " ++ showLenguaje (L (Alfabeto sigma) (drop 1 s))
                                | otherwise = "L = {" ++ head s ++ ", " ++ showLenguaje (L (Alfabeto sigma) (drop 1 s))
        where
            showLenguaje (L (Alfabeto sigma) s) | length s == 0 = "}"
                                                | length s == 1 = head s ++ showLenguaje (L (Alfabeto sigma) (drop 1 s))
                                                | otherwise = head s ++ ", " ++ showLenguaje (L (Alfabeto sigma) (drop 1 s))
--ESTADOS

data Estado = Q Integer

instance Show Estado where
    show (Q n) | elem (Q n) fábricaDeEstados == False = error "Los estados solo pueden ser numerados desde 0 hasta el 10" 
               | otherwise = "q" ++ show n

instance Eq Estado where
    (Q n1) == (Q n2) = n1 == n2

fábricaDeEstados :: [Estado]
fábricaDeEstados = [Q 0, Q 1, Q 2, Q 3, Q 4, Q 5, Q 6, Q 7, Q 8, Q 9, Q 10]

--FUNCIÓN DE TRANSICIÓN

data Transición = Delta [Estado] Alfabeto [(Estado, Char, Estado)]

instance Show Transición where
    show (Delta k sigma funcionDefinida) | and [elem x k && elem z k && cadenaSobreAlfabeto sigma [y] | (x,y,z) <- funcionDefinida] == False = error "La función de transición no es correcta; tiene elementos no introducidos en el conjunto K o en el alfabeto Sigma"
                                         | otherwise = "d = " ++ concat ["(" ++ (show est) ++ ", " ++ [s] ++ ") ---> " ++ (show sig) ++ " | " | (est, s, sig) <- funcionDefinida]

transicionEjemplo1 :: Transición
transicionEjemplo1 = Delta [Q 0, Q 1] (Alfabeto ['0', '1']) [(Q 0, '0', Q 0), (Q 0, '1', Q 1), (Q 1, '0', Q 1), (Q 1, '1', Q 0)]

imprimirTabla :: Transición -> IO ()
imprimirTabla (Delta k sigma tabla) = do
    putStrLn "Tabla de Función de Transición:"
    putStrLn "---------------------------------"
    putStrLn "| Estado Actual |  Símbolo  |  Estado Siguiente |"
    putStrLn "---------------------------------"
    mapM_ imprimirFila tabla
    putStrLn "---------------------------------"

imprimirFila :: (Estado, Char, Estado) -> IO ()
imprimirFila (estadoActual, simbolo, estadoSiguiente) = do
    putStrLn $ "|           " ++ show estadoActual ++ "        |        " ++ [simbolo] ++ "       |       " ++ show estadoSiguiente ++ "       |"

--AUTÓMATAS FINITOS DETERMINISTAS

data AutómataAFD = AFD [Estado] Alfabeto Transición Estado [Estado]

instance Show AutómataAFD where
    show (AFD k sigma (Delta a psi funcionDefinida) s f) | perteneceAK f k == False || elem s k == False || psi /= sigma || a /= k = error "uno de los datos que has usado para construir el autómata está mal; revísalo"
                                                          | otherwise = imprimirAutomata (AFD k sigma (Delta a psi funcionDefinida) s f)
imprimirAutomata :: AutómataAFD -> String
imprimirAutomata (AFD k sigma d s f) = "Automata AFD:\n" ++
        "Estados: " ++ show k ++ "\n" ++
        "Alfabeto: " ++ show sigma ++ "\n" ++
        "Transición: " ++ show d ++ "\n" ++
        "Estado Inicial: " ++ show s ++ "\n" ++
        "Estados Finales: " ++ show f

perteneceAK :: [Estado] -> [Estado] -> Bool
perteneceAK [] _ = True
perteneceAK _ [] = False
perteneceAK (x:xs) (y:ys)
    | x == y = perteneceAK xs ys
    | otherwise = perteneceAK (x:xs) ys


automataEjemplo :: AutómataAFD
automataEjemplo = AFD [Q 0, Q 1] (Alfabeto ['0', '1']) transicionEjemplo1 (Q 0) [Q 0]

automataEjemplo3 :: AutómataAFD
automataEjemplo3 = AFD [Q 0, Q 1, Q 2, Q 3] (Alfabeto ['0', '1']) (Delta [Q 0, Q 1, Q 2, Q 3] (Alfabeto ['0', '1']) [(Q 0, '0', Q 1), (Q 0, '1', Q 1), (Q 1, '0', Q 0), (Q 1, '1', Q 2), (Q 2, '0', Q 0), (Q 2, '1', Q 3), (Q 3, '0', Q 3), (Q 3, '1', Q 3)]) (Q 0) [Q 0, Q 1, Q 2]

tablaTransición :: AutómataAFD -> IO ()
tablaTransición (AFD k sigma d s f) = imprimirTabla d

configuración :: AutómataAFD -> (Estado, String) -> Bool
configuración (AFD k sigma d s f) (q, w) = elem q k && cadenaSobreAlfabeto sigma w 

configuraciónInicial :: AutómataAFD -> String -> (Estado, String)
configuraciónInicial (AFD k sigma d s f) w | cadenaSobreAlfabeto sigma w == False = error "la cadena no pertenece al alfabeto del autómata"
                                           | otherwise = (s, w)

configuraciónTerminal :: AutómataAFD -> (Estado, String) -> Bool
configuraciónTerminal (AFD k sigma d s f) (q, w) = if w == cadenaVacía && configuración (AFD k sigma d s f) (q, w) then True else False

transitarDirectamente :: AutómataAFD -> (Estado, String) -> (Estado, String)
transitarDirectamente (AFD k sigma d s f) (q, w) | (configuración (AFD k sigma d s f) (q, w)) == False = error "el par que se ha proporcionado no es una configuración del autómata proporcionado"
                                                  | otherwise = ((buscarConsecuente d (q, (head w))), (drop 1 w))
    where
        buscarConsecuente :: Transición -> (Estado, Char) -> Estado
        buscarConsecuente (Delta k sigma tabla) (q, w) | (q, w) == soloElPar (head tabla) = (obtenerSiguiente (head tabla))
                                                       | otherwise = buscarConsecuente (Delta k sigma (drop 1 tabla)) (q, w)
        obtenerSiguiente :: (Estado, Char, Estado) -> Estado
        obtenerSiguiente (q, w, qprima) = qprima
        soloElPar :: (Estado, Char, Estado) -> (Estado, Char)
        soloElPar (q, w, qprima) = (q, w)

transitarEnNPasos :: AutómataAFD -> Integer -> (Estado, String)  -> (Estado, String)
transitarEnNPasos (AFD k sigma d s f) n (q, w)  | n < 0 || n > fromIntegral (length w) = error "el número no puede ser negativo o más grande que la longitud de la cadena del par (q, w)"
                                                | n == 0 = (q, w)
                                                | otherwise = transitarEnNPasos (AFD k sigma d s f) (n-1) (transitarDirectamente (AFD k sigma d s f) (q,w))

computación :: AutómataAFD -> Integer -> (Estado, String) -> String
computación (AFD k sigma d s f) n (q, w)  | n < 0 || n > fromIntegral (length w)+1 = error "el número no puede ser negativo o más grande que la longitud de la cadena del par (q, w)"
                                          | n == 0 = ""
                                          | length w == 0 =  "(" ++ show q ++ ", " ++ "" ++ ")"
                                          | n == 1 = "(" ++ show q ++ ", " ++ w ++ ")" ++ computación (AFD k sigma d s f) (n-1) (transitarDirectamente (AFD k sigma d s f) (q, w))
                                          | otherwise = "(" ++ show q ++ ", " ++ w ++ ")" ++ " |-- " ++ computación (AFD k sigma d s f) (n-1) (transitarDirectamente (AFD k sigma d s f) (q, w))

computaciónTerminada :: AutómataAFD -> Int -> (Estado, String) -> Bool
computaciónTerminada (AFD k sigma d s f) n (q, w) | n < 0 || n > fromIntegral (length w)+1 = error "el número no puede ser negativo o más grande que la longitud de la cadena del par (q, w)"
                                                  | (configuración (AFD k sigma d s f) (q, w)) == False = error "el par que se ha proporcionado no es una configuración del autómata proporcionado"
                                                  | otherwise = (length w == (n-1))

estadoTerminal :: AutómataAFD -> Int -> (Estado, String) -> Estado
estadoTerminal (AFD k sigma d s f) n (q, w) | computaciónTerminada (AFD k sigma d s f) n (q, w) == False = error "No es una computación terminada"
                                            | otherwise = obtenerEstado(transitarEnNPasos (AFD k sigma d s f) (fromIntegral (n-1)) (q, w))
                        where
                            obtenerEstado (q, w) = q 

cadenaAceptada :: AutómataAFD -> String -> Bool
cadenaAceptada (AFD k sigma d s f) w = elem (estadoTerminal (AFD k sigma d s f) (length w+1) (s, w)) f

procesoAnalisisDeCadena :: AutómataAFD -> String -> String
procesoAnalisisDeCadena (AFD k sigma d s f) w | (cadenaAceptada (AFD k sigma d s f) w) == True = "PROCESO DE ANALISIS DE LA CADENA [" ++ w ++ "]: || "++ (computación (AFD k sigma d s f) (fromIntegral (length w+1)) (s, w)) ++ " || :) La cadena ha sido aceptada (termino en el estado final " ++ show (estadoTerminal (AFD k sigma d s f) (length w+1) (s, w)) ++ ")."
                                              | otherwise = "PROCESO DE ANALISIS DE LA CADENA [" ++ w ++ "]: || " ++ (computación (AFD k sigma d s f) (fromIntegral (length w+1)) (s, w)) ++ " || X :( La cadena ha sido rechazada (termino en el estado " ++ show (estadoTerminal (AFD k sigma d s f) (length w+1) (s, w)) ++ ", que no es final)."

lenguajeAceptado :: AutómataAFD -> Int -> Lenguaje
lenguajeAceptado (AFD k sigma d s f) n = L sigma (take n [w | w <- (take 15000 ((^+) sigma)), cadenaAceptada (AFD k sigma d s f) w])