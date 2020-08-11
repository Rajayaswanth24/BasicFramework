package LibraryFiles;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ListOfLists {
		public List<List<String>> outerList;
		private Iterator<List<String>> outerListIter;
		private List<String> innerList;
		private Iterator<String> innerListIter;
		
		public void init() {
			outerListIter = outerList.iterator();
			if(outerListIter.hasNext()) {
				innerList = outerListIter.next();
				innerListIter = innerList.iterator();
				
			}
		}
		
		public boolean hasNext() {
			// run through empty lists
			while(outerListIter.hasNext() && ! innerListIter.hasNext()) {
				innerList = outerListIter.next();
				innerListIter = innerList.iterator();
			}
			return innerListIter.hasNext();
		}
		
		public String next() {
			if(hasNext()) {
				return innerListIter.next();
			} else {
				throw new NoSuchElementException();
			}		
		}
	}