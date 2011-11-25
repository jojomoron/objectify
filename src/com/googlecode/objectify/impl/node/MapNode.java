package com.googlecode.objectify.impl.node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.googlecode.objectify.impl.Path;

/**
 */
public class MapNode extends EntityNode
{
	/** */
	Map<String, EntityNode> map;
	
	/** Because null is a legitimate value, we need to know if there is really a property value here */
	boolean hasProp;
	public boolean hasPropertyValue() { return this.hasProp; }
	
	/**
	 * A property value could appear at any point in the entity tree.  This is because we might attach
	 * denormalized data for the value at some point, and that data will likely be in a map underneath.
	 */
	Object propertyValue;
	public Object getPropertyValue() { return this.propertyValue; }
	
	/** */
	public void setPropertyValue(Object value) {
		this.propertyValue = value;
		this.hasProp = true;
	}

	/** */
	public void setPropertyValue(Object value, boolean index) {
		setPropertyValue(value);
		setPropertyIndexed(index);
	}
	
	/**
	 * Whether or not the property should be indexed on save.  During the load process this is ignored.
	 */
	boolean propertyIndexed;
	public boolean isPropertyIndexed() { return propertyIndexed; }
	public void setPropertyIndexed(boolean value) { propertyIndexed = value; }
	
	/** Constructor for save() operations */
	public MapNode(Path path) {
		super(path);
	}

	/** */
	public EntityNode get(String key) {
		return map().get(key);
	}
	
	/** */
	public void put(String key, EntityNode value) {
		map().put(key, value);
	}
	
	/** */
	public boolean containsKey(String key) {
		return map().containsKey(key);
	}
	
	/** */
	public Set<Map.Entry<String, EntityNode>> entrySet() {
		return map().entrySet();
	}
	
	/** */
	public Collection<EntityNode> values() {
		return map().values();
	}
	
	/** */
	public EntityNode remove(String key) {
		return map().remove(key);
	}
	
	/** Test for empty generating the least amount of garbage possible */
	public boolean isEmpty() {
		if (map == null)
			return true;
		else
			return map.isEmpty();
	}
	
	/** */
	private Map<String, EntityNode> map() {
		if (map == null)
			map = new HashMap<String, EntityNode>();
		
		return map;
	}
	
	/** */
	public MapNode pathMap(String key) {
		MapNode node = (MapNode)map().get(key);
		if (node == null) {
			node = new MapNode(path.extend(key));
			map().put(key, node);
		}
		
		return node;
	}

	/** */
	public ListNode pathList(String key) {
		ListNode node = (ListNode)map().get(key);
		if (node == null) {
			node = new ListNode(path.extend(key));
			map().put(key, node);
		}
		
		return node;
	}
	
	/** */
	@Override
	public String toString() {
		return map().toString();
	}
}
