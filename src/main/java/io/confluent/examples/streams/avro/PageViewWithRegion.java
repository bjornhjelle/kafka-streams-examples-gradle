/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.confluent.examples.streams.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class PageViewWithRegion extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -4589440717703753993L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PageViewWithRegion\",\"namespace\":\"io.confluent.examples.streams.avro\",\"fields\":[{\"name\":\"user\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"page\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"region\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<PageViewWithRegion> ENCODER =
      new BinaryMessageEncoder<PageViewWithRegion>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<PageViewWithRegion> DECODER =
      new BinaryMessageDecoder<PageViewWithRegion>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<PageViewWithRegion> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<PageViewWithRegion> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<PageViewWithRegion>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this PageViewWithRegion to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a PageViewWithRegion from a ByteBuffer. */
  public static PageViewWithRegion fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String user;
  @Deprecated public java.lang.String page;
  @Deprecated public java.lang.String region;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PageViewWithRegion() {}

  /**
   * All-args constructor.
   * @param user The new value for user
   * @param page The new value for page
   * @param region The new value for region
   */
  public PageViewWithRegion(java.lang.String user, java.lang.String page, java.lang.String region) {
    this.user = user;
    this.page = page;
    this.region = region;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return user;
    case 1: return page;
    case 2: return region;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: user = (java.lang.String)value$; break;
    case 1: page = (java.lang.String)value$; break;
    case 2: region = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'user' field.
   * @return The value of the 'user' field.
   */
  public java.lang.String getUser() {
    return user;
  }

  /**
   * Sets the value of the 'user' field.
   * @param value the value to set.
   */
  public void setUser(java.lang.String value) {
    this.user = value;
  }

  /**
   * Gets the value of the 'page' field.
   * @return The value of the 'page' field.
   */
  public java.lang.String getPage() {
    return page;
  }

  /**
   * Sets the value of the 'page' field.
   * @param value the value to set.
   */
  public void setPage(java.lang.String value) {
    this.page = value;
  }

  /**
   * Gets the value of the 'region' field.
   * @return The value of the 'region' field.
   */
  public java.lang.String getRegion() {
    return region;
  }

  /**
   * Sets the value of the 'region' field.
   * @param value the value to set.
   */
  public void setRegion(java.lang.String value) {
    this.region = value;
  }

  /**
   * Creates a new PageViewWithRegion RecordBuilder.
   * @return A new PageViewWithRegion RecordBuilder
   */
  public static io.confluent.examples.streams.avro.PageViewWithRegion.Builder newBuilder() {
    return new io.confluent.examples.streams.avro.PageViewWithRegion.Builder();
  }

  /**
   * Creates a new PageViewWithRegion RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new PageViewWithRegion RecordBuilder
   */
  public static io.confluent.examples.streams.avro.PageViewWithRegion.Builder newBuilder(io.confluent.examples.streams.avro.PageViewWithRegion.Builder other) {
    return new io.confluent.examples.streams.avro.PageViewWithRegion.Builder(other);
  }

  /**
   * Creates a new PageViewWithRegion RecordBuilder by copying an existing PageViewWithRegion instance.
   * @param other The existing instance to copy.
   * @return A new PageViewWithRegion RecordBuilder
   */
  public static io.confluent.examples.streams.avro.PageViewWithRegion.Builder newBuilder(io.confluent.examples.streams.avro.PageViewWithRegion other) {
    return new io.confluent.examples.streams.avro.PageViewWithRegion.Builder(other);
  }

  /**
   * RecordBuilder for PageViewWithRegion instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PageViewWithRegion>
    implements org.apache.avro.data.RecordBuilder<PageViewWithRegion> {

    private java.lang.String user;
    private java.lang.String page;
    private java.lang.String region;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.confluent.examples.streams.avro.PageViewWithRegion.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.user)) {
        this.user = data().deepCopy(fields()[0].schema(), other.user);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.page)) {
        this.page = data().deepCopy(fields()[1].schema(), other.page);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.region)) {
        this.region = data().deepCopy(fields()[2].schema(), other.region);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing PageViewWithRegion instance
     * @param other The existing instance to copy.
     */
    private Builder(io.confluent.examples.streams.avro.PageViewWithRegion other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.user)) {
        this.user = data().deepCopy(fields()[0].schema(), other.user);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.page)) {
        this.page = data().deepCopy(fields()[1].schema(), other.page);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.region)) {
        this.region = data().deepCopy(fields()[2].schema(), other.region);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'user' field.
      * @return The value.
      */
    public java.lang.String getUser() {
      return user;
    }

    /**
      * Sets the value of the 'user' field.
      * @param value The value of 'user'.
      * @return This builder.
      */
    public io.confluent.examples.streams.avro.PageViewWithRegion.Builder setUser(java.lang.String value) {
      validate(fields()[0], value);
      this.user = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'user' field has been set.
      * @return True if the 'user' field has been set, false otherwise.
      */
    public boolean hasUser() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'user' field.
      * @return This builder.
      */
    public io.confluent.examples.streams.avro.PageViewWithRegion.Builder clearUser() {
      user = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'page' field.
      * @return The value.
      */
    public java.lang.String getPage() {
      return page;
    }

    /**
      * Sets the value of the 'page' field.
      * @param value The value of 'page'.
      * @return This builder.
      */
    public io.confluent.examples.streams.avro.PageViewWithRegion.Builder setPage(java.lang.String value) {
      validate(fields()[1], value);
      this.page = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'page' field has been set.
      * @return True if the 'page' field has been set, false otherwise.
      */
    public boolean hasPage() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'page' field.
      * @return This builder.
      */
    public io.confluent.examples.streams.avro.PageViewWithRegion.Builder clearPage() {
      page = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'region' field.
      * @return The value.
      */
    public java.lang.String getRegion() {
      return region;
    }

    /**
      * Sets the value of the 'region' field.
      * @param value The value of 'region'.
      * @return This builder.
      */
    public io.confluent.examples.streams.avro.PageViewWithRegion.Builder setRegion(java.lang.String value) {
      validate(fields()[2], value);
      this.region = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'region' field has been set.
      * @return True if the 'region' field has been set, false otherwise.
      */
    public boolean hasRegion() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'region' field.
      * @return This builder.
      */
    public io.confluent.examples.streams.avro.PageViewWithRegion.Builder clearRegion() {
      region = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageViewWithRegion build() {
      try {
        PageViewWithRegion record = new PageViewWithRegion();
        record.user = fieldSetFlags()[0] ? this.user : (java.lang.String) defaultValue(fields()[0]);
        record.page = fieldSetFlags()[1] ? this.page : (java.lang.String) defaultValue(fields()[1]);
        record.region = fieldSetFlags()[2] ? this.region : (java.lang.String) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<PageViewWithRegion>
    WRITER$ = (org.apache.avro.io.DatumWriter<PageViewWithRegion>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<PageViewWithRegion>
    READER$ = (org.apache.avro.io.DatumReader<PageViewWithRegion>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
