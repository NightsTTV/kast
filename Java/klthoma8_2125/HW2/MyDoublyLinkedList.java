/**

    Partially stubbed implementation of a singly linked list, 
    prepped to begin transformation to a doubly linked list
    @author Dr. Christopher Summa, University of New Orleans
    @version 1.5.0

*/
public class MyDoublyLinkedList<AnyType> {

	// instance variables
	private Node myFirstNode;	
	private Node myLastNode;
	private int size;

	// constructor
	public MyDoublyLinkedList() {
		this.myFirstNode = null;
		this.myLastNode = null;
		this.size = 0;
	}

	// returns the number of elements the ArrayList has in it
	// this is going to be O(1) constant time
	public int size() {
		return this.size;
	}

	// returns the element at a particular index
	// because we have no choice but to start at the myFirstNode
	// and, while walking through linkages, keep track of how
	// many we have traversed, and stopping at index this is
	// going to cost us O(N) every time we invoke it!!!
	// NO BUENO!!!
	public AnyType get(int index) {
		if (index < 0 || index  > this.size -1)
			throw new IndexOutOfBoundsException();
	
		Node theCurrentNode = myFirstNode;
		int counter = 0;
		while (  counter < index ) {
			theCurrentNode = theCurrentNode.getNextNode();
			counter++;
		}

		return theCurrentNode.getData();
	}

    // THIS WILL NEED TO BE MODIFIED FOR DOUBLY LINKED LIST
	public void add(AnyType element) {

		Node theNodeToAdd = new Node(element);	

		if (this.size == 0) {
			this.myFirstNode = theNodeToAdd;
			this.myLastNode = theNodeToAdd;
			size++;
			return;
		}

		// If we keep around a myLastNode reference,
		// we go from O(N) for EVERY ADDITION to O(1)
		// AS LONG AS WE KEEP myLastNode UP TO DATE ALWAYS

		myLastNode.setNextNode(theNodeToAdd);
		theNodeToAdd.setPreviousNode(myLastNode);
		myLastNode = theNodeToAdd;
		size++;

	}

    // THIS WILL NEED TO BE MODIFIED FOR DOUBLY LINKED LIST
	public void add(int index, AnyType element) {
		if ( index < 0 || index > size)
			throw new IndexOutOfBoundsException();

			// if empty add an element
		if ( index == size) {
			add(element);
			return;
		}
		Node theNodeToAdd = new Node(element);	

		if (index == 0) {
			myFirstNode.setPreviousNode(theNodeToAdd);
			theNodeToAdd.setNextNode(myFirstNode);
			myFirstNode = theNodeToAdd;
			size++;
			return;
		}

        Node theCurrentNode = myFirstNode;
        int counter = 0;
		// starting at the first node, walk out to the
		// node BEFORE the insertion point
        while (  counter < index - 1 ) {
            theCurrentNode = theCurrentNode.getNextNode();
            counter++;
        }
		Node nodeAfterNewNode = theCurrentNode.getNextNode();
		theNodeToAdd.setNextNode(nodeAfterNewNode);
		theCurrentNode.setNextNode(theNodeToAdd);
		theNodeToAdd.setPreviousNode(theCurrentNode);
			if (nodeAfterNewNode != null)
		nodeAfterNewNode.setPreviousNode(theNodeToAdd);

		size++;

	}

    // THIS WILL NEED TO BE MODIFIED FOR DOUBLY LINKED LIST
	public AnyType remove(int index) {
		
		if (index < 0 || index  > this.size -1)
			throw new IndexOutOfBoundsException();

		if (index == 0) {
			Node nodeToRemove = myFirstNode;
			myFirstNode = nodeToRemove.getNextNode();
			if (myFirstNode != null) {
				myFirstNode.setPreviousNode(null);
			}
			size--;
			return nodeToRemove.getData();
		}

        Node theCurrentNode = myFirstNode;
        int counter = 0;

		// starting at the first node i walk out to the
		// node BEFORE the insertion point
        while (  counter < index - 1 ) {
            theCurrentNode = theCurrentNode.getNextNode();
            counter++;
        }

		Node nodeToRemove = theCurrentNode.getNextNode();
		Node nodeAfterRemoved = nodeToRemove.getNextNode();
		theCurrentNode.setNextNode(nodeAfterRemoved);
		if (nodeAfterRemoved != null) {
			nodeAfterRemoved.setPreviousNode(theCurrentNode);
		}
		
		// if we happen to be removing the last node
		if (index == size-1) {
			myLastNode = theCurrentNode;
		}

		size--;
		return nodeToRemove.getData();

	}

	public MyListIterator iterator() {
		return new MyListIterator();
	}

