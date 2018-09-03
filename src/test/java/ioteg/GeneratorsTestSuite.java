package ioteg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  IntegerGeneratorTestCase.class,
  StringGeneratorTestCase.class,
  BooleanGeneratorTestCase.class,
})
public class GeneratorsTestSuite {

}
