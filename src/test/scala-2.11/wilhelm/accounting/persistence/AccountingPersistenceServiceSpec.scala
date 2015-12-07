package wilhelm.accounting.persistence

import java.sql.Date

import wilhelm.accounting.UnitSpec
import wilhelm.accounting.persistence.Tables._

/**
  * Created by lwilhelm on 06.12.15.
  */

class AccountingPersistenceServiceSpec extends UnitSpec {

  describe("Given an AccountingPersistenceService") {
    val accountingPersistenceService = new AccountingPersistenceService()
    describe("When it gets a cashflow by id") {
      it("Then it should return empty Optional if id does not exist") {
        accountingPersistenceService.getById(-1) should be('empty)
      }
      it("And it should return the cashflow with this id if it exists") {
        val expectedCashflowRow = CashflowRow(Some(1), 1, new Date(2015 - 1900, 11, 3), BigDecimal(13.2), "käse")
        accountingPersistenceService.getById(1).get should be(expectedCashflowRow)
      }
    }

    describe("When it gets a cashflow by number") {
      it("Then it should return empty Optional if number does not exist") {
        accountingPersistenceService.getByNumber(-1) should be('empty)
      }
      it("And it should return the cashflow with this number if it exists") {
        val expectedCashflowRow = CashflowRow(Some(1), 1, new Date(2015 - 1900, 11, 3), BigDecimal(13.2), "käse")
        accountingPersistenceService.getByNumber(1).get should be(expectedCashflowRow)
      }
    }

    describe("When it persists a cashflow") {
      it("Then it should return the cashflow if no cashflow with this number exists") {
        val cashflow = CashflowRow(null, 9, new Date(2015 - 1900, 11, 5), BigDecimal(12.9), "wurst")

        val returnedCashflow = accountingPersistenceService.create(cashflow)

        val persistedCashflow = accountingPersistenceService.getByNumber(cashflow.cNumber)

        persistedCashflow should be('defined)
        persistedCashflow.get should equal(returnedCashflow)
      }
      it("Then it should throw IllegalArgumentException if cashflow with this number exsits") {
        val cashflow = CashflowRow(Option.empty, 1, new Date(2015 - 1900, 11, 3), BigDecimal(13.2), "käse")

        //accountingPersistenceService.create(cashflow)

        an[IllegalArgumentException] should be thrownBy accountingPersistenceService.create(cashflow)

      }
    }
  }
}
