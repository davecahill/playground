package expr

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import Parser.parse
import Operator._
import expr.{Operator => O}

class ParserSpec extends Spec with ShouldMatchers {
  val examples = Array(
    "1" -> Num(1)
    , "X" -> Name("X")
    , "1 + X" -> BinOp(O.+, Num(1), Name("X"))
    , "X * 2" -> BinOp(*, Name("X"), Num(2))
    , "2 ** X" -> BinOp(**, Num(2), Name("X"))
    , "1 ** 2 + 3 * 4" -> BinOp(O.+, BinOp(**, Num(1), Num(2)), BinOp(*, Num(3), Num(4)))
  )

  for ((input, ast) <- examples) {
    it("parses " + input) {
      expect(parse(input)) { ast }
    }
  }

  it("throws an exception on invalid input") {
    val invalidInput = "+ + +"
    intercept[BadInputException] { parse(invalidInput) }
  }
}