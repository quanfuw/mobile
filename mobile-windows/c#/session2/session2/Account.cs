using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace session2
{
    class Account
    {
        private Object thisLock = new Object();
        int balance;

        Random r = new Random();

        public Account(int initial)
        {
            balance = initial;
        }

        int Withdraw(int amount)
        {

            // This condition will never be true unless the lock statement
            // is commented out:
            if (balance < 0)
            {
                throw new Exception("Negative Balance");
            }

            // Comment out the next line to see the effect of leaving out 
            // the lock keyword:
            lock (thisLock)
            {
                if (balance >= amount)
                {
                    Console.WriteLine("Balance before Withdrawal :  " + balance);
                    Console.WriteLine("Amount to Withdraw        : -" + amount);
                    balance = balance - amount;
                    Console.WriteLine("Balance after Withdrawal  :  " + balance);
                    return amount;
                }
                else
                {
                    return 0; // transaction rejected
                }
            }
        }

        public void DoTransactions()
        {
            for (int i = 0; i < 100; i++)
            {
                Withdraw(r.Next(1, 100));
            }
        }
    }
}
