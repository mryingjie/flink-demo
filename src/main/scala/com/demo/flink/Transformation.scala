package com.demo.flink

import org.apache.flink.streaming.api.scala._

/**
  * @Author ZhengYingjie
  * @Date 2019/3/10
  * @Description
  */
object Transformation {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val source = env.readTextFile("test00.txt")

    //对每一行数据进行map
    val steamMap = source.map(_+"_test")

    //按 " "切分
    val value = steamMap.flatMap(_.split(" "))

    //过滤
    val filter = value.filter("hadoop".equals(_))
//    filter.print()

    //connect 合并DataStream 其实内部还是两个流 后续操作都是分开的 生成colMap
    val source_1 = env.readTextFile("test01.txt")

    val connRes = source_1.connect(filter)
    //union 合并DataStream 真正的合并为一个流
//    source_1.union(filter)

    //分开操作每一个流 互不影响
    val res = connRes.map(_+"<---source_1",_+"<---filter")

    //对一个流进行切分  按规则切分为两个流 并分别起名字
    val split = res.flatMap(_.split(" ")).split(
      word => word match {
        case "hadoop<---filter" => List("hadoop")
        case _ => List("other")
      }
    )
    //选择将两个流中的一个返回
    split.select("hadoop").print()




    env.execute("Transformation")




  }

}
