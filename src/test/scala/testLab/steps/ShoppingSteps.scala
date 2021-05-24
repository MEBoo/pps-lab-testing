package testLab.steps

import cucumber.api.scala.{EN, ScalaDsl}
import org.junit.Assert
import testLab._

class ShoppingSteps extends ScalaDsl with EN {

  var product:Product = _
  var catalog:Catalog = _
  var warehouse:Warehouse = _
  var cart:Cart = _
  var shopping:Shopping = _

  Given("""^A product Shoe with price (\d+.\d+) in catalog$"""){ (price:Double) => {
      product = Product("Shoe")
      catalog = new BasicCatalog(Map[Product,Price](
        product -> Price(price)
      ))
    }
  }
  Given("""^the product is available in warehouse in (\d+) units$"""){ (qty:Int) => {
      warehouse = new BasicWarehouse
      warehouse.supply(product, qty)
    }
  }
  Given("""^the cart is empty$"""){ cart = new BasicCart() }
  When("""^I pick (\d+) Shoes$"""){ (qty:Int) => {
      shopping = new Shopping(warehouse, catalog, cart, NullLogger)
      shopping.pick(product, qty )
    }
  }
  When("""^I remove the Shoes from the cart$"""){ ()=> shopping.remove( product ) }
  Then("""^The cart should have size (\d+) and total cost (\d+.\d+)$"""){ (size:Int, total:Double) => {
      Assert.assertEquals(size, cart.size)
      Assert.assertEquals(total, cart.totalCost,0.001)
    }
  }
}