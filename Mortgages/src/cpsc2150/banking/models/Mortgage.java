/**
 * Cayden Akers
 * Ben Curry
 * Cameron Scott
 */
package cpsc2150.banking.models;

/**
 * MortgageClassContract
 *
 * @invariant: APR >= 0 AND MIN_YEARS <= years <= MAX_YEARS AND
 *             principalAmount > 0
 * 
 * @corresponds: self.principalAmount = principal self.years = years AND
 *               self.Rate = APR AND self.Customer = customer
 * 
 */
public class Mortgage extends AbsMortgage implements IMortgage {
    private double APR;
    private int years;
    private double principalAmount;
    //added more variables I think are supposed to be added
    private double payment;
    private double DebtToIncomeRatio;
    private double PercentDown;
    int customerCreditScore;

    /**
     * This creates a new object to keep track of information for a specific
     * customer's mortgage.
     * 
     * @param homeCost the total cost of the customer's home
     * @param downPay  the customer's down payment
     * @param y        years the customer applied the mortgage loan for
     * @param customer the associated customer to the loan
     * 
     * @pre homeCost >= 0 AND downPay >= 0 AND MIN_YEARS <= y <= MAX_YEARS
     * @post years = y AND APR = BASERATE AND customer = self.customer
     */

    Mortgage(double homeCost, double downPay, int y, ICustomer customer) {
        years = y;
        // set APR to base rate of 2.5%
        APR = BASERATE;
        // if years < 30, add 0.5%, otherwise add 1%
        if (years < MAX_YEARS)
            APR += GOODRATEADD;
        else
            APR += NORMALRATEADD;
        // calculate 20% of the home cost
        double checkPercent = homeCost * PREFERRED_PERCENT_DOWN;
        // if the down payment is less than 20% of home cost, add 0.5%
        if (downPay < checkPercent)
            APR += GOODRATEADD;
        // get customer credit score
        customerCreditScore = customer.getCreditScore();
        // if credit score > 500, + 10%
        if (customerCreditScore < BADCREDIT)
            APR += VERYBADRATEADD;
        // if 600 > credit score >= 500, + 5%
        else if (customerCreditScore >= BADCREDIT && customerCreditScore < FAIRCREDIT)
            APR += BADRATEADD;
        // if 700 > credit score >= 600, + 1%
        else if (customerCreditScore >= FAIRCREDIT && customerCreditScore < GOODCREDIT)
            APR += NORMALRATEADD;
        // if 750 > credit score >= 700, + 0.5%
        else if (customerCreditScore >= GOODCREDIT && customerCreditScore < GREATCREDIT)
            APR += GOODRATEADD;
        // if credit score >= 750, + 0%
        else
            APR += 0;
        // calculate principal amount
        principalAmount = homeCost - downPay;
        //things we may need
        PercentDown = homeCost * PREFERRED_PERCENT_DOWN;
        DebtToIncomeRatio = customer.getMonthlyDebtPayments() / customer.getIncome(); 
        payment = (APR/MONTHS_IN_YEAR * principalAmount) / (1 - Math.pow(1 + APR/MONTHS_IN_YEAR,-1 * MONTHS_IN_YEAR));
    }

    public boolean loanApproved() {

        if(APR <= RATETOOHIGH &&  PercentDown >= MIN_PERCENT_DOWN && DebtToIncomeRatio <= DTOITOOHIGH)
        {
            return true;
        }
        return false;
    }

    public double getPayment() {
        return payment;
    }

    public double getRate() {
        return APR;
    }

    public double getPrincipal() {
        return principalAmount;
    }

    public int getYears() {
        return years;
    }

}
