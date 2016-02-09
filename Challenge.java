/* Challenge.java */

/**
 *
 * @author __MadHatter (alias used on https://www.reddit.com/r/dailyprogrammer)
 *
 * [2016-02-08] Challenge #253 [Easy] Unconditional Loan Income
 * https://www.reddit.com/r/dailyprogrammer/comments/44qzj5/20160208_challenge_253_easy_unconditional_loan/
 *
 */

package dailyprogrammer;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Challenge
{
    /* Input Values */
    private float interestRate;
    private float annualLoanAmount;
    private int startAge;
    private float clawbackBalanceTrigger;
    private float royaltyRateUnder65;
    private float royaltyRateOver65;
    private float incomingStream;
    private ArrayList<Integer> incomeStream;

    /* Output Values  */
    private float loansTaken;
    private float repaymentsFromIncome;
    private float repaymentsFromBenefitClawbacks;
    private float endingBalanceWithInterest;

    public Challenge()
    {
        /* Input Values */
        interestRate = (float)0;
        annualLoanAmount = (float)0;
        startAge = 0;
        clawbackBalanceTrigger = (float)0;
        royaltyRateUnder65 = (float)0;
        royaltyRateOver65 = (float)0;
        incomingStream = (float)0;
        incomeStream = new ArrayList<>();

        /* Output Values  */
        loansTaken = (float)0;
        repaymentsFromIncome = (float)0;
        repaymentsFromBenefitClawbacks = (float)0;
        endingBalanceWithInterest = (float)0;

        solve();
    }

    private void solve()
    {
        /**********************************************************************/
        /* Hard-coded values. */
        /**********************************************************************/
        interestRate = (float)0.02;
        annualLoanAmount = (float)15000;
        startAge = 18;
        clawbackBalanceTrigger = (float)100000;
        royaltyRateUnder65 = (float)0.2;
        royaltyRateOver65 = (float)0.4;
//        incomeStream = readIncomeStream("0 0 20 20 20 20 20 20 20 20 20 20 30 30 30 30 30 30 30 30 30 30 40 40 40 40 40 40 40 40 40 40 50 50 50 50 50 50 50 50 50 50 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
        incomeStream = readIncomeStream("0 0 20 20 20 20 20 20 20 20 20 20 30 30 30 30 30 30 30 30 30 30 40 40 40 40 40 40 40 40 40 40 50 50 50 50 50 50 50 50 50 50 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0");
//        incomeStream = readIncomeStream("0 0 30 30 30 30 30 30 30 30 30 30 40 40 40 40 40 40 40 40 40 40 50 50 50 50 50 50 50 50 50 50 60 60 60 60 60 60 60 60 60 60 100 120 140 160 200 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10 10");
        /**********************************************************************/


        /**********************************************************************/
        /* Uncomment the following if you wish to read from stdin. */
        /**********************************************************************/
//        Scanner input = new Scanner(System.in);
//        System.out.print("Interest Rate: ");
//        interestRate = readRate();
//        System.out.print("Annual Loan Amount: ");
//        annualLoanAmount = readDollarAmount();
//        System.out.print("Start Age: ");
//        startAge = readInt();
//        System.out.print("Clawback Balance Trigger: ");
//        clawbackBalanceTrigger = readDollarAmount();
//        System.out.print("Royalty Rate (under 65): ");
//        royaltyRateUnder65 = readRate();
//        System.out.print("Royalty Rate (over 65): ");
//        royaltyRateOver65 = readRate();
//        System.out.print("Income stream: ");
//        incomeStream = readIncomeStream();
        /**********************************************************************/


        /* Working variables. */
        float royalty;                 /* temp royalty based on iterated annual income */
        float clawback;                /* temp clawback based on iterated annual income */
        float annualIncome;            /* temp annual income from incomeStream */
        int age = startAge;            /* temp age counter increased every year */
        int len = incomeStream.size(); /* number of annual incomes */

        /* Loop through all annual incomes and calculate. */
        for (int i = 0; i < len; i++)
        {
            loansTaken += annualLoanAmount;
            endingBalanceWithInterest *= ((float)1 + interestRate);
            endingBalanceWithInterest += annualLoanAmount;
            royalty = (float)0;
            clawback = (float)0;
            annualIncome = (float)incomeStream.get(i) * (float)1000; /* income stream is in 1000s */

            /* Calculate royalty and clawback. */
            if (age < 65)
            {
                royalty = royaltyRateUnder65 * annualIncome;
                if (endingBalanceWithInterest >= clawbackBalanceTrigger)
                {
                    clawback = royaltyRateUnder65 * annualLoanAmount;
                }
            }
            else if (age >= 65)
            {
                royalty = royaltyRateOver65 * annualIncome;
                if (endingBalanceWithInterest >= clawbackBalanceTrigger)
                {
                    clawback = royaltyRateOver65 * annualLoanAmount;
                }
            }

            repaymentsFromIncome += royalty;
            repaymentsFromBenefitClawbacks += clawback;
            endingBalanceWithInterest = endingBalanceWithInterest - royalty - clawback;

            age++;
        }

        /* Display results. */
        printInfo();
    }

    private void printInfo()
    {
        System.out.printf(
            "*** INPUT ***\nInterest Rate: %.1f%% \nAnnual Loan Amount: $%.2f\nStart Age: %d\nClawback Balance Trigger: $%.2f\nRoyalty Rate (under 65): %.1f%%\nRoyalty Rate (over 65): %.1f%%",
            interestRate * (float)100,
            annualLoanAmount,
            startAge,
            clawbackBalanceTrigger,
            royaltyRateUnder65 * (float)100,
            royaltyRateOver65 * (float)100
        );

        StringBuilder sb = new StringBuilder();
        sb.append("\nIncome stream: ");
        int len = incomeStream.size();
        for (int i = 0; i < len; i++)
        {
            sb.append(incomeStream.get(i));
            sb.append(" ");
        }
        sb.append("\n");
        System.out.print(sb.toString());

        System.out.printf(
            "*** OUTPUT ***\nOverall loans taken: $%.2f\nRepayments from income: $%.2f\nRepayments from benefit clawbacks: $%.2f\nEnding balance with interest: $%.2f\n",
            loansTaken,
            repaymentsFromIncome,
            repaymentsFromBenefitClawbacks,
            endingBalanceWithInterest
        );
    }

    private float readRate()
    {
        float rate = (float)0;

        Scanner input = new Scanner(System.in);
        String line = input.nextLine();

        if (!line.isEmpty())
        {
            /* Input format as percentage: 2% */
            if (line.contains("%"))
            {
                int index = line.indexOf("%");
                line = line.substring(0, index);
                rate = stringToFloat(line);
                rate = rate / (float)100;
            }
            /* Input format as float: 0.02 */
            else
            {
                rate = stringToFloat(line);
            }
        }

        return rate;
    }

    private float readDollarAmount()
    {
        float dollars = (float)0;

        Scanner input = new Scanner(System.in);
        String line = input.nextLine();

        if (!line.isEmpty())
        {
            /* Input format with currency symbol */
            if (line.contains("$"))
            {
                line = line.substring(1, line.length());
            }
            dollars = stringToFloat(line);
        }

        return dollars;
    }

    private int readInt()
    {
        int number = 0;

        Scanner input = new Scanner(System.in);
        String line = input.nextLine();

        if (!line.isEmpty())
        {
            number = stringToInt(line);
        }

        return number;
    }

    private ArrayList<Integer> readIncomeStream()
    {
        ArrayList<Integer> stream = new ArrayList<>();

        Scanner input = new Scanner(System.in);
        String line = input.nextLine();

        if (!line.isEmpty())
        {
            int token;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens())
            {
                token = stringToInt(st.nextToken());
                stream.add(token);
            }
        }

        return stream;
    }

    private ArrayList<Integer> readIncomeStream(String s)
    {
        ArrayList<Integer> stream = new ArrayList<>();

        if (!s.isEmpty())
        {
            StringTokenizer st = new StringTokenizer(s);
            int token;
            while (st.hasMoreTokens())
            {
                token = stringToInt(st.nextToken());
                stream.add(token);
            }
        }

        return stream;
    }

    private float stringToFloat(String s)
    {
        try
        {
            float f = Float.parseFloat(s);
            return f;
        }
        catch (Exception e)
        {
            return (float)0.0;
        }
    }

    private int stringToInt(String s)
    {
        try
        {
            int i = Integer.parseInt(s);
            return i;
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}
