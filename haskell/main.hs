a = Frac 2 5
b = Frac 3 4
c = Frac (-2) 3

e = Mono 1 x 2
f = Mono (-2) x 3
ff = Mono 3 x (Frac 1 3)
g = Poly [e,f,e]
h = Poly [ff]
x = Var 'x'
y = Var 'y'

data Variable = Var Char

instance Show Variable where
	show (Var a) = id [a]

data FuncType = Ln Fraction Monomial Fraction

instance Show FuncType where
	show (Ln a x n) = show' a ++ "ln" ++ showPow n ++ "(" ++ show x ++ ")" where
		show' a = case a of
			(-1) -> "-"
			1 -> ""
			a' -> show a
		showPow n = case n of
			1 -> ""
			n' -> "^" ++ show n


data Fraction = Frac Integer Integer

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
simplify (Frac a b) = Frac (quot a factor) (quot b factor) where
	factor = gcd a b

data Monomial 	= Mono Fraction Variable Fraction
				| Func Fraction FuncType Fraction

instance Show Monomial where
	show (Mono a x 0) = show a
	show (Mono 1 x 1) = show x
	show (Mono 1 x n) = show x ++ "^" ++ show n
	show (Mono (-1) x 1) = "-" ++ show x
	show (Mono (-1) x n) = "-" ++ show x ++ "^" ++ show n
	show (Mono a x 1) = show a ++ show x
	show (Mono a x n) = show a ++ show x ++ "^" ++ show n 

sign :: Monomial -> Fraction
sign (Mono a _ _) = signum a

data Polynomial = Poly [Monomial]

instance Show Polynomial where
	show (Poly (x:[])) = show x
	show (Poly (x:xs)) = show x ++ " " ++ sign' sign'' ++ show (Poly xs) where
		sign' "1" = "+"
		sign' "-1" = ""
		sign''  = (show . sign . head) xs

derivative :: Polynomial -> Polynomial
derivative (Poly l) = Poly (map deriv' l)

deriv' :: Monomial -> Monomial
deriv' (Mono a x n) = Mono (a*n) x (n-1)

integrate :: Polynomial -> Polynomial
integrate (Poly l) = Poly (map integ' l)

integ' :: Monomial -> Monomial
integ' (Mono a x (-1))	= Mono 3 x 3 --ln...
integ' (Mono a x n)		= Mono (a/(n+1)) x (n+1)
