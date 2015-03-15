import junit.framework.TestCase;
import java.util.Arrays;

public class CombinationLockTest extends TestCase {

   public static void usage() {
	      System.out.println("Add Arguments");
	      System.exit(1);
	   }
   /** Prints error message and terminates program */
   public static void pperr(String msg) {
      System.out.println("Error:");
      System.out.println(msg);
      System.exit(1);
   }
   
   public static void main(String[] args){
	   CombinationLock locker = new CombinationLock();
	   String a = "000";
	   //int b = 010;
	   //int c = 100;
	   //int d = 001;
	   locker.open(a);
	   if (locker.locked) {
		   System.out.println("lock is closed");
	   }
	   else{
		   System.out.println("lock is open");
	   }
	   System.out.println("combo entered: " + a);
	   System.out.println("acutal lock combo");
   }

}
