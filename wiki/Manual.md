= Manual Documentation =
nucleus8583 provides API to read and write from / to ISO-8583 message. Before using read/write operation, you will need to create message factory configuration.

== Message Factory ==
message factory is a set of rules defined how to perform conversion to / from ISO-8583 message. These rules defined in specific XML format:
```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<iso-message encoding="">
	<iso-field id="" length="" type="" align="" pad-with="" empty-value="" />
        ...
</iso-message>
```

  * The ``` encoding ``` attribute means what type of encoding used in conversion. Currently only support ``` ASCII ```
  * ``` iso-message ``` tag can have one or more ``` iso-field ``` tag
  * The ``` iso-field ``` tag used to configure one iso field. So if there are 129 fields in an iso-8583 message, 129 ``` iso-field ``` tags are defined under ``` iso-package ``` tag
  * The ``` id ``` attribute is intended to determine which bit you want to configure. For example, ``` <iso-field id="4" /> ``` means that tag is intended to configure iso field number 4
  * The ``` length ``` attribute is intended to determine how long the field is. For example, ``` <iso-field id="4" length="12" /> ``` means iso field number 4 has 12 character length
  * The ``` type ``` attribute used to determine the type of iso field. For example, ``` <iso-field id="4" length="12" type="n" /> ``` means iso field number 4 has data element type n. Further info about the iso data elements can be found in http://en.wikipedia.org/wiki/ISO_8583#Data_elements. In the 2.2.0 version, nucleus8583 only supports: a, n, s, an, as, ns, ans, b, ., .., and ...
  * The ``` align ``` attribute is used to set how field value is aligned. This attribute has three possible values:
    # ```left```, means value will be left aligned, for example if the field length is 12 but the value set is 3 character length, in the write operation, field value will be 3 characters of value + 9 padding characters. In the read operation, 12 characters field value will be *right trimmed* so the 9 padding characters will be ignored resulting only 3 characters of value.
    # ```right```, means value will be right aligned, for example if the field length is 12 but the value set is 3 character length, in the write operation, field value will be 9 padding characters + 3 characters of value. In the read operation, 12 characters field value will be *left trimmed* so the 9 padding characters will be ignored resulting only 3 characters of value.
    # ```none```, means value will not be aligned, for example if the field length is 12 but the value set is 3 character length, in the write operation, field value will be 3 characters of value + 9 space characters. In the read operation, 12 characters field value will *not be trimmed* so no character will be ignored resulting the 12 characters with no change.
  For example, ``` <iso-field id="4" length="12" align="left" /> ``` means iso field number 4 has 12 character length and left aligned.
  * The ```pad-with``` attribute is used to specify what character will be used in padding operation. This attribute only allows one character of string. The padding operation itself has been explained in ```align``` attribute above.
  * The ```empty-value``` attribute. When a bit value is fully trimmed, it will result empty string value. By specifying this attribute, you can replace the empty string value to any other values you want. For example, you specify ```empty-value="abcd"``` in iso field number 4. If field number 4 is activated and has empty string, the field number 4 will have value ```abcd``` instead of empty string.

