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
		assertEquals(2, eventType.getBlocks().size());
		
		Block block = eventType.getBlocks().get(0);
		Block block2 = eventType.getBlocks().get(1);
		
		assertEquals("cabecera", block.getName());
		assertEquals(null, block.getRepetition());
		assertEquals("Hola", block.getValue());

		assertEquals("feeds", block2.getName());
		assertEquals(100, block2.getRepetition().intValue());
		assertEquals(null, block2.getValue());

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
		assertEquals(2, eventType.getBlocks().size());
		
		Block channelBlock = eventType.getBlocks().get(0);
		
		assertEquals("channel", channelBlock.getName());
		assertEquals(null, channelBlock.getRepetition());
		assertEquals(null, channelBlock.getValue());
		
		Field id = channelBlock.getFields().get(0);
		assertEquals("id", id.getName());
		assertEquals(false, id.getChooseone());
		assertEquals("false", id.getDependence());
		assertEquals(null, id.getCase());
		assertEquals(false, id.getQuotes());
		assertEquals("Integer", id.getType());
		assertEquals("5186", id.getValue());

		Field name = channelBlock.getFields().get(1);
		assertEquals( false, name.getChooseone());
		assertEquals("false", name.getDependence());
		assertEquals(null, name.getCase());
		assertEquals("name", name.getName());
		assertEquals(false, name.getQuotes());
		assertEquals("String", name.getType());
		assertEquals(Integer.valueOf(10), name.getLength());
		assertEquals("Channel 5186", name.getValue());
		
		Field description = channelBlock.getFields().get(2);
		assertEquals("description", description.getName());
		assertEquals(false, description.getQuotes());
		assertEquals("String", description.getType());
		assertEquals("Generated event type Channel 5186", description.getValue());
		
		Field latitude = channelBlock.getFields().get(3);
		assertEquals("latitude", latitude.getName());
		assertEquals(false, latitude.getQuotes());
		assertEquals("Float", latitude.getType());
		assertEquals("47.528936", latitude.getValue());
		
		Field longitude = channelBlock.getFields().get(4);
		assertEquals("longitude", longitude.getName());
		assertEquals(false, longitude.getQuotes());
		assertEquals("Float", longitude.getType());
		assertEquals("10.257247", longitude.getValue());
		
		Field field1 = channelBlock.getFields().get(5);
		assertEquals("field1", field1.getName());
		assertEquals(false, field1.getQuotes());
		assertEquals("Boolean", field1.getType());
		assertEquals(true, field1.getIsNumeric());
		
		Field field2 = channelBlock.getFields().get(6);
		assertEquals("field2", field2.getName());
		assertEquals(false, field2.getQuotes());
		assertEquals("Alphanumeric", field2.getType());
		assertEquals("F", field2.getEndcharacter());
		
		Field field3 = channelBlock.getFields().get(7);
		assertEquals("field3", field3.getName());
		assertEquals(false, field3.getQuotes());
		assertEquals("Date", field3.getType());
		assertEquals("yyyy-MM-DD", field3.getFormat());
		
		Field createdAt = channelBlock.getFields().get(8);
		assertEquals("created_at", createdAt.getName());
		assertEquals(false, createdAt.getQuotes());
		assertEquals("String", createdAt.getType());
		assertEquals("2013-04-04T12:12:05Z", createdAt.getValue());
		
		Field updatedAt = channelBlock.getFields().get(9);
		assertEquals("updated_at", updatedAt.getName());
		assertEquals(false, updatedAt.getQuotes());
		assertEquals("String", updatedAt.getType());
		assertEquals("2016-04-19T07:39:07Z", updatedAt.getValue());
		
		Field lastEntryId = channelBlock.getFields().get(10);
		assertEquals("last_entry_id", lastEntryId.getName());
		assertEquals(false, lastEntryId.getQuotes());
		assertEquals("Integer", lastEntryId.getType());
		assertEquals("1201242", lastEntryId.getValue());
		
		Block feedsBlock = eventType.getBlocks().get(1);

		assertEquals("feeds", feedsBlock.getName());
		assertEquals(Integer.valueOf(500), feedsBlock.getRepetition());
		assertEquals(null, feedsBlock.getValue());
		
		Field createAt = feedsBlock.getFields().get(0);
		assertEquals("create_at", createAt.getName());
		assertEquals(false, createAt.getQuotes());
		assertEquals("ComplexType", createAt.getType());
		assertEquals(null, createAt.getValue());
		
		Attribute attr = createAt.getAttributes().get(0);
		assertEquals("Integer", attr.getType());
		assertEquals("2016", attr.getValue());

		attr = createAt.getAttributes().get(1);
		assertEquals("String", attr.getType());
		assertEquals("-", attr.getValue());
		
		attr = createAt.getAttributes().get(2);
		assertEquals("Integer", attr.getType());
		assertEquals("05", attr.getValue());
		
		attr = createAt.getAttributes().get(3);
		assertEquals("String", attr.getType());
		assertEquals("-", attr.getValue());
		
		attr = createAt.getAttributes().get(4);
		assertEquals("Integer", attr.getType());
		assertEquals("14", attr.getValue());
		
		attr = createAt.getAttributes().get(5);
		assertEquals("String", attr.getType());
		assertEquals("T", attr.getValue());
		
		attr = createAt.getAttributes().get(6);
		assertEquals("Time", attr.getType());
		assertEquals("hh:mm:ss", attr.getFormat());
		
		attr = createAt.getAttributes().get(7);
		assertEquals("String", attr.getType());
		assertEquals("Z", attr.getValue());
		
		Field entryId = feedsBlock.getFields().get(1);
		assertEquals("entry_id", entryId.getName());
		assertEquals(false, entryId.getQuotes());
		assertEquals("Integer", entryId.getType());
		assertEquals(Double.valueOf(100000.0), entryId.getMin());
		assertEquals(Double.valueOf(999999.0), entryId.getMax());
		
		field1 = feedsBlock.getFields().get(2);
		assertEquals("field1", field1.getName());
		assertEquals(false, field1.getQuotes());
		assertEquals("Float", field1.getType());
		assertEquals(Double.valueOf(19.0), field1.getMin());
		assertEquals(Double.valueOf(20.0), field1.getMax());
		assertEquals(Integer.valueOf(2), field1.getPrecision());

		field2 = feedsBlock.getFields().get(3);
		assertEquals("field2", field2.getName());
		assertEquals(false, field2.getQuotes());
		assertEquals("Float", field2.getType());
		assertEquals(Double.valueOf(5.0), field2.getMin());
		assertEquals(Double.valueOf(10.0), field2.getMax());
		assertEquals(Integer.valueOf(2), field2.getPrecision());

		field3 = feedsBlock.getFields().get(4);
		assertEquals("field3", field3.getName());
		assertEquals(false, field3.getQuotes());
		assertEquals("Float", field3.getType());
		assertEquals(Double.valueOf(40.0), field3.getMin());
		assertEquals(Double.valueOf(50.0), field3.getMax());
		assertEquals(Integer.valueOf(2), field3.getPrecision());

		OptionalFields optionalFields = feedsBlock.getOptionalFields().get(0);
		field1 = optionalFields.getFields().get(0);
		assertEquals("false", optionalFields.getMandatory());
		assertEquals("optionalField1", field1.getName());
		assertEquals(false, field1.getQuotes());
		assertEquals("Float", field1.getType());
		assertEquals(Double.valueOf(19.0), field1.getMin());
		assertEquals(Double.valueOf(20.0), field1.getMax());
		assertEquals(Integer.valueOf(2), field1.getPrecision());	
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
		assertEquals(1, eventType.getBlocks().size());
		
		Block test1Block = eventType.getBlocks().get(0);

		assertEquals("test1", test1Block.getName());
		assertEquals(Integer.valueOf(500), test1Block.getRepetition());
		assertEquals(null, test1Block.getValue());
		
		Field test1Field = test1Block.getFields().get(0);
		assertEquals("test1", test1Field.getName());
		assertEquals(false, test1Field.getQuotes());
		assertEquals("ComplexType", test1Field.getType());
		assertEquals(null, test1Field.getValue());
		
		Attribute attr = test1Field.getAttributes().get(0);
		assertEquals("Boolean", attr.getType());
		assertEquals(true, attr.getIsNumeric());
		
		attr = test1Field.getAttributes().get(1);
		assertEquals("Float", attr.getType());
		assertEquals(Integer.valueOf(2), attr.getPrecision());
		
		attr = test1Field.getAttributes().get(2);
		assertEquals("String", attr.getType());
		assertEquals(Integer.valueOf(10), attr.getLength());
		assertEquals("low", attr.getCase());

		attr = test1Field.getAttributes().get(3);
		assertEquals("Integer", attr.getType());
		assertEquals(Double.valueOf(10), attr.getMin());
		assertEquals(Double.valueOf(20), attr.getMax());
		
		attr = test1Field.getAttributes().get(4);
		assertEquals("Alphanumeric", attr.getType());
		assertEquals(Integer.valueOf(10), attr.getLength());
		assertEquals("F", attr.getEndcharacter());
		assertEquals(null, attr.getCase());
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
		assertEquals(1, eventType.getBlocks().size());
		
		Block test1Block = eventType.getBlocks().get(0);

		assertEquals("test1", test1Block.getName());
		assertEquals(Integer.valueOf(500), test1Block.getRepetition());
		assertEquals(null, test1Block.getValue());
		
		Field test1Field = test1Block.getFields().get(0);
		assertEquals("test1", test1Field.getName());
		assertEquals(false, test1Field.getQuotes());
		assertEquals("ComplexType", test1Field.getType());
		assertEquals(null, test1Field.getValue());
		
		Field testSubField = test1Field.getFields().get(0);
		assertEquals("String", testSubField.getType());
		assertEquals(Integer.valueOf(4), testSubField.getLength());
	}


}
