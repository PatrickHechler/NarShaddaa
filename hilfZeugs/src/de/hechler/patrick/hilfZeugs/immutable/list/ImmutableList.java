package de.hechler.patrick.hilfZeugs.immutable.list;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class ImmutableList <E> {
	
	private List <E> delegate;
	
	public ImmutableList(List <E> liste) {
		delegate = liste;
	}
	
	public boolean isEmpty() {
		return delegate.isEmpty();
	}
	
	public boolean contains(Object o) {
		return delegate.contains(o);
	}
	
	public Object[] toArray() {
		return delegate.toArray();
	}
	
	public Object[] toArray(Object[] a) {
		return delegate.toArray(a);
	}
	
	public boolean containsAll(Collection <E> c) {
		return delegate.containsAll(c);
	}
	
	public boolean equals(Object o) {
		return delegate.equals(o);
	}
	
	public int hashCode() {
		return delegate.hashCode();
	}
	
	public Object get(int index) {
		return delegate.get(index);
	}
	
	public int indexOf(Object o) {
		return delegate.indexOf(o);
	}
	
	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}
	
	public Stream <E> stream() {
		return delegate.stream();
	}
	
	public Stream <E> parallelStream() {
		return delegate.parallelStream();
	}
	
	public int size() {
		return delegate.size();
	}
	
}
