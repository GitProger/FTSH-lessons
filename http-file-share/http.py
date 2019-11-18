#!/usr/bin/env python3

from http.server import HTTPServer, CGIHTTPRequestHandler

class HTTPException(Exception):
    pass

class FileHTTPServer:
    def __init__(self, a="127.0.0.1", p=8080):
        self.__addr = a
        self.__port = p
        self.__conf = (a, p)
    def __run(self):
        HTTPServer(self.__conf, CGIHTTPRequestHandler).serve_forever()
    def __udp(self):
        self.__conf = (self.__addr, self.__port)
    def __info(self):
        print("An exception occured!");
        print("New addr: ", self.__addr, ":", self.__port, sep="")
    def start(self):
        try:
            self.__run()
            return 0
        except PermissionError:
            self.__port = 8080
            self.__udp()
            self.__info()
            self.__run()
        except OSError:
            self.__addr = "127.0.0.1"
            self.__udp()
            self.__info()
            self.__run()
        except:
            raise HTTPException
if __name__ == "__main__":
    import sys
    try:
        exit(FileHTTPServer(sys.argv[1], int(sys.argv[2])).start())
    except HTTPException:
        print("An unresolved HTTP exception occured!")
        exit(-2)
    except:
        print()
        print("An unresolved exception occured!")
        exit(-3)
