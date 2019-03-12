import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularArrayRing<E> extends AbstractCollection<E> implements Ring<E>
{
	//private ints to hold size, head and tail pointers
	private int size, tail;
	//private array holds Object elements of queue
	private Object[] array;
	
	//default constructor calls constructor with size argument of 10
	public CircularArrayRing()
	{
		this(10);
	}
	
	//constructor initialises all class vars to 0 (empty) and creates empty array of specified int size
	public CircularArrayRing(int i)
	{
		//size starts at 0 for empty set
		this.size = 0;
		//tail starts at -1 for empty set
		this.tail = -1;
		//i specifies size of array
		this.array = new Object[i];
	}
	
	//adds element to queue
	public boolean add(E e)
	{
		//increment tail and return tail to beginning of array if it exceeds the index range
		this.tail = (this.tail + 1) % this.array.length;
		//add element to tail position
		this.array[this.tail] = e;
		//increase size if array not full
		if (this.size < this.array.length)
		{
			this.size++;
		}
		//return true as Collection specifies boolean
		return true;
	}
	
	//adds all elements in a Collection to queue
	public boolean addAll(Collection<? extends E> c) 
	{
		for (E e : c)
		{
			//add each element, e, in c to array
			this.add(e);
		}
		//return true as Collection specifies boolean
		return true;
	}

	//returns casted element to type E from Object[] array element at index
	@SuppressWarnings("unchecked")
	private E getCastedE(int i) 
	{
        return (E) this.array[i];
    }
	
	//gets element i indices behind the tail element
	public E get(int i) throws IndexOutOfBoundsException 
	{
		//throw exception if negative argument given or larger than size of ring
		if (i < 0 || i >= this.size())
		{
			throw new IndexOutOfBoundsException();
		}
		//reassign i to index of element for function to return
		i = this.tail - i;
		//ensure i points to a valid index for the requested element (positive and within range)
		if (i < 0)
		{
			i += this.array.length;
		}
		//return element of type E at index with getCastedE()
		return this.getCastedE(i);
	}

	//return logical size of array in ring
	public int size() 
	{
		return this.size;
	}
	
	//return Iterator over elements in the array
	public Iterator<E> iterator() 
	{
		return new Itr();
	}
	
	//private inner class defines Iterator class
	private class Itr implements Iterator<E>
	{
		//index of next element to be returned by iterator; starts 1 above tail as it is decremented
		private int next = CircularArrayRing.this.tail + 1;
		//number of iterations left initially given by the array size
		private int numItersLeft = CircularArrayRing.this.size;
		
		//returns whether the iteration has more elements
		public boolean hasNext() 
		{
			//if number of iterations left is 0, no more elements
			return this.numItersLeft > 0;
		}

		//returns next element in the iteration
		public E next() 
		{
			//if no elements left in iteration then throw exception
			if (!this.hasNext())
			{
				throw new NoSuchElementException();
			}
			//decrement number of iterations left, as this is 1 iteration
			this.numItersLeft--;
			//decrement next, (modulo gives valid index if it goes negative); getCastedE() on result returns element
			return CircularArrayRing.this.getCastedE(this.next = (this.next - 1) % CircularArrayRing.this.array.length);
		}
		
		//not implemented as per specification; throws exception
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}
}

