package oracle.net.resolver;

import java.util.BitSet;
import java.util.Random;
import java.util.Vector;
import oracle.net.TNSAddress.DescriptionList;
import oracle.net.TNSAddress.SchemaObjectFactoryInterface;
import oracle.net.nt.ConnStrategy;

public class NavDescriptionList extends DescriptionList implements
		NavSchemaObject {
	private Vector activeChildren = new Vector(1, 10);
	private int descProcessed;
	private boolean done;

	public NavDescriptionList(
			SchemaObjectFactoryInterface paramSchemaObjectFactoryInterface) {
		super(paramSchemaObjectFactoryInterface);
	}

	public void navigate(ConnStrategy paramConnStrategy,
			StringBuffer paramStringBuffer) {
		paramStringBuffer.append("(DESCRIPTION_LIST=");
		this.activeChildren = setActiveChildren(this.children, this.failover,
				this.loadBalance);
		while (this.descProcessed < this.activeChildren.size()) {
			((NavSchemaObject) this.activeChildren
					.elementAt(this.descProcessed)).navigate(paramConnStrategy,
					paramStringBuffer);
			this.descProcessed += 1;
		}
	}

	public void addToString(ConnStrategy paramConnStrategy) {
	}

	public static Vector setActiveChildren(Vector paramVector,
			boolean paramBoolean1, boolean paramBoolean2) {
		int j = paramVector.size();
		Vector localVector = new Vector(1, 10);
		Random localRandom = new Random();
		BitSet localBitSet = new BitSet(j);
		int i;
		if (paramBoolean1) {
			if (paramBoolean2)
				for (int k = 0; k < j; k++) {
					do
						i = Math.abs(localRandom.nextInt()) % j;
					while (localBitSet.get(i));
					localBitSet.set(i);
					localVector.addElement(paramVector.elementAt(i));
				}
			localVector = paramVector;
		} else if (paramBoolean2) {
			i = Math.abs(localRandom.nextInt()) % j;
			localVector.addElement(paramVector.elementAt(i));
		} else {
			localVector.addElement(paramVector.elementAt(0));
		}
		return localVector;
	}
}

/*
 * Location:
 * /Users/admin/.m2/repository/com/alibaba/external/jdbc.oracle/10.2.0.2
 * /jdbc.oracle-10.2.0.2.jar Qualified Name:
 * oracle.net.resolver.NavDescriptionList JD-Core Version: 0.6.0
 */