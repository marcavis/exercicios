{-# LANGUAGE DeriveDataTypeable, StandaloneDeriving #-}
import Data.Typeable
import Data.Data

a = Frac 2 5
b = Frac 3 4
c = Frac (-2) 3

d = Mono 1 x 1
e = Mono 1 x 2
f = Mono (-2) x 3
ff = Mono 3 x (Frac 1 3)
g = Poly [e,f,e]
h = Poly [ff]
x = Var 'x'
y = Var 'y'
{--
data FuncType 	= Ln Fraction Polynomial Fraction
				| Cos Fraction Polynomial Fraction
				| Sin Fraction Polynomial Fraction
				| Tan Fraction Polynomial Fraction
				| Sec Fraction Polynomial Fraction
				| Csc Fraction Polynomial Fraction
				| Cot Fraction Polynomial Fraction
	deriving (Typeable, Data)

instance Show FuncType where
	show (Ln a x n) 	= showFinal "ln" a x n
	show (Cos a x n) 	= showFinal "cos" a x n
	show (Sin a x n) 	= showFinal "sin" a x n
	show (Tan a x n) 	= showFinal "tan" a x n
	show (Sec a x n) 	= showFinal "sec" a x n
	show (Csc a x n) 	= showFinal "csc" a x n
	show (Cot a x n) 	= showFinal "cot" a x n


--helper functions for displaying (or not) the exponents and multiples (as in the 2 in 2ln(x))
showFinal :: String -> Fraction -> Polynomial -> Fraction -> String
showFinal name a x n = show' a ++ name ++ showPow n ++ "(" ++ show x ++ ")" 
show' :: Fraction -> String
show' a = case a of
	(-1) -> "-"
	1 -> ""
	a' -> show a
showPow :: Fraction -> String
showPow n = case n of
	1 -> ""
	n' -> "^" ++ show n
--}

data Fraction = Frac Integer Integer
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

num :: Fraction -> Integer
num (Frac a _) 		= a

denom :: Fraction -> Integer
denom (Frac _ b)	= b

simplify :: Fraction -> Fraction
simplify (Frac a b) = signAdjust(Frac (quot a factor) (quot b factor)) where
	factor = gcd a b

--move sign to the numerator, if needed, and simplify duplicate minus signs
signAdjust :: Fraction -> Fraction
signAdjust (Frac 0 b) = Frac 0 b
signAdjust (Frac a b) = if signum b == (-1) then Frac (-a) (-b) else Frac a b

data Monomial 	= Mono Fraction Monomial Fraction
				| Var Char
				| Mult Fraction Monomial Monomial Fraction
				| Sum Fraction Monomial Monomial Fraction
				| Poly [Monomial]
				| Ln Fraction Monomial Fraction
				| Cos Fraction Monomial Fraction
				| Sin Fraction Monomial Fraction
				| Tan Fraction Monomial Fraction
				| Sec Fraction Monomial Fraction
				| Csc Fraction Monomial Fraction
				| Cot Fraction Monomial Fraction
	deriving (Typeable, Data)

instance Show Monomial where
	show (Mono a x 0)	= show a
	show (Mono a x n)	= show' a ++ show x ++ showPow n
	show (Var x) 			= id [x]
	show (Mult a x1 x2 n)	= show' a ++ "(" ++ show x1 ++ " * " ++ show x2 ++ ")" ++ showPow n
	show (Sum a x1 x2 n)	= show' a ++ "(" ++ show x1 ++ " + " ++ show x2 ++ ")" ++ showPow n
	show (Poly (x:[])) = show x
	show (Poly (x:xs)) = show x ++ " " ++ sign' sign'' ++ show (Poly xs) where
		sign' "1" = "+"
		sign' _ = ""
		sign''  = (show . sign . head) xs
	show (Ln a x n) 	= showFunc "ln" a x n
	show (Cos a x n) 	= showFunc "cos" a x n
	show (Sin a x n) 	= showFunc "sin" a x n
	show (Tan a x n) 	= showFunc "tan" a x n
	show (Sec a x n) 	= showFunc "sec" a x n
	show (Csc a x n) 	= showFunc "csc" a x n
	show (Cot a x n) 	= showFunc "cot" a x n

showMono :: Fraction -> Monomial -> Fraction -> String
showMono a x n = show' a ++ show x ++ showPow n

--showDuo :: Fraction -> Monomial -> Monomial -> Fraction -> String
--showDuo a x1 x2 n = show' a ++ "((" ++ show x1 ++ ")(" ++ show x2 ++ "))" ++ showPow n

showFunc :: String -> Fraction -> Monomial -> Fraction -> String
showFunc name a x n = show' a ++ name ++ showPow n ++ "(" ++ show x ++ ")" 
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
sign (Mult s _ _ _) = signum s

derive :: Monomial -> Monomial
derive (Var x) = Mono 1 (Var x) 0
derive (Mono a x 0) = Mono 0 x 0
derive (Mono a x n) = Mono (a*n) x (n-1)
derive (Sum 1 x1 x2 1) = Sum 1 (derive x1) (derive x2) 1
derive (Sum a x1 x2 0) = derive (Mono a x 0)
derive (Sum a x1 x2 n) = Mult 1 (Sum (a*n) x1 x2 (n-1)) (Sum 1 (derive x1) (derive x2) 1) 1
derive (Ln a x 1) = Mult 1 (Mono a x (-1)) (derive x) 1
derive (Cos a x 1) = Mult 1 (Sin (-a) x 1) (derive x) 1
derive (Sin a x 1) = Mult 1 (Cos a x 1) (derive x) 1
derive (Tan a x 1) = Mult 1 (Sec a x 2) (derive x) 1
derive (Cot a x 1) = Mult 1 (Csc (-a) x 2) (derive x) 1
derive (Poly l) = Poly (map derive l)

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
