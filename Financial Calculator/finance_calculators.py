#This is a program that allows the user to access a calculator for an investment or a home loan repayment

#If the investment calculator is selected:
#  The user will input their investment amount, interest rate and number of years
#  The user will then decide on simple or compound interest 
#  The value of the investment will then be calculated and displayed

#If the home loan repayment (bond) calculator is selected:
#  The user will input the present value of the house, annual interest rate and the number of months needed to repay
#  The monthly repayment amount will then be calculated and displayed

import math

choice = ""
valid_choice = (choice == "investment" or choice == "bond" or "exit")

while choice != "exit":

    print("investment - to calculate the amount of interest you'll earn on your investment")
    print("bond       - to calculate the amount you'll have to pay on a home loan")
    print("exit       - to close calculator")
    choice = input("\nEnter either 'investment' or 'bond' from the menu above to proceed: ").lower()

    while not valid_choice:
        print("\nYou did not select a valid option, please try again.")

    if choice == "investment":
        amount = float(input("\nPlease enter the investment amount: "))
        interest_rate = float(input("Please enter the interest rate without '%' sign: "))
        years = float(input("Please enter the number of years you investing for: "))
        
        #Allow user to select type of interest (simple or compound) and calculate accordingly
        interest = input("Please enter 'simple' or 'compound' for the type of interest: ").lower()
        
        if interest == "simple":
            amount *= (1 + years * interest_rate / 100)
            print(f"\nYour investment will grow to £{round(amount,2)}")
        elif interest == "compound":
            amount *= ((1 + interest_rate / 100) ** years)
            print(f"\nYour investment will grow to £{round(amount,2)}")
        else:
            print("\nYou did not select a valid option. Please try again.")
        
        input("\nEnter any key to return to main menu\n")

    #Bond option (home repayment)
    elif choice == "bond":
        present_value = float(input("\nPlease enter the present value of the house: "))
        interest_rate = float(input("Please enter the annual interest rate without the '%' sign: "))
        months = int(input("Please enter the number of months you intend to use to pay off the house: "))
        
        i = interest_rate/12/100
        
        repayment = (i * present_value)/(1 - (1 + i)**(-months))
        
        print(f"\nYour repayment each month is £{round(repayment,2)}")

        input("\nEnter any key to return to main menu\n")

    else:
        print("Goodbye, thank you for using this finance calculator.")