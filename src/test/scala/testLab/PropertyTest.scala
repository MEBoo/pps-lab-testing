package testLab

import org.junit.runner.RunWith
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop, Properties}
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.{Checkers, PropertyChecks}
import org.scalatest.{Matchers, PropSpec}
import testLecture.ScalaCheckJUnitRunner

// PROPERTY TEST WITH SCALA-CHECK
@RunWith(classOf[ScalaCheckJUnitRunner])
class TestOnLists extends Properties("Lists") {
  def genericAssocProp[A:Arbitrary]: Prop = forAll{
    (xs: List[A], ys: List[A], zs: List[A]) => (xs ++ ys) ++ zs == xs ++ (ys ++ zs)
  }
  property("Associative of concatenation") = Prop.all(genericAssocProp[Int], genericAssocProp[String], genericAssocProp[(Boolean,Double)])

  property("Empty concatenation") = forAll {
    (xs: List[Int]) => List.empty ++ xs == xs
  }

  property("Map with identity yields the same list") = forAll {
    (xs: List[Int]) => xs.map(identity) == xs
  }

  val increment: Int => Int = _ + 1
  val double: Int => Int = _ * 2

  property("Map with f(g()) equals to map first with g() then map with f()") = forAll {
    (xs: List[Int]) => xs.map(increment compose double) == xs.map(double).map(increment)
  }
}

// PROPERTY TEST WITH SCALA-TEST USING PropertyChecks AND ALSO Checkers
@RunWith(classOf[JUnitRunner])
class ListSpec extends PropSpec with PropertyChecks with Checkers with Matchers {
  property("List concatenation with an empty list give the first") {
    // ScalaTest's property-based testing style
    forAll {
      (xs: List[Int]) => List.empty ++ xs == xs
    }
    // ScalaCheck's property-based testing style
    check { Prop.forAll { (xs: List[Int]) => List.empty ++ xs == xs } }
  }
}

@RunWith(classOf[ScalaCheckJUnitRunner])
class TestOnPalindrome extends Properties("Palindrome") {

  val gen = for {
    str <- Gen.alphaStr
    c <- Gen.option(Gen.alphaChar)
  } yield str ++ c ++ str.reverse

  property("equals its reverse") = forAll(gen) {
    (s:String) => s == s.reverse
  }
}