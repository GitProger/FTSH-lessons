package key
import java.math.BigInteger
import java.util.Random
import java.io.File

fun Int.toBigInt() = BigInteger(this.toString())
fun Int.to01() = if (this == 0) 0 else 1
const val BITS_COUNT = 8
final class TooLongByteSequenceEncoded : Exception() {}

open class PublicKey(public var nums: List<BigInteger>) {
    fun encode(msg: ByteArray): BigInteger {
        if (msg.size * BITS_COUNT > nums.size) throw TooLongByteSequenceEncoded()
        val bits = msg.flatMap { byte ->
            (0 until BITS_COUNT).toList().map { 
                (byte.toInt() and (1 shl it)).to01() 
            }
        }
        return (0 until bits.size).toList()
               .map { bits[it].toBigInt() * nums[it]}
               .reduce { a, b -> a + b }
    }
    fun encodeString(msg: String) = this.encode(msg.toByteArray())

    override fun toString() = nums.joinToString(" ")
    fun dump(fname: String) = File(fname).writeText(toString())
    companion object {
        fun load(fname: String): PublicKey {
            val lines = File(fname).readLines()
            return PublicKey(lines[0].split(" ").map { BigInteger(it) })
        }
    }
}

open class PrivateKey(
    private var maxLen: Int = 2048, // bits
    private val seed: Long = -1L
) { 
    private var nums = MutableList<BigInteger>(maxLen) { 0.toBigInt() }
    private var m = 0.toBigInt()
    private var p = 0.toBigInt()
    private val rnd = Random()
    init {
        if (seed != -1L) rnd.setSeed(seed);
        var sum = 0.toBigInt()
        for (i in 0 until maxLen) {
            nums[i] = sum + 1.toBigInt() + BigInteger(4, rnd)
            sum += nums[i]
        }
        p = sum.nextProbablePrime()
        do {
            m = BigInteger(p.bitLength(), rnd)
        } while (m >= p)
    }

    fun getSeed() = seed
    fun getLength() = maxLen
    fun decode(c: BigInteger): ByteArray {
        var sum = (c * m.modInverse(p)) % p
        var bits = MutableList(nums.size) {0}
        for (i in nums.size - 1 downTo 0) {
            if (nums[i] <= sum) {
                bits[i] = 1
                sum -= nums[i]
            }
        }
        return bits.chunked(BITS_COUNT).map {
            (0 until BITS_COUNT).sumBy { i -> 
                it[i] * (1 shl i)
            }.toByte()
        }.toByteArray()
	}
	fun decodeString(c: BigInteger) = String(this.decode(c))
    fun toPublicKey(): PublicKey = PublicKey(nums.map { (it * m) % p })

    override fun toString() = "$m $p\n" + nums.joinToString(" ")
    fun dump(fname: String) = File(fname).writeText(toString())
    companion object {
        fun load(fname: String): PrivateKey {	
            val lines = File(fname).readLines()
            var res = PrivateKey(0)
            val (mm, pp) = lines[0].split(" ").map { BigInteger(it) }
            res.m = mm
            res.p = pp
            res.nums = lines[1].split(" ").map { BigInteger(it) }.toMutableList()
            res.maxLen = res.nums.size
            return res
        }
    }
}
