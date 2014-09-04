package skalholt.codegen.main

object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(92); 
  println("Welcome to the Scala worksheet") //
  import com.typesafe.config._;$skip(45); 

var a = "Ab";System.out.println("""a  : String = """ + $show(a ));$skip(41); val res$0 = 
a.take(1).toLowerCase         +a.drop(1);System.out.println("""res0: String = """ + $show(res$0));$skip(13); val res$1 = 
a(0).isUpper;System.out.println("""res1: Boolean = """ + $show(res$1))}

}
