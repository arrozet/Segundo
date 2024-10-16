module Library.Lib (f1,f3) where   -- los en verde son los publicos
import Data.Char

f1:: Integer -> Integer -> Integer
f1 x y = f2 x + 100

f2 :: Integer -> Integer    -- esta es privada
f2 x = 10*x + 5*x*2

f3 :: Char -> Char
f3 c = chr (1 + ord c) 

-- chr pasa de numero a caracter (ASCII)