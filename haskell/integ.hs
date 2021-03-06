{-# LANGUAGE DeriveDataTypeable, StandaloneDeriving #-}
import Data.Typeable
import Data.Data

a = Frac 2 5
b = Frac 3 4
c = Frac (-2) 3

d = Mono 1 x 1
e = Mono 1 x 2
f = Mono (-2) x 3
fcos = Cos x 1
fsin = Sin x 1
flippy = derive (Mult fsin fsin)
dfcos2 = derive (Mult fcos fcos)
ff = Mono 3 x (Frac 1 3)
g = Poly [e,f,e]
h = Poly [ff]
x = Var 'x'
y = Var 'y' 	

--Return the string representation of a monomial's constructor
kind :: Monomial -> String
kind = show . toConstr

--Return an empty constructor of the same kind as the input
same :: Monomial -> Monomial -> Fraction -> Monomial
same = readF . kind

lookM x = (kind x, multiple x, term x, power x)
lookS x = (kind x, first x, second x)

data Fraction = Frac {num :: Integer, denom :: Integer}
	deriving (Typeable, Data)

instance Show Fraction where
	show (Frac a 1) = show a
	show (Frac a b) = "(" ++ show a ++ "/" ++ show b ++ ")"

instance Num Fraction where
	(+) (Frac a b) (Frac c d) 	= 	simplify (Frac (a*d + b*c) (b*d))
	(-) (Frac a b) (Frac c d)	=	(Frac a b) + (Frac (-c) d)
	(*) (Frac a b) (Frac c d)	=	simplify (Frac (a*c) (b*d))
	negate (Frac a b) 			= 	Frac (-a) b
	abs (Frac a b)				= 	Frac (abs a) (abs b)
	fromInteger x				= 	Frac x 1
	signum (Frac 0 b)			= 	0
	signum (Frac a b)			=	if signum a == signum b then 1  else (-1) 

instance Fractional Fraction where
	(/) f g						=	f * (recip g)
	recip (Frac a b)			=	Frac b a		

instance Eq Fraction where
	Frac a b == Frac c d = a == c && b == d

simplify :: Fraction -> Fraction
simplify (Frac a b) = signAdjust(Frac (quot a factor) (quot b factor)) where
	factor = gcd a b

--move sign to the numerator, if needed, and simplify duplicate minus signs
signAdjust :: Fraction -> Fraction
signAdjust (Frac 0 b) = Frac 0 b
signAdjust (Frac a b) = if signum b == (-1) then Frac (-a) (-b) else Frac a b

data Monomial 	= Mono {multiple :: Fraction, term :: Monomial, power :: Fraction}
				| Var {variable :: Char}
				| Mult {first :: Monomial, second :: Monomial}
				| Sum {first :: Monomial, second :: Monomial}
				| Poly [Monomial]
				| Exp {multiple :: Fraction, term :: Monomial}
				| Ln {multiple :: Fraction, term :: Monomial, power :: Fraction}
				| Sin {multiple :: Fraction, term :: Monomial, power :: Fraction}
				| Cos {multiple :: Fraction, term :: Monomial, power :: Fraction}
				| Tan {multiple :: Fraction, term :: Monomial, power :: Fraction}
				| Sec {multiple :: Fraction, term :: Monomial, power :: Fraction}
				| Csc {multiple :: Fraction, term :: Monomial, power :: Fraction}
				| Cot {multiple :: Fraction, term :: Monomial, power :: Fraction}
	deriving (Typeable, Data)
	--deriving (Typeable, Data)

instance Num Monomial where
	(+) 0 b = b
	(+) a 0 = a	
	(+) m1@(Mono a x n) m2@(Mono b y o)
		| x == y && n == o = Mono (a+b) x n
		| otherwise = Sum m1 m2
	(+) m1@(Mult a b) m2@(Mult c d)
		| m1 == m2 = Mono 2 (Mult a b) 1
		| otherwise = Sum m1 m2
	(+) a b				
		| a == b = Mono 2 ((same a) (term a) (power a)) 1
		| a == (-b) = 0
		| kind a == "Mono" && term a == b && power a == 1 = Mono (multiple a + 1) b 1 --a is a Mono containing several b
		| kind b == "Mono" && term b == a && power b == 1 = Mono (multiple b + 1) a 1 --b is a Mono containing several a
		| otherwise = Sum a b
	(-) a b 			= a + (-b)
	(*) 1 b = b
	(*) a 1 = a
	(*) 0 b = 0
	(*) a 0 = 0
	(*) x@(Var v) a = (Mono 1 x 1) * a
	(*) a x@(Var v) = a * (Mono 1 x 1)
	(*) m1@(Mono a x n) m2@(Mono b y o)
		| x == y || n == 0 || o == 0 = Mono (a*b) x (n+o)
		| otherwise = Mult m1 m2
	(*) m1@(Mono a _ 0) b
		| a == 0 = 0
		| a == 1 = b
		| otherwise = Mono a b 1
	(*) a m1@(Mono b _ 0)
		| b == 0 = 0
		| b == 1 = a
		| otherwise = Mono b a 1
	(*) m1@(Mult a b) m2@(Mult c d)
		| m1 == m2 = Mono 1 (Mult a b) 2
		| otherwise = Mult m1 m2
	(*) a b
		| kind a == kind b && term a == term b = (same a) (term a) (power a + power b)
		| otherwise = Mult a b
	negate (Mono a x n)	= Mono (-a) x n
	negate (Poly l)		= Poly (map negate l)
	negate other		= Mono (Frac (-1) 1) other (Frac 1 1)
	abs (Mono a x n)	= Mono (abs a) x n
	abs other			= other
	fromInteger x		= Mono (Frac x 1) (Var 'x') (Frac 0 1)
	signum (Mono a _ _) = Mono (signum a) (Var 'x') (Frac 0 1)
	signum _ 			= 1

instance Eq Monomial where
	Mono 0 x n == Mono 0 b y 	= True
	Mono a _ 0 == Mono b _ 0	= a == b
	Mono a x n == Mono b y o 	= a == b && x == y && n == o
	Mono 0 x n == Sum a b 		= a == 0 && b == 0
	Sum a b == Mono 0 x n 		= a == 0 && b == 0
	Sum a b == Sum c d 			= (a == c && b == d) || (a == d && b == c)
	Mult a b == Mult c d		= (a == c && b == d) || (a == d && b == c)
	Exp x == Exp y				= x == y
	Ln x n == Ln y o 			= x == y && n == o
	Sin x n == Sin y o 			= x == y && n == o
	Cos x n == Cos y o 			= x == y && n == o
	Tan x n == Tan y o 			= x == y && n == o
	Sec x n == Sec y o 			= x == y && n == o
	Csc x n == Csc y o 			= x == y && n == o
	Cot x n == Cot y o 			= x == y && n == o
	Var x == Var y 				= x == y
	_ == _						= False

instance Show Monomial where
	show (Mono 0 _ _)	= show 0
	show (Mono a x 0)	= show a
	show (Mono a (Var x) n)	= show' a ++ id [x] ++ showPow n
	show (Mono a x n)	=	show' a ++ "(" ++ show x ++ ")" ++ showPow n
	show (Var x) 		= id [x]
	show (Mult x1 x2)	= "(" ++ show x1 ++ " * " ++ show x2 ++ ")"
	show (Sum x1 x2)	= "(" ++ show x1 ++ " + " ++ show x2 ++ ")"
	show (Poly (x:[])) = show x
	show (Poly (x:xs)) = show x ++ " " ++ sign' sign'' ++ show (Poly xs) where
		sign' "1" = "+"
		sign' _ = ""
		sign''  = (show . sign . head) xs
	show (Exp x)	= "e^(" ++ show x ++ ")"
	show (Ln x n) 	= showFunc "ln" x n
	show (Cos x n) 	= showFunc "cos" x n
	show (Sin x n) 	= showFunc "sin" x n
	show (Tan x n) 	= showFunc "tan" x n
	show (Sec x n) 	= showFunc "sec" x n
	show (Csc x n) 	= showFunc "csc" x n
	show (Cot x n) 	= showFunc "cot" x n

readF :: [Char] -> Monomial -> Fraction -> Monomial
readF "Ln" = Ln
readF "Sin" = Sin
readF "Cos" = Cos
readF "Tan" = Tan
readF "Sec" = Sec
readF "Csc" = Csc
readF "Cot" = Cot

showFunc :: String -> Monomial -> Fraction -> String
showFunc name x n = name ++ showPow n ++ "(" ++ show x ++ ")" 
show' :: Fraction -> String
show' a = case a of
	(-1) -> "-"
	1 -> ""
	a' -> (show . simplify) a
showPow :: Fraction -> String
showPow n = case n of
	1 -> ""
	n' -> "^" ++ (show . simplify) n

sign :: Monomial -> Fraction
sign (Mono a _ _) = signum a
sign _ = 1


derive :: Monomial -> Monomial
derive (Var x) 				= Mono 1 (Var x) 0
derive (Mono a x 0) 		= Mono 0 x 0
derive (Mono a (Var x) n) 	= Mono (a*n) (Var x) (n-1)
derive (Mono a (Ln x 1) 1)	= (Mono a x (-1)) * (derive x) --not quite
derive (Mono a (Cos x 1) 1)	= (Mono (-a) (Sin x 1) 1) * (derive x)
derive (Mono a (Sin x 1) 1)	= (Mono a (Cos x 1) 1) * (derive x)
derive (Mono a (Tan x 1) 1)	= (Mono a (Sec x 2) 1) * (derive x)
derive (Mono a (Sec x 1) 1)	= (Mono a (Mult (Tan x 1) (Sec x 1)) 1) * (derive x)
derive (Mono a (Csc x 1) 1)	= (Mono (-a) (Mult (Cot x 1) (Csc x 1)) 1) * (derive x)
derive (Mono a (Cot x 1) 1)	= (Mono (-a) (Csc x 2) 1) * (derive x)
derive (Mono a x n) 		= (Mono (a*n) x (n-1)) * (derive x)
derive (Sum x1 x2) 			= (derive x1) + (derive x2)
derive (Mult x1 x2)			= (derive x1) * x2 + x1 * (derive x2)
derive (Exp x)				= (Exp x) * (derive x)
derive (Ln x 1) 			= (Mono 1 x (-1)) * (derive x) --not quite
derive (Cos x 1) 			= (Mono (-1) (Sin x 1) 1) * (derive x)
derive (Sin x 1) 			= (Cos x 1) * (derive x)
derive (Tan x 1) 			= (Sec x 2) * (derive x)
derive (Sec x 1) 			= (Mult (Tan x 1) (Sec x 1)) * (derive x)
derive (Csc x 1) 			= (Mult (Cot x 1) (Mono (-1) (Csc x 1) 1)) * (derive x)
derive (Cot x 1) 			= (Mono (-1) (Csc x 2) 1) * (derive x)
derive (Poly l) 			= simpM $ Poly (map derive l)

simpM :: Monomial -> Monomial
--simpM (Mono a (Mono b y o) n) = 
simpM (Mono 1 x 1) = x
simpM (Mult m1@(Mono a x n) m2@(Mono b y o)) =
	if x == y then Mono (a*b) x (n+o) else Mult m1 m2
simpM (Mult m1@(Mono a x 0) m2) = Mono a (simpM m2) 1
simpM (Mult m1 m2@(Mono a x 0)) = Mono a (simpM m1) 1
simpM (Mult m1 m2) = 
	if toConstr m1 == toConstr m2 then (same m1) (term m1) (power m1 + power m2) else Mult m1 m2 where
	
simpM (Sum m1@(Mono a x n) m2@(Mono b y o)) =
	if x == y && n == o then Mono (a+b) x n else Sum m1 m2
simpM (Sum m1 m2) = 
	if toConstr m1 == toConstr m2 && power m1 == power m2 && term m1 == term m2
	then Mono 2 m1 1 
	else Sum m1 m2
simpM other = other


{--
data Polynomial = Poly [Monomial]
				| Var Char

	deriving (Typeable, Data)

instance Show Polynomial where
	show (Var x) = id [x]
	show (Poly (x:[])) = show x
	show (Poly (x:xs)) = show x ++ " " ++ sign' sign'' ++ show (Poly xs) where
		sign' "1" = "+"
		sign' _ = ""
		sign''  = (show . sign . head) xs

derive :: Polynomial -> Polynomial
derive (Poly l) = Poly (map deriv' l)

deriv' :: Monomial -> Monomial
deriv' (Mono a x 0) = Mono 0 x 0
deriv' (Mono a x n) = Mono (a*n) x (n-1)
deriv' (Func (Cos a x 1)) = Func (Sin (-a) x 1)
deriv' (Func (Sin a x 1)) = Func (Cos a x 1)
deriv' (Func (Tan a x 1)) = Func (Sec a x 2)
deriv' (Func (Cot a x 1)) = Func (Csc (-a) x 2)
deriv' (Mult a b) = Sum (Mult a (deriv' b)) (Mult (deriv' a) b)

integrate :: Polynomial -> Polynomial
integrate (Poly l) = Poly (map integ' l)

integ' :: Monomial -> Monomial
integ' (Mono a x (-1))	= Func (Ln a (Poly [Mono 1 x 1]) 1) 
integ' (Mono a x n)		= Mono (a/(n+1)) x (n+1)

--S udv = uv - S duv
integByParts :: Monomial -> Polynomial
integByParts (Mult a b) = Poly [Mult a (integ' b), Mult (deriv' a) (integ' b)]
--}
