import key.*
import java.math.BigInteger
import java.util.Random
import java.io.File

const val PRIVATE   = "private.txt"
const val PUBLIC    = "public.txt"
const val ENCRYPTED = "encrypted.txt"
const val DECRYPTED = "decrypted.txt"
const val MESSAGE   = "message.txt"

fun fget(fname: String) = File(fname).readText()
fun fput(fname: String, text: String) = File(fname).writeText(text)


fun main() {
    println("/!\\ This program encodes and decodes unicode strings (or byte arrays), not bit arrays")
    fun help() {
        println("(m) Create keys ('public.txt', 'private.txt')")
        println("(e) Encrypt (from 'message.txt' to 'encrypted.txt')")
        println("(d) Decrypt (from 'encrypted.txt' to 'decrypted.txt')")
        println("(h) This menu")
        println("(q) Exit")
    } 
    help()
    while (true) {
        print("-> ")
        val cmd = readLine()!!
        if (cmd == "m") {
            print("length (bytes) (enter -> 128): ")
            val ln = readLine()!!.toIntOrNull()
            print("Seed (enter -> random): ")
            val sd = readLine()!!.toLongOrNull()
            
            val priv = PrivateKey(
                if (ln != null) ln else 128,
                if (sd != null) sd else -1L
            )
            val pub = priv.toPublicKey()
            priv.dump(PRIVATE)
            pub.dump(PUBLIC)
        } else if (cmd == "e") {
            val pub = PublicKey.load(PUBLIC)
            fput(ENCRYPTED, 
                pub.encodeString(fget(MESSAGE)).toString()
            )
        } else if (cmd == "d") {
            val prv = PrivateKey.load(PRIVATE)
            fput(DECRYPTED, 
                prv.decodeString(
                    BigInteger(fget(ENCRYPTED))
                )
            )
        } else if (cmd == "h") {
            help()
        } else if (cmd == "q") {
            return 
        } else {
            println("Unrecognized command")
        }
    }
}


