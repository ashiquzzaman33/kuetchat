package com.n.utility;
public class ChangedProperty<T1, T2> {
	private T1 property;
	private T2 oldValue;
	private T2 newValue;

	public ChangedProperty(T1 property, T2 oldValue, T2 newValue) {
		this.property = property;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public T2 getOldValue(T1 property) {
		if (this.property.equals(property)) {
			return this.oldValue;
		}
		return null;
	}

	public T2 getNewValue(T1 property) {
		if (this.property.equals(property)) {
			return newValue;
		}
		return null;
	}

	public boolean isChange(T1 property) {
		if (this.property == property)
			return true;
		return false;
	}

}
