/**
  * Created by zhouqihua on 2017/8/11.
  */

import org.apache.spark._
import org.apache.spark.SparkContext._

object WordCount {
  def main(args: Array[String]): Unit = {
    val inputFile =  "./helloInput"
    val outputFile = "./helloOutput"

//    val conf = new SparkConf().setMaster("local").setAppName("wordCount")
    val conf = new SparkConf().setAppName("KMSparkWordCount").setMaster("spark://192.168.10.18:7077")
      .setJars(List("/Users/zhouqihua/Desktop/SparkWordCount-IDEA-sbt-Scala2_11_11/out/artifacts/SparkWordCount_IDEA_sbt_Scala2_11_11_jar/SparkWordCount-IDEA-sbt-Scala2_11_11.jar"))
        //.set("spark.executor.extraJavaOptions", "-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005")

    // Create a Scala Spark Context.
    val sc = new SparkContext(conf)
    // Load our input data.
    val input =  sc.textFile(inputFile)
    // Split up into words.
    val words = input.flatMap(line => line.split(" "))
    // Transform into word and count.
    val counts = words.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}
    // Save the word count back out to a text file, causing evaluation.
    counts.saveAsTextFile(outputFile)

    Thread.sleep(100000)
    println("Thread Sleep for 100s ...")
  }
}

