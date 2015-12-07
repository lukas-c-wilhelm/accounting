package wilhelm.accounting.persistence

import com.typesafe.config.ConfigFactory
import slick.driver.PostgresDriver.api._
import wilhelm.accounting.persistence.Tables._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by lwilhelm on 06.12.15.
  */
// TODO handling of database exceptions
class AccountingPersistenceService {


  val conf = ConfigFactory.load();

  val TIMEOUT = Duration.create(conf.getInt("accountingdb.timeout"), "seconds")

  val database = Database.forConfig("accountingdb")

  def getById(id: Long) = {

    val query = Cashflow.filter(cashflow => cashflow.cNumber === id).result

    val queryResult = Await.result(database.run(query), TIMEOUT)

    if (queryResult.size > 0) {
      Option.apply(queryResult.iterator.next())
    }
    else {
      Option.empty[CashflowRow]
    }
  }

  def create(cashflow: CashflowRow): CashflowRow = {

    if (this.getByNumber(cashflow.cNumber).isDefined) {
      // TODO log warning and return Optional.absent ?
      throw new IllegalArgumentException("Cashflow with number " + cashflow.cNumber + " exists already.")
    }

    val insert = Cashflow.insertOrUpdate(cashflow)

    Await.result(database.run(insert), TIMEOUT)

    getByNumber(cashflow.cNumber).get
  }

  def getByNumber(number: Long): Option[CashflowRow] = {

    val query = Cashflow.filter(cashflow => cashflow.cNumber === number).result

    val queryResult = Await.result(database.run(query), TIMEOUT)

    if (queryResult.size > 0) {
      Option.apply(queryResult.iterator.next())
    }
    else {
      Option.empty[CashflowRow]
    }
  }
}
