.
import java.util.*;
class Employee{
	static int hId=0;
	static int mId=0;
	static double payroll=0;
	int employeeId;
	String employeeName;
	String designation;
	double bonus;
	double annualPay;

    Employee(int a){
		Scanner sc=new Scanner(System.in);
		if(a<3){
			this.employeeId=mId+1;
		}
		else{
			this.employeeId=hId+1;
		}
		System.out.println("Enter Employee Name");
		this.employeeName=sc.nextLine();
		switch(a)
		{
		case 2: this.designation="Professor";
				break;
		case 3: this.designation="Substitute Professor";
				break;
		case 1: this.designation="HOD";
				break;
		case 4: this.designation="Support Staff";
				break;
		default: System.out.println("Invalid Choice");
				break;
		}
	}
	void calculateBonus(){
		bonus=5000;
	}
	void display(){
		System.out.println("_____________________________________________________________");
		System.out.println("ID: "+employeeId);
		System.out.println("Name: "+employeeName);
		System.out.println("Designation: "+designation);
		System.out.println("Annual salary: "+annualPay);
		System.out.println("Bonus: "+bonus);
		System.out.println("Net salary: "+(annualPay+bonus));
	}
}
class HourlyEmployee extends Employee{
	double hourlyRate;
	int hoursWorked;
	HourlyEmployee(int a){
		super(a);
		Scanner sc= new Scanner(System.in);
		System.out.println("Enter Hourly rate (50-500)");
		do {
            while (!sc.hasNextDouble()) {
                String input = sc.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);
            }
            this.hourlyRate = sc.nextDouble();
        } while (this.hourlyRate < 50 || this.hourlyRate>500);
		System.out.println("Enter no. of hours worked (1-6)");
		do {
            while (!sc.hasNextInt()) {
                String input = sc.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);
            }
            this.hoursWorked = sc.nextInt();
        } while (this.hoursWorked < 1 || this.hoursWorked>6);
		details();
		calculateBonus();
	}
	void details(){
		System.out.println("_____________________________________________________________");
		System.out.println("ID: "+employeeId);
		System.out.println("Name: "+employeeName);
		System.out.println("Designation: "+designation);
		System.out.println("Weekly Salary: "+(hoursWorked*hourlyRate*5));
	}
	void calculateBonus(){
		super.calculateBonus();
		bonus+=(designation=="Substitute Professor")?15000:5000;
		annualPay=hoursWorked*hourlyRate*250;
		payroll+=annualPay+bonus;
	}
	void display(){
		super.display();
	}
}
class SalariedEmployee extends Employee{
	double monthlySalary;
	SalariedEmployee(int a){
		super(a);
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Monthly salary");
		this.monthlySalary=sc.nextDouble();
		details();
	}
	void details(){
		System.out.println("_____________________________________________________________");
		System.out.println("ID: "+employeeId);
		System.out.println("Name: "+employeeName);
		System.out.println("Designation: "+designation);
		System.out.println("Weekly Salary: "+(monthlySalary/4));
	}
	void calculateBonus(){
		super.calculateBonus();
		bonus+=(designation=="Professor")?30000:50000;
	}
	void display(){
		super.display();
	}
}
class ExecutiveEmployee extends SalariedEmployee{
	double bonusPercentage;
	ExecutiveEmployee(int a){
		super(a);
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter Bonus Percentage(min 10)");
		do {
            while (!sc.hasNextDouble()) {
                String input = sc.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);
            }
            this.bonusPercentage = sc.nextDouble();
        } while (this.bonusPercentage < 10);
        calculateBonus();
	}
	void calculateBonus(){
		super.calculateBonus();
		bonus+=(bonusPercentage/100)*(monthlySalary*12);
		annualPay=monthlySalary*12;
		payroll+=annualPay+bonus;
	}
	void display(){
		super.display();
	}
}
class Lab3{
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		int SIZE=10;
		ExecutiveEmployee obj1[]=new ExecutiveEmployee[SIZE];
		HourlyEmployee obj2[]=new HourlyEmployee[SIZE];
		int a,b=0;
		do{
			System.out.println("Choose an operation");
            System.out.println("1.Add Employee");
            System.out.println("2.Display all employee details");
            System.out.println("3.See total payroll");
            do {
            	while (!sc.hasNextInt()) {
                	String input = sc.next();
               		System.out.printf("\"%s\" is not a valid number.\n", input);
            	}
            	a = sc.nextInt();
        	} while (a < 0);
        	switch(a)
        	{
        	case 1: System.out.println("Choose Designation\n1.Head of the Department\n2.Professor\n3.Substitute Professor\n4.Support Staff");
        			int choice=sc.nextInt();
        			if(choice<3){
        				obj1[Employee.mId]=new ExecutiveEmployee(choice);
        				Employee.mId+=1;
        			}
        			else{
        				obj2[Employee.hId]=new HourlyEmployee(choice);
        				Employee.hId+=1;
        			}
        			break;
        	case 2: if(Employee.mId>0)
        			{
        			System.out.println("_______________________________\nMonthly Basis Employees\n_______________________________");
        			for(int i=0;i<Employee.mId;i++){
        				obj1[i].display();
        			}
        			}
        			if(Employee.hId>0)
        			{
        			System.out.println("_______________________________\nHourly Basis Employees\n_______________________________");
        			for(int i=0;i<Employee.hId;i++){
        				obj2[i].display();
        			}
        			}
        			if(Employee.hId==0 && Employee.mId==0)
        			{
        				System.out.println("No Employees Added");
        			}
        			break;
        	case 3: System.out.println("Total Payroll="+Employee.payroll);
        			break;
        	default: System.out.println("Invalid Choice!");
        			 break;
        	}
        	System.out.println("Do you want to: 1.Continue  2.Exit");
        	b=sc.nextInt();
		}while(b!=2);
		System.out.println("Thank you");
	}
}
