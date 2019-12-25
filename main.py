import sys, os
from math import *
from random import *


class Answer(BaseException):
	def __init__(self, txt):
		self.__text = txt
	def what(self):
		return self.__text
def answer(arg):
	raise Answer
def gcd(a, b: int):
	while b: a, b = b, a % b
	return a
def lcm(a, b: int):
	return a * b // gcd(a, b)
def readArr(__type):
    return list(map(__type, input().split()))
def chomp(s: str):
    if s[-1] == '\n':
        s = s[:-1]
    return s
def readFile(name: str):
    f = open(name, "r");
    r = f.readlines();
    f.close()
    return r
def init_all():
    sys.setrecursionlimit(max(sys.getrecursionlimit(), 500000))
    

def lower_bound(a, k):
    l, r = 0, len(a) - 1
    while l != r:
        mid = (l + r) // 2
        if a[mid] < k:
            l = mid + 1
        else:
            r = mid
    return l
            
def upper_bound(a, k):
        l, r = 0, len(a) - 1
        while l + 1 < r:
            mid = (l + r) // 2
            if a[mid] <= k:
                l = mid
            else:
                r = mid - 1
        if a[r] == k:
            return r
        else:
            return l

N = 128
g = [[] for _ in range(N)]
u = [0 for _ in range(N)]
order = []
def dfs(v: int):
    global g, u, order
    u[v] = 1
    for to in g[v]:
        if g[to] == 1:
            raise Exception("Error in task")
        if g[to] == 0:
            dfs(to)
    u[v] = 2
    order.append(v)
def topsort():
    global order, g, u
    for i in range(len(u)):
        if used[i] == 0:
            dfs(i)
    order.reverse()

def main(argv: list):
    init_all();

    return 0


if (__name__ == "__main__"):
    try:
        exit(main(sys.argv))
    except Answer as a:
        print(a.what())
        exit(0)
    except Exception as e:
        print("An exception occured:", *e.args)
        exit(-1)
