class ContestRuntimeException(Exception):
    pass
class MainProgram:
    def main(argv: list):
        ...
        return 0

################################################################################
##############################   Initialization   ##############################
################################################################################
if (__name__ == "__main__"):
    import sys, os, math, random
    retcode = MainProgram.main(sys.argv)
    exit(retcode)
