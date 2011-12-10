package ceylon.language;

import com.redhat.ceylon.compiler.metadata.java.Ceylon;
import com.redhat.ceylon.compiler.metadata.java.Class;
import com.redhat.ceylon.compiler.metadata.java.Name;
import com.redhat.ceylon.compiler.metadata.java.SatisfiedTypes;
import com.redhat.ceylon.compiler.metadata.java.Sequenced;
import com.redhat.ceylon.compiler.metadata.java.TypeInfo;
import com.redhat.ceylon.compiler.metadata.java.TypeParameter;
import com.redhat.ceylon.compiler.metadata.java.TypeParameters;
import com.redhat.ceylon.compiler.metadata.java.Variance;

@Ceylon
@TypeParameters(@TypeParameter(value = "Element", 
        variance = Variance.OUT))
@Class(extendsType="ceylon.language.Object")
@SatisfiedTypes("ceylon.language.Sequence<Element>")
public class Singleton<Element> 
        implements Sequence<Element> {
	
	Element element;
	
	public Singleton(Element element) {
		this.element = element;
	}
	
	@Override
	public Singleton<Element> getClone() {
		return this;
	}
	
	@Override
	@TypeInfo("ceylon.language.Nothing|Element")
	public Element item(@Name("index") Integer key) {
		return key.longValue()==0 ? element : null;
	}
	
	@Override
	public Category getKeys() {
		return Correspondence$impl.getKeys(this);
	}
	
	@Override
	public boolean definesEvery(@Sequenced @Name("keys") 
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<ceylon.language.Integer>")
	Iterable<? extends Integer> keys) {
		return Correspondence$impl.definesEvery(this, keys);
	}
	
	@Override
	public boolean definesAny(@Sequenced @Name("keys") 
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<ceylon.language.Integer>")
	Iterable<? extends Integer> keys) {
		return Correspondence$impl.definesAny(this, keys);
	}
	
	@Override
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<Element|ceylon.language.Nothing>")
	public Iterable<? extends Element> items(@Sequenced @Name("keys") 
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<ceylon.language.Integer>")
	Iterable<? extends Integer> keys) {
		return Correspondence$impl.items(this, keys);
	}
	
	@Override
	@TypeInfo("ceylon.language.Integer")
	public long getLastIndex() {
		return 0;
	}
	
	@Override
	public Element getFirst() {
		return element;
	}
	
	@Override
	public Empty getRest() {
		return $empty.getEmpty();
	}
	
	@Override
	public boolean getEmpty() {
		return false;
	}
	
	@Override
	@TypeInfo("ceylon.language.Integer")
	public long getSize() {
		return 1;
	}
	
	@Override
	public Element getLast() {
		return element;
	}
	
	@Override
	public boolean defines(@Name("index") Integer index) {
		return index.longValue()==0;
	}
	@Override
	public Iterator<Element> getIterator() {
		return new Iterator<Element>() {
			@Override
			public Element getHead() {
				return element;
			}
			@Override
			public Iterator<Element> getTail() {
				return null;
			}
			@Override
			public java.lang.String toString() {
				return "Iterator for " + Singleton.this.toString();
			}
		};
	}

    @Override
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<Element>")
    public Iterable<? extends Element> segment(@Name("from") Integer from, 
    		@Name("length") Integer length) {
    	if (from.longValue()>0||length.longValue()==0) 
    		return $empty.getEmpty();
    	return this;
    }
    
    @Override
    @TypeInfo("ceylon.language.Empty|ceylon.language.Sequence<Element>")
    public Iterable<? extends Element> span(@Name("from") Integer from, 
    		@TypeInfo("ceylon.language.Nothing|ceylon.language.Integer")
    		@Name("to") Integer to) {
    	//if (to==null) to = Integer.instance(0);
    	if (from.longValue()>0) 
    		return $empty.getEmpty();
    	return this;
    }

	@Override
	public java.lang.String toString() {
		return Sequence$impl.toString(this);
	}
	
}
