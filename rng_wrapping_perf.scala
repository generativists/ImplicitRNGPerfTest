import org.apache.commons.math3.random._
import scala.util.Random.javaRandomToRandom
import org.apache.commons.math3.random._


object Implicits {
  implicit def scalaRandom2JavaRandom(r: util.Random): java.util.Random = {
    r.self
  }

  implicit def random2RandomGenerator(r: scala.util.Random): RandomGenerator = {
    r.self.asInstanceOf[RandomAdaptor]
  }
}


object RNGWrappingPerformance {
  import Implicits._

  def timeItStats[R](name: String, n: Int)(f: => R): Unit = {
    val totalTime = (0 until n).map { _ =>
      val start = System.currentTimeMillis
      f
      System.currentTimeMillis - start
    }.sum
    val perSecond = 1.0 / (totalTime.toDouble / n) * 1000.0

    println(s"$name\t${totalTime}ms\t$perSecond/s")
  }

  val n = 10000000

  def main(args: Array[String]): Unit = {
    val scalaRng    = new scala.util.Random()
    val javaRng     = new scala.util.Random()
    val math3MT     = new MersenneTwister()
    val math3RA     = new RandomAdaptor(new MersenneTwister())
    val scalaMath3  = new scala.util.Random(
      new RandomAdaptor(new MersenneTwister())
    )

    timeItStats("scala.util.Random", n) {
      scalaRng.nextDouble
    }

    timeItStats("java.util.Random ", n) {
      javaRng.nextDouble
    }

    timeItStats("MersenneTwister  ", n) {
      math3MT.nextDouble
    }

    timeItStats("RandomAdaptor MT ", n) {
      math3RA.nextDouble
    }


    println("\n----\n")

    timeItStats("scala.util.Random  =>  java.util.Random", n) {
      val rng: java.util.Random = scalaRng
      rng.nextDouble
    }

    timeItStats("java.util.Random   => scala.util.Random", n) {
      val rng: scala.util.Random = javaRng
      rng.nextDouble
    }

    timeItStats("scala.util.Random  =>   RandomGenerator", n) {
      val rng: RandomGenerator = scalaMath3
      rng.nextDouble
    }
  }

  //def scalaPerformance()
}