package org.splink.veto.validators

import org.scalatest.{FlatSpec, Matchers}
import org.splink.veto.{Context, Error, Invalid, Valid}
import org.splink.veto.validators.StringValidators._

class StringValidatorsTest extends FlatSpec with Matchers {
  val emptyContext = Context("", "", "")

  "stringNonEmpty" should "return Valid, if the String is not empty" in {
    stringNonEmpty("hello") shouldBe Valid("hello")
  }

  it should "return Invalid, if the String is empty" in {
    stringNonEmpty("") shouldBe
      Invalid(Error(emptyContext, 'stringNonEmpty, Seq.empty))
  }

  "stringMinLength" should "return Valid, if the String length exceeds the min length" in {
    stringMinLength(3)("hello") shouldBe Valid("hello")
  }

  it should "return Invalid, if the String is shorter than the min length" in {
    stringMinLength(3)("yo") shouldBe
      Invalid(Error(emptyContext, 'stringMinLength, Seq("yo", 3)))
  }

  "stringMaxLength" should "return Valid, if the String length is shorter then the max length" in {
    stringMaxLength(3)("yo") shouldBe Valid("yo")
  }

  it should "return Invalid, if the String exceeds the max length" in {
    stringMaxLength(3)("hello") shouldBe
      Invalid(Error(emptyContext, 'stringMaxLength, Seq("hello", 3)))
  }

  "stringEquals" should "return Valid, if the String is equal to the value" in {
    stringEquals("hello")("hello") shouldBe Valid("hello")
  }

  it should "return Invalid, if the String is not equal to the value" in {
    stringEquals("yo")("hello") shouldBe
      Invalid(Error(emptyContext, 'stringEquals, Seq("hello", "yo")))
  }

  "stringStartsWith" should "return Valid, if the String starts with the value" in {
    stringStartsWith("hello")("hello world") shouldBe Valid("hello world")
  }

  it should "return Invalid, if the String does not start with the value" in {
    stringStartsWith("hello")("hi world") shouldBe
      Invalid(Error(emptyContext, 'stringStartsWith, Seq("hi world", "hello")))
  }

  "stringEndsWith" should "return Valid, if the String ends with the value" in {
    stringEndsWith("world")("hello world") shouldBe Valid("hello world")
  }

  it should "return Invalid, if the String does not end with the value" in {
    stringEndsWith("world!")("hi world") shouldBe
      Invalid(Error(emptyContext, 'stringEndsWith, Seq("hi world", "world!")))
  }

  "stringContains" should "return Valid, if the String ends with the value" in {
    stringContains("world")("hello world") shouldBe Valid("hello world")
  }

  it should "return Invalid, if the String does not end with the value" in {
    stringContains("hello")("hi world") shouldBe
      Invalid(Error(emptyContext, 'stringContains, Seq("hi world", "hello")))
  }

  "stringIsUrl" should "return Valid, if the String is an URL" in {
    stringIsUrl("https://some-url.com") shouldBe Valid("https://some-url.com")
  }

  it should "return Invalid, if the String does not end with the value" in {
    stringIsUrl("htps://some-url.com") shouldBe
      Invalid(Error(emptyContext, 'stringIsUrl, Seq("htps://some-url.com")))
  }

  "stringIsHexColor" should "return Valid, if the String is a hex color" in {
    stringIsHexColor("#ff00") shouldBe Valid("#ff00")
  }

  it should "return Invalid, if the String is not a hex color" in {
    stringIsHexColor("#f") shouldBe
      Invalid(Error(emptyContext, 'stringIsHexColor, Seq("#f")))
  }

  "stringIsUUID" should "return Valid, if the String is a UUID" in {
    stringIsUUID("2a307c0a-d0a1-4dd4-af2c-735c866f1bc8") shouldBe
      Valid("2a307c0a-d0a1-4dd4-af2c-735c866f1bc8")
  }

  it should "return Invalid, if the String is not a UUID" in {
    stringIsUUID("a3c0a-d0a1-41bc8") shouldBe
      Invalid(Error(emptyContext, 'stringIsUUID, Seq("a3c0a-d0a1-41bc8")))
  }

}