Here is the complete sample of codec XML file:
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<iso-message encoding="ASCII">
	<iso-field id="0" length="4" type="custom" align="none" />
	<iso-field id="1" length="32" type="b" />
	<iso-field id="2" length="19" type="custom .." align="none" />
	<iso-field id="3" length="6" type="custom" align="none" />
	<iso-field id="4" length="12" type="custom" align="none" />
	<iso-field id="5" length="12" type="custom" align="none" />
	<iso-field id="6" length="12" type="custom" align="none" />
	<iso-field id="7" length="10" type="custom" align="none" />
	<iso-field id="8" length="8" type="custom" align="none" />
	<iso-field id="9" length="8" type="custom" align="none" />
	<iso-field id="10" length="8" type="custom" align="none" />
	<iso-field id="11" length="6" type="custom" align="none" />
	<iso-field id="12" length="6" type="custom" align="none" />
	<iso-field id="13" length="4" type="custom" align="none" />
	<iso-field id="14" length="4" type="custom" align="none" />
	<iso-field id="15" length="4" type="custom" align="none" />
	<iso-field id="16" length="4" type="custom" align="none" />
	<iso-field id="17" length="4" type="custom" align="none" />
	<iso-field id="18" length="4" type="custom" align="none" />
	<iso-field id="19" length="3" type="custom" align="none" />
	<iso-field id="20" length="3" type="custom" align="none" />
	<iso-field id="21" length="3" type="custom" align="none" />
	<iso-field id="22" length="3" type="custom" align="none" />
	<iso-field id="23" length="3" type="custom" align="none" />
	<iso-field id="24" length="3" type="custom" align="none" />
	<iso-field id="25" length="2" type="custom" align="none" />
	<iso-field id="26" length="2" type="custom" align="none" />
	<iso-field id="27" length="1" type="custom" align="none" />
	<iso-field id="28" length="9" type="custom" align="none" />
	<iso-field id="29" length="9" type="custom" align="none" />
	<iso-field id="30" length="9" type="custom" align="none" />
	<iso-field id="31" length="9" type="custom" align="none" />
	<iso-field id="32" length="11" type="custom .." align="none" />
	<iso-field id="33" length="11" type="custom .." align="none" />
	<iso-field id="34" length="28" type="custom .." align="none" />
	<iso-field id="35" length="37" type="custom .." align="none" />
	<iso-field id="36" length="104" type="custom ..." align="none" />
	<iso-field id="37" length="12" type="custom" align="none" />
	<iso-field id="38" length="6" type="custom" align="none" />
	<iso-field id="39" length="2" type="custom" align="none" />
	<iso-field id="40" length="3" type="custom" align="none" />
	<iso-field id="41" length="8" type="custom" align="none" />
	<iso-field id="42" length="15" type="custom" align="none" />
	<iso-field id="43" length="40" type="custom" align="none" />
	<iso-field id="44" length="25" type="custom .." align="none" />
	<iso-field id="45" length="76" type="custom .." align="none" />
	<iso-field id="46" length="999" type="custom ..." align="none" />
	<iso-field id="47" length="999" type="custom ..." align="none" />
	<iso-field id="48" length="999" type="custom ..." align="none" />
	<iso-field id="49" length="3" type="custom" align="none" />
	<iso-field id="50" length="3" type="custom" align="none" />
	<iso-field id="51" length="3" type="custom" align="none" />
	<iso-field id="52" length="16" type="custom" align="none" />
	<iso-field id="53" length="16" type="custom" align="none" />
	<iso-field id="54" length="120" type="custom ..." align="none" />
	<iso-field id="55" length="999" type="custom ..." align="none" />
	<iso-field id="56" length="999" type="custom ..." align="none" />
	<iso-field id="57" length="999" type="custom ..." align="none" />
	<iso-field id="58" length="999" type="custom ..." align="none" />
	<iso-field id="59" length="999" type="custom ..." align="none" />
	<iso-field id="60" length="999" type="custom ..." align="none" />
	<iso-field id="61" length="999" type="custom ..." align="none" />
	<iso-field id="62" length="999" type="custom ..." align="none" />
	<iso-field id="63" length="999" type="custom ..." align="none" />
	<iso-field id="64" length="8" type="b" />
	<iso-field id="65" length="1" type="b" />
	<iso-field id="66" length="1" type="custom" align="none" />
	<iso-field id="67" length="2" type="custom" align="none" />
	<iso-field id="68" length="3" type="custom" align="none" />
	<iso-field id="69" length="3" type="custom" align="none" />
	<iso-field id="70" length="3" type="custom" align="none" />
	<iso-field id="71" length="4" type="custom" align="none" />
	<iso-field id="72" length="4" type="custom" align="none" />
	<iso-field id="73" length="6" type="custom" align="none" />
	<iso-field id="74" length="10" type="custom" align="none" />
	<iso-field id="75" length="10" type="custom" align="none" />
	<iso-field id="76" length="10" type="custom" align="none" />
	<iso-field id="77" length="10" type="custom" align="none" />
	<iso-field id="78" length="10" type="custom" align="none" />
	<iso-field id="79" length="10" type="custom" align="none" />
	<iso-field id="80" length="10" type="custom" align="none" />
	<iso-field id="81" length="10" type="custom" align="none" />
	<iso-field id="82" length="12" type="custom" align="none" />
	<iso-field id="83" length="12" type="custom" align="none" />
	<iso-field id="84" length="12" type="custom" align="none" />
	<iso-field id="85" length="12" type="custom" align="none" />
	<iso-field id="86" length="16" type="custom" align="none" />
	<iso-field id="87" length="16" type="custom" align="none" />
	<iso-field id="88" length="16" type="custom" align="none" />
	<iso-field id="89" length="16" type="custom" align="none" />
	<iso-field id="90" length="42" type="custom" align="none" />
	<iso-field id="91" length="1" type="custom" align="none" />
	<iso-field id="92" length="2" type="custom" align="none" />
	<iso-field id="93" length="6" type="custom" align="none" />
	<iso-field id="94" length="7" type="custom" align="none" />
	<iso-field id="95" length="42" type="custom" align="none" />
	<iso-field id="96" length="16" type="b" />
	<iso-field id="97" length="17" type="custom" align="none" />
	<iso-field id="98" length="25" type="custom" align="none" />
	<iso-field id="99" length="11" type="custom .." align="none" />
	<iso-field id="100" length="11" type="custom .." align="none" />
	<iso-field id="101" length="17" type="custom .." align="none" />
	<iso-field id="102" length="28" type="custom .." align="none" />
	<iso-field id="103" length="10" type="custom .." align="none" />
	<iso-field id="104" length="100" type="custom ..." align="none" />
	<iso-field id="105" length="999" type="custom ..." align="none" />
	<iso-field id="106" length="999" type="custom ..." align="none" />
	<iso-field id="107" length="999" type="custom ..." align="none" />
	<iso-field id="108" length="999" type="custom ..." align="none" />
	<iso-field id="109" length="999" type="custom ..." align="none" />
	<iso-field id="110" length="999" type="custom ..." align="none" />
	<iso-field id="111" length="999" type="custom ..." align="none" />
	<iso-field id="112" length="999" type="custom ..." align="none" />
	<iso-field id="113" length="999" type="custom ..." align="none" />
	<iso-field id="114" length="999" type="custom ..." align="none" />
	<iso-field id="115" length="999" type="custom ..." align="none" />
	<iso-field id="116" length="999" type="custom ..." align="none" />
	<iso-field id="117" length="999" type="custom ..." align="none" />
	<iso-field id="118" length="999" type="custom ..." align="none" />
	<iso-field id="119" length="999" type="custom ..." align="none" />
	<iso-field id="120" length="999" type="custom ..." align="none" />
	<iso-field id="121" length="999" type="custom ..." align="none" />
	<iso-field id="122" length="999" type="custom ..." align="none" />
	<iso-field id="123" length="999" type="custom ..." align="none" />
	<iso-field id="124" length="999" type="custom ..." align="none" />
	<iso-field id="125" length="999" type="custom ..." align="none" />
	<iso-field id="126" length="999" type="custom ..." align="none" />
	<iso-field id="127" length="999" type="custom ..." align="none" />
	<iso-field id="128" length="8" type="b" />
