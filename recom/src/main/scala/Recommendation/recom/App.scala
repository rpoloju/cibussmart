package Recommendation.recom
import org.apache.spark.sql.SparkSession
import java.io.PrintWriter;
import java.io.File;
import org.apache.spark.SparkContext
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.Rating

/**
 * @author ${Ravi Teja Poloju}
 */
object App {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {

    try {
      val sparksession = SparkSession.builder().appName("Recommendation Initial").getOrCreate()
      val sc = sparksession.sparkContext
      val rawdata = sc.textFile("file:/home/hadoopuser/Downloads/demo/ratings.csv")
      //val rawdata = sc.textFile("hdfs://localhost:9000/user/hadoopuser/ratings.csv");

      rawdata.first();

      val rawratings = rawdata.map(_.split(",").take(3))

      val ratings = rawratings.map {
        case Array(user, item, rating) => Rating(user.toInt, item.toInt, rating.toDouble)
      }

      ratings.first()

      val model = ALS.train(ratings, 50, 10, 0.01)

      //val predictedRating = model.predict(23, 2)

      val userid = args(0).toInt

      val k = 10

      val topKRecs = model.recommendProducts(userid, k)

      println(topKRecs.mkString("\n"))

      val movies = sc.textFile("file:/usr/local/items.csv")
      //val movies = sc.textFile("hdfs://localhost:9000/user/hadoopuser/items.csv")

      val titles = movies.map(line => line.split(",").take(2)).map(array => (array(0).toInt,
        array(1))).collectAsMap()

      val moviesForUser = ratings.keyBy(_.user).lookup(userid)

      println(moviesForUser.size)

      val op = moviesForUser.sortBy(-_.rating).take(10).map(rating => (titles(rating.product), rating.rating))

      //sc.parallelize(op.toSeq).saveAsTextFile("file:/usr/local/ops");
      
      sc.parallelize(topKRecs.toSeq).saveAsTextFile("file:/usr/local/ops");
      
    } catch {
      case ex: NoSuchElementException => {
        println("No element")
      }
    }
    //sc.parallelize(op.toSeq).saveAsTextFile("file:/usr/local/spout.txt");
  }

}
