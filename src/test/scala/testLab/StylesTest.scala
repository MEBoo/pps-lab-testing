package testLab

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest._

trait CartFixture extends BeforeAndAfterEach  { this: Suite =>
  val (p1,p2) = (Product("Shoe"), Product("Hat"))
  var cart:Cart = _
  override def beforeEach() {
    cart = new BasicCart()
    super.beforeEach()
  }
}

trait CatalogFixture extends BeforeAndAfterEach  { this: Suite =>
  val (p1,p2) = (Product("Shoe"), Product("Hat"))
  var catalog:Catalog = _
  override def beforeEach() {
    catalog = new BasicCatalog(Map[Product,Price](
      p1 -> Price(78),
      p2 -> Price(34)
    ))
    super.beforeEach()
  }
}

trait WarehouseFixture extends BeforeAndAfterEach  { this: Suite =>
  val (p1,p2) = (Product("Shoe"), Product("Hat"))
  var warehouse:Warehouse = _
  override def beforeEach() {
    warehouse = new BasicWarehouse
    warehouse.supply(p1, 2)
    warehouse.supply(p2, 50)
    super.beforeEach()
  }
}

/*
  FUNSUITE
*/

@RunWith(classOf[JUnitRunner])
class CartFunSuiteTest extends FunSuite with Matchers with CartFixture {

  test("Cart size should be 2"){
    cart.add(Item(p1, ItemDetails(1, Price(100))))
    cart.add(Item(p2, ItemDetails(2, Price(100))))
    cart.size shouldBe 2
  }

  test("Cart should be empty"){
    cart.size shouldBe 0
  }

  test("Cart total should be 100.0"){
    cart.add(Item(p1, ItemDetails(1, Price(100))))
    cart.totalCost shouldBe 100.0
  }
}

@RunWith(classOf[JUnitRunner])
class CatalogFunSuiteTest extends FunSuite with Matchers with CatalogFixture {
  test("Catalog priceFor one Hat should be 78.0") {
    catalog priceFor(p2, 1) shouldBe Price(34)
  }
  test("Catalog priceFor two Shoe should be 156.0") {
    catalog priceFor(p1, 2) shouldBe Price(156)
  }
}

@RunWith(classOf[JUnitRunner])
class WarehouseFunSuiteTest extends FunSuite with Matchers with WarehouseFixture {
  test("Getting 2x p1 should return 2x p1") {
    warehouse get(p1, 2) shouldBe (p1, 2)
  }
  test("Getting 4x p1 should return 2x p1") {
    warehouse get(p1, 4) shouldBe (p1, 2)
  }
}

/*
  FLATSPEC - BDD
*/

@RunWith(classOf[JUnitRunner])
class CartFlatSpec extends FlatSpec with Matchers with CartFixture {

  "Cart" should "be size 2 with 2 products" in {
    cart.add(Item(p1, ItemDetails(1, Price(100))))
    cart.add(Item(p2, ItemDetails(2, Price(100))))
    assert(cart.size == 2)     // NO MATCHERS
  }

  it should "be empty with no products" in {
    cart.size shouldBe 0      // WITH MATCHERS
  }

  it should "have totalCost = 100.0" in {
    cart.add(Item(p1, ItemDetails(1, Price(100))))
    cart.totalCost shouldBe 100.0
  }
}

@RunWith(classOf[JUnitRunner])
class CatalogFlatSpec extends FlatSpec with Matchers with CatalogFixture {
  "Catalog" should " get right price for a product" in {
    catalog priceFor(p2, 1) shouldBe Price(34)
  }
  it should " get right price for 2 products" in {
    catalog priceFor(p1, 2) shouldBe Price(156)
  }
}

@RunWith(classOf[JUnitRunner])
class WarehouseFlatSpec extends FlatSpec with Matchers with WarehouseFixture {
  "Warehouse" should "get 2x products available" in {
    warehouse get(p1, 2) shouldBe(p1, 2)
  }
  it should " not get products unavailable" in {
    warehouse get(p1, 4) shouldBe(p1, 2)
  }
}

/*
  FUNSPEC - BDD
*/

@RunWith(classOf[JUnitRunner])
class CartFunSpec extends FunSpec with CartFixture {
  describe("A Cart") {
    describe("when empty") {
      it("should have size 0") {
        assert(cart.size == 0)
      }
    }
    describe("when 2 product are added") {

      it("should have size 2") {
        cart.add(Item(p1, ItemDetails(1, Price(100))));
        cart.add(Item(p2, ItemDetails(2, Price(100))));
        assert(cart.size == 2)
      }
      it("should have totalCost = 100") {
        cart.add(Item(p1, ItemDetails(1, Price(100))));
        assert(cart.totalCost == 100.0)
      }
    }
  }
}

@RunWith(classOf[JUnitRunner])
class CatalogFunSpec extends FunSpec with CatalogFixture {
  describe("The default Catalog") {
    it("has priceFor one Hat 78.0") {
      assert(catalog.priceFor(p2, 1) == Price(34))
    }
    it("has priceFor two Shoe 156.0") {
      assert(catalog.priceFor(p1, 2) == Price(156))
    }
  }
}

@RunWith(classOf[JUnitRunner])
class WarehouseFunSpec extends FunSpec with WarehouseFixture {
  describe("The default Warehouse") {
    it("Getting 2x p1 return 2x p1") {
      assert(warehouse.get(p1, 2) == (p1, 2))
    }
    it("Getting 4x p1 return 2x p1") {
      assert(warehouse.get(p1, 4) == (p1, 2))
    }
  }
}

/*
  WORDSPEC - BDD
*/

@RunWith(classOf[JUnitRunner])
class CartWordSpec extends WordSpec with CartFixture {
 "A Cart" can {
    "empty" should {
      "have size 0" in {
        assert(cart.size == 0)
      }
    }
    "with a product" should {
      "have totalCost = 100" in {
        cart.add(Item(p1, ItemDetails(1, Price(100))));
        assert(cart.totalCost == 100.0)
      }
    }
    "with 2 products" should {
      "have size 2" in {
        cart.add(Item(p1, ItemDetails(1, Price(100))));
        cart.add(Item(p2, ItemDetails(2, Price(100))));
        assert(cart.size == 2)
      }
    }
  }
}

/*
  FREESPEC - SUPER BDD
*/

@RunWith(classOf[JUnitRunner])
class CartFreeSpec extends FreeSpec with CartFixture {
  "A Cart" - {
    "when empty" - {
      "should have size 0" in {
        assert(cart.size == 0)
      }
    }
    "when a product is added" - {
      "should have totalCost = 100" in {
        cart.add(Item(p1, ItemDetails(1, Price(100))));
        assert(cart.totalCost == 100.0)
      }
    }
    "with 2 products are added" - {
      "should have size 2" in {
        cart.add(Item(p1, ItemDetails(1, Price(100))));
        cart.add(Item(p2, ItemDetails(2, Price(100))));
        assert(cart.size == 2)
      }
    }
  }
}
