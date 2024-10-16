module SeqDemo where

import           Seq
import           Test.QuickCheck (Property, quickCheck, (==>))

main :: IO ()
main = do
  quickCheck (property :: Int -> Int -> Property)
  quickCheck (property 9 99999)
  where
    property d n = 0 <= d && d < 10 ==>
                fromInt (abs n + d) == addSingleDigit d (fromInt (abs n))
      where
        fromInt n = aux n Empty
          where
            aux n xs
              | n < 10    = Node n xs
              | otherwise = aux n' (Node d xs)
              where (n', d) = divMod n 10
