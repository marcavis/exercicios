{-# LANGUAGE TypeOperators #-}
{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE FlexibleContexts #-}
{-# LANGUAGE DeriveGeneric #-}

module Demo1 where


import GHC.Generics



data MyType = Con1 Int | Con2 Bool | Con3
  deriving (Generic)


main =
  do print (sameCon (Con1 1) (Con2 True))
     print (sameCon (Con1 1) (Con1 2))









sameCon :: (Generic a, GSameCon (Rep a)) => a -> a -> Bool
sameCon x y = gsameCon (from x) (from y)

class GSameCon f where
  gsameCon :: f p -> f p -> Bool

instance GSameCon f => GSameCon (D1 c f) where
  gsameCon (M1 x) (M1 y) = gsameCon x y

instance (GSameCon f, GSameCon g) => GSameCon (f :+: g) where
  gsameCon (L1 x) (L1 y) = gsameCon x y
  gsameCon (R1 x) (R1 y) = gsameCon x y
  gsameCon _      _      = False

instance GSameCon (C1 c f) where
  gsameCon _ _ = True
