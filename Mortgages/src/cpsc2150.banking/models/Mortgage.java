/**
 * Cayden Akers
 * Ben Curry
 * Cameron Scott
 */
package cpsc2150.banking.models;

/** MortgageClassContract
 *
 * @invariant:
 *
 * @corresponds:
 */
public class Mortgage extends AbsMortgage implements IMortgage{
    private double APR;
    private int years;
    private double principalAmount;
    int customerCreditScore;
    Mortgage(double homeCost, double downPay, int y, ICustomer customer){
        years = y;
        //set APR to base rate of 2.5%
        APR = BASERATE;
        //if years < 30, add 0.5%, otherwise add 1%
        if (years < MAX_YEARS) APR += GOODRATEADD;
        else APR += NORMALRATEADD;
        //calculate 20% of the home cost
        double checkPercent = homeCost * PREFERRED_PERCENT_DOWN;
        //if the down payment is less than 20% of home cost, add 0.5%
        if (downPay < checkPercent) APR += GOODRATEADD;
        //get customer credit score
        customerCreditScore = customer.getCreditScore();
        //if credit score > 500, + 10%
        if (customerCreditScore < BADCREDIT) APR += VERYBADRATEADD;
        //if 600 > credit score >= 500, + 5%
        else if (customerCreditScore >= BADCREDIT && customerCreditScore < FAIRCREDIT) APR += BADRATEADD;
        //if 700 > credit score >= 600, + 1%
        else if (customerCreditScore >= FAIRCREDIT && customerCreditScore < GOODCREDIT) APR += NORMALRATEADD;
        //if 750 > credit score >= 700, + 0.5%
        else if (customerCreditScore >= GOODCREDIT && customerCreditScore < GREATCREDIT) APR += GOODRATEADD;
        //if credit score >= 750, + 0%
        else APR += 0;
        //calculate principal amount
        principalAmount = homeCost - downPay;
    }
    public boolean loanApproved(){

    }
    public double getPayment() {

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