</iso-message>
```

===Java Code===
Before you can do any operations, you *must* have one instance of ```org.nucleus8583.core.Iso8583MessageFactory``` class, this instance can be safely shared in your Java Application so you can save your memory consumption. To instance the class, you can use class constructor. The class constructor has signatures
  ```public Iso8583MessageFactory(java.io.InputStream);```
  ```public Iso8583MessageFactory(java.lang.String);```

The constructor will read codec XML data from given input stream ```java.io.InputStream``` or given location ```java.lang.String```. For example, if you have codec XML file named nucleus8583.xml placed under root directory project, your code will be
   ```
import java.io.FileInputStream;

import org.nucleus8583.core.Iso8583MessageFactory;

...
        Iso8583MessageFactory factory = new Iso8583MessageFactory(new FileInputStream("nucleus8583.xml"));
...
```
or
   ```
import org.nucleus8583.core.Iso8583MessageFactory;

...
        Iso8583MessageFactory factory = new Iso8583MessageFactory("nucleus8583.xml");
...
```
or
   ```
import org.nucleus8583.core.Iso8583MessageFactory;

...
        Iso8583MessageFactory factory = new Iso8583MessageFactory("file:nucleus8583.xml");
...
```

For another sample if you have codec XML file named nucleus8583.xml under META-INF directory (on classpath), you can use code
   ```
import java.io.FileInputStream;

import org.nucleus8583.core.Iso8583MessageFactory;

...
        Iso8583MessageFactory factory = new Iso8583MessageFactory("classpath:META-INF/nucleus8583.xml");
...
```

===Note===
Every type has its own value for ```align```, ```pad-with```, and ```empty-value``` attributes (except ```custom``` type). If you use so, you can't override their value. If you would like to use custom value, please use ```custom``` type.

This is the value for ```align```, ```pad-with```, and ```empty-value``` attributes:
|| *type* || *align* || *pad-with* || *empty-value* ||
|| a      || left    || `<space>`  || `<empty-string>` ||
|| n      || right   || `0`  || `0` ||
|| s      || left    || `<space>`  || `<empty-string>` ||
|| an     || left    || `<space>`  || `<empty-string>` ||
|| ns     || left    || `<space>`  || `<empty-string>` ||
|| ans    || left    || `<space>`  || `<empty-string>` ||
|| .      || none    || `N/A`  || `<empty-string>` ||
|| ..     || none    || `N/A`  || `<empty-string>` ||
|| ...    || none    || `N/A`  || `<empty-string>` ||


==Creating Message==
Before invoking read/write operation, you need one instance of ```org.nucleus8583.core.Iso8583Message```. You can create the instance by using code
  ```
