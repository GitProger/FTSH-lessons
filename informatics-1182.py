if __name__ == "__main__":
    def search(a, diff, left=[], right=[]):
        if diff == 0:
            print(*left) if left else print(0)
            print(*right)
            exit(0)
        if not a:
            return
        search(a[1:], diff, left, right)
        search(a[1:], diff + a[0], left + [a[0]], right)
        search(a[1:], diff - a[0], left, right + [a[0]])

    w = int(input())
    n = int(input())
    a = list(map(int, input().split()))
    search(a, w)
    print(-1)