    // THESE WILL NEED TO BE IMPLEMENTED FOR DOUBLY LINKED LIST


    // returns true if element is in the list, false otherwise
    public boolean contains(AnyType element) {
	    Node current = myFirstNode;
	    while (current != null) {
	        if (current.getData().equals(element)) {
	            return true;
	        }
	        current = current.getNextNode();
	    }
	    return false;
	}

    // returns the index of the element if it is in the list, -1 if not found
    public int indexOf(AnyType element) {
	    Node current = myFirstNode;
	    int index = 0;
	    while (current != null) {
	        if (current.getData().equals(element)) {
	            return index;
	        }
	        current = current.getNextNode();
	        index++;
	    }
	    return -1;
	}

    // returns a MyIterator at the location of the element if it is in the list
    // if the element is not found returns the null reference 
    public MyListIterator iteratorAt(AnyType element) {
	    Node current = myFirstNode;
	    while (current != null) {
	        if (current.getData().equals(element)) {
			
	            MyListIterator iterator = new MyListIterator();
	            iterator.thePreviousNode = current.getPreviousNode();
	            iterator.theCurrentNode = current; 
			
	            return iterator;
	        }
	        current = current.getNextNode();
	    }
	    // Element isnt found
	    return null;
	}

    // destructively overwrites the element in the Node that
    // represents a particular index in the linked list
    public void set(int index, AnyType element) {
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException();
	    }

	    Node current = myFirstNode;
	    int counter = 0;

	    // Traverse
	    while (counter < index) {
	        current = current.getNextNode();
	        counter++;
	    }
	    current.setData(element);
	}

    public String toString() {
        String returnVal = "";
        Node current = this.myFirstNode;
        if (size != 0 ) {
            while (current != null ) {
                returnVal += current.getData().toString();
                returnVal += "\n";
                current = current.getNextNode();
            }
        }
        return returnVal;
    }  // end toString


	// public inner class - it has meaning outside of the MyArrayList class

    // NOTE:  update this next inner class to be a MyListIterator 
    //        since it will now be able to move in both directions
    // SEE:  https://docs.oracle.com/javase/8/docs/api/java/util/ListIterator.html
    //       for how the ListIterator should behave
	public class MyListIterator {

		// instance variables (should probably be modififed)
		private Node theCurrentNode;
		private Node thePreviousNode;

		public MyListIterator() {
			theCurrentNode = myFirstNode;
			thePreviousNode = null;
		}

		public boolean hasNext() {
			if (theCurrentNode == null)
				return false;
			return true;
		}

        // THIS WILL NEED TO BE IMPLEMENTED FOR DOUBLY LINKED LIST
        /*
        // return true if there is a "previous" element, otherwise returns false*/
        public boolean hasPrevious() {
			return !(thePreviousNode == null);
        }
        

		public AnyType next() {
			AnyType returnVal = theCurrentNode.getData();
			Node lastNodeRef = theCurrentNode;
			theCurrentNode = theCurrentNode.getNextNode();
			thePreviousNode = lastNodeRef;
			return returnVal;
		}

        // THESE WILL NEED TO BE IMPLEMENTED FOR DOUBLY LINKED LIST
        /*
        // returns the "previous" element and
        // moves the Iterator backward by one node
		*/
        public AnyType previous() {
			AnyType returnVal = thePreviousNode.getData();
			Node lastNodeRef = thePreviousNode;
			thePreviousNode = thePreviousNode.getPreviousNode();
			theCurrentNode = lastNodeRef;
			return returnVal;
        }
    
        // Sets this iterator to point to the last Node in the list
        public void setToEnd() {
			this.theCurrentNode = null;
			this.thePreviousNode = myLastNode;
        }
        

	} 									
	// end class MyListIterator
	
	private class Node {

		// instance variables (obviously needs to be modified)
		private AnyType element;
		private Node itsNextNode;
		private Node itsPreviousNode;

		public Node(AnyType element) {
			this.element = element;
			this.itsNextNode = null;
			this.itsPreviousNode = null;
		}

		public Node getNextNode() {
			return this.itsNextNode;
		}

        // TO BE IMPLEMENTED
        public Node getPreviousNode() {
			return this.itsPreviousNode;
        }
        

		public AnyType getData() {
			return this.element;
		}

		public void setData(AnyType element) {
			this.element = element;
		}

		public void setNextNode(Node next) {
			this.itsNextNode = next;
		}

        // TO BE IMPLEMENTED
        public void setPreviousNode(Node previous) {
			this.itsPreviousNode = previous;
        }


	} // end class Node

} // end class MyDoubleLinkedList
