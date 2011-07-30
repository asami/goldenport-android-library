package org.goldenport.android.feed;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * @since   Jun.  4, 2011
 * @version Jul. 27, 2011
 * @author  ASAMI, Tomoharu
 */
public class GASchemaField implements Parcelable {
    public final String name;
    public final Class<?> datatype; // XXX GDatatype
    public final GAMultiplicity multiplicity;
    public final List<GAConstraint> constraints;
    public final List<GAFacet> facets;
    public final Map<String, Object> properties;

    public GASchemaField(
            String name,
            Class<?> datatype,
            GAMultiplicity multiplicity,
            List<GAConstraint> constraints,
            List<GAFacet> facets,
            Map<String, Object> properties) {
        this.name = name;
        this.datatype = datatype;
        this.multiplicity = multiplicity;
        this.constraints = Collections.unmodifiableList(constraints);
        this.facets = Collections.unmodifiableList(facets);
        this.properties = Collections.unmodifiableMap(properties);
    }

    public GASchemaField(Parcel in) {
        name = in.readString();
        datatype = _read_datatype(in);
        multiplicity = _read_multiplicity(in);
        constraints = in.readArrayList(getClass().getClassLoader());
        facets = in.readArrayList(getClass().getClassLoader());
        properties = in.readHashMap(getClass().getClassLoader());
    }

    private Class<?> _read_datatype(Parcel in) {
        // TODO Auto-generated method stub
        return null;
    }

    private GAMultiplicity _read_multiplicity(Parcel in) {
        // TODO Auto-generated method stub
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        _write_datatype(dest, flags);
        _write_multiplicity(dest, flags);
        dest.writeList(constraints);
        dest.writeList(facets);
        dest.writeMap(properties);
    }

    private void _write_datatype(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        
    }

    private void _write_multiplicity(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        
    }

    public static final Parcelable.Creator<GASchemaField> CREATOR = new Parcelable.Creator<GASchemaField>() {  
        public GASchemaField createFromParcel(Parcel in) {  
            return new GASchemaField(in);
        }

        public GASchemaField[] newArray(int size) {  
            return new GASchemaField[size];  
        }  
    };
}
