package testLab

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers, OneInstancePerTest}


class ShoppingTests extends FlatSpec with Matchers with OneInstancePerTest with MockFactory {

    val product: Product = Product("Test")
    // mocko il Warehouse perché voglio specificare che venga prelevato esattamente 1 oggetto 1 sola volta
    val mockWarehouse: Warehouse = mock[Warehouse]
    // per il catalog invece uso uno stub perché non ho particolari pretese
    val stubCatalog: Catalog = stub[Catalog]
    // come discussione via email: uso un vero Cart perché ho bisogno di verficare che venga usato correttamente
    val cart = new BasicCart()
    val shopping = new Shopping(mockWarehouse, stubCatalog, cart, NullLogger)

    "shopping" should "fill the cart " in {

      (mockWarehouse.get _).expects(product, 1).returns((product, 1)).once()
      (stubCatalog.priceFor: (Product, Int) => Price).when(product, 1).returns(Price(10))

      shopping.pick(product, 1)

      cart.size shouldBe 1
      cart.totalCost shouldBe 10.0
    }

    it should "not add anything if product is unavailable" in {

      (mockWarehouse.get _).expects(product, 1).returns((product, 0)).once()

      shopping.pick(product, 1)

      cart.size shouldBe 0
      cart.totalCost shouldBe 0
    }
}
