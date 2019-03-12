package day04

/**
  * Created by wangfeng on 18/11/16.
  */
object MyPredef {

  implicit def fileToRichFile(file:String)=new RichFile(file)

  implicit val selectGirl=(g:MyGirl)=> new Ordered[MyGirl]{
    override def compare(that: MyGirl): Int ={
      if(g.faceValue==that.faceValue){
        that.age-g.age
      }else{
        g.faceValue-that.faceValue
      }
    }
  }
  implicit object OrderingGirl extends Ordering[MyGirl]{
    override def compare(x: MyGirl, y: MyGirl): Int = {
      if(x.faceValue==y.faceValue){
        y.age-x.age
      }else{
        x.faceValue-y.faceValue
      }
    }
  }
}
