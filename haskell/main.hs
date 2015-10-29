a = Frac 2 5
b = Frac 3 4

data Fraction = Frac Integer Integer

instance Show Fraction where
	show (Frac a b) = (show a) ++ "/" ++ (show b)

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

num :: Fraction -> Integer
num (Frac a _) 		= a

denom :: Fraction -> Integer
denom (Frac _ b)	= b

simplify :: Fraction -> Fraction
simplify (Frac a b) = Frac (quot a factor) (quot b factor) where
	factor = gcd a b