import org.nucleus8583.core.Iso8583MessageFactory;
import org.nucleus8583.core.Iso8583Message;

...
      // first, create message factory
      Iso8583MessageFactory factory = new Iso8583MessageFactory( ... );

      // then, create new Iso8583Message instance
      Iso8583Message msg = factory.createMessage();
...
```

Once you have an instance of Iso8583Message, you can reuse this instance for further use. To reuse instance, you need to invoke ```void clear()``` method first. *Notice:* this class is *not thread-safe*.

For example:
  ```
import org.nucleus8583.core.Iso8583MessageFactory;
import org.nucleus8583.core.Iso8583Message;

...
      // first, create message factory
      Iso8583MessageFactory factory = new Iso8583MessageFactory( ... );

      // then, create new Iso8583Message instance
      Iso8583Message msg = factory.createMessage();

      // do 1st read operation
      msg.unpack(...)

      // reuse
      msg.clear();

      // do 2nd read operation
      msg.unpack(...)
...
```

Another advantage using nucleus8583 is if you want to read iso-8583 message, set appropriate fields, and write it, you can use same instance without invoking the ```void clear()``` method. You can take a look into below code for clearer view
  ```
import org.nucleus8583.core.Iso8583MessageFactory;
import org.nucleus8583.core.Iso8583Message;

...
      // first, create message factory
      Iso8583MessageFactory factory = new Iso8583MessageFactory( ... );

      // then, create new Iso8583Message instance
      Iso8583Message msg = factory.createMessage();

      // do read operation
      msg.unpack(...);

      // manipulate fields
      msg.setMti(...);
      msg.set(...);
      ...

      // do write operation
      msg.pack(...);
...
```

== Read ==
Read operation means convert ISO-8583 message to Iso8583Message object for further retrieval and manipulation.

The read operation can be done using methods:
  # ```public void unpack(byte[]) throws Exception```
  # ```public void unpack(java.io.InputStream) throws Exception```
  # ```public void unpack(java.io.Reader) throws Exception```

Those 3 methods have same function, to read iso-8583 message from given data storage. The difference only the type of data storage. You can read from byte array, InputStream, or Reader.

*NOTE:* The slowest method is ```public void unpack(byte[]) throws Exception``` and the fastest method is ```public void unpack(java.io.Reader) throws Exception```.

Below is a sample code for read operation from network
```
import java.io.InputStream;
import java.net.Socket;

import org.nucleus8583.core.Iso8583MessageFactory;
import org.nucleus8583.core.Iso8583Message;

public class HelloSocket {
    ...
    public static void main(String[] args) throws Exception {
        Iso8583MessageFactory factory = new Iso8583MessageFactory("classpath:nucleus8583.xml");
        Socket socket = ...;

        InputStream socketIn = socket.getInputStream();
        Iso8583Message msg = factory.createMessage();

        msg.unpack(socketIn);

        System.out.println(msg);
        socket.close();
    }
    ...
}
```

== Write ==
Write operation means convert Iso8583Message object to ISO-8583 message.

The read operation can be done using methods:
  # ```byte[] void pack() throws Exception```
  # ```public void pack(java.io.OutputStream) throws Exception```
  # ```public void pack(java.io.Writer) throws Exception```

Those 3 methods have same function, to write iso-8583 message to given output storage. The difference only the type of output storage. You can write to byte array, OutputStream, or Writer.

*NOTE:* The slowest method is ```public byte[] pack() throws Exception``` and the fastest method is ```public void pack(java.io.Writer) throws Exception```.

Below is a sample code for write operation directly to network
```
import java.io.OutputStream;
import java.net.Socket;

import org.nucleus8583.core.Iso8583MessageFactory;
import org.nucleus8583.core.Iso8583Message;

public class HelloSocket {
    ...
    public static void main(String[] args) throws Exception {
        Iso8583MessageFactory factory = new Iso8583MessageFactory("classpath:nucleus8583.xml");
        Socket socket = ...;

        OutputStream socketOut = socket.getOutputStream();
        Iso8583Message msg = factory.createMessage();

        msg.setMti("0200");
        msg.set(2, "abcer");
        ...

        msg.pack(socketOut);

        System.out.println(msg);
        socket.close();
    }
    ...
}
```
