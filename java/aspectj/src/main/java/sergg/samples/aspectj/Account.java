/**
 * Copyright (c) Amdocs jNetX.
 * http://www.amdocs.com
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Amdocs. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license
 * agreement you entered into with Amdocs.
 * <p>
 * $Id:$
 */
package sergg.samples.aspectj;

/**
 * @author <a href="mailto:sergeygo@amdocs.com">Sergey Gomanyuk</a>
 * @version $Revision:$
 */
public class Account {
    int balance = 20;

    public boolean withdraw(int amount) {
        if (balance < amount) {
            return false;
        }
        balance = balance - amount;
        return true;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                '}';
    }

    public static void main(String[] args) {
        Account account = new Account();
        System.out.println("init:" + account);
        System.out.println("account.withdraw(12): " + account.withdraw(12) + " : " + account);
        System.out.println("account.withdraw(5): " + account.withdraw(5) + " : " + account);
        System.out.println("account.withdraw(4): " + account.withdraw(4) + " : " + account);
    }
}
