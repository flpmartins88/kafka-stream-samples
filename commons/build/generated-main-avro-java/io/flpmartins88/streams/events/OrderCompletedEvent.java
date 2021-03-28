/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.flpmartins88.streams.events;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class OrderCompletedEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 6781842287445728993L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OrderCompletedEvent\",\"namespace\":\"io.flpmartins88.streams.events\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"customer\",\"type\":{\"type\":\"record\",\"name\":\"CustomerEvent\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}},{\"name\":\"items\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"OrderItemEvent\",\"fields\":[{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"quantity\",\"type\":\"long\"},{\"name\":\"unit_amount\",\"type\":\"long\"}]}}},{\"name\":\"paymentStatus\",\"type\":{\"type\":\"enum\",\"name\":\"PaymentStatus\",\"symbols\":[\"PAID\",\"DECLINED\"]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<OrderCompletedEvent> ENCODER =
      new BinaryMessageEncoder<OrderCompletedEvent>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OrderCompletedEvent> DECODER =
      new BinaryMessageDecoder<OrderCompletedEvent>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<OrderCompletedEvent> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<OrderCompletedEvent> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<OrderCompletedEvent> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<OrderCompletedEvent>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this OrderCompletedEvent to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a OrderCompletedEvent from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a OrderCompletedEvent instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static OrderCompletedEvent fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String id;
   private io.flpmartins88.streams.events.CustomerEvent customer;
   private java.util.List<io.flpmartins88.streams.events.OrderItemEvent> items;
   private io.flpmartins88.streams.events.PaymentStatus paymentStatus;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OrderCompletedEvent() {}

  /**
   * All-args constructor.
   * @param id The new value for id
   * @param customer The new value for customer
   * @param items The new value for items
   * @param paymentStatus The new value for paymentStatus
   */
  public OrderCompletedEvent(java.lang.String id, io.flpmartins88.streams.events.CustomerEvent customer, java.util.List<io.flpmartins88.streams.events.OrderItemEvent> items, io.flpmartins88.streams.events.PaymentStatus paymentStatus) {
    this.id = id;
    this.customer = customer;
    this.items = items;
    this.paymentStatus = paymentStatus;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return id;
    case 1: return customer;
    case 2: return items;
    case 3: return paymentStatus;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: id = value$ != null ? value$.toString() : null; break;
    case 1: customer = (io.flpmartins88.streams.events.CustomerEvent)value$; break;
    case 2: items = (java.util.List<io.flpmartins88.streams.events.OrderItemEvent>)value$; break;
    case 3: paymentStatus = (io.flpmartins88.streams.events.PaymentStatus)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.String getId() {
    return id;
  }



  /**
   * Gets the value of the 'customer' field.
   * @return The value of the 'customer' field.
   */
  public io.flpmartins88.streams.events.CustomerEvent getCustomer() {
    return customer;
  }



  /**
   * Gets the value of the 'items' field.
   * @return The value of the 'items' field.
   */
  public java.util.List<io.flpmartins88.streams.events.OrderItemEvent> getItems() {
    return items;
  }



  /**
   * Gets the value of the 'paymentStatus' field.
   * @return The value of the 'paymentStatus' field.
   */
  public io.flpmartins88.streams.events.PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }



  /**
   * Creates a new OrderCompletedEvent RecordBuilder.
   * @return A new OrderCompletedEvent RecordBuilder
   */
  public static io.flpmartins88.streams.events.OrderCompletedEvent.Builder newBuilder() {
    return new io.flpmartins88.streams.events.OrderCompletedEvent.Builder();
  }

  /**
   * Creates a new OrderCompletedEvent RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OrderCompletedEvent RecordBuilder
   */
  public static io.flpmartins88.streams.events.OrderCompletedEvent.Builder newBuilder(io.flpmartins88.streams.events.OrderCompletedEvent.Builder other) {
    if (other == null) {
      return new io.flpmartins88.streams.events.OrderCompletedEvent.Builder();
    } else {
      return new io.flpmartins88.streams.events.OrderCompletedEvent.Builder(other);
    }
  }

  /**
   * Creates a new OrderCompletedEvent RecordBuilder by copying an existing OrderCompletedEvent instance.
   * @param other The existing instance to copy.
   * @return A new OrderCompletedEvent RecordBuilder
   */
  public static io.flpmartins88.streams.events.OrderCompletedEvent.Builder newBuilder(io.flpmartins88.streams.events.OrderCompletedEvent other) {
    if (other == null) {
      return new io.flpmartins88.streams.events.OrderCompletedEvent.Builder();
    } else {
      return new io.flpmartins88.streams.events.OrderCompletedEvent.Builder(other);
    }
  }

  /**
   * RecordBuilder for OrderCompletedEvent instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OrderCompletedEvent>
    implements org.apache.avro.data.RecordBuilder<OrderCompletedEvent> {

    private java.lang.String id;
    private io.flpmartins88.streams.events.CustomerEvent customer;
    private io.flpmartins88.streams.events.CustomerEvent.Builder customerBuilder;
    private java.util.List<io.flpmartins88.streams.events.OrderItemEvent> items;
    private io.flpmartins88.streams.events.PaymentStatus paymentStatus;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.flpmartins88.streams.events.OrderCompletedEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.customer)) {
        this.customer = data().deepCopy(fields()[1].schema(), other.customer);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (other.hasCustomerBuilder()) {
        this.customerBuilder = io.flpmartins88.streams.events.CustomerEvent.newBuilder(other.getCustomerBuilder());
      }
      if (isValidValue(fields()[2], other.items)) {
        this.items = data().deepCopy(fields()[2].schema(), other.items);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.paymentStatus)) {
        this.paymentStatus = data().deepCopy(fields()[3].schema(), other.paymentStatus);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing OrderCompletedEvent instance
     * @param other The existing instance to copy.
     */
    private Builder(io.flpmartins88.streams.events.OrderCompletedEvent other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.id)) {
        this.id = data().deepCopy(fields()[0].schema(), other.id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.customer)) {
        this.customer = data().deepCopy(fields()[1].schema(), other.customer);
        fieldSetFlags()[1] = true;
      }
      this.customerBuilder = null;
      if (isValidValue(fields()[2], other.items)) {
        this.items = data().deepCopy(fields()[2].schema(), other.items);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.paymentStatus)) {
        this.paymentStatus = data().deepCopy(fields()[3].schema(), other.paymentStatus);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.String getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder setId(java.lang.String value) {
      validate(fields()[0], value);
      this.id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder clearId() {
      id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'customer' field.
      * @return The value.
      */
    public io.flpmartins88.streams.events.CustomerEvent getCustomer() {
      return customer;
    }


    /**
      * Sets the value of the 'customer' field.
      * @param value The value of 'customer'.
      * @return This builder.
      */
    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder setCustomer(io.flpmartins88.streams.events.CustomerEvent value) {
      validate(fields()[1], value);
      this.customerBuilder = null;
      this.customer = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'customer' field has been set.
      * @return True if the 'customer' field has been set, false otherwise.
      */
    public boolean hasCustomer() {
      return fieldSetFlags()[1];
    }

    /**
     * Gets the Builder instance for the 'customer' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public io.flpmartins88.streams.events.CustomerEvent.Builder getCustomerBuilder() {
      if (customerBuilder == null) {
        if (hasCustomer()) {
          setCustomerBuilder(io.flpmartins88.streams.events.CustomerEvent.newBuilder(customer));
        } else {
          setCustomerBuilder(io.flpmartins88.streams.events.CustomerEvent.newBuilder());
        }
      }
      return customerBuilder;
    }

    /**
     * Sets the Builder instance for the 'customer' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */

    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder setCustomerBuilder(io.flpmartins88.streams.events.CustomerEvent.Builder value) {
      clearCustomer();
      customerBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'customer' field has an active Builder instance
     * @return True if the 'customer' field has an active Builder instance
     */
    public boolean hasCustomerBuilder() {
      return customerBuilder != null;
    }

    /**
      * Clears the value of the 'customer' field.
      * @return This builder.
      */
    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder clearCustomer() {
      customer = null;
      customerBuilder = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'items' field.
      * @return The value.
      */
    public java.util.List<io.flpmartins88.streams.events.OrderItemEvent> getItems() {
      return items;
    }


    /**
      * Sets the value of the 'items' field.
      * @param value The value of 'items'.
      * @return This builder.
      */
    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder setItems(java.util.List<io.flpmartins88.streams.events.OrderItemEvent> value) {
      validate(fields()[2], value);
      this.items = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'items' field has been set.
      * @return True if the 'items' field has been set, false otherwise.
      */
    public boolean hasItems() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'items' field.
      * @return This builder.
      */
    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder clearItems() {
      items = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'paymentStatus' field.
      * @return The value.
      */
    public io.flpmartins88.streams.events.PaymentStatus getPaymentStatus() {
      return paymentStatus;
    }


    /**
      * Sets the value of the 'paymentStatus' field.
      * @param value The value of 'paymentStatus'.
      * @return This builder.
      */
    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder setPaymentStatus(io.flpmartins88.streams.events.PaymentStatus value) {
      validate(fields()[3], value);
      this.paymentStatus = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'paymentStatus' field has been set.
      * @return True if the 'paymentStatus' field has been set, false otherwise.
      */
    public boolean hasPaymentStatus() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'paymentStatus' field.
      * @return This builder.
      */
    public io.flpmartins88.streams.events.OrderCompletedEvent.Builder clearPaymentStatus() {
      paymentStatus = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OrderCompletedEvent build() {
      try {
        OrderCompletedEvent record = new OrderCompletedEvent();
        record.id = fieldSetFlags()[0] ? this.id : (java.lang.String) defaultValue(fields()[0]);
        if (customerBuilder != null) {
          try {
            record.customer = this.customerBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("customer"));
            throw e;
          }
        } else {
          record.customer = fieldSetFlags()[1] ? this.customer : (io.flpmartins88.streams.events.CustomerEvent) defaultValue(fields()[1]);
        }
        record.items = fieldSetFlags()[2] ? this.items : (java.util.List<io.flpmartins88.streams.events.OrderItemEvent>) defaultValue(fields()[2]);
        record.paymentStatus = fieldSetFlags()[3] ? this.paymentStatus : (io.flpmartins88.streams.events.PaymentStatus) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OrderCompletedEvent>
    WRITER$ = (org.apache.avro.io.DatumWriter<OrderCompletedEvent>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OrderCompletedEvent>
    READER$ = (org.apache.avro.io.DatumReader<OrderCompletedEvent>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.id);

    this.customer.customEncode(out);

    long size0 = this.items.size();
    out.writeArrayStart();
    out.setItemCount(size0);
    long actualSize0 = 0;
    for (io.flpmartins88.streams.events.OrderItemEvent e0: this.items) {
      actualSize0++;
      out.startItem();
      e0.customEncode(out);
    }
    out.writeArrayEnd();
    if (actualSize0 != size0)
      throw new java.util.ConcurrentModificationException("Array-size written was " + size0 + ", but element count was " + actualSize0 + ".");

    out.writeEnum(this.paymentStatus.ordinal());

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.id = in.readString();

      if (this.customer == null) {
        this.customer = new io.flpmartins88.streams.events.CustomerEvent();
      }
      this.customer.customDecode(in);

      long size0 = in.readArrayStart();
      java.util.List<io.flpmartins88.streams.events.OrderItemEvent> a0 = this.items;
      if (a0 == null) {
        a0 = new SpecificData.Array<io.flpmartins88.streams.events.OrderItemEvent>((int)size0, SCHEMA$.getField("items").schema());
        this.items = a0;
      } else a0.clear();
      SpecificData.Array<io.flpmartins88.streams.events.OrderItemEvent> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<io.flpmartins88.streams.events.OrderItemEvent>)a0 : null);
      for ( ; 0 < size0; size0 = in.arrayNext()) {
        for ( ; size0 != 0; size0--) {
          io.flpmartins88.streams.events.OrderItemEvent e0 = (ga0 != null ? ga0.peek() : null);
          if (e0 == null) {
            e0 = new io.flpmartins88.streams.events.OrderItemEvent();
          }
          e0.customDecode(in);
          a0.add(e0);
        }
      }

      this.paymentStatus = io.flpmartins88.streams.events.PaymentStatus.values()[in.readEnum()];

    } else {
      for (int i = 0; i < 4; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.id = in.readString();
          break;

        case 1:
          if (this.customer == null) {
            this.customer = new io.flpmartins88.streams.events.CustomerEvent();
          }
          this.customer.customDecode(in);
          break;

        case 2:
          long size0 = in.readArrayStart();
          java.util.List<io.flpmartins88.streams.events.OrderItemEvent> a0 = this.items;
          if (a0 == null) {
            a0 = new SpecificData.Array<io.flpmartins88.streams.events.OrderItemEvent>((int)size0, SCHEMA$.getField("items").schema());
            this.items = a0;
          } else a0.clear();
          SpecificData.Array<io.flpmartins88.streams.events.OrderItemEvent> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<io.flpmartins88.streams.events.OrderItemEvent>)a0 : null);
          for ( ; 0 < size0; size0 = in.arrayNext()) {
            for ( ; size0 != 0; size0--) {
              io.flpmartins88.streams.events.OrderItemEvent e0 = (ga0 != null ? ga0.peek() : null);
              if (e0 == null) {
                e0 = new io.flpmartins88.streams.events.OrderItemEvent();
              }
              e0.customDecode(in);
              a0.add(e0);
            }
          }
          break;

        case 3:
          this.paymentStatus = io.flpmartins88.streams.events.PaymentStatus.values()[in.readEnum()];
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










