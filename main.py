import sys, os, math, random;

class Answer:
	def __init__(self, txt):
		self.__text = txt
	def what(self):
		return self.__text
class ContestException(Exception):
	pass
def answer(arg):
	raise Answer
def gcd(a, b: int):
	while b: a, b = b, a % b
	return a
def lcm(a, b: int):
	return a * b // gcd(a, b)
def readArr():
    return list(map(int, input().split()))

class ContestProgram:
	def __init__(self):
        raise Exception("This class has only static members (ContestProgram)")
    def main(argv: list):
        ...
        return 0

if (__name__ == "__main__"):
    try:
        exit(ContestProgram.main(sys.argv))
    except Answer as a:
		print(a.what())
		exit(0);
    except Exception as e:
		print("An exception occured:", e.args)
		exit(-1)
    finally:
		pass
