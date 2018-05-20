package org.jbltd.password.common;

import java.util.Comparator;

public class IPasswordSorter implements Comparator<IPassword> {

    @Override
    public int compare(IPassword o1, IPassword o2) {
	return o1.getName().compareTo(o2.getName());
    }

}
