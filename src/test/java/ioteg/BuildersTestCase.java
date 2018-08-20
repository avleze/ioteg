package ioteg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;

import com.ioteg.builders.EventTypeBuilder;
import com.ioteg.model.Attribute;
import com.ioteg.model.Block;
import com.ioteg.model.EventType;
import com.ioteg.model.Field;
import com.ioteg.model.OptionalFields;

public class BuildersTestCase {

	@Test
	public void testEventType1() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		ClassLoader classLoader = getClass().getClassLoader();
		File xmlFile = new File(classLoader.getResource("testEventType1.xml").getFile());
		Document document = builder.build(xmlFile);
		EventType eventType = eventTypeBuilder.build(document);
		
		assertFalse(eventType.getBlocks().isEmpty());
		assertEquals(eventType.getBlocks().size(), 2);
		
		Block block = eventType.getBlocks().get(0);
		Block block2 = eventType.getBlocks().get(1);
		
		assertEquals(block.getName(), "cabecera");
		assertEquals(block.getRepetition(), null);
		assertEquals(block.getValue(), "Hola");

		assertEquals(block2.getName(), "feeds");
		assertEquals(block2.getRepetition().intValue(), 100);
		assertEquals(block2.getValue(), null);

	}
	
	@Test
	public void testEventType2() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		ClassLoader classLoader = getClass().getClassLoader();
		File xmlFile = new File(classLoader.getResource("testEventType2.xml").getFile());
		Document document = builder.build(xmlFile);
		EventType eventType = eventTypeBuilder.build(document);
		
		assertFalse(eventType.getBlocks().isEmpty());
		assertEquals(eventType.getBlocks().size(), 2);
		
		Block channelBlock = eventType.getBlocks().get(0);
		
		assertEquals(channelBlock.getName(), "channel");
		assertEquals(channelBlock.getRepetition(), null);
		assertEquals(channelBlock.getValue(), null);
		
		Field id = channelBlock.getFields().get(0);
		assertEquals(id.getName(), "id");
		assertEquals(id.getChooseone(), false);
		assertEquals(id.getDependence(), "false");
		assertEquals(id.getCase(), null);
		assertEquals(id.getQuotes(), false);
		assertEquals(id.getType(), "Integer");
		assertEquals(id.getValue(), "5186");

		Field name = channelBlock.getFields().get(1);
		assertEquals(name.getChooseone(), false);
		assertEquals(name.getDependence(), "false");
		assertEquals(name.getCase(), null);
		assertEquals(name.getName(), "name");
		assertEquals(name.getQuotes(), false);
		assertEquals(name.getType(), "String");
		assertEquals(name.getLength().intValue(), 10);
		assertEquals(name.getValue(), "Channel 5186");
		
		Field description = channelBlock.getFields().get(2);
		assertEquals(description.getName(), "description");
		assertEquals(description.getQuotes(), false);
		assertEquals(description.getType(), "String");
		assertEquals(description.getValue(), "Generated event type Channel 5186");
		
		Field latitude = channelBlock.getFields().get(3);
		assertEquals(latitude.getName(), "latitude");
		assertEquals(latitude.getQuotes(), false);
		assertEquals(latitude.getType(), "Float");
		assertEquals(latitude.getValue(), "47.528936");
		
		Field longitude = channelBlock.getFields().get(4);
		assertEquals(longitude.getName(), "longitude");
		assertEquals(longitude.getQuotes(), false);
		assertEquals(longitude.getType(), "Float");
		assertEquals(longitude.getValue(), "10.257247");
		
		Field field1 = channelBlock.getFields().get(5);
		assertEquals(field1.getName(), "field1");
		assertEquals(field1.getQuotes(), false);
		assertEquals(field1.getType(), "Boolean");
		assertEquals(field1.getIsNumeric(), true);
		
		Field field2 = channelBlock.getFields().get(6);
		assertEquals(field2.getName(), "field2");
		assertEquals(field2.getQuotes(), false);
		assertEquals(field2.getType(), "Alphanumeric");
		assertEquals(field2.getEndcharacter(), "F");
		
		Field field3 = channelBlock.getFields().get(7);
		assertEquals(field3.getName(), "field3");
		assertEquals(field3.getQuotes(), false);
		assertEquals(field3.getType(), "Date");
		assertEquals(field3.getFormat(), "yyyy-MM-DD");
		
		Field createdAt = channelBlock.getFields().get(8);
		assertEquals(createdAt.getName(), "created_at");
		assertEquals(createdAt.getQuotes(), false);
		assertEquals(createdAt.getType(), "String");
		assertEquals(createdAt.getValue(), "2013-04-04T12:12:05Z");
		
		Field updatedAt = channelBlock.getFields().get(9);
		assertEquals(updatedAt.getName(), "updated_at");
		assertEquals(updatedAt.getQuotes(), false);
		assertEquals(updatedAt.getType(), "String");
		assertEquals(updatedAt.getValue(), "2016-04-19T07:39:07Z");
		
		Field lastEntryId = channelBlock.getFields().get(10);
		assertEquals(lastEntryId.getName(), "last_entry_id");
		assertEquals(lastEntryId.getQuotes(), false);
		assertEquals(lastEntryId.getType(), "Integer");
		assertEquals(lastEntryId.getValue(), "1201242");
		
		Block feedsBlock = eventType.getBlocks().get(1);

		assertEquals(feedsBlock.getName(), "feeds");
		assertEquals(feedsBlock.getRepetition().intValue(), 500);
		assertEquals(feedsBlock.getValue(), null);
		
		Field createAt = feedsBlock.getFields().get(0);
		assertEquals(createAt.getName(), "create_at");
		assertEquals(createAt.getQuotes(), false);
		assertEquals(createAt.getType(), "ComplexType");
		assertEquals(createAt.getValue(), null);
		
		Attribute attr = createAt.getAttributes().get(0);
		assertEquals(attr.getType(), "Integer");
		assertEquals(attr.getValue(), "2016");

		attr = createAt.getAttributes().get(1);
		assertEquals(attr.getType(), "String");
		assertEquals(attr.getValue(), "-");
		
		attr = createAt.getAttributes().get(2);
		assertEquals(attr.getType(), "Integer");
		assertEquals(attr.getValue(), "05");
		
		attr = createAt.getAttributes().get(3);
		assertEquals(attr.getType(), "String");
		assertEquals(attr.getValue(), "-");
		
		attr = createAt.getAttributes().get(4);
		assertEquals(attr.getType(), "Integer");
		assertEquals(attr.getValue(), "14");
		
		attr = createAt.getAttributes().get(5);
		assertEquals(attr.getType(), "String");
		assertEquals(attr.getValue(), "T");
		
		attr = createAt.getAttributes().get(6);
		assertEquals(attr.getType(), "Time");
		assertEquals(attr.getFormat(), "hh:mm:ss");
		
		attr = createAt.getAttributes().get(7);
		assertEquals(attr.getType(), "String");
		assertEquals(attr.getValue(), "Z");
		
		Field entryId = feedsBlock.getFields().get(1);
		assertEquals(entryId.getName(), "entry_id");
		assertEquals(entryId.getQuotes(), false);
		assertEquals(entryId.getType(), "Integer");
		assertEquals(entryId.getMin(), Double.valueOf(100000.0));
		assertEquals(entryId.getMax(), Double.valueOf(999999.0));
		
		field1 = feedsBlock.getFields().get(2);
		assertEquals(field1.getName(), "field1");
		assertEquals(field1.getQuotes(), false);
		assertEquals(field1.getType(), "Float");
		assertEquals(field1.getMin(), Double.valueOf(19.0));
		assertEquals(field1.getMax(), Double.valueOf(20.0));
		assertEquals(field1.getPrecision(), Integer.valueOf(2));

		field2 = feedsBlock.getFields().get(3);
		assertEquals(field2.getName(), "field2");
		assertEquals(field2.getQuotes(), false);
		assertEquals(field2.getType(), "Float");
		assertEquals(field2.getMin(), Double.valueOf(5.0));
		assertEquals(field2.getMax(), Double.valueOf(10.0));
		assertEquals(field2.getPrecision(), Integer.valueOf(2));

		field3 = feedsBlock.getFields().get(4);
		assertEquals(field3.getName(), "field3");
		assertEquals(field3.getQuotes(), false);
		assertEquals(field3.getType(), "Float");
		assertEquals(field3.getMin(), Double.valueOf(40.0));
		assertEquals(field3.getMax(), Double.valueOf(50.0));
		assertEquals(field3.getPrecision(), Integer.valueOf(2));

		OptionalFields optionalFields = feedsBlock.getOptionalFields().get(0);
		field1 = optionalFields.getFields().get(0);
		assertEquals(optionalFields.getMandatory(), "false");
		assertEquals(field1.getName(), "optionalField1");
		assertEquals(field1.getQuotes(), false);
		assertEquals(field1.getType(), "Float");
		assertEquals(field1.getMin(), Double.valueOf(19.0));
		assertEquals(field1.getMax(), Double.valueOf(20.0));
		assertEquals(field1.getPrecision(), Integer.valueOf(2));	
	}
	
	@Test
	public void testAttributes1() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		ClassLoader classLoader = getClass().getClassLoader();
		File xmlFile = new File(classLoader.getResource("testAttributes1.xml").getFile());
		Document document = builder.build(xmlFile);
		EventType eventType = eventTypeBuilder.build(document);
		
		assertFalse(eventType.getBlocks().isEmpty());
		assertEquals(eventType.getBlocks().size(), 1);
		
		Block test1Block = eventType.getBlocks().get(0);

		assertEquals(test1Block.getName(), "test1");
		assertEquals(test1Block.getRepetition().intValue(), 500);
		assertEquals(test1Block.getValue(), null);
		
		Field test1Field = test1Block.getFields().get(0);
		assertEquals(test1Field.getName(), "test1");
		assertEquals(test1Field.getQuotes(), false);
		assertEquals(test1Field.getType(), "ComplexType");
		assertEquals(test1Field.getValue(), null);
		
		Attribute attr = test1Field.getAttributes().get(0);
		assertEquals(attr.getType(), "Boolean");
		assertEquals(attr.getIsNumeric(), true);
		
		attr = test1Field.getAttributes().get(1);
		assertEquals(attr.getType(), "Float");
		assertEquals(attr.getPrecision(), Integer.valueOf(2));
		
		attr = test1Field.getAttributes().get(2);
		assertEquals(attr.getType(), "String");
		assertEquals(attr.getLength(), Integer.valueOf(10));
		assertEquals(attr.getCase(), "low");

		attr = test1Field.getAttributes().get(3);
		assertEquals(attr.getType(), "Integer");
		assertEquals(attr.getMin(), Double.valueOf(10));
		assertEquals(attr.getMax(), Double.valueOf(20));
		
		attr = test1Field.getAttributes().get(4);
		assertEquals(attr.getType(), "Alphanumeric");
		assertEquals(attr.getLength(), Integer.valueOf(10));
		assertEquals(attr.getEndcharacter(), "F");
		assertEquals(attr.getCase(), null);

	}
	
	@Test
	public void testSubFields1() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		EventTypeBuilder eventTypeBuilder = new EventTypeBuilder();
		ClassLoader classLoader = getClass().getClassLoader();
		File xmlFile = new File(classLoader.getResource("testSubFields1.xml").getFile());
		Document document = builder.build(xmlFile);
		EventType eventType = eventTypeBuilder.build(document);
		
		assertFalse(eventType.getBlocks().isEmpty());
		assertEquals(eventType.getBlocks().size(), 1);
		
		Block test1Block = eventType.getBlocks().get(0);

		assertEquals(test1Block.getName(), "test1");
		assertEquals(test1Block.getRepetition().intValue(), 500);
		assertEquals(test1Block.getValue(), null);
		
		Field test1Field = test1Block.getFields().get(0);
		assertEquals(test1Field.getName(), "test1");
		assertEquals(test1Field.getQuotes(), false);
		assertEquals(test1Field.getType(), "ComplexType");
		assertEquals(test1Field.getValue(), null);
		
		Field testSubField = test1Field.getFields().get(0);
		assertEquals(testSubField.getType(), "String");
		assertEquals(testSubField.getLength(), Integer.valueOf(4));
		
	

	}


}
