public class intGenerator
{
	public static void main(string[] args)
	{
		for(int a = 1; a < 10001; a++)
		{
			for(int b=a; b<1001; b++)
			{
				int numerator = a*a + b*b + 1;
				int denominator = a*b;
				if(numerator % denominator == 0)
				{
					system.out.println(a + " and " + b);
				}
			}
		}
	}
}