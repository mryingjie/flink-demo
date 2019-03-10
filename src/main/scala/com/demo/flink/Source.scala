package com.demo.flink

import org.apache.flink.streaming.api.scala._


/**
  * @Author ZhengYingjie
  * @Date 2019/3/10
  * @Description
  */
object Source {

  def main(args: Array[String]): Unit = {

    //自动获取当前运行环境 并行度为8
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //并行度为1
//    val environment = StreamExecutionEnvironment.createLocalEnvironment(1)



    //1 基于文件的数据源

//    val source = env.readTextFile("test00.txt")

    //2 基于socket的数据源  cmd nc -l -p 11111

//    val source = env.socketTextStream("localhost",11111)

    //3 基于collection（集合）的数据源

//    val list = List(1,2,3,4)
//
//    val source = env.fromCollection(list)

    val source = env.generateSequence(0,20)


    //
    source.print()
    env.execute("FileSource")

  }

}
