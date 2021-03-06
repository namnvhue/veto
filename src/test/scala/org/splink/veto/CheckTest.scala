package org.splink.veto

import org.scalatest.{FlatSpec, Matchers}

class CheckTest extends FlatSpec with Matchers {

  case class Person(name: String)

  val nonEmptyString = Validator[String] { (value, context) =>
    if (value.nonEmpty) Valid(value) else Invalid(Error(context, 'oopsString))
  }

  "Check" should "produce Valid if all is well" in {
    val max = Person("max")

    val result = Check(max)
      .field(_.name, "name")(nonEmptyString)
      .validate

    result should equal(Valid(max))
  }

  it should "produce Invalid if a field is invalid" in {
    val max = Person("")

    val result = Check(max)
      .field(_.name, "name")(nonEmptyString)
      .validate

    result should equal(Invalid(Error(Context(max, "name", ""), 'oopsString)))
  }

  case class Coffee(roast: String, topping: Option[String])

  def nonEmptyOption[T] = Validator[Option[T]] { (value, context) =>
    if (value.nonEmpty) Valid(value) else Invalid(Error(context, 'oopsOption))
  }

  it should "produce Valid if an Option field is set" in {
    val cappuccino = Coffee("italian", Some("milk-foam"))

    val result = Check(cappuccino)
      .field(_.roast, "roast")(nonEmptyString)
      .field(_.topping, "topping")(nonEmptyOption[String])
      .validate

    result should equal(Valid(cappuccino))
  }

  it should "produce Invalid if an Option field is empty" in {
    val espresso = Coffee("italian", topping = None)

    val result = Check(espresso)
      .field(_.roast, "roast")(nonEmptyString)
      .field(_.topping, "topping")(nonEmptyOption[String])
      .validate

    result should equal(Invalid(Error(Context(espresso, "topping", None), 'oopsOption)))
  }

  it should "produce Invalid with multiple errors, if multiple fields are invalid" in {
    val uhh = Coffee("", topping = None)

    val result = Check(uhh)
      .field(_.roast, "roast")(nonEmptyString)
      .field(_.topping, "topping")(nonEmptyOption[String])
      .validate

    result should equal(Invalid(
      Error(Context(uhh, "roast", ""), 'oopsString),
      Error(Context(uhh, "topping", None), 'oopsOption)))
  }

  case class Letters(xs: Seq[String])

  def nonEmptySeq[T] = Validator[Seq[T]] { (value, context) =>
    if (value.nonEmpty) Valid(value) else Invalid(Error(context, 'oopsSeq))
  }

  it should "produce Valid if a Seq field is set" in {
    val letters = Letters(Seq("a", "b", "c"))

    val result = Check(letters)
      .field(_.xs, "xs")(nonEmptySeq[String])
      .validate

    result should equal(Valid(letters))
  }

  it should "produce Invalid if a Seq field is empty" in {
    val letters = Letters(Seq.empty[String])

    val result = Check(letters)
      .field(_.xs, "xs")(nonEmptySeq[String])
      .validate

    result should equal(Invalid(Error(
      Context(letters, "xs", Seq.empty[String]), 'oopsSeq)))
  }
}
