import java.util.Scanner;
//import CITS2200.IllegalValue;
public class Oshit
{
	public static void main(String[] args)
	{		
		Scanner userInput = new Scanner(System.in);
		int n;
		//boolean error = true;
		
		
		System.out.println("What is the max positive in your range?");
		try
		{
			n = userInput.nextInt();
			userInput.close();
		
		System.out.println("Range is 0 - " + n);
		for(int a = 1; a <= n; ++a)
        {
            for (int b = a; b <= n; ++b)
            {
                int numerator = a * a + b * b + 1;
                int denominator = a * b;
                if (numerator % denominator == 0)
                {
                    System.out.println(a + " and " + b);
                }
            }
        }
		//error = false;
		}
		catch (Exception e)
		{
			System.out.println("Please enter a positive integer.");
		}
		
	}
}
