--import Lib
-- para importar solo f1 -> import Lib(f1)
import qualified Library.Lib as L
import Data.Char

-- Especificación
-----------------------
--          Signatura
--      Conj. de Axiomas


f4 :: Integer -> Integer -> Bool
f4 x y = even (L.f1 x y)
{-
f5 :: Char -> Char
f5 c = f3 (f3 c)
-}