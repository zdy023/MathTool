//LCM.java
package xyz.davidchangx.algorithms.math.operator;
import xyz.davidchangx.algorithms.math.operator.OperatorGroupMode;
import xyz.davidchangx.algorithms.math.operator.Operator;
/**
 * Solve the least common multiple of two floor integers of two doubles' absolute.  
 * 
 * @version 2.2
 * @author David Chang
 */
public class LCM extends Operator
{
	/**
	 * Construct an operator. 
	 */
	public LCM()
	{
		super("lcm(",15,1,2,OperatorGroupMode.NEEDING_CLOSED);
	}
	/**
	 * Calculate as a math operator. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public double solve(double[] x)
	{
		long m = (long)Math.abs(x[0]),n = (long)Math.abs(x[0]);
		long c = m,d = n;
		if(m<n)
		{
			long t = m;
			m = n;
			n = t;
		}
		long r;
		for(r = m%n;r!=0;m = n,n = r) ;
		return (double)(c*d/n);
	}
	/**
	 * Returns a clone of this object. 
	 *
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		return new LCM();
	}
}
